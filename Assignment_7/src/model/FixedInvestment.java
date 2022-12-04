package model;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * This interface represents the type of strategy that can be applied on the portfolio.
 * This interface provides option to invest via fixed investment strategy and dollar cost averaging.
 */
public interface FixedInvestment {
  /**
   * This function represents definition of fixed investment strategy.
   * Fixed investment strategy is calculated by taking multiple stocks each divided in ratio that
   * add upto 100 percent. The quantity of the transaction is then calculated based on the weight
   * and the commission.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents the weight percent of the current stock.
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param date          represents the date at which the transaction is supposed to be performed.
   * @param commissionfee represents the commission associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the transaction operation is successful.
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   * @throws ParseException if error in json parsing.
   */
  boolean fixedInvestmentStrategy(double amount, double weight, String symbol, String date,
                                  double commissionfee, String filename)
          throws IOException, ParseException, java.text.ParseException;

  /**
   * This function represents the definition of dollar cost averaging.
   * Dollar cost averaging is performed by taking start date , end date and interval.
   * The stocks are processed based on these dates and the strategy is stored for the future dates.
   *
   * @param amount        represents the total amount user want to invest in portfolio.
   * @param weight        represents the weight percent of the current stock.
   * @param symbol        represents the ticker symbol which is validated over SNP500 stock list.
   * @param startDate     represents start date at which transaction is supposed to be performed.
   * @param endDate       represents the end date at which transaction is supposed to be performed.
   * @param interval      represents the gap between two dates of the transaction.
   * @param commissionFee represents the commission fee associated with each transaction.
   * @param filename      represents the portfolio name in which transaction is performed.
   * @return true if the transaction operation is successful.
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   * @throws ParseException if error in json parsing.
   */
  boolean dollarCostAveraging(double amount, double weight, String symbol,
                              String startDate, String endDate, int interval,
                              double commissionFee, String filename)
          throws IOException, ParseException, java.text.ParseException;
}
