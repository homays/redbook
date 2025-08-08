package com.ymkx.redbook.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ymkx.framework.common.enums.ResponseCodeEnum;
import com.ymkx.framework.common.exception.BizException;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.request.SendVerificationCodeReq;
import com.ymkx.redbook.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.ymkx.framework.common.enums.RedisKeyEnums.VERIFICATION_CODE_KEY;
import static com.ymkx.framework.common.enums.RedisKeyEnums.buildVerificationCodeKey;

@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Response<?> send(SendVerificationCodeReq req) {
        String phone = req.getPhone();

        String key = buildVerificationCodeKey(phone);

        if (redisTemplate.hasKey(key)) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        String code = RandomUtil.randomNumbers(6);

        redisTemplate.opsForValue().set(key, code, VERIFICATION_CODE_KEY.getTime(), TimeUnit.SECONDS);

        return Response.success();
    }
}
