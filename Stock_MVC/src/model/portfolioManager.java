package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * An interface containing methods to perform
 * operations on a portfolio.
 */

public interface portfolioManager {

  /**
   * Build a portfolio from scratch by getting several stocks containing
   * ticker number and stock count from user, and the portfolio name in
   * which the input stocks are to be added.
   * @param list a list of stocks with ticker number and count of stocks
   * @param name name of the portfolio
   */
  public void portBuilder(ArrayList<Stock<String, Float>> list, String name);

  /**
   * Get the portfolio by portfolio name.
   * @param name name of the portfolio to be retrieved
   * @return the retrieved portfolio
   */
  public portfolio getPortfolio(String name);

  /**
   * Read a portfolio file in the program.
   * @param filename name of the portfolio file to be read
   * @throws FileNotFoundException if the file is missing or format is incorrect
   */
  public void readPortfolioFile(String filename) throws IOException;

  /**
   *
   * @param portfolioName
   * @throws IOException
   */
  public void savePortfolio(String portfolioName) throws IOException;

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
   * @return the content of portfolio and the total value in
   *          a string array
   */
  public String[] getPortfolioValue(String name, String date) throws IOException, ParseException;

  /**
   * Get the value of a specified portfolio for 10/31/2022
   * @param name name of the portfolio
   * @return the content of portfolio and the total value in
   *          a string array
   */
  public String[] getPortfolioValueLatest(String name) throws IOException;
  /**
   * Get the ticker numbers and count of stocks together
   * for a specified name of portfolio.
   * @param name name of the portfolio
   * @return the string array of content of the portfolio
   *          as pairs of ticker number and count of stocks
   */
  public String[] getPortfolioContents(String name);

  /**
   * Check whether the ticker is on the approved list of stocks.
   * @param ticker the symbol being checked
   * @return true if the ticker is recognized, false otherwise
   */
  public boolean validateTicker(String ticker) throws IOException;

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
