package com.devgurupk.frauddetectionsystem.controller;

import com.devgurupk.frauddetectionsystem.model.ProfanityWord;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/profanityWords")
public class ProfanityResultController {
  @Autowired
  private InteractiveQueryService interactiveQueryService;

  @GetMapping("{key}")
  public ResponseEntity<ProfanityWord> get(@PathVariable String key){
    final ReadOnlyKeyValueStore<String, ProfanityWord> songStore =
            interactiveQueryService.getQueryableStore("profanity_words_table", QueryableStoreTypes.<String, ProfanityWord>keyValueStore());
    songStore.all().forEachRemaining(stringStringKeyValue -> {
      System.out.println(stringStringKeyValue.value);
    });
    return ResponseEntity.ok(songStore.get(key));

  }
}
