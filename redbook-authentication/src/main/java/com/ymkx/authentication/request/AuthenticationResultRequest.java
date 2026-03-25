package com.ymkx.authentication.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResultRequest extends BaseRequest {

    /**
     * 全局请求ID
     */
    private String authRequestId;

    /**
     * 业务参数
     */
    private Map<String,  Object> authenticationParam;

}