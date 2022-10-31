package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 */

public class portfolioManagerImpl implements portfolioManager {
  private ArrayList<portfolioImpl> portfolios = new ArrayList<>();
  private API api = new APIImpl();

  @Override
  public void portBuilder(ArrayList<Pair<String, Float>> list, String name) {
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
  public void readPortfolioFile(String filename) {
      //filereader
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
    String[] out = new String[tickers.length+2];

    out[0] = "Value of Portfolio: " + name;

    float sum = 0;
    for (int i = 0; i < values.length; i++) {
      sum += values[i];
      out[i+1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "Value per: " + values[i] + "; Total value: " + values[i]*counts[i];
    }

    out[tickers.length+1] = "Total value of portfolio: " + sum;
    return out;
  }

  @Override
  public String[] getPortfolioContents(String name) {
    portfolio subject = getPortfolio(name);
    String[] tickers = subject.getTickers();
    Float[] counts = subject.getCounts();

    String[] out = new String[tickers.length+1];

    out[0] = "Contents of Portfolio: " + name;

    float sum = 0;
    for (int i = 0; i < tickers.length; i++) {
      out[i+1] = "Ticker: " + tickers[i] + "; Count: " + counts[i];
    }

    return out;
  }

  @Override
  public String[] getTickers(String name) {
    return getPortfolio(name).getTickers();
  }

  @Override
  public Float[] getCounts(String name) {
    return getPortfolio(name).getCounts();
  }
}