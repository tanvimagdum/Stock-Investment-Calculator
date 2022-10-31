package model;

/**
 * A class to introduce new variable
 * which stores stocks which contains
 * two components - ticker number and count of stocks
 * @param <S> the ticker number as string
 * @param <F> the count of stocks as float
 */

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
