package controller;

import model.Pair;
import model.portfolio;

import java.util.ArrayList;
import java.util.Date;

/**
 * This interface represents methods to
 */

public interface portfolioController {

  /**
   * Build a portfolio from scratch by getting several stocks containing
   * ticker number and stock count from user, and the portfolio name in
   * which the input stocks are to be added.
   * @param list a list of stocks with ticker number and count of stocks
   * @param name name of the portfolio
   */
  public void portBuilder(ArrayList<Pair<String, Float>> list, String name);

  /**
   * Get the portfolio by portfolio name.
   * @param name name of the portfolio to be retrieved
   * @return the retrieved portfolio
   */
  public portfolio getPortfolio(String name);

  /**
   * Read a portfolio file in the program.
   * @param filename name of the portfolio file to be read
   */
  public void readPortfolioFile(String filename);

  /**
   * Get names of all the portfolios in the system.
   * @return the string array containing names
   *          of portfolios
   */
  public String[] getPortfolioNames();

  /**
   * Get the value of a specified portfolio for a certain input date.
   * @param name name of the portfolio
   * @param date certain date at which portfolio value
   *             needs to be known
   * @return the string array of stock values at inputted date
   */
  public String[] getPortfolioValue(String name, Date date);

  /**
   * Get the ticker numbers and count of stocks together
   * for a specified name of portfolio.
   * @param name name of the portfolio
   * @return the string array of contents of the portfolio
   *          as pairs of ticker number and count of stocks
   */
  public String[] getPortfolioContents(String name);

  /**
   * Get the ticker numbers for all stocks in a portfolio.
   * @param name name of the portfolio
   * @return the string array of all tickers in the
   *          specified portfolio
   */
  public String[] getTickers(String name);

  /**
   * Get the count of stocks for all stocks in a portfolio.
   * @param name name of the portfolio
   * @return the float array of all the stock counts
   *          in the specified portfolio
   */
  public Float[] getCounts(String name);

}