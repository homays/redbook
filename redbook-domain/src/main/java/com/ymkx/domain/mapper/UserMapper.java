package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.ymkx.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @author ymkx
 */
@Mapper
public interface UserMapper extends MPJBaseMapper<UserDO> {

    default UserDO selectByPhone(String phone) {
        return selectOne(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getPhone, phone));
    }

    default void updatePassword(String userId, String encodePassword) {
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<UserDO>()
                .eq(UserDO::getUserId, userId)
                .set(UserDO::getPassword, encodePassword)
                .set(UserDO::getUpdateTime, LocalDateTime.now());
        update(updateWrapper);
    }
}
