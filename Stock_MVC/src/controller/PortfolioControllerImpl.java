package controller;

import model.PortfolioManager;
import view.ViewInterface;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * A class to represent methods that pass the user input
 * to model for processing and defines some methods to
 * take input from users other than menu options.
 */

public class PortfolioControllerImpl implements PortfolioController {

  PortfolioManager model;
  private Readable input;

  /**
   * This is the constructor for the portfolio controller.
   *
   * @param in input stream object for input
   */

  public PortfolioControllerImpl(Readable in, PortfolioManager model) {
    this.input = in;
    this.model = model;
  }

  @Override
  public String readPortfolioFile(String filename) throws IOException {
    return model.readPortfolioFile(filename);
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
  public String[] getFlexPortfolioNames() {
    return model.getFlexPortfolioNames();
  }

  @Override
  public String selectPortfolio(ViewInterface v, Scanner sc) {
    String[] portNames = getPortfolioNames();
    String[] numbered = new String[portNames.length];

    for (int i = 0; i < portNames.length; i++) {
      numbered[i] = (i + 1) + ". " + portNames[i];
    }

    v.printLines(numbered);
    v.printLine("Please choose one of the following options:");
    int index = sc.nextInt();
    sc.nextLine();

    return portNames[index - 1];
  }

  @Override
  public String selectFlexPortfolio(ViewInterface v, Scanner sc) {
    String[] portNames = getFlexPortfolioNames();
    String[] numbered = new String[portNames.length];

    for (int i = 0; i < portNames.length; i++) {
      numbered[i] = (i + 1) + ". " + portNames[i];
    }

    v.printLines(numbered);
    v.printLine("Please choose one of the following options:");
    int index = sc.nextInt();
    sc.nextLine();

    return portNames[index - 1];
  }

  @Override
  public float[] getPortfolioValue(String name, String date) throws IOException, ParseException {
    return model.getPortfolioValue(name, date);
  }

  @Override
  public String[] getPortfolioValueLatest(String name) throws IOException {
    return model.getPortfolioValueLatest(name);
  }

  @Override
  public String buildPortfolio(ViewInterface v, Scanner sc) throws IOException {
    String name;
    ArrayList<String> tickerList = new ArrayList<>();
    ArrayList<Float> floatList = new ArrayList<>();

    v.printLine("Please enter the portfolio's name.");
    name = sc.nextLine();

    try {
      String[] existing = getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name)) {
          v.printLine("A portfolio with that name already exists. Please try again.");
          return name;
        }
      }
    } catch (Exception e) {
      //there are no portfolios
    }

    boolean flag = true;
    for (int i = 0; i < name.length(); i++) {
      if (Character.isLetterOrDigit(name.charAt(i))) {
        flag = false;
      }
    }

    if (flag) {
      v.printLine("The entered name is not valid. Please try again.");
      return name;
    }

    while (true) {
      String ticker;
      String count;
      v.printLine("Please enter a ticker symbol or enter 'Done'.");
      ticker = sc.nextLine();

      if (ticker.equalsIgnoreCase("done")) {
        break;
      }

      if (!model.validateTicker(ticker)) {
        v.printLine("Warning: the symbol you entered is not recognized.");
        v.printLine("Enter 'y' to continue with this symbol. Enter anything else to try again.");
        String response = sc.nextLine();
        if (!response.equals("y")) {
          continue;
        }
      }

      v.printLine("Please enter the stock count.");
      count = sc.nextLine();
      try {
        int temp = Integer.parseInt(count);
        if (temp < 0) {
          v.printLine("The count entered is not a positive integer. Please try again.");
          continue;
        }
      } catch (Exception e) {
        v.printLine("The count entered was not an integer. Please try again.");
        continue;
      }

      tickerList.add(ticker);
      floatList.add(Float.parseFloat(count));
    }

    model.portBuilder(tickerList, floatList, name);
    return name;
  }



  @Override
  public String buildFlexPortfolio(ViewInterface v, Scanner sc) throws IOException {
    String name;
    ArrayList<String> tickerList = new ArrayList<>();
    ArrayList<Float> floatList = new ArrayList<>();
    ArrayList<Date> dateList = new ArrayList<>();

    v.printLine("Please enter the portfolio's name.");
    name = sc.nextLine();

    try {
      String[] existing = getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name)) {
          v.printLine("A portfolio with that name already exists. Please try again.");
          return name;
        }
      }
    } catch (Exception e) {
      //there are no portfolios
    }

    boolean flag = true;
    for (int i = 0; i < name.length(); i++) {
      if (Character.isLetterOrDigit(name.charAt(i))) {
        flag = false;
      }
    }

    if (flag) {
      v.printLine("The entered name is not valid. Please try again.");
      return name;
    }

    //return model.editFlexPortfolio();
    return name;
  }




  @Override
  public String[] manualValuation(String name, ViewInterface v, Scanner sc) {
    String[] tickers = getTickers(name);
    Float[] counts = getCounts(name);
    String[] out = new String[tickers.length + 2];
    v.printLine("For each of the following tickers, please enter a dollar value.");
    out[0] = "Value of Portfolio " + name;
    float sum = 0;
    float value;
    for (int i = 0; i < tickers.length; i++) {
      v.printLine(tickers[i]);

      try {
        value = Float.parseFloat(sc.nextLine());
        if (value < 0) {
          v.printLine("Please be sure to enter a positive number.");
          i--;
          continue;
        }
      } catch (Exception e) {
        v.printLine("Please be sure to enter a number.");
        i--;
        continue;
      }
      sum += value * counts[i];
      out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "; Value per: " + String.format("%.02f", value)
              + "; Total value: " + String.format("%.02f", value * counts[i]);
    }

    out[tickers.length + 1] = "Total value: " + sum;
    return out;
  }

  //change
  @Override
  public String[] portfolioPerformance(String name) {
    return new String[0];
  }

  @Override
  public String[] getTickers(String name) {
    return model.getTickers(name);
  }

  @Override
  public Float[] getCounts(String name) {
    return model.getCounts(name);
  }

  @Override
  public Date[] getDates(String name) throws IllegalArgumentException{
    return model.getDates(name);
  }

}