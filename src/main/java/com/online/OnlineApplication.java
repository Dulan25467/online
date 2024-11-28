package com.online;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Lazy;


@ComponentScan(basePackages = {"com.online.service", "com.online.repository", "com.online.controller", "com.online.domain", "com.online.resource","com.online.websocket"})
@SpringBootApplication
public class OnlineApplication  {


    public static void main(String[] args) {
        SpringApplication.run(OnlineApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }







}
