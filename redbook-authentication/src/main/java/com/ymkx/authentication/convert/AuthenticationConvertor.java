package com.ymkx.authentication.convert;

import com.ymkx.authentication.request.BaseRequest;

/**
 * @author ymkx
 * @date 2026/3/24 16:10
 * @description 封装请求
 */
public class AuthenticationConvertor {

    public static <R extends BaseRequest> void buildParam(R request) {
        // 封装公共请求参数，比如userId
        request.setAuthType(request.getAuthType());
    }

}
