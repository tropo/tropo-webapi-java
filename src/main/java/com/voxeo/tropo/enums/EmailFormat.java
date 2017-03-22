package com.voxeo.tropo.enums;

public enum EmailFormat {

  PLAIN,ENCODED;

@Override public String toString() {
    
    String s = super.toString();
    return s.toLowerCase();
  }
}
