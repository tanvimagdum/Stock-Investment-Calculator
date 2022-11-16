package controller;

import view.ViewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;


/**
 * A controller interface to send the inputs acquired from input controller
 * to further processing and take inputs from users required for internal operations
 * other than selecting from menu.
 *
 * Changes:
 * 1. Removed returnPortfolio() as it was removed in the model.
 * 2. Removed getPortfolioContents() as other methods are sufficient.
 * 3. readPortfolioFile() and buildPortfolio() now return the portfolio name for convenience.
 * 4. buildPortfolio(), manualValuation(), and selectPortfolio() now take a scanner as input.
 *    This is in keeping with maintaining a single scanner in the Input Controller.
 * 5. Now takes the model in construction, making this class more flexible and aiding testing.
 * 6. Removed getPortfolioValueLatest() as the method was removed in model.
 * 7. Several functions that invoke the model now pass an API object, to keep the API in the
 *    controller.
 */

public interface PortfolioController {


  /**
   * This method calls the method in model to read the desired portfolio.
   *
   * @param filename file to be read
   * @throws FileNotFoundException if the file is missing or name is invalid
   */
  public String readPortfolioFile(String filename) throws IOException, ParseException;

  /**
   * This method calls the method in model to save or write the desired portfolio.
   *
   * @param filename file to be saved/write
   * @throws IOException if the file is invalid
   */
  public void savePortfolio(String filename) throws IOException;

  /**
   * This method calls the method in model to get
   * all the portfolio names loaded in the system.
   *
   * @return a string of portfolio names
   */
  public String[] getPortfolioNames();

  /**
   * This method calls the method in model to get
   * all the flexible portfolio names loaded in the system.
   *
   * @return a string of flexible portfolio names
   */
  String[] getFlexPortfolioNames();

  /**
   * Makes a change to a flexible portfolio by adding a stock item representing either a buy
   * or a sell.
   *
   * @param name the name of the portfolio
   * @param v the view interface the controller speaks through
   * @param sc the scanner object for the controller to take input
   * @throws IllegalArgumentException if the edit breaks the rules
   */
  void editFlexPortfolio(String name, ViewInterface v, Scanner sc) throws IllegalArgumentException, IOException, ParseException;

  /**
   * This method prompts the user to select a portfolio from those loaded in the program
   * then returns the name of the portfolio.
   *
   * @param view an object of viewInterface to send messages
   *             to UI to allow user to input values required
   *             for selecting a portfolio
   * @return selected portfolio name
   */
  public String selectPortfolio(ViewInterface view, Scanner sc);

  /**
   * This method prompts the user to select a flexible portfolio from those loaded in the program
   * then returns the name of the portfolio.
   *
   * @param view an object of viewInterface to send messages
   *             to UI to allow user to input values required
   *             for selecting a flexible portfolio
   * @return selected flexible portfolio name
   */
  String selectFlexPortfolio(ViewInterface view, Scanner sc);

  /**
   * This method calls the method in model to get the value of stocks of a portfolio
   * on a certain date.
   *
   * @param name name of the portfolio
   * @param date the desired date at which value is to be known, 'yyyy-MM-dd'
   * @return the content/description and the total value of the portfolio
   *         in a string array
   * @throws IOException if there is difficulty reading files
   * @throws ParseException if there is a wrong string trying to be read in as a float
   */
  public float[] getPortfolioValue(String name, String date, controller.API api) throws IOException, ParseException;

  /**
   * Gets the cost basis for a portfolio on a given date, via API calls.
   *
   * @param name the name of the portfolio
   * @param date the date to be used: must be 'yyyy-MM-dd'
   * @param api the object making API calls
   * @return a list of prices
   * @throws ParseException if the date is incorrectly entered or not a real date
   * @throws IOException if the API encounters difficulty
   */
  public float[] getCostBasis(String name, String date, controller.API api) throws ParseException, IOException;

  /**
   * This method calls the method in model to create
   * a portfolio.
   *
   * @param v an object of viewInterface to send messages
   *          to UI to allow user to input values required
   *          for building a portfolio
   * @throws IOException if there is difficulty reading in files
   */
  public String buildPortfolio(ViewInterface v, Scanner sc) throws IOException;

  /**
   * This method calls the method in model to build
   * a flexible portfolio.
   *
   * @param v an object of viewInterface to send messages
   *          to UI to allow user to input values required
   *          for building a portfolio
   * @throws IOException if there is difficulty reading in files
   */
  public String buildFlexPortfolio(ViewInterface v, Scanner sc) throws IOException, ParseException;

  /**
   * A method to find value of a portfolio by manually
   * adding the stock values.
   *
   * @param name name of the portfolio
   * @param v    an object of viewInterface to send messages
   *             to UI to allow user to input values required
   *             for the manual Valuation of a portfolio
   * @return the contents along with the values of individual stocks
   *         of a portfolio in the form of string array
   */
  public String[] manualValuation(String name, ViewInterface v, Scanner sc);

  /**
   * Collects a list of total portfolio values across the provided dates.
   *
   * @param name  the name of the flexible portfolio
   * @param dates the dates to get values for
   * @return the value of the portfolio on those dates
   */
  public float[] portfolioPerformance(String name, Date[] dates, controller.API api) throws IOException, ParseException;

  /**
   * This method calls the method in model
   * to get all the tickers in a specified portfolio.
   *
   * @param name name of the portfolio
   * @return the string array containing all the tickers
   */
  public String[] getTickers(String name);

  /**
   * This method calls the method in model
   * to get all the stock counts in a specified portfolio.
   *
   * @param name name of the portfolio
   * @return the string array containing all the stock counts
   */
  public Float[] getCounts(String name);

  /**
   * This method calls the method in model to get purchased/sold date for all the stocks in a
   * specified portfolio.
   *
   * @param name name of the portfolio
   * @return the string array containing dates for all stocks
   */
  public Date[] getDates(String name) throws IllegalArgumentException;

  /**
   * Accesses the current commission fee from the model.
   *
   * @return the commission fee
   */
  public float getCommissionFee();

  /**
   * Allows the commission fee to be set.
   *
   * @param fee the value to set the commission fee to
   */
  public void setCommissionFee(float fee);


}