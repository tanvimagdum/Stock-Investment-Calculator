package controller;
import view.viewImpl;
import view.viewInterface;

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
            //v.displayError();
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
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }

    }

    private void buildScreen(int inputOption) {

        if (inputOption < 1 || inputOption > 3) {
            //v.displayError();
        }
        else {
            switch (inputOption) {
                case 1 :
                    //helper method to process input
                    break;
                case 2 :
                    // declare build
                    break;
                case 3 :
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }

    }

    private void loadScreen(int inputOption) {

        if (inputOption < 1 || inputOption > 2) {
            //v.displayError();
        }
        else {
            switch (inputOption) {
                case 1 :
                    // handle file loading
                    break;
                case 2 :
                    currentScreen = "WS";
                    break;
                default :
                    break;
            }
        }
    }

    private void welcomeScreen(int inputOption) {

      if (inputOption < 1 || inputOption > 6) {
          //v.displayError();
      }
      else {
          switch (inputOption) {
              case 1 :
                  v.showLoadScreen();
                  currentScreen = "LS";
                  System.out.println("LS");
                  break;
              case 2 :
                  v.showBuildScreen();
                  currentScreen = "BS";
                  System.out.println("BS");
                  break;
              case 3 :
                  v.showPortfolioScreen();
                  currentScreen = "PS";
                  System.out.println("PS");
                  break;
              case 4 :
                  //save portfolio
                  break;
              case 5 :
                  //save all portfolios
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
