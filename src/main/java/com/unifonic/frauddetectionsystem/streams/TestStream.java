package com.unifonic.frauddetectionsystem.streams;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TestStream
{

    @Bean
    public Function<String, String> toLowerCase() {
        return delivery -> {
            return delivery.toLowerCase();
        };
    }

    @Bean
    public Function<String, String> echo() {
        return v -> {
            System.out.println("Echo: " + v);
            return v;
        };
    }

}
