package model;

import java.util.ArrayList;

/**
 * An interface to describe the general properties of an investment strategy.
 */
public interface Strategy<A,B,C,D,E> {
  ArrayList<Stock<A,B>> getList();
  B getB();
  C getC();
  D getD();
  E getE();
}
