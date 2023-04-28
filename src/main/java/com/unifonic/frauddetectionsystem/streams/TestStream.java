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
            System.out.println(delivery);
            return delivery.toLowerCase();
        };
    }

}
