package com.unifonic.frauddetectionsystem.model;

import org.springframework.kafka.support.serializer.JsonSerde;

public class ProfanityWordSplit extends JsonSerde<ProfanityWordSplit> {
  private String word;
  private ProfanityWordCheck profanityWordCheck;

  public ProfanityWordSplit() {
  }

  public ProfanityWordSplit(String word, ProfanityWordCheck profanityWordCheck) {
    this.word = word;
    this.profanityWordCheck = profanityWordCheck;
  }

  public String getWord() {
    return word;
  }

  public ProfanityWordCheck getProfanityWordCheck() {
    return profanityWordCheck;
  }
}
