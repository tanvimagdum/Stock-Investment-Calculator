package model;

/**
 * A class that defines new object and its methods containing
 * two components - ticker number and count of stocks.
 * @param <S> the ticker number as string
 * @param <F> the count of stocks as float
 */

public class Stock<S,F> implements StockInterface {
  S dataOne;
  F dataTwo;

  public S getS() {
    return dataOne;
  }

  public F getF() {
    return dataTwo;
  }

  public Stock(S newDataOne, F newDataTwo) {
    dataOne = newDataOne;
    dataTwo = newDataTwo;
  }

}