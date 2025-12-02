package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.ymkx.domain.entity.RoleDO;
import com.ymkx.domain.entity.UserRoleRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleRelMapper extends MPJBaseMapper<UserRoleRelDO> {

    default List<UserRoleRelDO> selectListByUserid(String userId) {
        return selectList(new LambdaQueryWrapper<UserRoleRelDO>()
                .eq(UserRoleRelDO::getUserId, userId));
    }

    /**
     * 根据用户ID查询角色标识
     *
     * @param userId 用户ID
     * @return 角色标识
     */
    default List<String> selectRoleKeyByUserId(String userId) {
        MPJLambdaWrapper<UserRoleRelDO> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(RoleDO::getRoleKey);
        wrapper.eq(UserRoleRelDO::getUserId, userId);
        wrapper.innerJoin(RoleDO.class, on -> on.eq(UserRoleRelDO::getRoleId, RoleDO::getId));

        return selectJoinList(String.class, wrapper);
    }

}




