package controller;

import model.PortfolioManagerImpl;
import view.JFrameView;
import view.ViewImpl;
import view.ViewInterface;

import javax.swing.*;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * A class representing a continuous input method which asks user to enter the input pertaining to
 * the menu.
 */

public class InputControllerImpl implements InputController {

  public String currentScreen = "WS";
  public boolean flag = true;
  public ViewInterface v;
  public PortfolioController p;
  private Scanner sc;
  private API api;

  /**
   * The main loop, which facilitates menu navigation and continues until the user enters the exit
   * condition.
   *
   * @param args arguments to the program
   */
  public static void main(String[] args) {
    InputController in = new InputControllerImpl(new ViewImpl(System.out),
        new PortfolioControllerImpl(new InputStreamReader(System.in),
            new PortfolioManagerImpl(new Persistence())),
        new InputStreamReader(System.in), System.out, new APIImpl());
    //in.start();

    JFrameView.setDefaultLookAndFeelDecorated(false);
    JFrameView frame = new JFrameView();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }

  /**
   * Construct an Input Controller Implementation object in order to invoke the continuous input
   * method in the main function.
   *
   * @param view    an object of viewInterface
   * @param portCon an object of portfolioController
   * @param in      an object containing the input stream
   * @param out     an object containing the output stream
   */

  public InputControllerImpl(ViewInterface view, PortfolioController portCon,
      Readable in, PrintStream out, API api) {
    this.v = view;
    this.p = portCon;
    this.sc = new Scanner(in);
    this.api = api;
  }

  @Override
  public void start() {

    v.showWelcomeScreen();
    while (flag) {
      int inputOption = 0;
      try {
        inputOption = Integer.parseInt(sc.next());
        sc.nextLine(); //swallows \n
      } catch (Exception e) {
        v.printLine("Please be sure to enter an integer for menu selection.");
        switch (currentScreen) {
          case "WS":
            v.showWelcomeScreen();
            break;

          case "LS":
            v.showLoadScreen();
            break;

          case "BS":
            v.showBuildScreen();
            break;

          case "PS":
            v.showPortfolioScreen();
            break;

          default:
            break;
        }
        continue;
      }
      switch (currentScreen) {
        case "WS":
          welcomeScreen(inputOption);
          break;

        case "LS":
          loadScreen(inputOption);
          break;

        case "BS":
          buildScreen(inputOption);
          break;

        case "PS":
          portfolioScreen(inputOption);
          break;

        default:
          break;
      }
    }
  }

