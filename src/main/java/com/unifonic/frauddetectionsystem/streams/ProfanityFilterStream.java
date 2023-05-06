package com.unifonic.frauddetectionsystem.streams;

import com.unifonic.frauddetectionsystem.model.ProfanityWord;
import com.unifonic.frauddetectionsystem.model.ProfanityWordCheck;
import com.unifonic.frauddetectionsystem.model.ProfanityWordSplit;
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
  public BiFunction<KStream<String, ProfanityWordCheck>, KTable<String, ProfanityWord>, KStream<String, ProfanityWordResult.SmsSmsRecipientStatusAggregator>> process() {
    return (inputTextStream, blockWordsTable) -> (inputTextStream
            .flatMapValues((readOnlyKey, value) -> Arrays.asList(value.getWord().split("\\s+"))
                    .stream().map(s -> new ProfanityWordSplit(s, value))
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
            .toTable(Materialized.with(Serdes.String(), new ProfanityWordResult()))
            .groupBy((key, value) -> {
             return KeyValue.pair(value.getProfanityCheckText().getProfanityWordCheck().getWord(),value);
            }, Grouped.valueSerde(new ProfanityWordResult()))
            //TODO we need to aggregate the ProfanityWords because it's returning only the latest value
            .aggregate(ProfanityWordResult.SmsSmsRecipientStatusAggregator::new, (key, value, aggregate) -> {
              if (key != null)
              {
                aggregate.adder(value);
              }
              return aggregate;
            }, (key, value, agg) -> {
                      agg.remover(value);
                      return agg;
                    },
                    Materialized.with(Serdes.String(), new ProfanityWordResult.SmsSmsRecipientStatusAggregator()))
            .toStream()
            .filter((key, value) -> value != null && !CollectionUtils.isEmpty(value.getSmsSmsRecipientStatusModels()))
            .peek((key, value) -> {
              System.out.println(key + " value:" + value.getSmsSmsRecipientStatusModels().iterator().next().getProfanityCheckText().getWord());
            }));
  }
}
