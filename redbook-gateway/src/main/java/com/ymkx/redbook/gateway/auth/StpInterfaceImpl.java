package com.ymkx.redbook.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymkx.framework.common.constant.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Slf4j
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.info("## 获取用户权限列表, loginId: {}", loginId);

        List<String> roleList = this.getRoleList(loginId, loginType);
        if (CollUtil.isEmpty(roleList)) {
            return null;
        }

        List<String> rolePermissionKeys = roleList.stream().map(RedisKeyConstants::buildRolePermissionsKey).toList();
        List<String> rolePermissionsValues = stringRedisTemplate.opsForValue().multiGet(rolePermissionKeys);
        if (CollUtil.isEmpty(rolePermissionsValues)) {
            return null;
        }

        List<String> resList = new ArrayList<>();
        // 遍历所有角色的权限集合，统一添加到 permissions 集合中
        for (String jsonValue : rolePermissionsValues) {
            if (StringUtils.isNotBlank(jsonValue)) {
                List<String> rolePermissions = JSON.parseArray(jsonValue, String.class);
                resList.addAll(rolePermissions);
            }
        }

        return resList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("## 获取用户角色列表, loginId: {}", loginId);

        String key = RedisKeyConstants.buildUserRoleKey(loginId.toString());
        String userRolesStr = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(userRolesStr)) {
            return null;
        }

        return JSON.parseArray(userRolesStr, String.class);
    }

}