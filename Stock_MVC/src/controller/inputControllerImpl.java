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
                    v.printLines(p.getPortfolioNames());
                    v.printLine("Please enter one of the following names:");
                    Scanner sc = new Scanner(System.in);
                    String name = sc.nextLine();
                    v.printLines(p.getPortfolioContents(name));
                    v.printLine("Hit any key to return to the previous menu.");
                    sc.nextLine();
                    v.showPortfolioScreen();
                    break;
                case 2 :
                    sc = new Scanner(System.in);
                    String date = sc.next();
                    //get value by date
                    break;
                case 3 :
                    //manually input values
                    v.printLines(p.getPortfolioNames());
                    v.printLine("Please enter one of the following names:");
                    sc = new Scanner(System.in);
                    name = sc.nextLine();
                    String[] valueByHand = portfolioValueHelper(name);
                    v.printLines(valueByHand);
                    v.printLine("Hit any key to return to the previous menu.");
                    sc.nextLine();
                    v.showPortfolioScreen();
                case 4 :
                    //save portfolio
                    break;
                case 5 :
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

    private void buildScreenHelper() {
        String name;
        ArrayList<Pair<String, Float>> tempList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        v.printLine("Please enter the portfolio's name.");

        name = sc.nextLine();

        while(true) {
            String ticker;
            int count;
            v.printLine("Please enter a ticker symbol or enter 'done'.");
            ticker = sc.nextLine();
            if (ticker.equals("done")) {
                break;
            }

            v.printLine("Please enter the stock count.");
            count = sc.nextInt();
            tempList.add(new Pair<>(ticker, (float)count));
            String a = sc.nextLine();

        }
        p.portBuilder(tempList, name);
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
