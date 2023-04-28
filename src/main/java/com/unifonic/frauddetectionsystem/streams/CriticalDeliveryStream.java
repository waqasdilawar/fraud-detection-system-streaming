package com.unifonic.frauddetectionsystem.streams;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CriticalDeliveryStream
{

    @Bean
    public Function<String, String> criticaldelivery() {
        return delivery -> {
            System.out.println(delivery);
            return delivery.toLowerCase();
        };
    }






}
