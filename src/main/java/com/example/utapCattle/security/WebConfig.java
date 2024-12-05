package com.example.utapCattle.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // Apply to all endpoints
                            .allowedOrigins("*") // Allow all origins
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all HTTP methods
                            .allowedHeaders("*") // Allow all headers
                            .allowCredentials(false); // Disable credentials if not needed
                }
            };
        }
    }


//    @Value("${settings.cors_origin}")
//    private String corsOrigin;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(corsOrigin) // Use the property value for allowed origins
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}


