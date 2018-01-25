package com.fmi110.dinnermall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SellApplication {
    public static void main(String[] arg){
        SpringApplication.run(SellApplication.class, arg);
    }
}
