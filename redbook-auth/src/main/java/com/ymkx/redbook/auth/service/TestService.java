package com.ymkx.redbook.auth.service;

import com.alibaba.fastjson2.JSON;
import com.dtzhejiang.openapi.sdk.OpenApiClient;
import com.dtzhejiang.openapi.sdk.request.UicRegionQueryRegionTreeRequest;
import com.dtzhejiang.openapi.sdk.response.UicRegionQueryRegionTreeResponse;
import com.ymkx.redbook.auth.client.MyClient;
import com.ymkx.redbook.auth.request.QQUserInfo;
import com.ymkx.redbook.auth.request.SendVerificationCodeReq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ymkx
 * @date 2025/12/4 11:45
 * @description
 */
@Slf4j
@Service
public class TestService {

    private AtomicInteger count = new AtomicInteger(0);

    @Resource
    private MyClient myClient;

    @Retryable(listeners = {"logRetryListener"})
    public void test(SendVerificationCodeReq sms) {
        OpenApiClient client = OpenApiClient.createClient("http://112.124.241.167:28080/",
                "uic", "uic_key", "763f03b781944d34ae7a6367256c2f30");

        UicRegionQueryRegionTreeRequest req = new UicRegionQueryRegionTreeRequest();
        UicRegionQueryRegionTreeRequest.RegionTreeRequest regionTreeRequest = new UicRegionQueryRegionTreeRequest.RegionTreeRequest();
        regionTreeRequest.setRegionCode(sms.getRegionCode());
        req.setRequest(regionTreeRequest);

        long startTime = System.currentTimeMillis();

        log.info("开始执行接口");

        int i = count.incrementAndGet();
        if (i != 3) {
            int a = 1 / 0;
        }

        UicRegionQueryRegionTreeResponse response = client.execute(req);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("Response data: {}", JSON.toJSONString(response.getData()));
        log.info("接口执行时间: {} 毫秒", executionTime);
    }

    public void test2(String qq) {
        QQUserInfo qqUserInfo = myClient.getQqInfo(qq);
        //log.info("qqInfo: {}", JSON.toJSONString(qqUserInfo));
    }
}
