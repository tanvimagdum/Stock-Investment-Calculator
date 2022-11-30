package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import view.JFrameView;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFlexibleCommandGui implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    String name = null;
    try {
      name = f.getPortfolioName();
    } catch (Exception e) {
      f.printLine("There are either no flexible portfolios yet or the input was out of bounds.");
      return;
    }
    try {
      //f.printLines(contentsHelper(name, p));
      editFlexPortfolio(name, f, p);
    } catch (Exception e) {
      f.printLine("There was difficulty editing the portfolio. Please try again.");
      return;
    }
    //f.printLines(contentsHelper(name, p));
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

  private void editFlexPortfolio(String name, JFrameView f, PortfolioManager p)
          throws IllegalArgumentException, IOException, ParseException {
    //get data from view
    Object[] o = f.getOperationalStuff();

    String ticker;
    String count;

    String bs = o[0].toString();
    //System.out.println(bs);
    if (bs.equalsIgnoreCase("done")) {
      //break;
    }

    ticker = o[1].toString();
    boolean dateCheck = true;
    if (!p.validateTicker(ticker)) {
      String response = f.printWarning("Warning: the symbol you entered is not recognized. " +
              "Click 'YES' to continue with this symbol. Else click 'No'");
      //f.printLine("Enter 'y' to continue with this symbol. Enter anything else to try again.");
      if (!response.equals("y")) {
        return;
      } else {
        dateCheck = false;
      }
    }

    count = o[2].toString();
    try {
      int temp = Integer.parseInt(count);
      if (temp <= 0) {
        f.printLine("The count entered is not a positive integer above 0. Please try again.");
        return;
      }
    } catch (Exception e) {
      f.printLine("The count entered was not an integer. Please try again.");
      return;
    }

    String year = o[3].toString();
    String mon = o[4].toString();
    String day = o[5].toString();
    //System.out.println(year + " " + mon + " " + day);
    Date target = null;
    try {
      DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
      date.setLenient(false);
      target = date.parse(year + "-" + mon + "-" + day);
      Date upperLimit = new Date();
      if (target.compareTo(upperLimit) > 0) {
        f.printLine("The date entered is out of bounds.");
        return;
      }
    } catch (Exception e) {
      f.printLine("The date provided was not valid.");
      return;
    }

    if (dateCheck) {
      if (!p.validateTicker(ticker, target)) {
        f.printLine("You cannot buy a stock before it is available. Please try again.");
        return;
      }
    }

    if (bs.equalsIgnoreCase("Sell Stock")) {
      boolean valid = p.checkFlexEdit(name, ticker, Float.parseFloat(count), target);
      if (!valid) {
        f.printLine("This sale would invalidate the existing portfolio.");
        return;
      }
    }

    if (bs.equalsIgnoreCase("Buy Stock")) {
      p.editFlexPortfolio(name, ticker, Float.parseFloat(count), target);
      f.printLine("Stock purchase details updated successfully!");
    } else {
      p.editFlexPortfolio(name, ticker, -1 * Float.parseFloat(count), target);
      f.printLine("Stock sale details updated successfully!");
    }

  }
}
