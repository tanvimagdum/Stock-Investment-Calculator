package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import model.Stock;
import view.JFrameView;
import view.ViewInterface;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StrategyBuildGuiCommand implements GuiCommand {
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
      editStrategy(name, f, p);
    } catch (Exception e) {
      f.printLine("There was difficulty editing the strategy. Please try again.");
      return;
    }
  }

  public void editStrategy(String name, JFrameView f, PortfolioManager p)
          throws IllegalArgumentException, IOException, ParseException {

    Object[] o = f.getOperationalStuff();

    boolean amountFlag = true;
    boolean frequencyFlag = true;
    boolean startDateFlag = true;
    boolean endDateFlag = true;
    float amount = 1;
    int frequency = 1;
    ArrayList<String> tickers = new ArrayList<>();
    ArrayList<Stock<String, Float>> list = new ArrayList<>();

    while(amountFlag) {
      //f.printLine("Please enter a positive, non-zero dollar amount to buy each period (xx.yy).");
      try {
        amount = Float.parseFloat(o[0].toString());
        if (amount < 1) {
          throw new IllegalArgumentException();
        }
        amountFlag = false;
      } catch (Exception e) {
        f.printLine("Please enter a valid dollar amount.");
      }
    }


    while(frequencyFlag) {
      //f.printLine("Please enter a positive, integer number of days between buys.");
      try {
        frequency = Integer.parseInt(o[1].toString());
        if (frequency < 1 || frequency > 1825) {
          throw new IllegalArgumentException();
        }
        frequencyFlag = false;
      } catch (Exception e) {
        f.printLine("Please enter a valid interval between 1 day and 5 years.");
      }
    }


    Date upperLimit = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setLenient(false);
    Date target1 = null;
    Date target2 = null;

    while (startDateFlag) {
      //v.printLine("Please enter the starting year (4 digits):");
      String year = o[2].toString();
      //v.printLine("Please enter the starting month (2 digits):");
      String mon = o[3].toString();
      //v.printLine("Please enter the starting day (2 digits):");
      String day = o[4].toString();
      try {
        target1 = formatter.parse(year + "-" + mon + "-" + day);
        if (target1.compareTo(upperLimit) > 0) {
          f.printLine("The date entered must be before today.");
          continue;
        }
        startDateFlag = false;
      } catch (Exception e) {
        f.printLine("The date provided was not valid.");
      }
    }

    while (endDateFlag) {
      /*f.printLine("Please enter the ending year(4 digits) "
              + "or enter 'done' if the strategy will be ongoing:");*/
      String year = o[5].toString();
      if (year.equalsIgnoreCase("done")) {
        target2 = formatter.parse("2100-01-01");
        break;
      }
      //v.printLine("Please enter the ending month (2 digits):");
      String mon = o[6].toString();
      //v.printLine("Please enter the ending day (2 digits):");
      String day = o[7].toString();
      try {
        target2 = formatter.parse(year + "-" + mon + "-" + day);
        if (target2.compareTo(target1) < 1) {
          f.printLine("The date entered is out of bounds.");
          continue;
        }
        endDateFlag = false;
      } catch (Exception e) {
        f.printLine("The date provided was not valid.");
      }
    }

    long interval = target2.getTime() - target1.getTime();
    long minimum = 1000L * 60 * 60 * 24;
    if (interval < minimum) {
      //f.printLine("Please enter two dates, chronologically and at least 1 day apart.");
      //f.showPortfolioScreen();
      return;
    }


    while (true) {
      String ticker;

      //v.printLine("Please enter a ticker symbol or enter 'done' to finish.");
      ticker = o[8].toString();
      if (ticker.equalsIgnoreCase("done")) {
        break;
      }
      if (!p.validateTicker(ticker)) {
        f.printLine("The symbol you entered is not recognized and "
                + "is incompatible with constructing a portfolio in this way.");
        continue;
      }
      if (!p.validateTicker(ticker, target1)) {
        f.printLine("This stock was not available by the given starting date.");
        continue;
      }
      if (tickers.contains(ticker)) {
        f.printLine("You have already added that stock to the list.");
        continue;
      }
      tickers.add(ticker);
      f.printLine("The following stocks are in the strategy:");
      for (String tick : tickers) {
        f.printLine(tick);
      }
    }

    if (tickers.size() < 1) {
      f.showPortfolioScreen();
      return;
    }

    float[] percentages = new float[tickers.size()];
    float sum = 0;
    int i = 0;

    /*f.printLine("Here are the tickers present in the portfolio: ");
    for (String tick : tickers) {
      f.printLine(tick);
    }*/
    //f.printLine("Next, please enter a set of values that add to 100.");
    while (i < tickers.size()) {
      //f.printLine("There is currently room for " + String.format("%.02f",100-sum) + "%");
      f.printLine("Please select an apportioning (40.5% as '40.5') for the following ticker: "
              + tickers.get(i));

      String percent = o[9].toString();
      try {
        float per = Float.parseFloat(percent);
        percentages[i] = per;
        sum += per;
        if (sum - 100 > 0.1) {
          sum -= per;
          throw new IllegalArgumentException();
        }
      } catch (Exception e) {
        f.printLine("Please be sure to enter a number less than or equal to what remains.");
        continue;
      }
      i++;
    }

    if (Math.abs(100-sum) > 0.1) {
      f.printLine("The given apportioning does not add up to 100%. Please try again.");
      //v.showBuildScreen();
      return;
    }

    //v.printLines(contentsStrategyHelper(tickers, percentages, amount));
    //f.printLine("Enter any key to return to the previous menu.");
    //sc.nextLine();
    //v.showBuildScreen();
    for (int j = 0; j < tickers.size(); j++) {
      list.add(new Stock(tickers.get(j), amount * percentages[j] * 0.01));
    }
    p.addStrategy(name, list, target1, target2, frequency);
    //p.strategyUpdate(name);
  }
}
