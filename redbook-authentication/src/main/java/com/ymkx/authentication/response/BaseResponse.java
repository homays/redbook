package com.ymkx.authentication.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * @author ymkx
 * @date 2026/3/24 16:06
 * @description 基础返回类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    /**
     * 业务扩展参数
     */
    private Map<String, Object> authenticationResult;

}
