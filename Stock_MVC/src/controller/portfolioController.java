package controller;

import model.Stock;
import model.portfolio;
import view.viewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A controller interface to send the
 * inputs acquired from input controller
 * to further processing and take inputs
 * from users required for internal operations
 * other than selecting from menu.
 */

public interface portfolioController {

  /**
   * This method passes the stocks for a particular portfolio
   * for processing to model.
   * @param list list of stocks
   * @param name name of portfolio
   */
  public void portBuilder(ArrayList<Stock<String, Float>> list, String name);

  /**
   * This method calls the method in model to get
   * the desired portfolio.
   * @param name name of portfolio
   * @return the desired portfolio
   */
  public portfolio getPortfolio(String name);

  /**
   * This method calls the method in model to read
   * the desired portfolio.
   * @param filename file to be read
   * @throws FileNotFoundException if the file is missing or name is invalid
   */
  public void readPortfolioFile(String filename) throws FileNotFoundException;

  /**
   * This method calls the method in model to save or write
   * the desired portfolio.
   * @param filename file to be saved/write
   * @throws IOException if the file is invalid
   */
  public void savePortfolio(String filename) throws IOException;

  /**
   * This method calls the method in model to get
   * all the portfolio names loaded in the system.
   * @return a string of portfolio names
   */
  public String[] getPortfolioNames();

  /**
   * This method
   * @param view an object of viewInterface to send messages
   *          to UI to allow user to input values required
   *          for selecting a portfolio
   * @return selected portfolio name
   */
  public String selectPortfolio(viewInterface view);

  /**
   * This method calls the method in model
   * to get the value of stocks of a portfolio
   * on a certain date.
   * @param name name of the portfolio
   * @param date the desired date at which value is to be known
   * @return the content/description and the total value of the portfolio
   *          in a string array
   */
  public String[] getPortfolioValue(String name, String date) throws IOException;

  /**
   * This method calls the method in model
   * to get the value of stocks of a portfolio
   * on a 10/31/2022
   * @param name name of the portfolio
   * @return the content/description and the total value of the portfolio
   *          in a string array
   */
  public String[] getPortfolioValueLatest(String name) throws IOException;

  /**
   * This method calls the method in model
   * to get the contents of a specified portfolio.
   * @param name name of the portfolio
   * @return the contents of the specified portfolio
   *          as ticker number and count of stocks
   *          in a string array
   */
  public String[] getPortfolioContents(String name);

  /**
   * This method calls the method in model to create
   * a portfolio.
   * @param v an object of viewInterface to send messages
   *          to UI to allow user to input values required
   *          for building a portfolio
   */
  public void buildPortfolio(viewInterface v) throws IOException;

  /**
   * A method to find value of a portfolio by manually
   * adding the stock values.
   * @param name name of the portfolio
   * @param v an object of viewInterface to send messages
   *        to UI to allow user to input values required
   *        for the manual Valuation of a portfolio
   * @return the contents along with the values of individual stocks
   *          of a portfolio in the form of string array
   */
  public String[] manualValuation(String name, viewInterface v);

  /**
   * This method calls the method in model
   * to get all the tickers in a specified portfolio.
   * @param name name of the portfolio
   * @return the string array containing all the tickers
   */
  public String[] getTickers(String name);

  /**
   * This method calls the method in model
   * to get all the stock counts in a specified portfolio.
   * @param name name of the portfolio
   * @return the string array containing all the stock counts
   */
  public Float[] getCounts(String name);

}