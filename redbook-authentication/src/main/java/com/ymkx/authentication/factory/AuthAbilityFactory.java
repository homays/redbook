package com.ymkx.authentication.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.ymkx.authentication.ability.AuthenticationAbility;
import com.ymkx.authentication.ability.IAbility;
import com.ymkx.framework.common.enums.ResponseCodeEnum;
import com.ymkx.framework.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author ymkx
 * @date 2026/3/24 16:32
 * @description 认证工厂
 */
@Slf4j
@Component
public class AuthAbilityFactory {

    private Map<String, AuthenticationAbility> authenticationAbilityMap = new HashMap<>();

    public AuthAbilityFactory(List<AuthenticationAbility> authenticationAbilities) {
        if (MapUtil.isEmpty(authenticationAbilityMap)) {
            authenticationAbilityMap = new HashMap<>();
        }
        if (CollectionUtil.isNotEmpty(authenticationAbilities)) {
            authenticationAbilities.forEach(f -> {
                authenticationAbilityMap.put(f.getName(), f);
            });
        }
        log.info("初始化认证能力：{}", authenticationAbilityMap);
    }

    public <T> IAbility getAbility(String abilityName, Class<T> tClass) {
        IAbility ability = authenticationAbilityMap.get(abilityName);
        if (Objects.isNull(ability)) {
            throw new BizException(ResponseCodeEnum.AUTHENTICATION_ABILITY_ERROR);
        }
        if (!tClass.isInstance(ability)) {
            throw new BizException(ResponseCodeEnum.AUTHENTICATION_ABILITY_ERROR);
        }
        return ability;
    }

    public <T, R> R execute(String abilityName, Class<T> tClass, Function<T, R> function) {
        IAbility ability = getAbility(abilityName, tClass);
        @SuppressWarnings("unchecked")
        T castedAbility = (T) ability;
        return function.apply(castedAbility);
    }

}
