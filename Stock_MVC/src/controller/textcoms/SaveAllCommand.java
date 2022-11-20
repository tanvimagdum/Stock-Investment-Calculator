package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.util.Scanner;
import view.ViewInterface;

public class SaveAllCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
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
  }
}
