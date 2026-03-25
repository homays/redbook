package com.ymkx.authentication.ability;

import com.ymkx.authentication.param.AuthenticationParam;
import com.ymkx.authentication.result.AuthenticationResult;

/**
 * @author ymkx
 * @date 2026/3/24 16:34
 * @description TODO
 */
public interface AuthenticationAbility extends IAbility {

    /**
     * 认证前
     */
    AuthenticationResult authenticationPrepare(AuthenticationParam param);

    /**
     * 认证：获取认证状态
     */
    AuthenticationResult authenticationStatus(AuthenticationParam param);

    /**
     * 认证结果
     */
    default AuthenticationResult authenticationResult(AuthenticationParam param) {
        return AuthenticationResult.result("暂不支持");
    }

}
