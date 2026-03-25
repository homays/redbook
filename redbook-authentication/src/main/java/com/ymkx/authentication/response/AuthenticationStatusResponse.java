package com.ymkx.authentication.response;

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
public class AuthenticationStatusResponse extends BaseResponse {

    private Boolean pass;

    /**
     * 认证状态
     */
    private String authStatus;

    private String errorMsg;

    public static AuthenticationStatusResponse result(String errorMsg, String authStatus, Map<String, Object> authenticationResult) {
        AuthenticationStatusResponse response = AuthenticationStatusResponse.builder()
                .authStatus(authStatus)
                .errorMsg(errorMsg)
                .build();
        response.setAuthenticationResult(authenticationResult);
        return response;
    }

}