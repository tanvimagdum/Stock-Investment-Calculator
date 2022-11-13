package model;

import controller.PortfolioController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class representing the methods to build, read, write,
 * get contents of the desired portfolio.
 */

public class PortfolioManagerImpl implements PortfolioManager {
  private ArrayList<Portfolio> portfolios = new ArrayList<>();

  private Persistence pers;
  private API api = new APIImpl();

  public PortfolioManagerImpl(Persistence pers) {
    this.pers = pers;
  }

  @Override
  public void portBuilder(ArrayList<String> tickerList, ArrayList<Float> floatList, String name) {
    ArrayList<Stock<String, Float>> finalList = new ArrayList<>();
    for (int i = 0; i < tickerList.size(); i++) {
      finalList.add(new Stock<>(tickerList.get(i), floatList.get(i)));
    }
    PortfolioImpl newPort = PortfolioImpl.builder().build(finalList, name);
    portfolios.add(newPort);
  }

  @Override
  public void portFlexBuilder(String name) {
    Portfolio flexPort = new FlexPortfolioImpl(name);
    portfolios.add(flexPort);
  }

  private Portfolio getPortfolio(String name) {
    int size = portfolios.size();

    for (int i = 0; i < size; i++) {
      if (portfolios.get(i).getPortfolioName().equals(name)) {
        return portfolios.get(i);
      }
    }

    throw new IllegalArgumentException("No portfolio by that name was found.");
  }

  @Override
  public String readPortfolioFile(String filename) throws IllegalArgumentException,
          IOException {

    if (filename.length() < 5) {
      throw new IllegalArgumentException("String given is not an accepted filename.");
    }
    if (filename.endsWith(".csv")) {
      readCSV(filename);
    } else if (filename.endsWith(".json")) {
      readJSON(filename);
    } else {
      throw new IllegalArgumentException("String given is not an accepted filename.");
    }

    return filename.substring(0, filename.length() - 4);

  }

  private void readCSV(String filename) throws IOException {
    String name = filename.substring(0, filename.length() - 4);
    ArrayList<String> tickerList = new ArrayList<>();
    ArrayList<Float> floatList = new ArrayList<>();

    BufferedReader reader = new BufferedReader(new FileReader("./" + filename));

    String row = reader.readLine();

    while (row != null) {
      String[] elements = row.split(",");

      if (elements.length != 2) {
        throw new RuntimeException("File not properly formatted. Please ensure there"
                + "are no headers and only one string and one value per line.");
      }

      try {
        float newFloat = Float.parseFloat(elements[1]);
        if ((newFloat != Math.ceil(newFloat) || (newFloat < 0))) {
          throw new RuntimeException();
        }
      } catch (Exception e) {
        throw new RuntimeException("Only integers are allowed for stock counts.");
      }
      tickerList.add(elements[0]);
      floatList.add(Float.valueOf((int) Float.parseFloat(elements[1])));
      row = reader.readLine();
    }

    reader.close();
    portBuilder(tickerList, floatList, name);

  }

  private void readJSON(String filename) {
    //not yet implemented
  }

  @Override
  public void savePortfolio(String portfolioName) throws IOException {
    Portfolio thisPortfolio = getPortfolio(portfolioName);
    pers.saveToDisc(thisPortfolio, portfolioName);
  }

  @Override
  public String[] getPortfolioNames() {
    int size = portfolios.size();
    String[] listOfNames = new String[size];

    if (size == 0) {
      throw new IllegalArgumentException("There are no portfolios yet.");
    }
    for (int i = 0; i < size; i++) {
      listOfNames[i] = portfolios.get(i).getPortfolioName();
    }

    return listOfNames;
  }

  @Override
  public String[] getFlexPortfolioNames() {
    int j = 0;
    int size = portfolios.size();
    String[] listOfNames = new String[size];

    if (size == 0) {
      throw new IllegalArgumentException("There are no flexible portfolios yet.");
    }
    for (int i = 0; i < size; i++) {
      if (portfolios.get(i) instanceof FlexPortfolioImpl) {
        listOfNames[j] = portfolios.get(i).getPortfolioName();
        j++;
      }
    }

    return listOfNames;
  }

  @Override
  public float[] getPortfolioValue(String name, String date) throws IOException, ParseException {

    Portfolio subject = getPortfolio(name);
    String[] tickers = subject.getTickers();

    float[] values = getPricesByDate(tickers, date);

    return values;
  }

