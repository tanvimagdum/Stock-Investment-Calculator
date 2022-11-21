package controller;

import controller.textcoms.BuildFlexibleCommand;
import controller.textcoms.BuildSimpleCommand;
import controller.textcoms.CostBasisCommand;
import controller.textcoms.DollarCostBuyCommand;
import controller.textcoms.EditFlexibleCommand;
import controller.textcoms.LoadCommand;
import controller.textcoms.ManualValuationCommand;
import controller.textcoms.PortfolioPerformanceCommand;
import controller.textcoms.PortfolioValueCommand;
import controller.textcoms.SaveAllCommand;
import controller.textcoms.SaveCommand;
import controller.textcoms.ViewContentsCommand;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.PortfolioManagerImpl;
import view.ViewImpl;
import view.ViewInterface;

public class ControllerImpl implements InputController{
  String ui;
  String currentScreen;
  ViewInterface v;
  PortfolioController p;
  Scanner sc;
  API api;
  boolean flag = true;
  Map<String, Map<String, Map<Integer, TextCommand>>> uiMap = new HashMap<>();
  Map<String, Map<Integer, TextCommand>> textMenus = new HashMap<>();
  Map<String, Map<Integer, TextCommand>> guiMenus = new HashMap<>();
  Map<Integer, TextCommand> textWelcomeScreen = new HashMap<>();
  Map<Integer, TextCommand> textLoadScreen = new HashMap<>();
  Map<Integer, TextCommand> textBuildScreen = new HashMap<>();
  Map<Integer, TextCommand> textViewScreen = new HashMap<>();


  public static void main(String[] args) {
    InputController in = new ControllerImpl(new ViewImpl(System.out),
        new PortfolioControllerImpl(new InputStreamReader(System.in),
            new PortfolioManagerImpl(new Persistence())),
        new InputStreamReader(System.in), System.out, new APIImpl());
    in.start();
  }


  public ControllerImpl(ViewInterface view, PortfolioController portCon,
      Readable in, PrintStream out, API api) {
    this.v = view;
    this.p = portCon;
    this.sc = new Scanner(in);
    this.api = api;
  }

  /**
   * A method that starts the program and asks the user input and sends it for processing.
   */
  @Override
  public void start() {
    currentScreen = "WS";
    boolean uiSelect = true;
    while(uiSelect) {
      v.printLine("Please enter 'text' for a text-based interface. Alternatively, enter 'gui' "
          + "for a graphics interface.");
      String selection = sc.nextLine();
      if (selection.equals("gui") || selection.equals("text")){
        ui = selection;
        uiSelect = false;
      }
    }

    if (ui.equals("text")) {
      v.showWelcomeScreen();
    } else {
      //do gui stuff
    }

    setupMaps();
    TextCommand com;
    Map<String, Map<Integer, TextCommand>> menu;
    Map<Integer, TextCommand> screen;
    int input;
    while (flag) {
      if (!flag) {
        break;
      }
      try {
        input = Integer.parseInt(sc.nextLine());
      } catch (Exception e) {
        v.printLine("Please be sure to enter an integer.");
        continue;
      }
      menu = uiMap.get(ui);
      screen = menu.get(currentScreen);
      com = screen.getOrDefault(input, null);

      if (com == null) {
        v.printLine("Please be sure to enter one of the available selections.");
      } else {
        com.go(sc, v, p, api);
      }
    }
  }

  private void setupMaps() {
    uiMap.put("text", textMenus);
    uiMap.put("gui", guiMenus);

    textMenus.put("WS", textWelcomeScreen);
    textMenus.put("LS", textLoadScreen);
    textMenus.put("BS", textBuildScreen);
    textMenus.put("PS", textViewScreen);

    textWelcomeScreen.put(1, new LoadScreenCommand());
    textWelcomeScreen.put(2, new BuildScreenCommand());
    textWelcomeScreen.put(3, new ViewScreenCommand());
    textWelcomeScreen.put(4, new SaveCommand());
    textWelcomeScreen.put(5, new SaveAllCommand());
    textWelcomeScreen.put(6, new ExitCommand());

    textLoadScreen.put(1, new LoadCommand());
    textLoadScreen.put(2, new BackCommand());

    textBuildScreen.put(1, new BuildSimpleCommand());
    textBuildScreen.put(2, new BuildFlexibleCommand());
    textBuildScreen.put(4, new EditFlexibleCommand());
    textBuildScreen.put(5, new DollarCostBuyCommand());
    textBuildScreen.put(6, new BackCommand());

    textViewScreen.put(1, new ViewContentsCommand());
    textViewScreen.put(2, new PortfolioValueCommand());
    textViewScreen.put(3, new CostBasisCommand());
    textViewScreen.put(4, new PortfolioPerformanceCommand());
    textViewScreen.put(5, new ManualValuationCommand());
    textViewScreen.put(6, new BackCommand());

  }

  class BackCommand implements TextCommand {
    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
      currentScreen = "WS";
      v.showWelcomeScreen();
    }
  }

  class LoadScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
      currentScreen = "LS";
      v.showLoadScreen();
    }
  }

  class BuildScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
      currentScreen = "BS";
      v.showBuildScreen();
    }
  }

  class ViewScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
      currentScreen = "PS";
      v.showPortfolioScreen();
    }
  }

  class ExitCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioController p, API api) {
      flag = false;
    }
  }
}
