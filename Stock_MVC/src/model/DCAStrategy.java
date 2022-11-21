package model;

import java.util.ArrayList;

public class DCAStrategy<A,B,C,D,E> implements Strategy<A,B,C,D,E>{
  B amount;
  C startDate;
  D endDate;
  E frequency;
  ArrayList<Stock<A,B>> list = new ArrayList<>();

  DCAStrategy(ArrayList<Stock<A,B>> lst, B amt, C sd, D ed, E freq) {
   this.list = lst;
   this.amount = amt;
   this.startDate = sd;
   this.endDate = ed;
   this.frequency = freq;
  }

  @Override
  public ArrayList<Stock<A, B>> getList() {
    return this.list;
  }

  @Override
  public B getB() {
    return this.amount;
  }

  @Override
  public C getC() {
    return this.startDate;
  }

  @Override
  public D getD() {
    return this.endDate;
  }

  @Override
  public E getE() {
    return this.frequency;
  }

}
