package controller.textcoms;

import controller.API;
import controller.TextCommand;
import java.util.Scanner;
import model.PortfolioManager;
import view.ViewInterface;

public class ManualValuationCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
    String name;
    try {
      name = selectPortfolio(v, sc, p);
    } catch (Exception e) {
      v.printLine("There are either no portfolios yet or the input was out of bounds.");
      sc.nextLine();
      v.showPortfolioScreen();
      return;
    }

    String[] valueByHand = manualValuation(name, v, sc, p);
    v.printLines(valueByHand);
    v.printLine("Enter any key to return to the previous menu.");
    sc.nextLine();
    v.showPortfolioScreen();
  }

  public String[] manualValuation(String name, ViewInterface v, Scanner sc, PortfolioManager p) {
    String[] tickers = p.getTickers(name);
    Float[] counts = p.getCounts(name);
    String[] out = new String[tickers.length + 2];
    v.printLine("For each of the following tickers, please enter a dollar value.");
    out[0] = "Value of Portfolio " + name;
    float sum = 0;
    float value;
    for (int i = 0; i < tickers.length; i++) {
      v.printLine(tickers[i]);

      try {
        value = Float.parseFloat(sc.next());
        sc.nextLine();
        if (value < 0) {
          v.printLine("Please be sure to enter a positive number.");
          i--;
          continue;
        }
      } catch (Exception e) {
        v.printLine("Please be sure to enter a number.");
        i--;
        continue;
      }
      sum += value * counts[i];
      out[i + 1] = "Ticker: " + tickers[i] + "; Count: " + counts[i]
          + "; Value per: " + String.format("%.02f", value)
          + "; Total value: " + String.format("%.02f", value * counts[i]);
    }

    out[tickers.length + 1] = "Total value: " + sum;
    return out;
  }

  private String selectPortfolio(ViewInterface v, Scanner sc, PortfolioManager p) {
    String[] portNames = p.getPortfolioNames();
    String[] numbered = new String[portNames.length];

    for (int i = 0; i < portNames.length; i++) {
      numbered[i] = (i + 1) + ". " + portNames[i];
    }

    v.printLines(numbered);
    v.printLine("Please choose one of the following options:");
    int index = sc.nextInt();
    sc.nextLine();
    return portNames[index - 1];
  }
}
