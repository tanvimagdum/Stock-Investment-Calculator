package controller;

import model.Persistence;
import model.PortfolioManagerImpl;
import view.ViewImpl;
import view.ViewInterface;

import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * A class representing a continuous input method
 * which asks user to enter the input pertaining
 * to the menu.
 */

public class InputControllerImpl implements InputController {

  public String currentScreen = "WS";
  public boolean flag = true;
  public ViewInterface v;
  public PortfolioController p;
  private Readable input;
  private Scanner sc;

  /**
   * The main loop, which facilitates menu navigation and continues until the user
   * enters the exit condition.
   * @param args arguments to the program
   */
  public static void main(String[] args) {
    InputController in = new InputControllerImpl(new ViewImpl(System.out),
            new PortfolioControllerImpl(new InputStreamReader(System.in), new PortfolioManagerImpl(new Persistence())),
            new InputStreamReader(System.in), System.out);
    in.start();
  }

  /**
   * Construct an Input Controller Implementation object in order
   * to invoke the continuous input method in the main function.
   *
   * @param view    an object of viewInterface
   * @param portCon an object of portfolioController
   * @param in      an object containing the input stream
   * @param out     an object containing the output stream
   */

  public InputControllerImpl(ViewInterface view, PortfolioController portCon,
                             Readable in, PrintStream out) {
    this.v = view;
    this.p = portCon;
    this.input = in;
    this.sc = new Scanner(input);
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
            v.printLine("Hit any key to return to the previous menu.");
            sc.nextLine();
            v.showPortfolioScreen();
            break;
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            v.showPortfolioScreen();
            break;
          }

        case 2:
          String name;
          try {
            name = p.selectPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
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
            v.printLines(simpleValueHelper(name, year + "-" + mon + "-" + day));
          } catch (Exception e) {
            v.printLine("There was an error attempting to value the portfolio.");
          }
          v.printLine("Hit any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 3:
          //cost basis
          v.showPortfolioScreen();
          break;

        case 4:
          //performance over time
          try {
            name = p.selectFlexPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no flexible portfolios yet or the input was out of bounds.");
            v.showPortfolioScreen();
            break;
          }
          try {
            v.printLines(p.portfolioPerformance(name));
          } catch (Exception e) {
            v.printLine("There was an error attempting to calculate portfolio's performance.");
          }
          v.printLine("Hit any key to return to the previous menu.");
          sc.nextLine();
          v.showPortfolioScreen();
          break;

        case 5:
          //manually input values
          try {
            name = p.selectPortfolio(v, sc);
          } catch (Exception e) {
            v.printLine("There are either no portfolios yet or the input was out of bounds.");
            v.showPortfolioScreen();
            break;
          }

          String[] valueByHand = p.manualValuation(name, v, sc);
          v.printLines(valueByHand);
          v.printLine("Hit any key to return to the previous menu.");
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
          out[i + 1] = "BUY " +
                  "; Ticker: " + tickers[i] +
                  "; Count: " + String.format("%.02f", counts[i]) +
                  "; Date: " + formatter.format(dates[i]);
        }
        if (counts[i] < 0) {
          out[i + 1] = "SELL" +
                  "; Ticker: " + tickers[i] +
                  "; Count: " + String.format("%.02f", Math.abs(counts[i])) +
                  "; Date: " + formatter.format(dates[i]);
        }
      }
      return out;

    } catch(Exception e) {
      String[] tickers = p.getTickers(name);
      Float[] counts = p.getCounts(name);

      String[] out = new String[tickers.length + 1];

      out[0] = "Contents of Simple Portfolio: " + name;

      for (int i = 0; i < tickers.length; i++) {
        out[i + 1] = "Ticker: " + tickers[i] +
                    "; Count: " + String.format("%.02f", counts[i]);
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
      for (int i = 0; i < startDates.length; i++){
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

      float[] values = p.getPortfolioValue(name, date);
      String[] out = new String[tickers.length + 2];
      out[0] = "Value of Portfolio: " + name + " on " + date;
      float sum = 0;
      for (int i = 0; i < values.length; i++) {
        if (values[i] < 0) {
          out[i + 1] = "No information found for symbol: " + tickers[i];
        } else {
          sum += values[i] * counts[i];
          if (counts[i] > 0) {
            out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
                    + "; Value per: " + String.format("%.02f", values[i])
                    + "; Purchased: " + formatter.format(dates[i])
                    + "; Total Value: " + String.format("%.02f", values[i] * counts[i]);
          } else {
            out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + -1*counts[i]
                    + "; Value per: " + String.format("%.02f", values[i])
                    + "; Sold: " + formatter.format(dates[i])
                    + "; Total Value: " + String.format("%.02f", values[i] * counts[i]);
          }
        }
      }
      out[tickers.length + 1] = "Total value of portfolio: " + String.format("%.02f", sum);
      return out;
    } catch (Exception e) {
      String[] tickers = p.getTickers(name);
      Float[] counts = p.getCounts(name);
      float[] values = p.getPortfolioValue(name, date);
      String[] out = new String[tickers.length + 2];
      out[0] = "Value of Portfolio: " + name + " on " + date;
      float sum = 0;
      for (int i = 0; i < values.length; i++) {
        if (values[i] < 0) {
          out[i + 1] = "No information found for symbol: " + tickers[i];
        } else {
          sum += values[i] * counts[i];
          out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
                  + "; Value per: " + String.format("%.02f", values[i])
                  + "; Total Value: " + String.format("%.02f", values[i] * counts[i]);
        }
      }
      out[tickers.length + 1] = "Total value of portfolio: " + String.format("%.02f", sum);
      return out;
    }
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
            String name = p.buildPortfolio(v,sc);
            try {
              v.printLines(contentsHelper(name));
              v.printLine("Hit any key to return to the previous menu.");
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
            String name = p.buildFlexPortfolio(v,sc);
            try {
              v.printLines(contentsHelper(name));
              v.printLine("Hit any key to return to the previous menu.");
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
            v.printLine("There are either no flexible portfolios yet or the input was out of bounds.");
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
          v.printLine("Hit any key to return to the previous menu.");
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
            v.printLine("Hit any key to return to the previous menu.");
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
          String name = p.selectPortfolio(v, sc);
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