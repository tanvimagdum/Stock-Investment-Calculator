package model;

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