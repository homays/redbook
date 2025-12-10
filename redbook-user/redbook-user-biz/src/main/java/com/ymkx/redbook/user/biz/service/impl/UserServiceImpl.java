package com.ymkx.redbook.user.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.entity.UserRoleRelDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.domain.mapper.UserRoleRelMapper;
import com.ymkx.framework.common.constant.RoleConstants;
import com.ymkx.framework.common.enums.DeletedEnum;
import com.ymkx.framework.common.enums.StatusEnum;
import com.ymkx.framework.common.response.Response;
import com.ymkx.framework.common.util.UidGeneratorUtils;
import com.ymkx.redbook.context.holder.LoginUserContextHolder;
import com.ymkx.redbook.user.biz.request.UpdatePasswordReq;
import com.ymkx.redbook.user.biz.service.UserService;
import com.ymkx.redbook.user.request.RegisterUserReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author ymkx
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRoleRelMapper userRoleRelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response<String> register(RegisterUserReqDTO registerUserReqDTO) {
        String phone = registerUserReqDTO.getPhone();
        UserDO dbUser = userMapper.selectByPhone(phone);

        if (ObjectUtil.isNotNull(dbUser)) {
            return Response.success(dbUser.getUserId());
        }

        String userId = UidGeneratorUtils.getUid();

        UserDO userDO = UserDO.builder()
                .userId(userId)
                .phone(phone)
                .nickname("redbook_" + phone.substring(7))
                .status(StatusEnum.ENABLE.getValue())
                .isDeleted(DeletedEnum.NO.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userMapper.insert(userDO);

        UserRoleRelDO userRoleRelDO = UserRoleRelDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .isDeleted(DeletedEnum.NO.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userRoleRelMapper.insert(userRoleRelDO);

        return Response.success(userId);
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReq req) {
        String newPassword = req.getNewPassword();
        String encodePassword = passwordEncoder.encode(newPassword);

        String userId = LoginUserContextHolder.getUserId();
        // 修改密码
        userMapper.updatePassword(userId, encodePassword);

        return Response.success();
    }

}
