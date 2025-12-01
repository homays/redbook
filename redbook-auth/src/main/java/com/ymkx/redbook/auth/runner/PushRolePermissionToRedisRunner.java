package com.ymkx.redbook.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.ymkx.domain.entity.PermissionDO;
import com.ymkx.domain.entity.RoleDO;
import com.ymkx.domain.entity.RolePermissionRelDO;
import com.ymkx.domain.mapper.PermissionMapper;
import com.ymkx.domain.mapper.RoleMapper;
import com.ymkx.domain.mapper.RolePermissionRelMapper;
import com.ymkx.framework.common.constant.RedisKeyConstants;
import com.ymkx.framework.common.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PushRolePermissionToRedisRunner implements ApplicationRunner {

    private final RoleMapper roleMapper;
    private final RolePermissionRelMapper rolePermissionRelMapper;
    private final PermissionMapper permissionMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

        try {
            Boolean canPushed = redisTemplate.opsForValue().setIfAbsent(RedisKeyConstants.PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);
            // 如果无法同步权限数据
            if (Boolean.FALSE.equals(canPushed)) {
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }

            // 1.查询所有角色
            List<RoleDO> roleDOList = roleMapper.selectList(null);
            if (CollUtil.isEmpty(roleDOList)) {
                return;
            }

            // 2.查询角色关联的权限
            List<Long> roleIdList = roleDOList.stream().map(RoleDO::getId).toList();
            List<RolePermissionRelDO> rolePermissionRelList = rolePermissionRelMapper.selectListInRoleIdList(roleIdList);
            Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionRelList.stream().collect(Collectors.groupingBy(RolePermissionRelDO::getRoleId,
                    Collectors.mapping(RolePermissionRelDO::getPermissionId, Collectors.toList())));

            // 3.查询权限
            List<PermissionDO> permissionList = permissionMapper.selectAppEnabledList();
            Map<Long, List<PermissionDO>> permissionMap = permissionList.stream().collect(Collectors.groupingBy(PermissionDO::getId));

            // 4.存入redis
            roleIdPermissionIdsMap.forEach((roleId, permissionIds) -> {
                if (CollUtil.isNotEmpty(permissionIds)) {
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleId);
                    List<PermissionDO> permissionDOList = permissionIds.stream().map(permissionMap::get).flatMap(List::stream).toList();
                    redisTemplate.opsForValue().set(key, JsonUtils.toJsonString(permissionDOList), 1, TimeUnit.DAYS);
                }
            });

            log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
        } catch (Exception e) {
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }
    }

}
