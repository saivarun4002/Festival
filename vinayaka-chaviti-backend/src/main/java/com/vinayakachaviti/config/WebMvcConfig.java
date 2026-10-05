package com.vinayakachaviti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * PRD-STORAGE: Serves locally-stored uploads (dev-only StorageService
 * implementation) back out at /uploads/** so gallery/video image URLs
 * returned by the API are actually resolvable in the browser.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${storage.base-path:./uploads}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = java.nio.file.Paths.get(basePath).toAbsolutePath().normalize().toUri().toString();
        registry.addResourceHandler("/uploads/**").addResourceLocations(location);
    }
}
