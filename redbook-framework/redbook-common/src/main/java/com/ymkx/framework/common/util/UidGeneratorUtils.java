package com.ymkx.framework.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * Uid 生成工具类
 */
public class UidGeneratorUtils {

    public static String getUid() {
        return IdUtil.getSnowflakeNextIdStr();
    }

}