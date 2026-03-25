package com.ymkx.authentication.context;

import com.ymkx.authentication.emums.AuthStatusEnum;
import com.ymkx.authentication.request.AuthenticationRequest;
import com.ymkx.authentication.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationContext<T extends AuthenticationRequest, R extends AuthenticationResponse> {

    /**
     * 认证类型
     */
    private String authType;

    /**
     * 认证周期的全局标识
     */
    private String authRequestId;

    /**
     * 认证的入参
     */
    private T authenticationParam;

    /**
     * 认证状态码
     */
    private AuthStatusEnum authStatus;

    /** 认证结果 */
    private Boolean pass;

    private String errorMsg;

    /**
     * 认证的出参
     */
    private R authenticationResult;

}