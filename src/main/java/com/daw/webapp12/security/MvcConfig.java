package com.daw.webapp12.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
/*

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePath = Paths.get("static/images").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("static/images/**")
                .addResourceLocations(resourcePath);

    }
/*
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/new/").setViewName("forward:/new/index.html");
    }*/
}
