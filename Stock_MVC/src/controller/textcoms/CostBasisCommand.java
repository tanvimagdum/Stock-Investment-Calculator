package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import view.ViewInterface;

public class CostBasisCommand implements TextCommand {

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

    v.printLine("Please enter the year (4 digits):");
    String year = sc.nextLine();
    v.printLine("Please enter the month (2 digits):");
    String mon = sc.nextLine();
    v.printLine("Please enter the day (2 digits):");
    String day = sc.nextLine();
    try {
      DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
      date.setLenient(false);
      Date target = date.parse(mon + "/" + day + "/" + year);
      Date upperLimit = new Date();
      if (target.compareTo(upperLimit) > 0) {
        v.printLine("The date entered is out of bounds.");
        v.showPortfolioScreen();
        return;
      }
    } catch (Exception e) {
      v.printLine("The date provided was not valid.");
      v.showPortfolioScreen();
      return;
    }

    v.printLine("The current commission fee is: $" + p.getCommissionFee());
    v.printLine("If you would like to change the fee, enter a dollar amount ('xx.yy'). "
        + "Otherwise, enter anything else.");
    String cf = sc.nextLine();
    try {
      p.setCommissionFee(Float.parseFloat(cf));
    } catch (Exception e) {
      //do nothing
    }
    String dateString = year + "-" + mon + "-" + day;
    try {
      v.printLine("Please wait while the API retrieves that information.");
      v.printLines(costBasisHelper(name, dateString, p, api));
    } catch (Exception e) {
      v.printLine("There was difficulty calculating the cost-basis. Please try again.");
      v.showPortfolioScreen();
      return;
    }

    v.printLine("Enter any key to return to the previous menu.");
    sc.nextLine();
    v.showPortfolioScreen();
  }

  private String[] costBasisHelper(String name, String date, PortfolioController p, API api)
      throws ParseException, IOException {
    String[] startTickers = p.getTickers(name);
    Float[] startCounts = p.getCounts(name);
    Date[] startDates = p.getDates(name);
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    int j = 0;
    for (int i = 0; i < startDates.length; i++) {
      if (startDates[i].compareTo(formatter.parse(date)) < 1 && startCounts[i] > 0) {
        j++;
      }
    }
    String[] tickers = new String[j];
    Float[] counts = new Float[j];
    Date[] dates = new Date[j];

    int k = 0;
    int l = 0;
    while (k < j) {
      if (startDates[l].compareTo(formatter.parse(date)) < 1 && startCounts[l] > 0) {
        tickers[k] = startTickers[l];
        counts[k] = startCounts[l];
        dates[k] = startDates[l];
        k++;
      }
      l++;
    }

    float[] values = p.getCostBasis(name, formatter.parse(date), api);
    String[] out = new String[tickers.length + 3];
    out[0] = "Cost Basis of Portfolio: " + name + " on " + date;
    float sum = 0;
    for (int i = 0; i < values.length; i++) {
      if (values[i] <= 0) {
        out[i + 1] = "No information found for symbol: " + tickers[i];
      } else {
        sum += values[i] * counts[i];
        if (counts[i] > 0) {
          out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "; Price per: $" + String.format("%.02f", values[i])
              + "; Purchased: " + formatter.format(dates[i])
              + "; Total Cost: $" + String.format("%.02f", values[i] * counts[i]);
        }
      }
    }
    out[tickers.length + 1] = "Total Spent on Commission Fee: $"
        + String.format("%.02f", p.getCommissionFee() * startTickers.length);
    out[tickers.length + 2] = "Total Cost Basis of Portfolio: $"
        + String.format("%.02f", (sum - p.getCommissionFee() * startTickers.length));
    return out;
  }
}