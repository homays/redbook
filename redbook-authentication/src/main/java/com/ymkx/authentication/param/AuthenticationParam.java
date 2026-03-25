package com.ymkx.authentication.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationParam extends BaseParam {

    /**
     * 认证码
     */
    private String authRequestId;

    private Map<String, Object> authenticationParam;

    public static AuthenticationParam of(String authType, Map<String, Object> authenticationParam) {
        AuthenticationParam build = AuthenticationParam.builder()
                .authenticationParam(authenticationParam)
                .build();
        build.setAuthType(authType);
        return build;
    }

    public static AuthenticationParam of(String authType, String authRequestId, Map<String, Object> authenticationParam) {
        AuthenticationParam build = of(authType, authenticationParam);
        build.setAuthRequestId(authRequestId);
        return build;
    }

}