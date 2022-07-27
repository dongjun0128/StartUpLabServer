package com.startuplab.config.oauth2;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientPermissionExpression implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("ClientPermissionExpression hasPermission authentication=" + authentication + " targetDomainObject=" + targetDomainObject + " permission=" + permission);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}
