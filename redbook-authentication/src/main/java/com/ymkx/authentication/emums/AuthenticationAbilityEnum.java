package com.ymkx.authentication.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationAbilityEnum {

    TEST_A("test_a", "测试A"),
    ;

    private String code;
    private String desc;

    public static AuthenticationAbilityEnum getByCode(String code) {
        for (AuthenticationAbilityEnum value : AuthenticationAbilityEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
