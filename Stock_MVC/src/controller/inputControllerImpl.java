package controller;
import model.Pair;
import view.viewImpl;
import view.viewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class inputControllerImpl implements inputController{

    public String currentScreen = "WS";
    public boolean flag = true;
    public viewInterface v = new viewImpl();
    public portfolioController p = new portfolioControllerImpl();

    public static void main(String[] args) {
        inputController in = new inputControllerImpl();
        in.start();
    }


    @Override
    public void start() {

        portfolioController bus = new portfolioControllerImpl();

        v.showWelcomeScreen();
        while (flag) {
          Scanner sc = new Scanner(System.in);
          try {
              int inputOption = sc.nextInt();
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
          } catch (Exception e) {
              v.printLine("Please be sure to enter an integer for menu selection.");
              v.showWelcomeScreen();
          }
        }

    }

    private void portfolioScreen(int inputOption) {

        if (inputOption < 1 || inputOption > 4) {
            v.displayError();
            v.showPortfolioScreen();
        }
        else {
            switch (inputOption) {
                case 1 :
                    try {
                        String name = p.selectPortfolio(v);
                        Scanner sc = new Scanner(System.in);
                        v.printLines(p.getPortfolioContents(name));
                        v.printLine("Hit any key to return to the previous menu.");
                        sc.nextLine();
                        v.showPortfolioScreen();
                        break;
                    } catch (Exception e) {
                        v.printLine("There are no portfolios, yet.");
                        v.showPortfolioScreen();
                        break;
                    }

                case 2 :
                    Scanner sc = new Scanner(System.in);
                    String date = sc.nextLine();
                    //get value by date
                    v.showPortfolioScreen();
                    break;
                case 3 :
                    //manually input values
                    try {
                        sc = new Scanner(System.in);
                        String name = p.selectPortfolio(v);
                        String[] valueByHand = p.manualValuation(name, v);
                        v.printLines(valueByHand);
                        v.printLine("Hit any key to return to the previous menu.");
                        sc.nextLine();
                        v.showPortfolioScreen();
                        break;
                    } catch (Exception e) {
                        v.printLine("There are no portfolios, yet.");
                        v.showPortfolioScreen();
                        break;
                    }
                case 4 :
                    v.showWelcomeScreen();
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }
    }

    private String[] portfolioValueHelper(String name) {
        String[] tickers = p.getTickers(name);
        Float[] counts = p.getCounts(name);
        String[] out = new String[tickers.length+2];
        v.printLine("For each of the following tickers, please enter a dollar value.");
        Scanner sc = new Scanner(System.in);
        out[0] = "Value of Portfolio " + name;
        float sum = 0;
        for (int i = 0; i < tickers.length; i++) {
            v.printLine(tickers[i]);
            //try
            float value = Float.valueOf(sc.nextLine());
            sum += value*counts[i];
            //String a = sc.nextLine();2
            out[i+1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
                    + "; Value per: " + value + "; Total value: " + value*counts[i];
        }
        out[tickers.length+1] = "Total value: " + sum;

        return out;
    }

    private void buildScreen(int inputOption) {

        if (inputOption < 1 || inputOption > 2) {
            v.displayError();
            v.showBuildScreen();
        }
        else {
            //scanner to ask for portfolio name
            switch (inputOption) {
                case 1 :
                    //helper method to process input
                    p.buildPortfolio(v);
                    v.showBuildScreen();
                    break;
                case 2 :
                    v.showWelcomeScreen();
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }

    }

    private void loadScreen(int inputOption) {

        if (inputOption < 1 || inputOption > 2) {
            v.displayError();
            v.showLoadScreen();
        }
        else {
            switch (inputOption) {
                case 1 :
                    //
                    v.printLine("Please enter the filename.");
                    Scanner sc = new Scanner(System.in);
                    String name = sc.nextLine();
                    try {
                        p.readPortfolioFile(name);
                        v.printLine("File loaded.");
                    } catch (FileNotFoundException e) {
                        v.printLine("The file was either not found, or not in the right format.");
                    }
                    v.showLoadScreen();
                    break;
                case 2 :
                    v.showWelcomeScreen();
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }
    }

    private void welcomeScreen(int inputOption) {

      if (inputOption < 1 || inputOption > 6) {
          v.displayError();
          v.showWelcomeScreen();
      }
      else {
          switch (inputOption) {
              case 1 :
                  v.showLoadScreen();
                  currentScreen = "LS";
                  break;
              case 2 :
                  v.showBuildScreen();
                  currentScreen = "BS";
                  break;
              case 3 :
                  v.showPortfolioScreen();
                  currentScreen = "PS";
                  break;
              case 4 :
                  String name = p.selectPortfolio(v);
                  try {
                      p.savePortfolio(name);
                      v.printLine("Portfolio saved.");
                  } catch (IOException e) {
                      v.printLine("Saving failed.");
                  }
                  v.showWelcomeScreen();
                  break;
              case 5 :
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
              case 6 :
                  flag = false;
                  break;
              default :
                  break;
          }
      }
    }
}
