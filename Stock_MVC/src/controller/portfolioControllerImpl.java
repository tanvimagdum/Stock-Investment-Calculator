package controller;

import model.Stock;
import model.portfolioManager;
import model.portfolioManagerImpl;
import model.portfolio;
import view.viewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class portfolioControllerImpl implements portfolioController {

  portfolioManager model = new portfolioManagerImpl();
  @Override
  public void portBuilder(ArrayList<Stock<String, Float>> list, String name) {
    model.portBuilder(list, name);
  }

  @Override
  public portfolio getPortfolio(String name) {
    return model.getPortfolio(name);
  }

  @Override
  public void readPortfolioFile(String filename) throws FileNotFoundException {
    model.readPortfolioFile(filename);
  }

  @Override
  public void savePortfolio(String filename) throws IOException {
    model.savePortfolio(filename);
  }

  @Override
  public String[] getPortfolioNames() {
    return model.getPortfolioNames();
  }

  @Override
  public String selectPortfolio(viewInterface v) {
    String[] portNames = getPortfolioNames();
    String[] numbered = new String[portNames.length];
    for (int i = 0; i < portNames.length; i++) {
      numbered[i] = (i+1) + ". " + portNames[i];
    }
    v.printLines(numbered);
    v.printLine("Please enter one of the following names:");
    Scanner sc = new Scanner(System.in);
    int index = sc.nextInt();
    sc.nextLine();
    return portNames[index-1];
  }

  @Override
  public String[] getPortfolioValue(String name, Date date) {
    return model.getPortfolioValue(name,date);
  }
  @Override
  public String[] getPortfolioContents(String name) {
    return model.getPortfolioContents(name);
  }

  @Override
  public void buildPortfolio(viewInterface v) {
    String name;
    ArrayList<Stock<String, Float>> tempList = new ArrayList<>();

    Scanner sc = new Scanner(System.in);
    v.printLine("Please enter the portfolio's name.");
    name = sc.nextLine();
    while(true) {
      String ticker;
      int count;
      v.printLine("Please enter a ticker symbol or enter 'done'.");
      ticker = sc.nextLine();
      if (ticker.equals("done")) {
        break;
      }

      v.printLine("Please enter the stock count.");
      count = sc.nextInt();
      tempList.add(new Stock<>(ticker, (float)count));
      sc.nextLine();

    }
    portBuilder(tempList, name);
  }

  @Override
  public String[] manualValuation(String name, viewInterface v) {
    String[] tickers = getTickers(name);
    Float[] counts = getCounts(name);
    String[] out = new String[tickers.length+2];
    v.printLine("For each of the following tickers, please enter a dollar value.");
    Scanner sc = new Scanner(System.in);
    out[0] = "Value of Portfolio " + name;
    float sum = 0;
    for (int i = 0; i < tickers.length; i++) {
      v.printLine(tickers[i]);
      //try
      float value = Float.valueOf(sc.nextLine());
      sum += value*counts[i];
      //String a = sc.nextLine();2
      out[i+1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "; Value per: " + value + "; Total value: " + value*counts[i];
    }
    out[tickers.length+1] = "Total value: " + sum;

    return out;
  }

  @Override
  public String[] getTickers(String name) {
    return model.getTickers(name);
  }

  @Override
  public Float[] getCounts(String name) {
    return model.getCounts(name);
  }

}