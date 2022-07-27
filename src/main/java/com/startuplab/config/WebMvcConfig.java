package com.startuplab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.startuplab.common.interceptor.SessionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("sessionInterceptor")
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(sessionInterceptor).addPathPatterns("/**") // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
        // .excludePathPatterns("/botAdmin/**") // 해당 경로는 인터셉터가 가로채지 않는다.
        // ;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //
                .allowedMethods("*")
        // .allowedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
        // HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(),
        // HttpMethod.OPTIONS.name(), HttpMethod.PATCH.name(), HttpMethod.TRACE.name())
        // .allowedOrigins("http://localhost:3000")
        // .allowCredentials(true);
        // .allowedMethods("GET, POST"); // .allowedMethods(
        // "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers")
        // .exposedHeaders("Access-Control-Allow-Origin,Access-Control-Allow-Credentials").allowCredentials(true)
        // .maxAge(3000);
        ;
    }

}
