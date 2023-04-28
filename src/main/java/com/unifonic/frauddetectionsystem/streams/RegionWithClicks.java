package com.unifonic.frauddetectionsystem.streams;

public class RegionWithClicks {
  private final String str;
  private final String value;

  public RegionWithClicks(String str, String value) {
    this.str = str;
    this.value = value;
  }

  public String getStr() {
    return str;
  }

  public String getValue() {
    return value;
  }
}
