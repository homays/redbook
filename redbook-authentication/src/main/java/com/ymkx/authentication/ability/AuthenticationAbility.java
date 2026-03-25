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


}
