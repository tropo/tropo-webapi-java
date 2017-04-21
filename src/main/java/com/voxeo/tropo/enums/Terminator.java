package com.voxeo.tropo.enums;

public enum Terminator {

  ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, STAR, POUND;

  @Override
  public String toString() {

    switch (this) {
      case ZERO:
        return "0";
      case ONE:
        return "1";
      case TWO:
        return "2";
      case THREE:
        return "3";
      case FOUR:
        return "4";
      case FIVE:
        return "5";
      case SIX:
        return "6";
      case SEVEN:
        return "7";
      case EIGHT:
        return "8";
      case NINE:
        return "9";
      case STAR:
        return "*";
      case POUND:
        return "#";
      default:
        return null;
    }
  }
}