  private float[] getPricesByDate(String[] tickers, String date) throws IOException,
                                                                        ParseException {
    float[] prices = new float[tickers.length];
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date target = format.parse(date);
    for (int i = 0; i < tickers.length; i++) {
      boolean empty = true;
      String symbol = tickers[i];
      BufferedReader reader;
      try {
        reader = new BufferedReader(new FileReader("./Stocks/" + symbol + ".csv"));
      } catch (Exception e) {
        prices[i] = -1;
        continue;
      }
      String row;
      row = reader.readLine(); //skip header
      while (row != null) {
        String[] elements = row.split(",");

        Date rowDate = format.parse(elements[0]);
        if (target.compareTo(rowDate) < 1) {
          prices[i] = Float.parseFloat(elements[1]);
          empty = false;
        }

        row = reader.readLine();
      }
      if (empty) {
        prices[i] = -1;
      }
    }

    return prices;
  }

  /**
   * This method is similar to get prices, but instead of finding the prices based on a certain
   * date, it gets the last trade prices as of the specific day 10/31/2022.
   *
   * @param name name of the portfolio
   * @return a string array detailing the contents of the portfolio and their value
   * @throws IOException if it has difficulty reading files
   */
  public String[] getPortfolioValueLatest(String name) throws IOException {
    Portfolio subject = getPortfolio(name);
    String[] tickers = subject.getTickers();
    Float[] counts = subject.getCounts();

    float[] values = getPricesLatest(tickers);
    String[] out = new String[tickers.length + 2];

    out[0] = "Value of Portfolio: " + name + " as of 10/31/2022";

    float sum = 0;
    for (int i = 0; i < values.length; i++) {
      if (values[i] < 0) {
        out[i + 1] = "No information found for symbol: " + tickers[i];
      } else {
        sum += values[i] * counts[i];
        out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
                + "; Value per: " + String.format("%.02f", values[i])
                + "; Total Value: " + String.format("%.02f", values[i] * counts[i]);

      }
    }
    out[tickers.length + 1] = "Total value of portfolio: " + String.format("%.02f", sum);

    return out;
  }

  @Override
  public boolean checkFlexEdit(String name, String ticker, float count, Date date) {
    FlexPortfolioImpl port = (FlexPortfolioImpl) getPortfolio(name);
    String[] tickers = port.getTickers();
    Float[] counts =  port.getCounts();
    float sum = 0;
    for (int i = 0; i < tickers.length; i++) {
      if (tickers[i].equals(ticker)){
        sum += counts[i];
      }
    }
    if (sum < count) {
      return false;
    }

    return true;
  }


  private float[] getPricesLatest(String[] tickers) throws IOException {
    float[] prices = new float[tickers.length];
    for (int i = 0; i < tickers.length; i++) {
      boolean empty = true;
      BufferedReader reader;
      try {
        reader = new BufferedReader(new FileReader("./TickerListSP500.csv"));
      } catch (Exception e) {
        prices[i] = -1;
        continue;
      }
      String row;
      row = reader.readLine();
      while (row != null) {
        String[] elements = row.split(",");
        if (elements[0].equals(tickers[i])) {
          prices[i] = Float.parseFloat(elements[2]);
          empty = false;
        }
        row = reader.readLine();
      }
      if (empty) {
        prices[i] = -1;
      }
    }

    return prices;
  }

  /**
   * This method performs a check on a submitted ticker symbol to see if it matches those in
   * our database.
   * @param ticker the symbol being checked
   * @return a boolean for whether the symbol was or was not recognized
   * @throws IOException when there is difficulty reading the reference file
   */
  public boolean validateTicker(String ticker) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("./Full Ticker List.csv"));
    String row = reader.readLine();

    while (row != null) {
      String[] elements = row.split(",");
      if (elements[0].equals(ticker)) {
        return true;
      }
      row = reader.readLine();
    }

    reader.close();

    return false;
  }

  public boolean validateTicker(String ticker, Date date) throws IOException, ParseException {
    BufferedReader reader = new BufferedReader(new FileReader("./Full Ticker List.csv"));
    String row = reader.readLine();

    while (row != null) {
      String[] elements = row.split(",");
      if (elements[0].equals(ticker)) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date readDate = format.parse(elements[1]);
        if (readDate.compareTo(date) < 0) {
          return true;
        } else {
          return false;
        }
      }
      row = reader.readLine();
    }

    reader.close();

    return false;
  }

  @Override
  public void editFlexPortfolio(String name, String ticker, Float count, Date date)
          throws IllegalArgumentException {
    FlexPortfolioImpl port = (FlexPortfolioImpl) getPortfolio(name);
    port.addFlexStock(ticker, count, date);
  }

  public String[] getTickers(String name) {
    return getPortfolio(name).getTickers();
  }

  public Float[] getCounts(String name) {
    return getPortfolio(name).getCounts();
  }

  public Date[] getDates(String name) throws IllegalArgumentException{

    try {
      Portfolio flexPort = getPortfolio(name);
      return ((FlexPortfolioImpl) flexPort).getDates();
    } catch(Exception e) {
      throw new IllegalArgumentException("Portfolio must be a flexible portfolio " +
                        "in order to get dates.");
    }

  }

}