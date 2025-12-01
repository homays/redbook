package com.ymkx.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.ymkx.domain.entity.PermissionDO;
import com.ymkx.framework.common.enums.StatusEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends MPJBaseMapper<PermissionDO> {

    default List<PermissionDO> selectAppEnabledList() {
        return selectList(new LambdaQueryWrapper<PermissionDO>()
                .eq(PermissionDO::getStatus, StatusEnum.ENABLE.getValue())
                .eq(PermissionDO::getType, 3)); // 按钮权限
    }
}




