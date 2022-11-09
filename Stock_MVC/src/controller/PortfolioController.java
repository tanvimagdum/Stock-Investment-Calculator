package controller;

import model.Portfolio;
import view.ViewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;


/**
 * A controller interface to send the
 * inputs acquired from input controller
 * to further processing and take inputs
 * from users required for internal operations
 * other than selecting from menu.
 */

public interface PortfolioController {


  /**
   * This method calls the method in model to read
   * the desired portfolio.
   *
   * @param filename file to be read
   * @throws FileNotFoundException if the file is missing or name is invalid
   */
  public void readPortfolioFile(String filename) throws IOException;

  /**
   * This method calls the method in model to save or write
   * the desired portfolio.
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
   * This method prompts the user to select a portfolio from those loaded in the program
   * then returns the name of the portfolio.
   *
   * @param view an object of viewInterface to send messages
   *             to UI to allow user to input values required
   *             for selecting a portfolio
   * @return selected portfolio name
   */
  public String selectPortfolio(ViewInterface view);

  /**
   * This method calls the method in model
   * to get the value of stocks of a portfolio
   * on a certain date.
   *
   * @param name name of the portfolio
   * @param date the desired date at which value is to be known
   * @return the content/description and the total value of the portfolio
   *         in a string array
   * @throws IOException if there is difficulty reading files
   * @throws ParseException if there is a wrong string trying to be read in as a float
   */
  public String[] getPortfolioValue(String name, String date) throws IOException, ParseException;

  /**
   * This method calls the method in model to get the value of stocks of a portfolio
   * on 10/31/2022.
   *
   * @param name name of the portfolio
   * @return the content/description and the total value of the portfolio
   *         in a string array
   * @throws IOException if there is difficulty reading files
   */
  public String[] getPortfolioValueLatest(String name) throws IOException;

  /**
   * This method calls the method in model
   * to get the contents of a specified portfolio.
   *
   * @param name name of the portfolio
   * @return the contents of the specified portfolio
   *         as ticker number and count of stocks
   *         in a string array
   */
  public String[] getPortfolioContents(String name);

  /**
   * This method calls the method in model to create
   * a portfolio.
   *
   * @param v an object of viewInterface to send messages
   *          to UI to allow user to input values required
   *          for building a portfolio
   * @throws IOException if there is difficulty reading in files
   */
  public void buildPortfolio(ViewInterface v) throws IOException;

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
  public String[] manualValuation(String name, ViewInterface v);

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

}