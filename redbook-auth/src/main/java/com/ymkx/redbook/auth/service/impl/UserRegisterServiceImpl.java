package com.ymkx.redbook.auth.service.impl;

import com.ymkx.domain.entity.UserDO;
import com.ymkx.domain.entity.UserRoleRelDO;
import com.ymkx.domain.mapper.UserMapper;
import com.ymkx.domain.mapper.UserRoleRelMapper;
import com.ymkx.framework.common.constant.RoleConstants;
import com.ymkx.framework.common.enums.DeletedEnum;
import com.ymkx.framework.common.enums.StatusEnum;
import com.ymkx.framework.common.util.UidGeneratorUtils;
import com.ymkx.redbook.auth.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserMapper userMapper;
    private final UserRoleRelMapper userRoleRelMapper;

    @Transactional(rollbackFor = Exception.class)
    public String registerUser(String phone) {
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

        return userId;
    }
}
