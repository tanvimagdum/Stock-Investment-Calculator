package model;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@code FileIO} represents interface that consist of methods that performs file operations.
 * These operations include creating file, reading file, writing date to file as well as
 * validating the file.
 */
public interface FileIO {

  /**
   * This function creates the file in the directory.
   *
   * @return filename of the file created by the function.
   */
  String createFile() throws IOException;

  /**
   * This function reads the file data from filename.
   *
   * @return Map object consisting of symbol and their respective json object stock.
   */
  Map<String, Object> readFileData(String filename) throws ParseException, IOException;

  /**
   * This function writes data to the file.
   * This symbol, quantity, date, commission fee, operation such as buy or sell as well as filename.
   *
   * @param symbol        represents the ticker symbol (SNP500 Symbols) as input.
   * @param quantity      represents the integer quantity for the stock.
   * @param date          represents the date at which the transaction is performed.
   * @param commissionFee represents the fee associated with each transaction.
   * @param operation     represents operation associated with each transaction.
   * @param filename      represents filename in which the above parameters will be written.
   * @throws IOException    If input datatype mismatch occurs.
   * @throws ParseException generates error for error in parsing.
   */
  void writeFileData(String symbol, double quantity, String date, double commissionFee,
                     String operation, String filename)
          throws IOException, ParseException;

  /**
   * This function validates the json file when user uploads external file in the program.
   *
   * @param filename represents the portfolio file.
   * @param newFile  represents the new portfolio file which is validated.
   * @return true if the operation is successful
   * @throws IOException              if the filename is not found.
   * @throws ParseException           if the parsing operation of file is invalid.
   * @throws java.text.ParseException if the parsing of date in fails.
   * @throws NoSuchFieldException     if the json field is not present in the file.
   */
  boolean validateJSONFile(String filename, String newFile) throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException;

  /**
   * This function performs the operation to add multiple stocks to the strategy file.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents the weight percent of the current stock.
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param timeInterval  represents the interval between two dates.
   * @param endDate       represents the end date of the transaction
   * @param currentDate   represents start date at which transaction is supposed to be performed.
   * @param commissionfee represents the commission fee associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the operation is successful else false.
   * @throws IOException    if the file name is not found.
   * @throws ParseException if the parsing operation of file fails.
   */
  boolean writeStrategy(double amount, List<Double> weight, List<String> symbol,
                        String currentDate, int timeInterval, String endDate,
                        List<Double> commissionfee, String filename)
          throws IOException, ParseException;

  /**
   * This method checks for the strategy present in the dollar cost averaging strategy.
   * @param fileName represents the portfolio file.
   * @param date     represents the date to check the strategy
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   * @throws ParseException if error in json parsing.
   */
  void checkForStrategy(String fileName, String date)
          throws IOException, ParseException, java.text.ParseException;
}
