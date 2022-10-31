package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class representing the methods to build, read, write,
 * get contents of the desired portfolio.
 */

public class portfolioManagerImpl implements portfolioManager {
  private ArrayList<portfolioImpl> portfolios = new ArrayList<>();
  private API api = new APIImpl();

  @Override
  public void portBuilder(ArrayList<Stock<String, Float>> list, String name) {
    portfolioImpl newPort = portfolioImpl.builder().build(list, name);
    portfolios.add(newPort);
  }

  @Override
  public portfolio getPortfolio(String name) {
    int size = portfolios.size();

    for (int i = 0; i < size; i++) {
      if (portfolios.get(i).getPortfolioName().equals(name)) {
        return portfolios.get(i);
      }
    }

    throw new IllegalArgumentException("No portfolio by that name was found.");
  }

  @Override
  public void readPortfolioFile(String filename) throws IllegalArgumentException,
        FileNotFoundException {

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
  }

  private void readCSV(String filename) throws FileNotFoundException {
    String name = filename.substring(0, filename.length() - 4);
    ArrayList<Stock<String, Float>> tempList = new ArrayList<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader("./" + filename));
      String row = reader.readLine();

      while (row != null) {
        String[] elements = row.split(",");

        if (elements.length != 2) {
          throw new RuntimeException("File not properly formatted. Please ensure there"
                  + "are no headers and only one string and one value per line.");
        }

        try {
          int a = (int) Float.parseFloat(elements[1]);
        } catch (Exception e) {
          throw new RuntimeException("Only integers are allowed for stock counts.");
        }

        tempList.add(new Stock<>(elements[0], Float.valueOf((int) Float.parseFloat(elements[1]))));
        row = reader.readLine();
      }

      reader.close();
      portBuilder(tempList, name);
    } catch (Exception e) {
      throw new FileNotFoundException();
    }
  }

  private void readJSON(String filename) {
      //not yet implemented
  }

  @Override
  public void savePortfolio(String portfolioName) throws IOException {
    portfolio thisPortfolio = getPortfolio(portfolioName);
    String[] tickers = thisPortfolio.getTickers();
    Float[] counts = thisPortfolio.getCounts();

    FileWriter writer = new FileWriter(portfolioName + ".csv");
    for (int i = 0; i < tickers.length; i++) {
      writer.append(tickers[i]);
      writer.append(",");
      writer.append(counts[i].toString());
      writer.append("\n");
    }
    writer.flush();
    writer.close();

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
  public String[] getPortfolioValue(String name, Date date) {
    portfolio subject = getPortfolio(name);
    String[] tickers = subject.getTickers();
    Float[] counts = subject.getCounts();

    float[] values = api.getPrices(tickers, date);
    String[] out = new String[tickers.length + 2];

    out[0] = "Value of Portfolio: " + name;

    float sum = 0;
    for (int i = 0; i < values.length; i++) {
      sum += values[i];
      out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "Value per: " + values[i] + "; Total value: " + values[i] * counts[i];
    }

    out[tickers.length + 1] = "Total value of portfolio: " + sum;

    return out;
  }

  @Override
  public String[] getPortfolioContents(String name) {
    portfolio subject = getPortfolio(name);
    String[] tickers = subject.getTickers();
    Float[] counts = subject.getCounts();

    String[] out = new String[tickers.length + 1];

    out[0] = "Contents of Portfolio: " + name;

    float sum = 0;
    for (int i = 0; i < tickers.length; i++) {
      out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + String.format("%.02f", counts[i]);
    }

    return out;
  }

  public String[] getTickers(String name) {
    return getPortfolio(name).getTickers();
  }

  public Float[] getCounts(String name) {
    return getPortfolio(name).getCounts();
  }

}