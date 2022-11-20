package controller.textcoms;

import controller.API;
import controller.PortfolioController;
import controller.TextCommand;
import java.io.IOException;
import java.util.Scanner;
import view.ViewInterface;

public class SaveCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
    String name;
    try {
      name = p.selectPortfolio(v, sc);
    } catch (Exception e) {
      v.printLine(
          "There are either no flexible portfolios yet or the input was out of bounds.");
      sc.nextLine();
      v.showWelcomeScreen();
      return;
    }
    try {
      p.savePortfolio(name);
      v.printLine("Portfolio saved.");
    } catch (IOException e) {
      v.printLine("Saving failed.");
    }
    v.showWelcomeScreen();
  }
}
