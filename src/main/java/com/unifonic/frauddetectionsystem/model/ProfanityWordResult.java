package com.unifonic.frauddetectionsystem.model;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfanityWordResult extends JsonSerde<ProfanityWordResult> {
  private ProfanityWordSplit profanityCheckText;
  private ProfanityWord profanityWord;

  public ProfanityWordResult() {
  }

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

  public static class ProfanityResultAggregator extends JsonSerde<ProfanityResultAggregator>
  {
    List<ProfanityWordResult> profanityWordResults;

    public ProfanityResultAggregator()
    {
      this.profanityWordResults = new ArrayList<>();
    }

    public void adder(ProfanityWordResult profanityWordResultAdder)
    {
      if (profanityWordResultAdder != null)
      {
        this.profanityWordResults.add(profanityWordResultAdder);
      }
    }

    public void remover(ProfanityWordResult profanityWordResultRemover) {
      this.profanityWordResults.removeIf(profanityWordResult -> Objects.equals(profanityWordResult.getProfanityCheckText()
                      .getProfanityWordCheck().getWord(),
              profanityWordResultRemover.getProfanityCheckText().getProfanityWordCheck().getWord()));
    }

    public List<ProfanityWordResult> getProfanityWordResults() {
      return profanityWordResults;
    }
  }
}
