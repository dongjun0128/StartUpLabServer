package com.startuplab.config.oauth2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 범위
@Target(ElementType.PARAMETER) // 어노테이션 적용위치
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
// @AuthenticationPrincipal(expression = "account")
public @interface CurrentUser {}
