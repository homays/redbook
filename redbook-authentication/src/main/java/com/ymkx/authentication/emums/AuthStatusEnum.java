package com.ymkx.authentication.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AuthStatusEnum {

    INIT("认证初始化"),
    PROGRESS("认证中"),
    INVALID("认证失效"),
    SUCCESS("认证成功"),
    FAIL("认证失败"),
    ;
    private String desc;

    public static AuthStatusEnum of(String name) {
        for (AuthStatusEnum o : values()) {
            if (o.name().equals(name)) {
                return o;
            }
        }
        return null;
    }

    public static Boolean isInit(AuthStatusEnum authStatusEnum) {
        return authStatusEnum == INIT;
    }

    public static AuthStatusEnum getAuthStatusEnum(String actionType) {
        return StringUtils.equals(actionType, "INIT") ? AuthStatusEnum.INIT : AuthStatusEnum.PROGRESS;
    }

    public static Boolean isSuccess(AuthStatusEnum authStatusEnum) {
        return authStatusEnum == SUCCESS;
    }

}