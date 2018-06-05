package com.allyes.minimec.api.config;


import com.allyes.minimec.api.interceptor.AuthHandlerInterceptor;
import com.allyes.minimec.api.interceptor.EncodeHandlerInterceptor;
import com.allyes.minimec.api.web.bind.support.CurrentUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
        return new CurrentUserHandlerMethodArgumentResolver();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(encodeHandlerInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authHandlerInterceptor()).addPathPatterns("/**/authApi/**");
    }

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }

    @Bean
    public EncodeHandlerInterceptor encodeHandlerInterceptor() {
        return new EncodeHandlerInterceptor();
    }


}