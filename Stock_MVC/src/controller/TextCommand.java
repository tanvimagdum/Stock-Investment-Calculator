package controller;

import java.util.Scanner;
import model.PortfolioManager;
import view.ViewInterface;

public interface TextCommand {
  void go(Scanner sc, ViewInterface v, PortfolioManager p, API api);
}
