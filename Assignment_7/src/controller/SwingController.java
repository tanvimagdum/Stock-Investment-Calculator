package controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This interface represents the controller part of the architecture.
 * This interface provides definition of function such as examine portfolio, loading portfolios,
 * generating cost basis, generating valuation, performing investment strategy operations such as
 * fixed investment strategy and dollar cost averaging, and generating performance graph.
 */
public interface SwingController {
  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for the examine window to SwingView.
   */
  void examinePortfolio();

  /**
   * This function generates the panel by populating the portfolios in the form of Map.
   *
   * @return the JPanel consisting of the portfolio list.
   */
  JPanel getPortfolioList();

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for cost basis window to SwingView.
   */
  void costBasis();

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for valuation to SwingView.
   */
  void valuation();

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for buying share window to SwingView.
   */
  void buyShares();

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for performing dollar cost averaging
   * on existing file to SwingView.
   */
  void existingDollarCostAvgPanel();

  /**
   * This function calculates the cost basis of the portfolio file for the specified date.
   * This function invokes method of model that evaluates the portfolio for deriving cost basis.
   * This result is further displayed on the view in the form of table.
   *
   * @param fileName represents the portfolio name for which cost basis is derived.
   * @param date     represents the date for which the cost basis is calculated.
   */
  void getCostBasis(String fileName, String date);

  /**
   * This function calculates the valuation of the portfolio file for the specified date.
   * This function invokes method of model that evaluates the portfolio for deriving valuation.
   * This result is further displayed on the view in the form of table along with total valuation.
   *
   * @param fileName represents the portfolio name for which valuation is derived.
   * @param date     represents the date for which the valuation is calculated.
   */
  void getValuation(String fileName, String date);

  /**
   * This function calculates the composition of portfolio file by fetching symbol and quantity.
   *
   * @param fileName represents the filename for which the composition is to be calculated
   * @param date     represents the date for the composition if calculated.
   */
  void examinePortfolioList(String fileName, String date);

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to generate view for loading external portfolio
   * window to SwingView.
   */
  void uploadPortfolio();

  /**
   * This function uploads file present at path selected by the user to flexible_portfolio folder.
   *
   * @param path represents the absolute path of the uploaded file.
   */
  void getUploadedPortfolio(String path);

  /**
   * This function is used to add the stock to the portfolio.
   * This function invokes the method of the model to buy the share in the portfolio at the date.
   *
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param date          represents the date at which the transaction is supposed to be performed.
   * @param commissionFee represents the commission associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   */
  void addStock(String symbol, String quantity, String date, String commissionFee, String filename);

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to perform selling operation.
   */
  void sellShares();

  /**
   * This function is used to sell the stock from the portfolio based on validation of the date.
   * This function invokes the method of the model to sell share in the portfolio at the date.
   *
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param date          represents the date at which the transaction is supposed to be performed.
   * @param commissionFee represents the commission associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   */
  void sellStock(String symbol, String quantity, String date, String commissionFee,
                 String filename);

  /**
   * This function performs operation to delegate the creating portfolio implementation to the view.
   */
  void createPortfolio();

  /**
   * This function generates the portfolio file when create new file operation is invoked.
   *
   * @return the filename generated by the model.
   */
  String getNewPortfolio();

  /**
   * This function is used to sell the stock from the portfolio based on validation of the date.
   * This function invokes the method of the model to sell share in the portfolio at the date.
   *
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param date          represents the date at which the transaction is supposed to be performed.
   * @param commissionFee represents the commission associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   */
  void addNewStocks(String symbol, String quantity, String date, String commissionFee,
                    String filename);

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates the operation to perform portfolio performance operation.
   */
  void getPortfolioPerformance();

  /**
   * This function fetch the data from the model that generates interval data as well as scaled
   * data. Further this function delegates the operation to the view to display performance graph.
   *
   * @param portfolioName   represents the portfolio name in which transaction is performed.
   * @param selectedDate    represents the interval start date.
   * @param selectedEndDate represents the interval end date.
   * @throws ParseException           if parsing of the date operation fails.
   * @throws java.text.ParseException if parsing of the filename fails.
   * @throws IOException              if file is not found at the location.
   */
  void getPortfolioPerformanceData(String portfolioName, String selectedDate,
                                   String selectedEndDate) throws ParseException,
          java.text.ParseException, IOException;

  /**
   * This function saves the stock in list for multiple add stock operation on single portfolio.
   *
   * @param portfolioName  represents the portfolio name in which transaction is performed.
   * @param amount         represents the total amount user want to invest in portfolio.
   * @param symbol         represents the symbol which is validated over SNP500 stock symbol.
   * @param weight         represents the weight for each stock.
   * @param selectedDate   represents the date at which transaction is performed.
   * @param commissionText represents the commission value for each transaction.
   */
  void saveStocks(String portfolioName, String amount, String symbol, String weight,
                  String selectedDate, String commissionText);

  /**
   * This function performs the investment operation of all the stock present in the stock list.
   *
   * @throws IOException              if the portfolio file is not found.
   * @throws ParseException           if the parsing of the portfolio file fails.
   * @throws java.text.ParseException if the parsing of the date fails.
   */
  void investStocks() throws IOException, ParseException, java.text.ParseException;

  /**
   * This function perform investment operation of dollar cost averaging for stock in stock list.
   *
   * @param intervalDate represents the interval duration between the operation.
   * @param date         represent the end date of the transaction.
   * @throws java.text.ParseException if the parsing for date fails.
   * @throws IOException              if the file if not found.
   * @throws ParseException           if the file parsing fails.
   */
  void investDollarCost(int intervalDate, String date) throws java.text.ParseException,
          IOException, ParseException;

  /**
   * This function loads the portfolio in the current panel.
   * This function delegates operation to perform investment in existing portfolio operation.
   */
  void investInExistingPortfolio();

  /**
   * This function validates the stock properties.
   * This validation includes if the filename is not present, stock is not present in the SNP500
   * list, invalid weight is provided by user etc.
   *
   * @param portfolioName  represents the filename of the selected portfolio.
   * @param amountText     represents the total amount entered by the user.
   * @param symbolText     represents the symbol entered by the user.
   * @param weightText     represents the weight associated for the given stock.
   * @param selectedDate   represents the date selected by the user.
   * @param commissionText represents the commission provided by the user.
   * @return true if the properties are valid else false.
   * @throws IOException              if the filename if not found.
   * @throws java.text.ParseException if the date parsing fails.
   */
  boolean validateStock(String portfolioName, String amountText, String symbolText,
                        String weightText, String selectedDate, String commissionText)
          throws IOException, java.text.ParseException;

  /**
   * This function represents the callback method to from the view to generate the generic panel.
   * This panel is shared across dollar cost averaging as well as fixed portfolio.
   */
  void createNewInvestmentMenu();

  void rebalancePortfolioChoice();
  void validatePortfolio(String portfolioName, String selectedDate)
          throws ParseException, java.text.ParseException, IOException;
  void saveShares(ArrayList<String> arr);
  boolean validateShare(String symbolText, String txtQuantity, String txtCommission)
          throws IOException, java.text.ParseException;
  void rebalancePortfolio();
}
