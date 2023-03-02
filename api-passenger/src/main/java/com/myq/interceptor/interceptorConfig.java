package com.myq.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorConfig implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截路径
                .addPathPatterns("/**")
                //不拦截路径
                .excludePathPatterns("/verfication-code")
                .excludePathPatterns("/verfication-code-check")
                .excludePathPatterns("/token-refresh")
                ;
    }
}
