package com.ymkx.redbook.user.api;

import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.user.request.RegisterUserReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ymkx
 * @date 2025/12/9 17:33
 * @description
 */
@FeignClient(name = "redbook-user")
public interface UserFeignApi {

    String PREFIX = "/user";

    /**
     * 用户注册
     */
    @PostMapping(value = PREFIX + "/register")
    Response<String> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);

}
