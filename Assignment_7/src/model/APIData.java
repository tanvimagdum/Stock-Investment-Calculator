package model;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * {@code APIData} is used to extract data from datasource e.g. Vantage API, Yahoo Finance.
 * This interface has methods to extract the entire data, extract month wise data and get
 * price of stock at particular date.
 */
public interface APIData {
  /**
   * This method generates the entire InputStream consisting of the API data for particular symbol.
   *
   * @param symbol represents the ticker symbol (SNP500 Symbols) as input.
   * @return InputStream object consisting of entire API Data for the symbol.
   * @throws IOException If input datatype mismatch occurs.
   */
  String[] getInputStream(String symbol) throws IOException;

  /**
   * This method generates the API data month wise (last working date of the month).
   *
   * @param symbol represents the ticker symbol (SNP500 Symbols) as input.
   * @param date   represents the date of any particular month.
   * @return List consisting of price and month of the input symbol.
   * @throws IOException If input datatype mismatch occurs.
   */
  List<String> getInputStreamMonthly(String symbol, String date) throws IOException;

  /**
   * This method parses price for given date from the InputStream of the Data.
   *
   * @param reader        represents data from the data source as InputStream.
   * @param date      represents date at which price is extracted.
   * @param operation represents daily or monthly flag to extract date wise or month wise data.
   * @return StringBuilder object consisting of the price for the input date and operation.
   * @throws IOException If input datatype mismatch occurs.
   * @throws ParseException if parsing operation fails.
   */
  StringBuilder getPriceForDate(String[] reader, String date, String operation)
          throws IOException, ParseException;
}
