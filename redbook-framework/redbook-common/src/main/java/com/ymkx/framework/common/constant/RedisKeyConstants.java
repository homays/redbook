package com.ymkx.framework.common.constant;

public class RedisKeyConstants {

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 构建用户-角色 Key
     * @param phone 手机号
     * @return 用户-角色 Key
     */
    public static String buildUserRoleKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

}

