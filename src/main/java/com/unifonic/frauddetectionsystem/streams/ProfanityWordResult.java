package com.unifonic.frauddetectionsystem.streams;

import com.unifonic.frauddetectionsystem.model.ProfanityWord;
import com.unifonic.frauddetectionsystem.model.ProfanityWordSplit;

public class ProfanityWordResult {
  private final ProfanityWordSplit profanityCheckText;
  private final ProfanityWord profanityWord;

  public ProfanityWordResult(ProfanityWordSplit profanityCheckText, ProfanityWord profanityWord) {
    this.profanityCheckText = profanityCheckText;
    this.profanityWord = profanityWord;
  }

  public ProfanityWordSplit getProfanityCheckText() {
    return profanityCheckText;
  }

  public ProfanityWord getProfanityWord() {
    return profanityWord;
  }
}
