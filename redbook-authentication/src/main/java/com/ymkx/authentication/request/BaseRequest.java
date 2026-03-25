package com.ymkx.authentication.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author ymkx
 * @date 2026/3/24 16:01
 * @description 基础请求类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRequest {

    /**
     * 认证方式
     */
    private String authType;

    /**
     * 当前登录人ID
     */
    private String userId;

    /**
     * 认证请求ID
     */
    private String authRequestId;

}
