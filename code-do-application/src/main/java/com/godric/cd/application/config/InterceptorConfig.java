package com.godric.cd.application.config;

import com.godric.cd.application.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> userExcludePaths = new ArrayList<>();
        userExcludePaths.add("/login/**");
        userExcludePaths.add("/handler/message");

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(userExcludePaths);
        super.addInterceptors(registry);
    }
}
