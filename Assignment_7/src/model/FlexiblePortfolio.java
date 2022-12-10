package model;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * {@code FlexiblePortfolio} represents the interface for operations related to flexible interface.
 * This class has methods to perform operations on portfolios such as buy, sell, calculate cost
 * basis, generate valuation, generate total valuation and generate performance graph.
 */
public interface FlexiblePortfolio extends StockPortfolio {

  /**
   * This method performs operation to buy the share at the particular date in the particular file.
   *
   * @param symbol        represents the ticker symbol (SNP500 Symbols) as input.
   * @param quantity      represents the integer quantity for the stock.
   * @param date          represents the date at which the transaction is performed.
   * @param commissionFee represents the fee associated with each transaction.
   * @param filename      represents filename in which the above parameters will be written.
   * @return true if the buy operation is successful else return false
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   */
  boolean buyShares(String symbol, double quantity, String date, double commissionFee,
                    String filename)
          throws ParseException, IOException, java.text.ParseException;

  /**
   * This method performs operation to buy the share at the particular date in the particular file.
   *
   * @param symbol        represents the ticker symbol (SNP500 Symbols) as input.
   * @param quantity      represents the integer quantity for the stock.
   * @param date          represents the date at which the transaction is performed.
   * @param commissionFee represents the fee associated with each transaction.
   * @param filename      represents filename in which the above parameters will be written.
   * @return true if the sell operation is successful else return false
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   */
  boolean sellShares(String symbol, double quantity, String date, double commissionFee,
                     String filename)
          throws ParseException, IOException, NoSuchFieldException, java.text.ParseException;

  /**
   * This method calculates the cost basis of the portfolio for the input date.
   * Cost basis is calculated on the formula that if operation is buy, then cost is total valuation
   * plus commission on the total valuation and if the operation is sell then cost is commission on
   * total valuation.
   *
   * @param filename represents the filename of which cost basis is to be calculated.
   * @param date     represents the date at which the cost basis of the portfolio is the calculated.
   * @return the cost basis of the portfolio
   * @throws ParseException if error in json parsing.
   * @throws IOException    if input mismatch occurs.
   */
  double calculateCostBasis(String filename, String date) throws ParseException, IOException,
          java.text.ParseException;

  /**
   * This function calculates the valuation of the flexible portfolio calculated till input date.
   *
   * @param filename represents the filename of which valuation is to be calculated.
   * @param date     represents the date at which the cost basis of the portfolio is the calculated.
   * @return list of list with each representing symbol, price, quantity, date and valuation.
   * @throws ParseException           if error in json parsing.
   * @throws IOException              if input mismatch occurs.
   * @throws java.text.ParseException if unable to parse string.
   */
  List<List<String>> getFlexiblePortfolioValuation(String filename, String date)
          throws ParseException, IOException, java.text.ParseException;

  /**
   * This function generates the total valuation of the entire portfolio.
   *
   * @param stocks represents the stocks present in the portfolio.
   * @return total valuation of the entire portfolio.
   */
  String getFlexiblePortfolioTotalValuation(List<List<String>> stocks);

  /**
   * This function generates the composition of the portfolio till date with symbol and quantity.
   *
   * @param filename represents the filename of which composition is to be calculated.
   * @param date     represents the date at which the composition of the portfolio is to be
   *                 displayed.
   * @return list of list string with each list representing stockname, quantity of each portoflio.
   * @throws ParseException           if error in json parsing.
   * @throws java.text.ParseException if unable to parse string.
   * @throws IOException              if input mismatch occurs.
   */
  List<List<String>> getFlexiblePortfolioComposition(String filename, String date)
          throws ParseException, java.text.ParseException, IOException;

  /**
   * This functions creates the flexible portfolio file.
   *
   * @return String representing the filename of the generated portfolio.
   * @throws IOException if input mismatch occurs.
   */
  String createFlexiblePortfolio() throws IOException;

