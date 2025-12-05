package com.ymkx.redbook.auth.client;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Retry;
import com.ymkx.redbook.auth.config.LogInterceptor;
import com.ymkx.redbook.auth.request.QQUserInfo;
import org.springframework.stereotype.Component;

/**
 * @author ymkx
 * @date 2025/12/5 16:06
 * @description TODO
 */
@Component
public interface MyClient {

    @Get(url = "https://uapis.cn/api/v1/social/qq/userinfo",
            interceptor = LogInterceptor.class,
            contentType = "application/json",
            connectTimeout = 10000,
            readTimeout = 10000)
    @Retry(maxRetryCount = "3", maxRetryInterval = "10")
    QQUserInfo getQqInfo(@Query("qq") String qq);

}
