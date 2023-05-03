package com.unifonic.frauddetectionsystem.model;

import java.util.UUID;

public class ProfanityWord {
  private UUID id;

  private String word;

  private String type;

  private String country;

  private Boolean global;

  private String account;

  private String subAccount;

  private String senderId;

  public ProfanityWord() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Boolean getGlobal() {
    return global;
  }

  public void setGlobal(Boolean global) {
    this.global = global;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getSubAccount() {
    return subAccount;
  }

  public void setSubAccount(String subAccount) {
    this.subAccount = subAccount;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

}
