package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import view.ViewInterface;

public class DollarCostBuyCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
    String name;
    try {
      name = p.selectFlexPortfolio(v, sc);
    } catch (Exception e) {
      v.printLine("There are either no portfolios yet or the input was out of bounds.");
      sc.nextLine();
      v.showPortfolioScreen();
      return;
    }

    float value;
    v.printLine("Please enter the positive dollar amount you wish to invest ('xx.yy')");
    String totalValue = sc.nextLine();
    try {
      value = Float.parseFloat(totalValue);
      if (value <= 0) {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      v.printLine("There was an error trying to read the dollar amount. Please try again.");
      v.showBuildScreen();
      return;
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
      upperLimit = new Date(upperLimit.getTime()-(1000L*60*60*24));
      Date lowerLimit = date.parse("2000-01-01");
      if (target.compareTo(upperLimit) > -1 || target.before(lowerLimit)) {
        v.printLine("The date entered is out of bounds (2000-01-01 to yesterday). "
            + "Please try again.");
        v.showBuildScreen();
        return;
      }
    } catch (Exception e) {
      v.printLine("The date provided was not valid. Please try again.");
      v.showBuildScreen();
      return;
    }

    String[] allTickers = p.getTickers(name); //get unique tickers
    ArrayList<String> tempTickers = new ArrayList<>();
    for (int i = 0; i < allTickers.length; i++) {
      if (!tempTickers.contains(allTickers[i])) {
        tempTickers.add(allTickers[i]);
      }
    }

    String[] tickers = new String[tempTickers.size()];
    for (int i = 0; i < tempTickers.size(); i++) {
      tickers[i] = tempTickers.get(i);
    }

    try{
      for (int i = 0; i < tickers.length; i++) {
        boolean valid = validateTicker(tickers[i], target);
        if (!valid) {
          v.printLine("There is a stock in this portfolio which is not available to buy on the"
              + " given date.\nPlease choose a date on which all stocks in the portfolio are "
              + "available.");
          v.showBuildScreen();
          return;
        }
      }
    } catch (Exception e) {
      v.printLine("There was an issue reading the ticker validation file. Please check it is "
          + "in the correct location and try again.");
      v.showBuildScreen();
      return;
    }



    float[] percentages = new float[tickers.length];
    float sum = 0;
    int i = 0;

    v.printLine("Here are the tickers present in the portfolio: ");
    v.printLines(tickers);
    v.printLine("Next, please enter a set of values that add to 100.");
    while (i < tickers.length) {
      v.printLine("There is currently room for " + String.format("%.02f",100-sum) + "%");
      v.printLine("Please select an apportioning (40.5% as '40.5') for the following ticker: "
          + tickers[i]);

      String percent = sc.nextLine();
      try {
        float per = Float.parseFloat(percent);
        percentages[i] = per;
        sum += per;
        if (sum - 100 > 0.1) {
          sum -= per;
          throw new IllegalArgumentException();
        }
      } catch (Exception e) {
        v.printLine("Please be sure to enter a number less than or equal to what remains.");
        continue;
      }
      i++;
    }

    if (Math.abs(100-sum) > 0.1) {
      v.printLine("The given apportioning does not add up to 100%. Please try again.");
      v.showBuildScreen();
      return;
    }

    float[] prices;
    try {
      prices = api.getPrices(tickers, target);
    } catch (Exception e) {
      v.printLine("There was an error when in the API call. Please try again.");
      v.showBuildScreen();
      return;
    }
    for (int k = 0;  k < prices.length; k++) {
      v.printLine(String.valueOf(prices[k]));
    }

    for (int j = 0; j < tickers.length; j++) {
      float countBuy = 0;
      if (prices[j] == 0) {
        System.out.println("Price was 0.");
        countBuy = 0;
      }
      p.addToFlex(name, tickers[j], countBuy, target);
    }

    System.out.println(p.getTickers(name)[3]);
    System.out.println(p.getCounts(name)[3]);
    System.out.println(p.getDates(name)[3]);

    v.printLines(contentsHelper(name, p));
    v.printLine("Enter any key to return to the previous menu.");
    sc.nextLine();
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

  private boolean validateTicker(String ticker, Date date) throws IOException, ParseException {
    BufferedReader reader = new BufferedReader(new FileReader("./Full Ticker List.csv"));
    String row = reader.readLine();

    while (row != null) {
      String[] elements = row.split(",");
      if (elements[0].equals(ticker)) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date readDate = format.parse(elements[1]);
        return (readDate.compareTo(date) < 0);
      }
      row = reader.readLine();
    }
    reader.close();
    return false;
  }
}
