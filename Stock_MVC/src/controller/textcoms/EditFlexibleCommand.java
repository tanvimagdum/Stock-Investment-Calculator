package controller.textcoms;

import controller.API;
import controller.TextCommand;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import model.PortfolioManager;
import view.ViewInterface;

public class EditFlexibleCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
    String name = null;
    try {
      name = selectFlexPortfolio(v, sc, p);
    } catch (Exception e) {
      v.printLine(
          "There are either no flexible portfolios yet or the input was out of bounds.");
      sc.nextLine();
      v.showBuildScreen();
      return;
    }
    try {
      v.printLines(contentsHelper(name, p));
      editFlexPortfolio(name, v, sc, p);
    } catch (Exception e) {
      v.printLine("There was difficulty editing the portfolio. Please try again.");
      v.showBuildScreen();
      return;
    }
    v.printLines(contentsHelper(name, p));
    v.printLine("Enter any key to return to the previous menu.");
    sc.nextLine();
    v.showBuildScreen();
  }
  private String[] contentsHelper(String name, PortfolioManager p) {

    try {
      String[] tickers = p.getTickers(name);
      Float[] counts = p.getCounts(name);
      Date[] dates = p.getDates(name);

      String[] out = new String[tickers.length + 1];

      out[0] = "Contents of Flexible Portfolio: " + name;
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      for (int i = 0; i < tickers.length; i++) {
        if (counts[i] > 0) {
          out[i + 1] = "BUY "
              + "; Ticker: " + tickers[i]
              + "; Count: " + String.format("%.02f", counts[i])
              + "; Date: " + formatter.format(dates[i]);
        }
        if (counts[i] < 0) {
          out[i + 1] = "SELL"
              + "; Ticker: " + tickers[i]
              + "; Count: " + String.format("%.02f", Math.abs(counts[i]))
              + "; Date: " + formatter.format(dates[i]);
        }
      }
      return out;

    } catch (Exception e) {
      String[] tickers = p.getTickers(name);
      Float[] counts = p.getCounts(name);

      String[] out = new String[tickers.length + 1];

      out[0] = "Contents of Simple Portfolio: " + name;

      for (int i = 0; i < tickers.length; i++) {
        out[i + 1] = "Ticker: " + tickers[i]
            + "; Count: " + String.format("%.02f", counts[i]);
      }
      return out;
    }
  }

  private void editFlexPortfolio(String name, ViewInterface v, Scanner sc, PortfolioManager p)
      throws IllegalArgumentException, IOException, ParseException {
    while (true) {
      String ticker;
      String count;

      v.printLine("Please choose whether to buy or sell, by entering 'b' or 's'. Alternatively, "
          + "or enter 'Done' to finish.");
      String bs = sc.next();
      sc.nextLine();
      if (bs.equalsIgnoreCase("done")) {
        break;
      }

      if (!bs.equalsIgnoreCase("b") && !bs.equalsIgnoreCase("s")) {
        v.printLine("Please be sure to enter 'b' or 's'.");
        continue;
      }

      v.printLine("Please enter a ticker symbol");
      ticker = sc.next();
      sc.nextLine();
      boolean dateCheck = true;
      if (!p.validateTicker(ticker)) {
        v.printLine("Warning: the symbol you entered is not recognized.");
        v.printLine("Enter 'y' to continue with this symbol. Enter anything else to try again.");
        String response = sc.next();
        sc.nextLine();
        if (!response.equals("y")) {
          continue;
        } else {
          dateCheck = false;
        }
      }

      v.printLine("Please enter the stock count.");
      count = sc.next();
      sc.nextLine();
      try {
        int temp = Integer.parseInt(count);
        if (temp <= 0) {
          v.printLine("The count entered is not a positive integer above 0. Please try again.");
          continue;
        }
      } catch (Exception e) {
        v.printLine("The count entered was not an integer. Please try again.");
        continue;
      }

      String year;
      String mon;
      String day;
      v.printLine("Please enter the year (4 digits):");
      year = sc.next();
      sc.nextLine();
      v.printLine("Please enter the month (2 digits):");
      mon = sc.next();
      sc.nextLine();
      v.printLine("Please enter the day (2 digits):");
      day = sc.next();
      sc.nextLine();
      Date target;
      try {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        date.setLenient(false);
        target = date.parse(year + "-" + mon + "-" + day);
        Date upperLimit = new Date();
        if (target.compareTo(upperLimit) > 0) {
          v.printLine("The date entered is out of bounds.");
          continue;
        }
      } catch (Exception e) {
        v.printLine("The date provided was not valid.");
        continue;
      }

      if (dateCheck) {
        if (!p.validateTicker(ticker, target)) {
          v.printLine("You cannot buy a stock before it is available. Please try again.");
          continue;
        }
      }

      if (bs.equalsIgnoreCase("s")) {
        boolean valid = p.checkFlexEdit(name, ticker, Float.parseFloat(count), target);
        if (!valid) {
          v.printLine("This sale would invalidate the existing portfolio.");
          continue;
        }
      }

      if (bs.equalsIgnoreCase("b")) {
        p.editFlexPortfolio(name, ticker, Float.parseFloat(count), target);
      } else {
        p.editFlexPortfolio(name, ticker, -1 * Float.parseFloat(count), target);
      }
    }
  }
  private String selectFlexPortfolio(ViewInterface v, Scanner sc, PortfolioManager p) {
    String[] portNames = p.getFlexPortfolioNames();
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
}
