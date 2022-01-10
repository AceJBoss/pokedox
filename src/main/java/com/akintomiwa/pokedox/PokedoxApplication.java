package com.akintomiwa.pokedox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokedoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokedoxApplication.class, args);
    }

}
