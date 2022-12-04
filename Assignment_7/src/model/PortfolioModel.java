package model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * {@code PortfolioModel} represents the interface that defines method definition of operations.
 * This interface consist of methods such as creating the portfolio, loading portfolio,
 * API call to the Vantage API, Examining the composition and getting the valuation of the
 * portfolio.
 */
public interface PortfolioModel extends StockPortfolio {

  /**
   * This method initializes the file creation operation which is used to create the portfolio file.
   * It checks the portfolio already present in the database and then creates file with the
   * appropriate filename to avoid duplications.
   *
   * @throws IOException if the database folder itself in not present in the directory.
   */
  void createFile() throws IOException;

  /**
   * This method create the portfolio and returns true if both stockName and quantity are valid.
   * For stockName to be valid, the stockName must be listed in the ticker symbol list.
   * This ticker symbol list is generated out of SMP500 stock data.
   * For quantity to be valid, the quantity must be whole number of shares.
   *
   * @param stockName represents the ticker symbol of the company.
   * @param quantity  represents the number of shares as whole number.
   * @return true if the parameters are valid else return false;
   * @throws IOException if file writing using FileWriter operation fails.
   */
  boolean createPortfolio(String stockName, int quantity) throws IOException;


  /**
   * This method generates the content present inside the portfolio based on the function type.
   *
   * @param file         represents the file chosen by the user to either examine or get valuation.
   * @param functionType represents the functional part i.e. either examine or valuation.
   * @return List representing portfolio content and Each portfolio content is represented as List.
   * @throws IOException if file reading using FileReading operation fails.
   */
  List<List<String>> displayPortfolioContent(File file, String functionType) throws IOException;


  /**
   * This method represents  composition of portfolio in form of symbol and quantity in portfolio.
   *
   * @param index        represents the index of the file for which user wants to examine
   *                     composition.
   * @param functionType represents the string to perform examine operation.
   * @return the list of stock with symbol and quantity for each stock which itself represents list.
   * @throws IOException throws exception for file not found at the path.
   */
  List<List<String>> getExamineComposition(int index, String functionType) throws IOException;


  /**
   * This method parses the file which is provided as input.
   * The set of parsed value includes stock and quantity which is then validated.
   *
   * @param file is the input text file consisting of the text to be validated.
   * @return the Map object representing the valid ticker symbol and valid quantity.
   * @throws IOException if BufferedReader is unable to read the file.
   */
  Map<String, Integer> processFile(File file) throws IOException;

  /**
   * This method is used to get the object of properties class used to set the path.
   *
   * @return an instance of Properties class
   */
  Properties getProperties();


}
