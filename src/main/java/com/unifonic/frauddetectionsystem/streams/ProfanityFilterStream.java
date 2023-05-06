package com.unifonic.frauddetectionsystem.streams;

import com.unifonic.frauddetectionsystem.model.ProfanityWord;
import com.unifonic.frauddetectionsystem.model.ProfanityWordCheck;
import com.unifonic.frauddetectionsystem.model.ProfanityWordSplit;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.BiFunction;

@Component
public class ProfanityFilterStream {

  @Bean
  public BiFunction<KStream<String, ProfanityWordCheck>, KTable<String, ProfanityWord>, KStream<String, ProfanityWordResult>> process() {
    return (inputTextStream, blockWordsTable) -> (inputTextStream
            .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.getWord().split("\\s+"))
                    .stream().distinct().map(s -> new ProfanityWordSplit(s, value))
                    .toList())
            .map((key, value) -> KeyValue.pair(value.getWord(), value))
            .join(blockWordsTable, (readOnlyKey, value1, value2) -> new ProfanityWordResult(value1, value2),
                    Joined.valueSerde(new ProfanityWordSplit()))
            .filter((key, value) -> {
              var profanityWord = value.getProfanityWord();
              var profanityCheckText = value.getProfanityCheckText();
              if (profanityWord.getWord() != null) {
                return Arrays.stream(profanityCheckText.getWord().split("\\s+"))
                        .anyMatch(s -> profanityWord.getWord().equals(s));
              } else return false;
            })
            .peek((key, value) -> System.out.println(key + " value:" + value.getProfanityCheckText().getWord()))
            //.groupBy((key, value) -> value.getProfanityCheckText().getProfanityWordCheck().getWord())
            .groupBy((key, value) -> value.getProfanityCheckText().getProfanityWordCheck().getWord(), Grouped.valueSerde(new ProfanityWordResult()))
            //TODO we need to aggregate the ProfanityWords because it's returning only the latest value
            .reduce((value1, value2) -> value2)
            .toStream()
            .peek((key, value) -> System.out.println(key + " value:" + value.getProfanityCheckText().getWord())));
  }
}
