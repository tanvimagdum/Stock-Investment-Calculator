package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import view.ViewInterface;

public class BuildSimpleCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
    try {
      String name = p.buildPortfolio(v, sc);
      try {
        v.printLines(contentsHelper(name, p));
        v.printLine("Enter any key to return to the previous menu.");
        sc.nextLine();
      } catch (Exception e) {
        //do nothing
      }
    } catch (IOException e) {
      v.printLine("There was an error building the simple portfolio. Please try again.");
    }
    v.showBuildScreen();
  }

  private String[] contentsHelper(String name, PortfolioController p) {

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
}
