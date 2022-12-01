package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import view.GuiInterface;
import view.JFrameView;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildFlexibleCommandGui implements GuiCommand {

  @Override
  public void go(GuiInterface f, PortfolioManager p, API api) {

    try {
      String name = buildFlexPortfolio(f, p);
      try {
        //f.printLines(contentsHelper(name, p));
      } catch (Exception e) {
        //do nothing
      }
    } catch (IOException | ParseException e) {
      f.printLine("There was an error building the flexible portfolio. Please try again.");
      f.setCurrScreen("Error");
    }
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

  private String buildFlexPortfolio(GuiInterface f, PortfolioManager p)
          throws IOException, ParseException {

    //get data from view
    String name = f.getPortfolioName();

    try {
      String[] existing = p.getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name)) {
          f.printLine("A portfolio with that name already exists. Please try again.");
          f.setCurrScreen("Error");
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
      f.setCurrScreen("Error");
      return name;
    }

    p.portFlexBuilder(name);
    f.printLine("Portfolio name set successfully!");
    String getCurrScreen = f.getCurrScreen();
    switch (getCurrScreen) {
      case "Build Portfolio" :
        f.setCurrScreen("Add Stock");
        break;
      case "Build Strategy" :
        f.setCurrScreen("Add Strategy");
        break;
      default :
        break;
    }

    //System.out.println(name + " in build");
    return name;
  }

}
