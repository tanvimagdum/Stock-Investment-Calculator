package controller;
import view.viewImpl;
import view.viewInterface;

import java.io.*;
import java.util.Scanner;

/**
 * A class representing a continuous input method
 * which asks user to enter the input pertaining
 * to the menu.
 */

public class inputControllerImpl implements inputController{

  public String currentScreen = "WS";
  public boolean flag = true;
  public viewInterface v;
  public portfolioController p;
  private InputStream input;
  private PrintStream output;

  public static void main(String[] args) {
    inputController in = new inputControllerImpl(new viewImpl(System.out),
            new portfolioControllerImpl(System.in),
            System.in, System.out);
    in.start();
  }

  /**
   * Construct an Input Controller Implementation object in order
   * to invoke the continuous input method in the main function.
   * @param view an object of viewInterface
   * @param portCon an object of portfolioController
   * @param in an object containing the input stream
   * @param out an object containing the output stream
   */

  public inputControllerImpl(viewInterface view, portfolioController portCon,
                             InputStream in, PrintStream out) {
    this.v = view;
    this.p = portCon;
    this.input = in;
    this.output = out;
  }
  @Override
  public void start() {

    //portfolioController bus = new portfolioControllerImpl(input);

    v.showWelcomeScreen();
    while (flag) {
      Scanner sc = new Scanner(input);
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
            Scanner sc = new Scanner(input);
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
          Scanner sc = new Scanner(input);
          String date = sc.nextLine();
          //get value by date
          v.showPortfolioScreen();
          break;

        case 3 :
          //manually input values
          try {
            sc = new Scanner(input);
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
          v.printLine("Please enter the filename.");
          Scanner sc = new Scanner(input);
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