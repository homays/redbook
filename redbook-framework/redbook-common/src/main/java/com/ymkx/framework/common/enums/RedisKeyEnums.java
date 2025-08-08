package com.ymkx.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeyEnums {

    VERIFICATION_CODE_KEY("verification_code:", 60 * 3, "验证码");

    private final String key;
    private final Integer time;
    private final String desc;

    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY.getKey() + phone;
    }

}
