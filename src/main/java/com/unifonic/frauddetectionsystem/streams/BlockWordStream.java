package com.unifonic.frauddetectionsystem.streams;

import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class BlockWordStream {

  //@Bean
  public Consumer<KTable<String, String>> toTable() {
    return kTable -> {};
  }

}
