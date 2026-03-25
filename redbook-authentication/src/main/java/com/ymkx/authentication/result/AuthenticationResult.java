package com.ymkx.authentication.result;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ymkx.authentication.context.AuthenticationContext;
import com.ymkx.authentication.emums.AuthStatusEnum;
import com.ymkx.authentication.request.AuthenticationRequest;
import com.ymkx.authentication.response.AuthenticationResponse;
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
public class AuthenticationResult {

    private Boolean pass = Boolean.TRUE;

    private String authStatus;

    private String errorMsg;

    private String authRequestId;

    private Map<String, Object> authenticationResult;

    public static AuthenticationResult result(String errorMsg) {
        return AuthenticationResult.builder().pass(Boolean.FALSE).errorMsg(errorMsg).build();
    }

    public static AuthenticationResult result(AuthStatusEnum authStatusEnum) {
        return AuthenticationResult.builder().authStatus(authStatusEnum.name()).build();
    }

    public static <T> AuthenticationResult result(T response) {
        Map<String, Object> authenticationResult = JSONObject.parseObject(JSONObject.toJSONString(response), new TypeReference<>() {
        });
        return AuthenticationResult.builder().pass(Boolean.TRUE).authenticationResult(authenticationResult).build();
    }

    public static <T extends AuthenticationRequest, R extends AuthenticationResponse> AuthenticationResult result(AuthenticationContext<T, R> context) {
        return AuthenticationResult.builder()
                .authRequestId(context.getAuthRequestId())
                .pass(context.getPass())
                .authStatus(context.getAuthStatus().name())
                .errorMsg(context.getErrorMsg())
                .build();
    }

    public static <T extends AuthenticationRequest, R extends AuthenticationResponse, Z> AuthenticationResult result(AuthenticationContext<T, R> context, Z response) {
        Map<String, Object> authenticationResult = null;
        if (response != null) {
            authenticationResult = JSONObject.parseObject(JSONObject.toJSONString(response), new TypeReference<>() {
            });
        }
        return AuthenticationResult.builder()
                .authStatus(context.getAuthStatus().name())
                .authenticationResult(authenticationResult)
                .authRequestId(context.getAuthRequestId())
                .errorMsg(context.getErrorMsg())
                .pass(context.getPass())
                .build();
    }

}