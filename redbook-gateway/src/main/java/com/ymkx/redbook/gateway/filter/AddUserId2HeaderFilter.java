package com.ymkx.redbook.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.ymkx.framework.common.constant.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 转发请求时，将用户 ID 添加到 Header 请求头中，透传给下游服务
 */
@Component
@Slf4j
public class AddUserId2HeaderFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 用户 ID
        long userId;
        try {
            // 获取当前登录用户的 ID
            userId = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            // 若没有登录，则直接放行
            return chain.filter(exchange);
        }

        Long finalUserId = userId;
        // 将用户 ID 设置到请求头中
        ServerWebExchange newExchange = exchange.mutate()
                .request(builder -> builder.header(GlobalConstants.USER_ID, String.valueOf(finalUserId)))
                .build();
        return chain.filter(newExchange);
    }
}