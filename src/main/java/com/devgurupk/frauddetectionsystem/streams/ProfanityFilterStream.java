package com.devgurupk.frauddetectionsystem.streams;

import com.devgurupk.frauddetectionsystem.model.ProfanityWord;
import com.devgurupk.frauddetectionsystem.model.ProfanityWordCheck;
import com.devgurupk.frauddetectionsystem.model.ProfanityWordSplit;
import com.devgurupk.frauddetectionsystem.model.ProfanityWordResult;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.function.BiFunction;

@Component
public class ProfanityFilterStream {

  @Bean
  public BiFunction<KStream<String, ProfanityWordCheck>, KTable<String, ProfanityWord>,
          KStream<String, ProfanityWordResult.ProfanityResultAggregator>> process() {
    return (inputTextStream, blockWordsTable) -> (inputTextStream
            //TODO-1 splitText
            .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.getWord().split("\\s+"))
                    .stream().map(s -> new ProfanityWordSplit(s, value))
                    .toList())
            //TODO-2 createKeyValue
            .map((key, value) -> KeyValue.pair(value.getWord(), value))
            .join(blockWordsTable, (readOnlyKey, value1, value2) -> new ProfanityWordResult(value1, value2),
                    Joined.valueSerde(new ProfanityWordSplit()))
            //TODO-3 additionalFilters
            .filter((key, value) -> {
              var profanityWord = value.getProfanityWord();
              var profanityCheckText = value.getProfanityCheckText();
              if (profanityWord.getWord() != null) {
                return Arrays.stream(profanityCheckText.getWord().split("\\s+"))
                        .anyMatch(s -> profanityWord.getWord().equals(s));
              } else return false;
            })
            .peek((key, value) -> System.out.println(key + " value:" + value.getProfanityCheckText().getWord()))
            .toTable(Materialized.with(Serdes.String(), new ProfanityWordResult()))
            //TODO-4 aggregate
            .groupBy((key, value) -> {
             return KeyValue.pair(value.getProfanityCheckText().getProfanityWordCheck().getWord(),value);
            }, Grouped.valueSerde(new ProfanityWordResult()))
            .aggregate(ProfanityWordResult.ProfanityResultAggregator::new, (key, value, aggregate) -> {
              if (key != null)
              {
                aggregate.adder(value);
              }
              return aggregate;
            }, (key, value, agg) -> {
                      agg.remover(value);
                      return agg;
                    },
                    Materialized.with(Serdes.String(), new ProfanityWordResult.ProfanityResultAggregator()))
            .toStream()
            //TODO-5 filter
            .filter((key, value) -> value != null && !CollectionUtils.isEmpty(value.getProfanityWordResults()))
            .peek((key, value) -> {
              System.out.println(key + " value:" + value.getProfanityWordResults().iterator().next().getProfanityCheckText().getWord());
            }));
  }
}
