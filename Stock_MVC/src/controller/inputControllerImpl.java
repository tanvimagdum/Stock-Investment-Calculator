package controller;
import model.Pair;
import view.viewImpl;
import view.viewInterface;

import java.util.ArrayList;
import java.util.Scanner;


public class inputControllerImpl implements inputController{

    public String currentScreen = "WS";
    public boolean flag = true;
    public viewInterface v = new viewImpl();


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
          int inputOption = sc.nextInt();
          switch (currentScreen) {
              case "WS" :
                  welcomeScreen(inputOption);
                  break;
              case "LS" :
                  loadScreen(inputOption);
                  break;
              case "BS":
                  buildScreen(inputOption);
                  break;
              case "PS":
                  portfolioScreen(inputOption);
                  break;
              default :
                  break;
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
                    //print stockList
                    break;
                case 2 :
                    Scanner sc = new Scanner(System.in);
                    String date = sc.next();
                    //get value by date
                    break;
                case 3 :
                    //save portfolio
                    break;
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

        if (inputOption < 1 || inputOption > 3) {
            v.displayError();
            v.showBuildScreen();
        }
        else {
            //scanner to ask for portfolio name
            switch (inputOption) {
                case 1 :
                    //helper method to process input
                    buildScreenHelper();
                    break;
                case 2 :
                    // declare build
                    break;
                case 3 :
                    v.showWelcomeScreen();
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }

    }

    private void buildScreenHelper() {
        String name;
        ArrayList<Pair<String, Float>> tempList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        v.printLine("Please enter the portfolio's name.");

        name = sc.next();

        while(true) {
            String ticker;
            int count;
            v.printLine("Please enter a ticker symbol or enter 'done'.");
            ticker = sc.next();
            if (ticker.equals("done")) {
                break;
            }

            v.printLine("Please enter the stock count.");
            count = sc.nextInt();
            tempList.add(new Pair<>(ticker, (float)count));

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
                    // handle file loading
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
                  //save portfolio
                  System.out.println("Save");
                  break;
              case 5 :
                  //save all portfolios
                  System.out.println("Save All");
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
