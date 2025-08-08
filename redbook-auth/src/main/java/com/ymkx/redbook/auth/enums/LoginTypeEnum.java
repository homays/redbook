package com.ymkx.redbook.auth.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    VERIFICATION_CODE("code", "手机号登录"),
    PASSWORD("password", "用户名登录");

    private final String type;
    private final String desc;

    public static LoginTypeEnum getByType(String type) {
        for (LoginTypeEnum value : values()) {
            if (ObjectUtil.equal(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }

}
