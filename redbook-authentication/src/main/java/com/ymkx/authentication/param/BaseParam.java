package com.ymkx.authentication.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseParam {

    /**
     * 认证方式
     */
    private String authType;

    /**
     * 当前登录人ID
     */
    private String userId;

}