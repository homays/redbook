package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.ymkx.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MPJBaseMapper<UserDO> {

    default UserDO selectByPhone(String phone) {
        return selectOne(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getPhone, phone));
    }

}
