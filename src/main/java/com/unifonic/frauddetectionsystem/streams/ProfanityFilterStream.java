package com.unifonic.frauddetectionsystem.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifonic.frauddetectionsystem.model.ProfanityWord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class ProfanityFilterStream {

  public static final Serde<ProfanityWord> SERDE_SMS_SMS_RECIPIENT_STATUS_MODEL = new JsonSerde<>(ProfanityWord.class, new ObjectMapper());

  //@Bean
  public Function<KStream<String, String>, KStream<String, String>> splitInputStream() {
    return v -> v
            //.mapValues((readOnlyKey, value) -> value.split(" "))
            .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.split("\\s+")))
            .map((key, value) -> new KeyValue<>(value,key));
  }

  @Bean
  public BiFunction<KStream<String, String>, KTable<String, ProfanityWord>, KStream<String, RegionWithClicks>> process() {
    return (inputTextStream, blockWordsTable) -> (inputTextStream
            .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.split("\\s+")))
            .map((key, value) -> new KeyValue<>(value,key))
            .join(blockWordsTable,(left, right)->
                    new RegionWithClicks(left,right.getWord()),
                    Joined.with(Serdes.String(),Serdes.String(),null))
            .toTable()
            .toStream());
  }
}
