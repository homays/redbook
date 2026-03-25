package com.ymkx.authentication.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResultResponse extends AuthenticationStatusResponse {

    private Boolean pass;

    /**
     * 临时票据，可用来获取用户信息
     */
    private String accessToken;

    private String errorMsg;

    public static AuthenticationResultResponse result(Boolean pass, String errorMsg, Map<String, Object> authenticationResult) {
        AuthenticationResultResponse response = new AuthenticationResultResponse();
        response.setPass(pass);
        response.setErrorMsg(errorMsg);
        response.setAuthenticationResult(authenticationResult);
        return response;
    }

}