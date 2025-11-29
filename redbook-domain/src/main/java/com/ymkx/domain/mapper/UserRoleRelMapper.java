package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.ymkx.domain.entity.UserRoleRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleRelMapper extends MPJBaseMapper<UserRoleRelDO> {

    default List<UserRoleRelDO> selectListByUserid(String userId) {
        return selectList(new LambdaQueryWrapper<UserRoleRelDO>()
                .eq(UserRoleRelDO::getUserId, userId));
    }

}




