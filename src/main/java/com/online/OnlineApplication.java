package com.online;

import com.online.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = {"com.online.service", "com.online.repository", "com.online.controller", "com.online.domain", "com.online.resource", "com.online.config", "com.online.websocket"})
@SpringBootApplication
public class OnlineApplication  {

    private final UserService userService;

    @Autowired
    public OnlineApplication(@Lazy UserService userService) {
        this.userService = userService;
    }


    public static void main(String[] args) {
        SpringApplication.run(OnlineApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }




    @Configuration
    public class WebConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedOrigins("http://localhost:4200");
                }
            };
        }
    }


}
