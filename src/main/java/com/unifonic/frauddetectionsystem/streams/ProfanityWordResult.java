package com.unifonic.frauddetectionsystem.streams;

import com.unifonic.frauddetectionsystem.model.ProfanityWord;
import com.unifonic.frauddetectionsystem.model.ProfanityWordSplit;
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

  public static class SmsSmsRecipientStatusAggregator extends JsonSerde<SmsSmsRecipientStatusAggregator>
  {
    List<ProfanityWordResult> smsSmsRecipientStatusModels;

    public SmsSmsRecipientStatusAggregator()
    {
      this.smsSmsRecipientStatusModels = new ArrayList<>();
    }

    public void adder(ProfanityWordResult smsSmsRecipientStatusModel)
    {
      if (smsSmsRecipientStatusModel != null)
      {
        this.smsSmsRecipientStatusModels.add(smsSmsRecipientStatusModel);
      }
    }

    public void remover(ProfanityWordResult kSmsSmsRecipientStatusModel)
    {
      this.smsSmsRecipientStatusModels.removeIf(smsModel -> Objects.equals(smsModel.getProfanityCheckText().getProfanityWordCheck().getWord(),
              kSmsSmsRecipientStatusModel.getProfanityCheckText().getProfanityWordCheck().getWord()));
    }

    public List<ProfanityWordResult> getSmsSmsRecipientStatusModels() {
      return smsSmsRecipientStatusModels;
    }
  }
}
