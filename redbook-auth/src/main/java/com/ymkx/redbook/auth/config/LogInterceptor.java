package com.ymkx.redbook.auth.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import com.ymkx.framework.common.exception.BizException;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ymkx
 */
@Component
public class LogInterceptor<T> implements Interceptor<T> {

    @Generated
    private final static Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean beforeExecute(ForestRequest req) {
        return true;
    }

    @Override
    public void onSuccess(T data, ForestRequest req, ForestResponse res) {
        log.info("{} query=[{}] body=[{}] 请求成功,请求结果: {}", req.getURI(), JSON.toJSONString(req.getQuery("qq")), JSON.toJSONString(req.getBody()), JSON.toJSONString(data));
    }

    @Override
    public void onError(ForestRuntimeException ex, ForestRequest request, ForestResponse response) {
        log.error("{} query=[{}] body=[{}] 接口请求失败, {}", request.getURI(), JSONObject.toJSONString(request.getQuery()), JSON.toJSONString(request.getBody()), ex.getMessage());
        throw new BizException(String.format("接口请求失败, %s , %s", request.getUrl(), ex.getMessage()));
    }
}