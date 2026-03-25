package com.ymkx.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author ymkx
 * @date 2026/3/24 16:06
 * @description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationPrepareResponse extends BaseResponse {

    /**
     * 认证周期Id
     */
    private String authRequestId;

    public static AuthenticationPrepareResponse result(String authRequestId, Map<String, Object> authenticationResult) {
        AuthenticationPrepareResponse response = AuthenticationPrepareResponse.builder()
                .authRequestId(authRequestId)
                .build();
        response.setAuthenticationResult(authenticationResult);
        return response;
    }

}
