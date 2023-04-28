package com.unifonic.frauddetectionsystem.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ProfanityFilterStream {

  @Bean
  public BiFunction<KStream<String, String>, KTable<String, String>, KStream<String, String>> process() {
    return (inputTextStream, blockWordsTable) -> (inputTextStream
            .join(blockWordsTable, (clicks, region) ->
              new RegionWithClicks(region == null ?
                      "UNKNOWN" : region, clicks),
                      Joined.with(Serdes.String(), Serdes.String(), null)
            ))
            .mapValues((key, value) -> value.getStr().toUpperCase())
            .toTable().toStream();
  }
}
