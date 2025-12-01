package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.ymkx.domain.entity.RolePermissionRelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionRelMapper extends MPJBaseMapper<RolePermissionRelDO> {

    default List<RolePermissionRelDO> selectListInRoleIdList(List<Long> roleIdList) {
        return selectList(new LambdaQueryWrapper<RolePermissionRelDO>().in(RolePermissionRelDO::getRoleId, roleIdList));
    }
}




