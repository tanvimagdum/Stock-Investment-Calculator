package controller;

import model.Stock;
import model.portfolioManager;
import model.portfolioManagerImpl;
import model.portfolio;
import view.viewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * A class to represent methods that pass the user input
 * to model for processing and defines some methods to
 * take input from users other than menu options.
 */

public class portfolioControllerImpl implements portfolioController {

  portfolioManager model = new portfolioManagerImpl();
  private InputStream input;

  /**
   * Construct a Controller Implementation object
   * @param in input stream object for input
   */
  public portfolioControllerImpl(InputStream in) {
    this.input = in;
  }
  @Override
  public void portBuilder(ArrayList<Stock<String, Float>> list, String name) {
    model.portBuilder(list, name);
  }

  @Override
  public portfolio getPortfolio(String name) {
    return model.getPortfolio(name);
  }

  @Override
  public void readPortfolioFile(String filename) throws IOException {
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
      numbered[i] = (i + 1) + ". " + portNames[i];
    }

    v.printLines(numbered);
    v.printLine("Please choose one of the following options:");
    Scanner sc = new Scanner(input);
    int index = sc.nextInt();
    sc.nextLine();

    return portNames[index - 1];
  }

  @Override
  public String[] getPortfolioValue(String name, String date) throws IOException, ParseException {
    return model.getPortfolioValue(name,date);
  }

  @Override
  public String[] getPortfolioValueLatest(String name) throws IOException {
    return model.getPortfolioValueLatest(name);
  }
  @Override
  public String[] getPortfolioContents(String name) {
    return model.getPortfolioContents(name);
  }

  @Override
  public void buildPortfolio(viewInterface v) throws IOException {
    String name;
    ArrayList<Stock<String, Float>> tempList = new ArrayList<>();
    Scanner sc = new Scanner(input);
    v.printLine("Please enter the portfolio's name.");
    name = sc.nextLine();
    boolean flag = true;
    for (int i = 0; i < name.length(); i++) {
      if (Character.isLetterOrDigit(name.charAt(i))) {
        flag = false;
      }
    }

    if (flag) {
      v.printLine("The entered name is not valid. Please try again.");
      return ;
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


      tempList.add(new Stock<>(ticker, Float.parseFloat(count)));
    }

    portBuilder(tempList, name);
    v.printLines(getPortfolioContents(name));
    v.printLine("Hit any key to return to the previous menu.");
    sc.nextLine();
  }

  @Override
  public String[] manualValuation(String name, viewInterface v) {
    String[] tickers = getTickers(name);
    Float[] counts = getCounts(name);
    String[] out = new String[tickers.length + 2];
    v.printLine("For each of the following tickers, please enter a dollar value.");
    Scanner sc = new Scanner(input);
    out[0] = "Value of Portfolio " + name;
    float sum = 0;

    for (int i = 0; i < tickers.length; i++) {
      v.printLine(tickers[i]);
      float value = Float.valueOf(sc.nextLine());
      sum += value*counts[i];
      out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "; Value per: " + value + "; Total value: " + value*counts[i];
    }

    out[tickers.length + 1] = "Total value: " + sum;
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