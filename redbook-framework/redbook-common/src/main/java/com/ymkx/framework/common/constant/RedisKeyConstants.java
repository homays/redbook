package com.ymkx.framework.common.constant;

public class RedisKeyConstants {

    // 权限同步标记 Key
    public static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 构建用户-角色 Key
     *
     * @param userId 用户 ID
     * @return 用户-角色 Key
     */
    public static String buildUserRoleKey(String userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    /**
     * 构建角色对应的权限集合 KEY
     *
     * @param roleKey 角色标识
     * @return 角色对应的权限集合 KEY
     */
    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

}

