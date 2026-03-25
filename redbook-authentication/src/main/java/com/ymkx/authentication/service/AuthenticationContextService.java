package com.ymkx.authentication.service;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymkx.authentication.context.AuthenticationContext;
import com.ymkx.authentication.emums.AuthStatusEnum;
import com.ymkx.authentication.param.AuthenticationParam;
import com.ymkx.authentication.request.AuthenticationRequest;
import com.ymkx.authentication.response.AuthenticationResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ymkx.authentication.constants.RedisConstant.AUTH_CONTEXT_KEY;
import static com.ymkx.authentication.constants.RedisConstant.AUTH_CONTEXT_TIME;

@Slf4j
@Service
public class AuthenticationContextService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    public static <T> T getRequest(Map<String, Object> authenticationParam, Class<T> tClazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(authenticationParam), tClazz);
    }

    public static <T extends AuthenticationRequest, R extends AuthenticationResponse> AuthenticationContext<T, R> buildAuthenticationContext(AuthenticationParam param, Class<T> tClazz) {
        T request = getRequest(param.getAuthenticationParam(), tClazz);
        AuthenticationContext<T, R> authenticationContext = new AuthenticationContext<>();
        authenticationContext.setAuthenticationParam(request);
        authenticationContext.setAuthType(param.getAuthType());
        String authRequestId = UUID.randomUUID().toString();
        authenticationContext.setAuthRequestId(authRequestId);
        authenticationContext.setAuthStatus(AuthStatusEnum.PROGRESS);
        return authenticationContext;
    }

    public <T extends AuthenticationRequest, R extends AuthenticationResponse> AuthenticationContext<T, R> restore(AuthenticationParam param, Class<T> tClazz, Class<R> rClazz) {
        String contextKey = getContextKey(param.getAuthType(), param.getAuthRequestId());
        String contextStr = stringRedisTemplate.opsForValue().get(contextKey);
        Assert.notBlank(contextStr, "认证已过期，请重新发起");
        try {
            return objectMapper.readValue(
                    contextStr,
                    objectMapper.getTypeFactory().constructParametricType(
                            AuthenticationContext.class,
                            tClazz,
                            rClazz
                    )
            );
        } catch (JsonProcessingException e) {
            log.error("AuthenticationContext restore, 序列化失败, param:{}, exception：", param, e);
            throw new RuntimeException(e);
        } finally {
            log.info("AuthenticationContext restore, key:{}, context:{}", contextKey, contextStr);
        }
    }

    public <T extends AuthenticationRequest, R extends AuthenticationResponse> void store(AuthenticationContext<T, R> context) {
        String contextKey = getContextKey(context.getAuthType(), context.getAuthRequestId());
        String contextStr = JSONObject.toJSONString(context);
        long time = stringRedisTemplate.getExpire(contextKey);
        try {
            if (time < 0 && (context.getAuthStatus() == AuthStatusEnum.PROGRESS || context.getAuthStatus() == AuthStatusEnum.INIT)) {
                time = AUTH_CONTEXT_TIME;
                stringRedisTemplate.opsForValue().set(contextKey, JSONObject.toJSONString(context), time, TimeUnit.SECONDS);
                return;
            }
            Assert.isTrue(time > 0, "认证已过期，请重新发起");
            if (BooleanUtils.isTrue(context.getPass())) {
                context.setErrorMsg(null);
                context.setAuthStatus(AuthStatusEnum.SUCCESS);
            }
            stringRedisTemplate.opsForValue().set(contextKey, JSONObject.toJSONString(context), time, TimeUnit.SECONDS);
        } finally {
            log.info("AuthenticationContext store, key:{}, time:{}, context:{}", contextKey, time, contextStr);
        }
    }

    public void remove(String authType, String key) {
        stringRedisTemplate.delete(getContextKey(authType, key));
    }

    public String getContextKey(String authType, String authRequestId) {
        return String.format(AUTH_CONTEXT_KEY, authType, authRequestId);
    }
}