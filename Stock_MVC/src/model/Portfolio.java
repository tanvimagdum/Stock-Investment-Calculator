package model;


/**
 * A portfolio interface to define the structure of a portfolio.
 * Changes:
 * 1. Removed returnPortfolio() as this method is now defunct.
 */

public interface Portfolio {


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