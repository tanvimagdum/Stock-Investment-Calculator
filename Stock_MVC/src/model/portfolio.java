package model;

/**
 * A
 */

import java.util.ArrayList;

public interface portfolio {

  /**
   * Returns an array list of stocks.
   * @return arraylist containing stocks with a
   *          ticker number and count of stocks
   */
  ArrayList<Pair<String, Float>> returnList();

  /**
   * Get the name of portfolio.
   * @return the portfolio name as a string
   */
  String getPortfolioName();

  /**
   * Get the list of ticker numbers in the portfolio.
   * @return string array of ticker numbers
   */
  String[] getTickers();

  /**
   * Get the list of counts of each stock in the portfolio.
   * @return float array of counts of stocks
   */
  Float[] getCounts();

}

