package com.ymkx.redbook.user.biz.recycle;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.ymkx.redbook.context.holder.LoginUserContextHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MyBatis 删除操作拦截器
 * 拦截所有 DELETE 操作，在删除前将数据记录到回收表
 * @author ymkx
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Order(150)
public class DeleteRecycleInterceptor implements Interceptor {

    private static final String RECYCLE_TABLE = "deleted_record_recycle";

    /**
     * 需要记录的表名集合
     */
    private static final Set<String> MONITORED_TABLES = new HashSet<>(List.of(
            "t_user"
    ));

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String originalSql = boundSql.getSql();

        // 解析 SQL 语句
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(originalSql);
        } catch (Exception e) {
            log.debug("Failed to parse SQL: {}", originalSql);
            return invocation.proceed();
        }

        if (!(statement instanceof Delete deleteStatement)) {
            return invocation.proceed();
        }

        String tableName = deleteStatement.getTable().getName().toLowerCase();

        // 跳过回收表自身的删除操作，避免循环
        if (RECYCLE_TABLE.equals(tableName)) {
            return invocation.proceed();
        }

        // 只记录配置的表
        if (!MONITORED_TABLES.contains(tableName)) {
            return invocation.proceed();
        }

        // 检查 WHERE 条件，无条件不允许删除
        Expression whereClause = deleteStatement.getWhere();
        if (whereClause == null) {
            log.error("DELETE 语句必须包含 WHERE 条件，禁止全表删除: {}", originalSql);
        }

        log.info("拦截到删除语句，表名: {}", tableName);

        // 构建新的 SQL：INSERT INTO recycle + DELETE
        String newSql = buildRecycleSql(deleteStatement, tableName);

        // 替换 SQL
        replaceSql(boundSql, newSql);

        // 复制参数（INSERT 和 DELETE 都需要相同的 WHERE 条件参数）
        duplicateParameters(boundSql);

        log.info("已将删除语句转换为回收站操作");

        return invocation.proceed();
    }

    /**
     * 构建回收站 SQL：INSERT SELECT + DELETE
     */
    private String buildRecycleSql(Delete deleteStatement, String tableName) {
        Expression whereClause = deleteStatement.getWhere();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String deletor = getCurrentUser();
        if (deletor == null) {
            deletor = "system";
        }

        // 获取表信息
        TableInfo tableInfo = getTableInfoByTableName(tableName);

        // 获取主键列名
        String primaryKeyColumn = getPrimaryKeyColumn(tableInfo);

        // 获取表字段列表，构建 JSON
        String jsonSelect = buildJsonSelect(tableInfo);

        StringBuilder sqlBuilder = new StringBuilder();

        // 1. INSERT INTO 回收表
        sqlBuilder.append("INSERT INTO ").append(RECYCLE_TABLE)
                .append(" (source_table, source_id, record_snapshot, deletor, delete_time, create_time) ")
                .append("SELECT ")
                .append("'").append(tableName).append("', ")
                .append(primaryKeyColumn).append(", ")
                .append(jsonSelect).append(", ")
                .append("'").append(escapeSql(deletor)).append("', ")
                .append("'").append(currentTime).append("', ")
                .append("'").append(currentTime).append("' ")
                .append("FROM ").append(tableName)
                .append(" WHERE ").append(whereClause);

        sqlBuilder.append("; ");

        // 2. DELETE 原表数据
        sqlBuilder.append("DELETE FROM ").append(tableName)
                .append(" WHERE ").append(whereClause);

        return sqlBuilder.toString();
    }

    /**
     * 获取主键列名
     */
    private String getPrimaryKeyColumn(TableInfo tableInfo) {
        if (tableInfo != null && tableInfo.getKeyColumn() != null) {
            return tableInfo.getKeyColumn();
        }
        // 默认主键列名
        return "id";
    }

    /**
     * 构建 JSON_OBJECT SELECT 表达式
     */
    private String buildJsonSelect(TableInfo tableInfo) {
        if (tableInfo == null) {
            log.warn("无法获取表字段信息，使用空 JSON");
            return "'{}'";
        }

        List<String> jsonPairs = new ArrayList<>();

        // 添加主键
        if (tableInfo.getKeyColumn() != null) {
            jsonPairs.add("'" + tableInfo.getKeyColumn() + "', " + tableInfo.getKeyColumn());
        }

        // 添加普通字段
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        if (fieldList != null) {
            for (TableFieldInfo field : fieldList) {
                jsonPairs.add("'" + field.getColumn() + "', " + field.getColumn());
            }
        }

        if (jsonPairs.isEmpty()) {
            return "'{}'";
        }

        return "JSON_OBJECT(" + String.join(", ", jsonPairs) + ")";
    }

    /**
     * 通过表名获取 TableInfo
     */
    private TableInfo getTableInfoByTableName(String tableName) {
        try {
            var tableInfos = TableInfoHelper.getTableInfos();
            for (TableInfo tableInfo : tableInfos) {
                if (tableName.equalsIgnoreCase(tableInfo.getTableName())) {
                    return tableInfo;
                }
            }
        } catch (Exception e) {
            log.error("获取 TableInfo 失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 替换 SQL 语句
     */
    private void replaceSql(BoundSql boundSql, String newSql) {
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);
        metaObject.setValue("sql", newSql);
    }

    /**
     * 复制参数，因为 INSERT 和 DELETE 都需要相同的 WHERE 条件参数
     */
    private void duplicateParameters(BoundSql boundSql) {
        try {
            MetaObject metaObject = SystemMetaObject.forObject(boundSql);

            @SuppressWarnings("unchecked")
            List<ParameterMapping> parameterMappings =
                    (List<ParameterMapping>) metaObject.getValue("parameterMappings");

            if (parameterMappings != null && !parameterMappings.isEmpty()) {
                List<ParameterMapping> newParameterMappings = new ArrayList<>(parameterMappings);
                newParameterMappings.addAll(parameterMappings);
                metaObject.setValue("parameterMappings", newParameterMappings);
            }
        } catch (Exception e) {
            log.error("复制参数失败", e);
            throw new RuntimeException("参数复制失败", e);
        }
    }

    /**
     * 获取当前操作人
     */
    private String getCurrentUser() {
        try {
           return LoginUserContextHolder.getUserId();
        } catch (Exception e) {
            log.debug("获取当前用户失败", e);
        }
        return null;
    }

    /**
     * 转义 SQL 字符串
     */
    private String escapeSql(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}