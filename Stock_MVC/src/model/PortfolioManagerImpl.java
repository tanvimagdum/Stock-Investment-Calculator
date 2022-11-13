package model;

import controller.PortfolioController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
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

  private float commissionFee;

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
          IOException, ParseException {

    if (filename.length() < 5) {
      throw new IllegalArgumentException("String given is not an accepted filename.");
    }
    if (filename.endsWith(".csv")) {
      Portfolio in = pers.loadCSV(filename);
      portfolios.add(in);
    } else {
      throw new IllegalArgumentException("String given is not an accepted filename.");
    }

    return filename.substring(0, filename.length() - 4);

  }

  @Override
  public void savePortfolio(String portfolioName) throws IOException {
    Portfolio thisPortfolio = getPortfolio(portfolioName);
    try {
      ((FlexPortfolioImpl) thisPortfolio).getDates();
      pers.saveFlexCSV(thisPortfolio);
    } catch (Exception e){
      pers.saveSimpleCSV(thisPortfolio);
    }
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

    if (j == 0) {
      throw new IllegalArgumentException("There are no flexible portfolios yet.");
    }

    String[] flexNames = new String[j];
    for (int i = 0; i  < j; i++) {
      flexNames[i] = listOfNames[i];
    }

    return flexNames;
  }

  @Override
  public float[] getPortfolioValue(String name, String date) throws IOException, ParseException {
    try {
      Portfolio subject = getPortfolio(name);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Date target = format.parse(date);
      String[] startTickers = subject.getTickers();
      Date[] startDates = ((FlexPortfolioImpl) subject).getDates();
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      int j = 0;
      for (int i = 0; i < startDates.length; i++){
        if (startDates[i].compareTo(target) < 1) {
          j++;
        }
      }
      String[] tickers = new String[j];

      int k = 0;
      int l = 0;
      while (k < j) {
        if (startDates[l].compareTo(formatter.parse(date)) < 1) {
          tickers[k] = startTickers[l];
          k++;
        }
        l++;
      }
      float[] values = api.getPrices(tickers, target);
      return values;


    } catch (Exception e) {
      Portfolio subject = getPortfolio(name);
      String[] tickers = subject.getTickers();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Date target = format.parse(date);

      float[] values = api.getPrices(tickers, target);

      return values;
    }
  }

  @Override
  public boolean checkFlexEdit(String name, String ticker, float count, Date date) {
    FlexPortfolioImpl port = (FlexPortfolioImpl) getPortfolio(name);
    return port.checkEdit(ticker, count, date);
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

  public float getCommissionFee() {
    return commissionFee;
  }
  public void setCommissionFee(float cf) {
    this.commissionFee = cf;
  }

}