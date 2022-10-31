package model;

import java.util.ArrayList;

/**
 * A class representing the composition
 * of a portfolio.
 */


public class portfolioImpl implements portfolio {
  private final ArrayList<Stock<String, Float>> stockList;
  private final String portfolioName;

  /**
   *
   * @param tempStockList the list of stocks
   * @param name name of the portfolio
   */
  private portfolioImpl(ArrayList<Stock<String, Float>> tempStockList, String name){
    stockList = tempStockList;
    portfolioName = name;
  }

  /**
   *
   * @return the final portfolio
   */
  public static portfolioBuilder builder() {
    return new portfolioBuilder();
  }

  /**
   *
   */

  public static final class portfolioBuilder {
    private ArrayList<Stock<String, Float>> tempStockList;
    private String tempName = "";

    /**
     * Create a final portfolio after all the stocks information
     * has been entered and portfolio preparation is Done.
     * @param list list of stocks entered
     * @param name name of the portfolio
     * @return
     */
    public portfolioImpl build(ArrayList<Stock<String, Float>> list, String name) {
      tempName = name;
      tempStockList = list;
      return new portfolioImpl(tempStockList, tempName);
    }

  }

  @Override
  public ArrayList<Stock<String,Float>> returnList() {
    return stockList;
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public String[] getTickers() {
    int size = stockList.size();
    String[] tickers = new String[size];
    for (int i = 0; i < size; i++) {
      tickers[i] = stockList.get(i).getS();
    }
    return tickers;
  }

  @Override
  public Float[] getCounts() {
    int size = stockList.size();
    Float[] counts = new Float[size];
    for (int i = 0; i < size; i++) {
      counts[i] = stockList.get(i).getF();
    }
    return counts;
  }
}
