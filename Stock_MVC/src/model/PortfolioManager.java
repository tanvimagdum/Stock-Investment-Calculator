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

public interface PortfolioManager {

  /**
   * Build a portfolio from scratch by getting several stocks containing
   * ticker number and stock count from user, and the portfolio name in
   * which the input stocks are to be added.
   *
   * @param tickerList a list of stocks with ticker number
   * @param floatList  a list of counts of stocks
   * @param name       name of the portfolio
   */
  public void portBuilder(ArrayList<String> tickerList, ArrayList<Float> floatList, String name);

  /**
   * Build a flexible portfolio that is editable.
   *
   * @param name       name of the portfolio
   */
  public void portFlexBuilder(String name);

  /**
   * Read a portfolio file in the program.
   *
   * @param filename name of the portfolio file to be read
   * @throws FileNotFoundException if the file is missing or format is incorrect
   */
  public String readPortfolioFile(String filename) throws IOException, ParseException;

  /**
   * Attempts to write the selected portfolio out to a csv. The file will be given the name of
   * the portfolio.
   *
   * @param portfolioName denotes the title of the portfolio to be saved
   * @throws IOException when there's trouble writing the file
   */
  public void savePortfolio(String portfolioName) throws IOException;

  /**
   * Get names of all the portfolios in the system.
   *
   * @return the string array containing names
   *         of portfolios
   */
  public String[] getPortfolioNames();

  /**
   * Get names of all the flexible portfolios in the system.
   *
   * @return the string array containing names
   *         of flexible portfolios
   */
  public String[] getFlexPortfolioNames();

  /**
   * Get the value of a specified portfolio for a certain input date.
   *
   * @param name name of the portfolio
   * @param date certain date at which portfolio value
   *             needs to be known
   * @return the content of portfolio and the total value in
   *         a string array
   * @throws IOException if there is difficulty reading in files
   * @throws ParseException if there is an incorrect entry in a file being read
   */
  public float[] getPortfolioValue(String name, String date) throws IOException, ParseException;

  /**
   *
   * @param name
   * @param ticker
   * @param count
   * @param date
   * @return
   */
  public boolean checkFlexEdit(String name, String ticker, float count, Date date);

  /**
   * Check whether the ticker is on the approved list of stocks.
   *
   * @param ticker the symbol being checked
   * @return true if the ticker is recognized, false otherwise
   * @throws IOException if there is difficulty reading the reference file
   */
  public boolean validateTicker(String ticker) throws IOException;

  /**
   * Check whether the ticker is on the approved and the given date is after the stock's IPO.
   *
   * @param ticker the symbol being checked
   * @return true if the ticker is recognized, false otherwise
   * @throws IOException if there is difficulty reading the reference file
   * @throws ParseException if there is difficulty reading the date off the reference file
   */
  public boolean validateTicker(String ticker, Date date) throws IOException, ParseException;

  /**
   *
   * @param name
   * @param ticker
   * @param count
   * @param date
   * @throws IllegalArgumentException
   */
  public void editFlexPortfolio(String name, String ticker, Float count, Date date)
                                throws IllegalArgumentException;

  public float[] getCostBasis(String name, String date) throws ParseException, IOException;

  public float[] portfolioPerformance(String name, Date[] dates);

  /**
   * Get the ticker numbers for all stocks in a portfolio.
   *
   * @param name name of the portfolio
   * @return the string array of all tickers in the
   *         specified portfolio
   */
  public String[] getTickers(String name);

  /**
   * Get the count of stocks for all stocks in a portfolio.
   *
   * @param name name of the portfolio
   * @return the float array of all the stock counts
   *         in the specified portfolio
   */
  public Float[] getCounts(String name);

  /**
   * Get the purchased/sold date for all stocks in a portfolio.
   *
   * @param name name of the portfolio
   * @return the date array for all the stocks
   *         in the specified portfolio
   */
  public Date[] getDates(String name) throws IllegalArgumentException;

  public float getCommissionFee();

  public void setCommissionFee(float fee);

}


