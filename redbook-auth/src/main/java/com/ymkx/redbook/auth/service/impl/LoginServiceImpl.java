package com.ymkx.redbook.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Preconditions;
import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.domain.mapper.UserRoleRelMapper;
import com.ymkx.framework.common.constant.RedisKeyConstants;
import com.ymkx.framework.common.enums.RedisKeyEnums;
import com.ymkx.framework.common.enums.ResponseCodeEnum;
import com.ymkx.framework.common.exception.BizException;
import com.ymkx.framework.common.response.Response;
import com.ymkx.redbook.auth.enums.LoginTypeEnum;
import com.ymkx.redbook.auth.request.LoginReq;
import com.ymkx.redbook.auth.service.LoginService;
import com.ymkx.redbook.auth.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final UserRoleRelMapper userRoleRelMapper;
    private final UserRegisterService userRegisterService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Response<String> login(LoginReq req) {
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getByType(req.getType());

        // 获取登录用户 ID
        String userId = getLoginUserId(req, loginTypeEnum);

        // 授权
        this.authorize(userId);

        // SaToken 登录用户，并返回 token 令牌
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return Response.success(tokenInfo.tokenValue);
    }

    private String getLoginUserId(LoginReq req, LoginTypeEnum loginTypeEnum) {
        String userId = null;
        switch (Objects.requireNonNull(loginTypeEnum)) {
            case VERIFICATION_CODE:
                // 校验入参验证码是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(req.getCode()), "验证码不能为空");

                String phone = req.getPhone();
                String key = RedisKeyEnums.buildVerificationCodeKey(phone);

                if (StrUtil.equals(stringRedisTemplate.opsForValue().get(key), req.getCode())) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                UserDO userDO = userMapper.selectByPhone(phone);
                if (ObjectUtil.isNull(userDO)) {
                    // 注册用户
                    userId = userRegisterService.registerUser(phone);
                } else {
                    // 登录
                    userId = userDO.getUserId();
                }
                return userId;
            case PASSWORD:
                // todo 密码登录
                break;
            default:
                break;
        }

        return userId;
    }

    private void authorize(String userId) {
        List<String> RoleKeyList = userRoleRelMapper.selectRoleKeyByUserId(userId);

        String userRoleKey = RedisKeyConstants.buildUserRoleKey(userId);
        stringRedisTemplate.opsForValue().set(userRoleKey, JSON.toJSONString(RoleKeyList), 3, TimeUnit.HOURS);
    }
}
