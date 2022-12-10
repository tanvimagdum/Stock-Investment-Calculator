package model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Interface represents structure for common methods between portfolio model and flexible model.
 * This interface has generic method such as validation of symbol, generating portfolio valuation,
 * validating date, loading portfolios, getting File from path.
 * These methods are then incorporated across both portfolio model and flexible portfolio model.
 */
interface StockPortfolio {

  /**
   * This method validates the symbol from the SNP500 symbol list present in the data folder.
   *
   * @param symbol represents the input symbol to be validated.
   * @return true if SNP500 list contains the symbol else false.
   * @throws IOException if the function is unable to detect SNP500 file.
   */
  boolean validateSymbol(String symbol) throws IOException;

  /**
   * This method validates the input string to be number.
   *
   * @param s represents the string input having numbers.
   * @return true if the function is able to parse the number.
   */
  boolean isNumber(String s);

  /**
   * This method calculates the total valuation of the portfolio with all the stocks in portfolio.
   *
   * @param result represents the entire stocks present in the portfolio.
   * @return the total valuation of the stock.
   */
  String getTotalValuation(List<List<String>> result);

  /**
   * This method loads all the portfolio files from the folder defined in {@code config.properties}.
   * This method loads the files and sort them alphanumerically.
   *
   * @return the Map object denoting the Index and the File.
   */
  Map<Integer, File> loadPortfolioFiles();

  /**
   * This method validates the date in the form of yyyy-MM-dd.
   *
   * @param date represents the string form of the date.
   * @return true if parsing operation of date is successful else false.
   */
  boolean validateDate(String date);

  /**
   * This method represents valuation result that will be generated for the date provided as input.
   * This method generates symbol, quantity, date and the value of individual stock inside
   * portfolio.
   *
   * @param result represents the list of the stock for the selected portfolio.
   * @param date   represents the date at which the valuation of the portfolio stocks are required.
   * @return the list of stocks with valuation and date for each stock which itself represents list.
   * @throws IOException if the API call to get the value at input date fails.
   * @throws ParseException if error in json parsing.
   */
  List<List<String>> getUpdatedRecord(List<List<String>> result, String date)
          throws IOException, ParseException;

  /**
   * This method get the file from the path provided as the input.
   * If the file does not exist then it returns null.
   *
   * @param filePath represents the path of the file.
   * @return the file if the file is present at the path.
   */
  File getFile(String filePath);

}