  /**
   * This function generates the list of string with each string representing the intervals, price.
   *
   * @param startDate represents start date of the interval.
   * @param endDate   represents end date of the interval.
   * @param fileName  represents the filename of which graph is to be calculated.
   * @return list of string with intervals and price.
   * @throws ParseException           if error in json parsing.
   * @throws java.text.ParseException if unable to parse string.
   * @throws IOException              if input mismatch occurs.
   */
  List<String> generateAndDisplayStockGraph(String startDate, String endDate, String fileName)
          throws ParseException, java.text.ParseException, IOException;

  /**
   * This method scales the values of intervals, price into intervals, integer where integer
   * is the scaled value of the portfolio value.
   *
   * @param priceList represents the list of string with each string representing interval, price.
   * @return list of string with each string converted from interval, price to interval.
   */
  List<String> generateAsterisk(List<String> priceList);

  /**
   * This method generates the minimum, maximum and the interval scale for the graph plotting.
   *
   * @param priceList represents the list of string with each string representing interval, price.
   * @return list of double which contains the min, max and interval of the range.
   */
  List<Double> getMinMaxPrice(List<String> priceList);

  /**
   * Method used to upload user generated file to the flexible portfolio directory.
   *
   * @param path    represents the current file path to be uploaded.
   * @param newFile represents the new file name which is to be stored in flexible directory.
   * @return boolean to determine success failure of the operation.
   * @throws ParseException           if error in json parsing.
   * @throws java.text.ParseException if unable to parse string.
   * @throws IOException              if input mismatch occurs.
   * @throws NoSuchFieldException     if field is not present.
   */
  boolean uploadFlexiblePortfolio(String path, String newFile) throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException;

  /**
   * This method is used to get the object of properties class used to set the path.
   *
   * @return an instance of Properties class
   */
  Properties getProperties();

  /**
   * This function perform the fixed investment operation on the portfolio for the stock.
   * This function delegates the operation to the FixedInvestment's fixed investment strategy,
   * where the amount is first derived based on weight and the reduction of commission.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents the weight percent of the current stock.
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param date          represents start date at which transaction is supposed to be performed.
   * @param commissionfee represents the commission fee associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the transaction is successful
   * @throws IOException              if filename is not present.
   * @throws ParseException           if filename parsing fails.
   * @throws java.text.ParseException if the date parsing fails.
   */
  boolean fixedInvestmentStrategy(double amount, double weight, String symbol, String date,
                                  double commissionfee, String filename)
          throws IOException, ParseException, java.text.ParseException;

  /**
   * This function perform dollar cost averaging operation on the portfolio for the stock,
   * This function delegates the operation to the FixedInvestment's dollarCostAveraging,
   * where strategy is saved for the future dates.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents the weight percent of the current stock.
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param timeInterval  represents the interval between two dates.
   * @param endDate       represents the end date of the transaction
   * @param date          represents start date at which transaction is supposed to be performed.
   * @param commissionfee represents the commission fee associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the transaction is successful
   * @throws IOException              if filename is not present.
   * @throws ParseException           if filename parsing fails.
   * @throws java.text.ParseException if the date parsing fails.
   */
  boolean dollarCostAveraging(double amount, double weight, String symbol, String date,
                              int timeInterval,
                              String endDate,
                              double commissionfee, String filename) throws IOException,
          ParseException, java.text.ParseException;

  /**
   * This function performs the operation to add multiple stocks to the strategy file.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents list of weight percent of the current stock.
   * @param symbol        represents list of symbol which is validated over SNP500 stock list.
   * @param timeInterval  represents the interval between two dates.
   * @param endDate       represents the end date of the transaction
   * @param currentDate   represents start date at which transaction is supposed to be performed.
   * @param commissionfee represents the commission fee associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the transaction is successful
   * @throws IOException    if filename is not present.
   * @throws ParseException if filename parsing fails.
   */
  boolean writeToStrategy(double amount, List<Double> weight, List<String> symbol,
                          String currentDate,
                          int timeInterval,
                          String endDate,
                          List<Double> commissionfee, String filename)
          throws IOException, ParseException;
}
