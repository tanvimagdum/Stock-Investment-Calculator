package model;


import java.util.ArrayList;

public interface portfolio {
  ArrayList<Stock<String, Float>> returnList();
  String getPortfolioName();
  String[] getTickers();
  Float[] getCounts();

}