  private void portfolioScreen(int inputOption) {

    if (inputOption < 1 || inputOption > 6) {
      v.displayError();
      v.showPortfolioScreen();
    } else {
      switch (inputOption) {
        case 1:
          try {
            String name = p.selectPortfolio(v, sc);
            v.printLines(contentsHelper(name));
            v.printLine("Enter any key to return to the previous menu.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          }

        case 2:
          String name;
          try {
            name = p.selectPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          }
          String year;
          String mon;
          String day;
          v.printLine("Please enter the year (4 digits):");
          year = sc.nextLine();
          v.printLine("Please enter the month (2 digits):");
          mon = sc.nextLine();
          v.printLine("Please enter the day (2 digits):");
          day = sc.nextLine();
          try {
            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            date.setLenient(false);
            Date target = date.parse(mon + "/" + day + "/" + year);
            Date upperLimit = new Date();
            if (target.compareTo(upperLimit) > 0) {
              v.printLine("The date entered is out of bounds.");
              v.showPortfolioScreen();
              break;
            }
          } catch (Exception e) {
            v.printLine("The date provided was not valid.");
            v.showPortfolioScreen();
            break;
          }
          try {
            v.printLine("Please wait while the API retrieves that information.");
            v.printLines(simpleValueHelper(name, year + "-" + mon + "-" + day));
          } catch (Exception e) {
            v.printLine("There was an error attempting to value the portfolio.");
          }
          v.printLine("Enter any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 3:
          //cost basis
          try {
            name = p.selectFlexPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          }

          v.printLine("Please enter the year (4 digits):");
          year = sc.nextLine();
          v.printLine("Please enter the month (2 digits):");
          mon = sc.nextLine();
          v.printLine("Please enter the day (2 digits):");
          day = sc.nextLine();
          try {
            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            date.setLenient(false);
            Date target = date.parse(mon + "/" + day + "/" + year);
            Date upperLimit = new Date();
            if (target.compareTo(upperLimit) > 0) {
              v.printLine("The date entered is out of bounds.");
              v.showPortfolioScreen();
              break;
            }
          } catch (Exception e) {
            v.printLine("The date provided was not valid.");
            v.showPortfolioScreen();
            break;
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
            v.printLines(costBasisHelper(name, dateString));
          } catch (Exception e) {
            v.printLine("There was difficulty calculating the cost-basis. Please try again.");
            v.showPortfolioScreen();
            break;
          }

          v.printLine("Enter any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 4:
          //performance over time
          try {
            name = p.selectFlexPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine(
                "There are either no flexible portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          }
          Date upperLimit = new Date();
          DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
          formatter.setLenient(false);
          Date lowerLimit = new Date();

          v.printLine(
              "Note that the date range includes the first date entered up to but not including"
                  + " the second date entered");
          v.printLine("Please enter the starting year (4 digits):");
          year = sc.nextLine();
          v.printLine("Please enter the starting month (2 digits):");
          mon = sc.nextLine();
          v.printLine("Please enter the starting day (2 digits):");
          day = sc.nextLine();
          Date target1 = new Date();
          try {
            lowerLimit = formatter.parse("01/01/1990");
            target1 = formatter.parse(mon + "/" + day + "/" + year);
            if (target1.compareTo(upperLimit) > 0 || target1.compareTo(lowerLimit) < 0) {
              v.printLine("The date entered is out of bounds.");
              v.showPortfolioScreen();
              break;
            }
          } catch (Exception e) {
            v.printLine("The date provided was not valid.");
            v.showPortfolioScreen();
            break;
          }

          v.printLine("Please enter the ending year(4 digits):");
          year = sc.nextLine();
          v.printLine("Please enter the ending month (2 digits):");
          mon = sc.nextLine();
          v.printLine("Please enter the ending day (2 digits):");
          day = sc.nextLine();
          Date target2 = new Date();
          try {
            target2 = formatter.parse(mon + "/" + day + "/" + year);
            if (target2.compareTo(upperLimit) > 0 || target1.compareTo(lowerLimit) < 0) {
              v.printLine("The date entered is out of bounds.");
              v.showPortfolioScreen();
              break;
            }
          } catch (Exception e) {
            v.printLine("The date provided was not valid.");
            v.showPortfolioScreen();
            break;
          }

          long interval = target2.getTime() - target1.getTime();
          long minimum = 1000L * 60 * 60 * 24;
          long maximum = 1000L * 60 * 60 * 24 * 365 * 20;
          if (interval < minimum || interval > maximum) {
            v.printLine("Please enter two dates, chronologically and at least 1 day apart,"
                + "but no more than 20 years apart.");
            v.showPortfolioScreen();
            break;
          }

          Date[] dates = new Date[0];
          try {
            dates = dateHelper(target1, target2);
          } catch (Exception e) {
            //do nothing
          }
          float[] values;
          try {
            v.printLine("Please wait while the API retrieves that information.");
            values = p.portfolioPerformance(name, dates, api);
          } catch (Exception e) {
            v.printLine("There was an error attempting to calculate the portfolio's performance.");
            v.showPortfolioScreen();
            break;
          }
          if (values.length == 0) {
            v.printLine("There was an error attempting to calculate the portfolio's performance.");
            v.showPortfolioScreen();
            break;
          }
          v.printLines(performanceOverTimeHelper(name, dates, values));
          v.printLine("Enter any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 5:
          //manually input values
          try {
            name = p.selectPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          }

          String[] valueByHand = p.manualValuation(name, v, sc);
          v.printLines(valueByHand);
          v.printLine("Enter any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 6:
          v.showWelcomeScreen();
          currentScreen = "WS";
          break;

        default:
          break;
      }
    }
  }

  private String[] contentsHelper(String name) {

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

  private String[] simpleValueHelper(String name, String date) throws IOException, ParseException {
    try {
      String[] startTickers = p.getTickers(name);
      Float[] startCounts = p.getCounts(name);
      Date[] startDates = p.getDates(name);
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      int j = 0;
      for (int i = 0; i < startDates.length; i++) {
        if (startDates[i].compareTo(formatter.parse(date)) < 1) {
          j++;
        }
      }
      String[] tickers = new String[j];
      Float[] counts = new Float[j];
      Date[] dates = new Date[j];

      int k = 0;
      int l = 0;
      while (k < j) {
        if (startDates[l].compareTo(formatter.parse(date)) < 1) {
          tickers[k] = startTickers[l];
          counts[k] = startCounts[l];
          dates[k] = startDates[l];
          k++;
        }
        l++;
      }

      float[] values = p.getPortfolioValue(name, formatter.parse(date), api);
      String[] out = new String[tickers.length + 2];
      out[0] = "Value of Portfolio: " + name + " on " + date;
      float sum = 0;
      for (int i = 0; i < values.length; i++) {
        sum += values[i];
        out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
            + "; Value per: $" + String.format("%.02f", values[i])
            + "; Purchased: " + formatter.format(dates[i])
            + "; Total Value: $" + String.format("%.02f", values[i]);

      }
      out[tickers.length + 1] = "Total value of portfolio: $" + String.format("%.02f", sum);
      return out;
    } catch (Exception e) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String[] tickers = p.getTickers(name);
      Float[] counts = p.getCounts(name);
      float[] values = p.getPortfolioValue(name, formatter.parse(date), api);
      String[] out = new String[tickers.length + 2];
      out[0] = "Value of Portfolio: " + name + " on " + date;
      float sum = 0;
      for (int i = 0; i < values.length; i++) {
        if (values[i] <= 0) {
          out[i + 1] = "No information found for symbol: " + tickers[i];
        } else {
          sum += values[i] * counts[i];
          out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
              + "; Value per: $" + String.format("%.02f", values[i])
              + "; Total Value: $" + String.format("%.02f", values[i] * counts[i]);
        }
      }
      out[tickers.length + 1] = "Total value of portfolio: $" + String.format("%.02f", sum);
      return out;
    }
  }

  private String[] costBasisHelper(String name, String date) throws ParseException, IOException {
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

  private Date[] dateHelper(Date start, Date end) {
    long day = 1000L * 60 * 60 * 24;
    ArrayList<Date> tempDateList = new ArrayList<>();
    Date current = start;
    if (end.getTime() - start.getTime() < 5 * day) {
      while (current.compareTo(end) < 0) {
        tempDateList.add(current);
        current = new Date(current.getTime() + day);
      }
      Date[] out = new Date[tempDateList.size()];
      for (int i = 0; i < tempDateList.size(); i++) {
        out[i] = tempDateList.get(i);
      }
      return out;
    }

    boolean goodEnough = false;
    int days = (int) ((end.getTime() - start.getTime()) / day);
    int i = 0;
    int extra = 0;
    while (!goodEnough) {
      extra = 0;
      i++;
      if (days % i == 0 && days / i < 30 && days / i > 4) {
        goodEnough = true;
      } else if (days % i == 0) {
        int temp = i;
        while (days / i < 30) {
          i += temp;
        }
        if (days / i < 30 && days / i > 4) {
          goodEnough = true;
        }
      } else {
        if (days / i < 30 && days / i > 4) {
          extra = days % i;
          goodEnough = true;
        }
      }
    }
    current = start;
    while (current.before(end)) {
      tempDateList.add(current);
      current = new Date(current.getTime() + i * day + extra * day);
      if (extra > 0) {
        extra--;
      }
    }

    Date[] out = new Date[tempDateList.size()];
    for (i = 0; i < tempDateList.size(); i++) {
      out[i] = tempDateList.get(i);
    }

    //out = new Date[]{formatter.parse("2015-01-01"), formatter.parse("2016-01-01"),
    //        formatter.parse("2017-01-01"), formatter.parse("2018-01-01"),
    //        formatter.parse("2019-01-01")};

    return out;
  }

  private String[] performanceOverTimeHelper(String name, Date[] dates, float[] values) {

    String[] out = new String[dates.length + 2];

    float min = values[0];
    float max = values[0];
    for (int i = 0; i < values.length; i++) {
      if (values[i] > max) {
        max = values[i];
      }
      if (values[i] < min) {
        min = values[i];
      }
    }

    int base = 1;

    while (min - Math.pow(10, base + 1) >= 0) {
      base = base + 1;
    }
    if (base < 2) {
      base = 0;
    } else {
      base = (int) Math.pow(10, base);
    }
    while (min - base * 2 > 0) {
      base = base * 2;
    }

    float ast = (max - base) / 40;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    out[0] = "Contents of Flexible Portfolio: \"" + name + "\" from " + formatter.format(dates[0])
        + " to "
        + formatter.format(dates[dates.length - 1]) + "\n";
    for (int i = 0; i < dates.length; i++) {
      int astCount = 1;
      while (astCount * ast + base <= values[i]) {
        astCount++;
      }
      out[i + 1] = formatter.format(dates[i]) + ": " + "*".repeat(astCount);
    }
    out[dates.length + 1] = "\nOne * represents up to: $" + String.format("%.02f", ast)
        + " above the base of $" + base;
    return out;
  }

  private void buildScreen(int inputOption) {

    if (inputOption < 1 || inputOption > 4) {
      v.displayError();
      v.showBuildScreen();
    } else {
      //scanner to ask for portfolio name
      switch (inputOption) {
        case 1:
          //helper method to process input
          try {
            String name = p.buildPortfolio(v, sc);
            try {
              v.printLines(contentsHelper(name));
              v.printLine("Enter any key to return to the previous menu.");
              sc.nextLine();
            } catch (Exception e) {
              //do nothing
            }
          } catch (IOException e) {
            v.printLine("There was an error building the simple portfolio. Please try again.");
          }
          v.showBuildScreen();
          break;

        case 2:
          //flex.csv build
          try {
            String name = p.buildFlexPortfolio(v, sc);
            try {
              v.printLines(contentsHelper(name));
              v.printLine("Enter any key to return to the previous menu.");
              sc.nextLine();
            } catch (Exception e) {
              //do nothing
            }
          } catch (IOException | ParseException e) {
            v.printLine("There was an error building the flexible portfolio. Please try again.");
          }
          v.showBuildScreen();
          break;

        case 3:
          String name = null;
          try {
            name = p.selectFlexPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine(
                "There are either no flexible portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showBuildScreen();
            break;
          }
          try {
            v.printLines(contentsHelper(name));
            p.editFlexPortfolio(name, v, sc);
          } catch (Exception e) {
            v.printLine("There was difficulty editing the portfolio. Please try again.");
            v.showBuildScreen();
            break;
          }
          v.printLines(contentsHelper(name));
          v.printLine("Enter any key to return to the previous menu.");
          sc.nextLine();
          v.showBuildScreen();
          break;

        case 4:
          v.showWelcomeScreen();
          currentScreen = "WS";
          break;

        default:
          break;
      }
    }
  }

  private void loadScreen(int inputOption) {

    if (inputOption < 1 || inputOption > 2) {
      v.displayError();
      v.showLoadScreen();
    } else {
      switch (inputOption) {
        case 1:
          v.printLine("Please enter the filename.");
          String name = sc.nextLine();

          try {
            boolean problem = false;
            String[] existing = p.getPortfolioNames();
            for (int i = 0; i < existing.length; i++) {
              if (existing[i].equals(name.substring(0, name.length() - 4))) {
                v.printLine("A portfolio with that name already exists. Please try again.");
                problem = true;
              }
            }
            if (problem) {
              v.showLoadScreen();
              break;
            }
          } catch (Exception e) {
            //there are no portfolios
          }

          try {
            p.readPortfolioFile(name);
            name = name.substring(0, name.length() - 4);
            v.printLines(contentsHelper(name));
            v.printLine("Enter any key to return to the previous menu.");
            sc.nextLine();
          } catch (Exception e) {
            v.printLine("The file was either not found, or not in the right format.");
          }
          v.showLoadScreen();
          break;

        case 2:
          v.showWelcomeScreen();
          currentScreen = "WS";
          break;

        default:
          break;
      }
    }
  }

  private void welcomeScreen(int inputOption) {

    if (inputOption < 1 || inputOption > 6) {
      v.displayError();
      v.showWelcomeScreen();
    } else {
      switch (inputOption) {
        case 1:

          v.showLoadScreen();
          currentScreen = "LS";
          break;

        case 2:
          v.showBuildScreen();
          currentScreen = "BS";
          break;

        case 3:
          v.showPortfolioScreen();
          currentScreen = "PS";
          break;

        case 4:
          String name;
          try {
            name = p.selectPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine(
                "There are either no flexible portfolios yet or the input was out of bounds.");
            sc.nextLine();
            v.showWelcomeScreen();
            break;
          }
          try {
            p.savePortfolio(name);
            v.printLine("Portfolio saved.");
          } catch (IOException e) {
            v.printLine("Saving failed.");
          }
          v.showWelcomeScreen();
          break;

        case 5:
          String[] names = p.getPortfolioNames();
          try {
            for (int i = 0; i < names.length; i++) {
              p.savePortfolio(names[i]);
            }
            v.printLine("All portfolios saved.");
          } catch (Exception e) {
            v.printLine("Unable to successfully save all portfolios.");
          }
          v.showWelcomeScreen();
          break;

        case 6:
          flag = false;
          break;

        default:
          break;
      }
    }
  }
}