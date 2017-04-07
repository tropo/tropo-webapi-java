package com.voxeo.tropo.enums;

public enum AsrLogSecurity {

  NONE, SUPPRESS, MASK;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }
}
