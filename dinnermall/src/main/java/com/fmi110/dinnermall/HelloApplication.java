package com.fmi110.dinnermall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HelloApplication {
    public static void main(String[] arg){
        SpringApplication.run(HelloApplication.class, arg);
    }
}
