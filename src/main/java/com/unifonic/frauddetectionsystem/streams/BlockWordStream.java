package com.unifonic.frauddetectionsystem.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

@Component
public class BlockWordStream {

  @Bean
  public Function<KStream<String, String>, KStream<String, Long>> toTable() {
    return input -> input
            .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
            .map((key, value) -> new KeyValue<>(value, value))
            .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
            .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
            .count(Materialized.as("wordcount-store"))
            .toStream()
            .map((key, value) -> new KeyValue<>(key.key(), value));
  }
}
