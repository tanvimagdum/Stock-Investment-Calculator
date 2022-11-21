package controller.textcoms;

import controller.API;
import controller.TextCommand;
import java.io.IOException;
import java.util.Scanner;
import model.PortfolioManager;
import view.ViewInterface;

public class SaveCommand implements TextCommand {

  @Override
  public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
    String name;
    try {
      name = selectPortfolio(v, sc, p);
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
