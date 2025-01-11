package com.restaurant.restaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClientType {
  COMUN,
  FRECUENT;

  @JsonCreator
  public static ClientType fromString(String value) {
    return ClientType.valueOf(value.toUpperCase());
  }

  public String toValue() {
    return this.name().toUpperCase();
  }
}
