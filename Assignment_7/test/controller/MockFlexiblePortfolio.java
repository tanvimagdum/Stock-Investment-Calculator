package controller;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.FlexiblePortfolioImpl;

/**
 * This class represents Mock Model used to implement isolation testcases for Controller.
 */
public class MockFlexiblePortfolio extends FlexiblePortfolioImpl {
  private StringBuilder log;

  /**
   * Constructs the object of the PortfolioModel implementation.
   * This constructor initializes the properties from the config.properties.
   *
   * @param log StringBuilder to log the arguments of the methods.
   * @throws IOException if the input stream is unable to load the properties file.
   */
  public MockFlexiblePortfolio(StringBuilder log) throws IOException {
    super();
    this.log = log;
  }

  @Override
  public boolean buyShares(String symbol, double quantity, String date, double commissionFee,
                           String filename) throws ParseException, IOException {
    log.append(symbol + " " + quantity + " " + date + " " + commissionFee + " " + filename);
    return true;
  }

  @Override
  public boolean sellShares(String symbol, double quantity, String date, double commissionFee,
                            String filename) throws ParseException, IOException,
          java.text.ParseException {
    log.append(symbol + " " + quantity + " " + date + " " + commissionFee + " " + filename);
    return true;
  }

  @Override
  public double calculateCostBasis(String filename, String date) throws ParseException,
          IOException, java.text.ParseException {
    log.append(filename + " " + date);
    return 0;
  }


  @Override
  public Properties getProperties() {
    return null;
  }

  @Override
  public boolean validateSymbol(String symbol) throws IOException {
    return false;
  }

  @Override
  public boolean isNumber(String s) {
    return false;
  }

  @Override
  public String getTotalValuation(List<List<String>> result) {
    return null;
  }

  @Override
  public Map<Integer, File> loadPortfolioFiles() {
    return null;
  }

  @Override
  public boolean validateDate(String date) {
    return false;
  }

  @Override
  public List<List<String>> getUpdatedRecord(List<List<String>> result, String date)
          throws IOException {
    return null;
  }

  @Override
  public File getFile(String filePath) {
    return null;
  }
}
