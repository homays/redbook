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
     * @param phone 手机号
     * @return 用户-角色 Key
     */
    public static String buildUserRoleKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

    /**
     * 构建角色对应的权限集合 KEY
     *
     * @param roleId 角色 ID
     * @return 角色对应的权限集合 KEY
     */
    public static String buildRolePermissionsKey(Long roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }

}

