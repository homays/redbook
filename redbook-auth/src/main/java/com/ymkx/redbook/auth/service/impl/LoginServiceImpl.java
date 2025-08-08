package com.ymkx.redbook.auth.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.framework.common.enums.RedisKeyEnums;
import com.ymkx.framework.common.enums.ResponseCodeEnum;
import com.ymkx.framework.common.exception.BizException;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.enums.LoginTypeEnum;
import com.ymkx.redbook.auth.request.LoginReq;
import com.ymkx.redbook.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Response<String> login(LoginReq req) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getByType(req.getType());

        switch (loginTypeEnum) {
            case VERIFICATION_CODE:
                Assert.notNull(req.getCode(), "验证码不能为空");

                String phone = req.getPhone();
                String key = RedisKeyEnums.buildVerificationCodeKey(phone);

                if (ObjectUtil.notEqual(redisTemplate.opsForValue().get(key), req.getCode())) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                UserDO userDO = userMapper.selectByPhone(phone);
                if (ObjectUtil.isNull(userDO)) {
                    // 注册用户
                } else {
                    // 登录
                    String userId = userDO.getUserId();
                }

                break;
            case PASSWORD:
                // todo 密码登录
                break;
            default:
                break;
        }

        // SaToken 登录用户，并返回 token 令牌
        // todo

        return Response.success();
    }
}
