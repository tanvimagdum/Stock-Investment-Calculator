package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.util.Scanner;
import view.ViewInterface;

public class ManualValuationCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
    String name;
    try {
      name = p.selectPortfolio(v, sc);
    } catch (Exception e) {
      v.printLine("There are either no portfolios yet or the input was out of bounds.");
      sc.nextLine();
      v.showPortfolioScreen();
      return;
    }

    String[] valueByHand = p.manualValuation(name, v, sc);
    v.printLines(valueByHand);
    v.printLine("Enter any key to return to the previous menu.");
    sc.nextLine();
    v.showPortfolioScreen();
  }
}
