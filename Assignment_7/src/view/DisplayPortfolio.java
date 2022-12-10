package view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@code DisplayPortfolio} represents the view of the mvc architecture.
 * This interface contains method definitions for the functions to display composition,
 * display valuation, display menu as well as invalid scenario.
 */
public interface DisplayPortfolio {

  /**
   * This method display the File Menu of the portfolio from the Map of Index and Files.
   * The method also uses Appendable interface that displays the output on the screen using class
   * that implements the Appendable interface.
   *
   * @param fileMap represents the Map of Index and the File at that index
   * @param out     represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayExamineFile(Map<Integer, File> fileMap, Appendable out) throws IOException;

  /**
   * This method displays the composition of the portfolio in form of Symbol and Quantity.
   *
   * @param result represents the data of the stock containing list of symbol and quantity.
   * @param out    represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayExamineComposition(List<List<String>> result, Appendable out) throws IOException;

  /**
   * This method displays the valuation of the stock present inside the portfolio.
   * The value is calculated using the API call which is displayed along with the total valuation
   * of the portfolio.
   *
   * @param result         represents the single stock valuation.
   * @param totalValuation represents the entire portfolio valutaiton.
   * @param out            represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayValuation(List<List<String>> result, String totalValuation,
                        Appendable out) throws IOException;

  /**
   * This method displays the driver menu consisting of the 5 options.
   * These option incldues creating portfolio, examining portfolio, calculating the value of
   * the portfolio as well as exit.
   *
   * @param out represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayMenu(Appendable out) throws IOException;

  /**
   * This method displays the invalid date provided by the user to calculate the valuation.
   *
   * @param date represents the date which is provided by the user.
   * @param out  represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void invalidDateError(String date, Appendable out) throws IOException;

  /**
   * This method displays the error message for the file not found in the particular format to user.
   *
   * @param out represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void printFileNotFound(Appendable out) throws IOException;

  /**
   * This method displays the error message for the portfolio not found in the database.
   *
   * @param out represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayNoPortfolioFound(Appendable out) throws IOException;

  /**
   * This method displays the generic error message to the user.
   *
   * @param out     represents the Appendable object to display on the console.
   * @param message represents the generic string messsage to the user.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayMessage(Appendable out, String message) throws IOException;

  /**
   * This method displays the graph representation of the portfolio to the user.
   *
   * @param out represents the Appendable object to display on the console.
   * @param res represents the List of string containing the number of asterisks for each interval.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayGraph(Appendable out, List<String> res) throws IOException;

  /**
   * This method displays the Flexible Portfolio menu message to the user.
   *
   * @param out represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayFlexibleMenu(Appendable out) throws IOException;

  /**
   * This method displays the main menu list  to the user.
   *
   * @param out represents the Appendable object to display on the console.
   * @throws IOException if the Appendable object fails to display on the console.
   */
  void displayMainMessage(Appendable out) throws IOException;
}
