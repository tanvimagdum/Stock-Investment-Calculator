package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import view.JFrameView;
import view.ViewInterface;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BuildFlexibleCommandGui implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {

    try {
      String name = buildFlexPortfolio(f, p);
      try {
        f.printLines(contentsHelper(name, p));
        //f.printLine("Enter any key to return to the previous menu.");
        //sc.nextLine();
      } catch (Exception e) {
        //do nothing
      }
    } catch (IOException | ParseException e) {
      f.printLine("There was an error building the flexible portfolio. Please try again.");
    }
    //v.showBuildScreen();
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

  private String buildFlexPortfolio(JFrameView f, PortfolioManager p)
          throws IOException, ParseException {

    //get data from view
    String name = f.getPortfolioName();
    System.out.println(name);

    try {
      String[] existing = p.getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name)) {
          f.printLine("A portfolio with that name already exists. Please try again.");
          return name;
        }
      }
    } catch (Exception e) {
      //there are no portfolios
    }

    boolean flag = true;
    for (int i = 0; i < name.length(); i++) {
      if (!Character.isLetterOrDigit(name.charAt(i)) && !Character.isWhitespace(name.charAt(i))) {
        flag = false;
      }
    }

    if (!flag) {
      f.printLine("The entered name is not valid. Please try again.");
      return name;
    }

    p.portFlexBuilder(name);
    editFlexPortfolio(name, f, p);
    return name;
  }

  public void editFlexPortfolio(String name, JFrameView f, PortfolioManager p)
          throws IllegalArgumentException, IOException, ParseException {

    //get data from view
    Object[] o = f.getOperationalStuff();

    while (true) {
      String ticker;
      String count;

      //v.printLine("Please choose whether to buy or sell, by entering 'b' or 's'. Alternatively, "
       //       + "or enter 'Done' to finish.");
      String bs = o[0].toString();
      if (bs.equalsIgnoreCase("done")) {
        break;
      }

      /*if (!bs.equalsIgnoreCase("b") && !bs.equalsIgnoreCase("s")) {
        v.printLine("Please be sure to enter 'b' or 's'.");
        continue;
      }

      v.printLine("Please enter a ticker symbol");*/
      ticker = o[1].toString();
      boolean dateCheck = true;
      if (!p.validateTicker(ticker)) {
        String response = f.printWarning("Warning: the symbol you entered is not recognized. " +
                "Click 'YES' to continue with this symbol. Else click 'No'");
        //f.printLine("Enter 'y' to continue with this symbol. Enter anything else to try again.");
        if (!response.equals("y")) {
          continue;
        } else {
          dateCheck = false;
        }
      }

      //v.printLine("Please enter the stock count.");
      count = o[2].toString();
      try {
        int temp = Integer.parseInt(count);
        if (temp <= 0) {
          f.printLine("The count entered is not a positive integer above 0. Please try again.");
          continue;
        }
      } catch (Exception e) {
        f.printLine("The count entered was not an integer. Please try again.");
        continue;
      }

      String year = o[3].toString();
      String mon = o[4].toString();
      String day = o[5].toString();
      /*v.printLine("Please enter the year (4 digits):");
      year = sc.next();
      sc.nextLine();
      v.printLine("Please enter the month (2 digits):");
      mon = sc.next();
      sc.nextLine();
      v.printLine("Please enter the day (2 digits):");
      day = sc.next();
      sc.nextLine();*/
      Date target;
      try {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        date.setLenient(false);
        target = date.parse(year + "-" + mon + "-" + day);
        Date upperLimit = new Date();
        if (target.compareTo(upperLimit) > 0) {
          f.printLine("The date entered is out of bounds.");
          continue;
        }
      } catch (Exception e) {
        f.printLine("The date provided was not valid.");
        continue;
      }

      if (dateCheck) {
        if (!p.validateTicker(ticker, target)) {
          f.printLine("You cannot buy a stock before it is available. Please try again.");
          continue;
        }
      }

      if (bs.equalsIgnoreCase("s")) {
        boolean valid = p.checkFlexEdit(name, ticker, Float.parseFloat(count), target);
        if (!valid) {
          f.printLine("This sale would invalidate the existing portfolio.");
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
}