package com.ymkx.authentication.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author ymkx
 * @date 2026/3/24 16:00
 * @description 认证前置请求类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationPrepareRequest extends BaseRequest {

    /**
     * 认证参数
     */
    private Map<String,  Object> authenticationParam;

}
