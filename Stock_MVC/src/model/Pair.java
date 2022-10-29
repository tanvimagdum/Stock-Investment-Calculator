package model;

public class Pair<S,F> {
  S dataOne;
  F dataTwo;

  public S getS() {
    return dataOne;
  }

  public F getF() {
    return dataTwo;
  }

  public Pair(S newDataOne, F newDataTwo) {
    dataOne = newDataOne;
    dataTwo = newDataTwo;
  }

}
