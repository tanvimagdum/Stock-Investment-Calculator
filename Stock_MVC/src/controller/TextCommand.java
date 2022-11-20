package controller;

import java.util.Scanner;
import view.ViewInterface;

public interface TextCommand {
  void go(Scanner sc, ViewInterface v, PortfolioController p, API api);
}
