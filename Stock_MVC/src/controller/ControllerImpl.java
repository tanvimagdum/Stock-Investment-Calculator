package controller;

import controller.guicoms.*;
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
import controller.textcoms.StrategyBuildCommand;
import controller.textcoms.StrategyCommand;
import controller.textcoms.ViewContentsCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.PortfolioManager;
import model.PortfolioManagerImpl;
import view.JFrameView;
import view.ViewImpl;
import view.ViewInterface;

import javax.swing.*;

public class ControllerImpl implements InputController, ActionListener {

  //public static final String ELEMENT_TEXT_PROPERTY = "Text";
  String ui;
  String currentScreen;
  String currentButton;
  ViewInterface v;
  JFrameView f;
  PortfolioManager p;
  Scanner sc;
  API api;
  boolean flag = true;
  Map<String, Map<String, Map<String, Command>>> uiMap = new HashMap<>();

  Map<String, Map<String, Command>> textMenus = new HashMap<>();
  Map<String, Map<String, Command>> guiMenus = new HashMap<>();

  Map<String, Command> textWelcomeScreen = new HashMap<>();
  Map<String, Command> textLoadScreen = new HashMap<>();
  Map<String, Command> textBuildScreen = new HashMap<>();
  Map<String, Command> textViewScreen = new HashMap<>();
  Map<String, Command> textSaveScreen = new HashMap<>();

  Map<String, Command> guiWelcomeScreen = new HashMap<>();
  Map<String, Command> guiLoadScreen = new HashMap<>();
  Map<String, Command> guiBuildScreen = new HashMap<>();
  Map<String, Command> guiViewScreen = new HashMap<>();
  Map<String, Command> guiSaveScreen = new HashMap<>();


  public static void main(String[] args) {
    InputController in = new ControllerImpl(new ViewImpl(System.out),
        new PortfolioManagerImpl(new Persistence()),
        new InputStreamReader(System.in), System.out, new APIImpl());
    in.start();
  }


  public ControllerImpl(ViewInterface view, PortfolioManager portMan,
      Readable in, PrintStream out, API api) {
    this.v = view;
    this.p = portMan;
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
    while (uiSelect) {
      v.printLine("Please enter 'text' for a text-based interface. Alternatively, enter 'gui' "
          + "for a graphics interface.");
      String selection = sc.nextLine();
      if (selection.equals("gui") || selection.equals("text")) {
        ui = selection;
        uiSelect = false;
      }
    }

    setupMaps();

    if (ui.equals("text")) {
      v.showWelcomeScreen();
    } else {
      JFrameView.setDefaultLookAndFeelDecorated(false);
      f = new JFrameView(this);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setVisible(true);
      return;
    }

    Command com;
    Map<String, Map<String, Command>> menu;
    Map<String, Command> screen;
    String input;
    while (flag) {
      if (!flag) {
        break;
      }
      input = sc.nextLine();
      try {
        int inputInt = Integer.parseInt(input);
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
        ((TextCommand) com).go(sc, v, p, api);
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
    textMenus.put("SS", textSaveScreen);

    textWelcomeScreen.put("1", new LoadScreenCommand());
    textWelcomeScreen.put("2", new BuildScreenCommand());
    textWelcomeScreen.put("3", new ViewScreenCommand());
    textWelcomeScreen.put("4", new SaveScreenCommand());
    textWelcomeScreen.put("5", new ExitCommand());

    textLoadScreen.put("1", new LoadCommand());
    textLoadScreen.put("2", new BackCommand());

    textBuildScreen.put("1", new BuildSimpleCommand());
    textBuildScreen.put("2", new BuildFlexibleCommand());
    textBuildScreen.put("3", new StrategyBuildCommand());
    textBuildScreen.put("4", new EditFlexibleCommand());
    textBuildScreen.put("5", new StrategyCommand());
    textBuildScreen.put("6", new DollarCostBuyCommand());
    textBuildScreen.put("7", new BackCommand());

    textViewScreen.put("1", new ViewContentsCommand());
    textViewScreen.put("2", new PortfolioValueCommand());
    textViewScreen.put("3", new CostBasisCommand());
    textViewScreen.put("4", new PortfolioPerformanceCommand());
    textViewScreen.put("5", new ManualValuationCommand());
    textViewScreen.put("6", new BackCommand());

    textSaveScreen.put("1", new SaveCommand());
    textSaveScreen.put("2", new SaveAllCommand());
    textSaveScreen.put("3", new BackCommand());

    guiMenus.put("WS", guiWelcomeScreen);
    guiMenus.put("LS", guiLoadScreen);
    guiMenus.put("BS", guiBuildScreen);
    guiMenus.put("PS", guiViewScreen);
    guiMenus.put("SS", guiSaveScreen);

    guiWelcomeScreen.put("Load Button", new LoadScreenCommandGui());
    guiWelcomeScreen.put("Build Button", new BuildScreenCommandGui());
    guiWelcomeScreen.put("View Button", new ViewScreenCommandGui());
    guiWelcomeScreen.put("Save Button", new SaveScreenCommandGui());

    guiLoadScreen.put("Upload Port", new LoadCommandGui());
    guiLoadScreen.put("Back Button", new BackCommandGui());

    guiBuildScreen.put("Build/Edit Port", new BuildEditCommandGui());
    guiBuildScreen.put("Back Button", new BackCommandGui());

    guiBuildScreen.put("Set Portfolio Name", new BuildFlexibleCommandGui());
    guiBuildScreen.put("Add Stock", new EditFlexibleCommandGui());
    //guiBuildScreen.put("Done", new EditFlexibleCommandGui());

    guiBuildScreen.put("Add Stock", new EditFlexibleCommandGui());

    guiBuildScreen.put("Add Strategy", new StrategyGuiCommand());


    //guiBuildScreen.put("Button Name", new CommandName());

    //guiViewScreen.put("Button Name", new CommandName());

    //guiSaveScreen.put("Button Name", new CommandName());
  }

  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    //if (action == "Load Button"){
    //  currentScreen = "LS";
    //}

    /*switch (e.getActionCommand()) {
      case "Load Button":
        //currentScreen = "LS";
        break;
      case "Build Button":
        currentScreen = "BS";
        break;
      case "View Button":
        currentScreen = "PS";
        break;
      case "Save Button":
        currentScreen = "SS";
        break;
      case "Back":
        currentScreen = "WS";
        f.showWelcomeScreen();
        break;
      case "Upload Button":
        currentButton = "Upload Button";
        String fin = f.getOperationalStuff().toString();
        System.out.println(fin);
        comGo();
        break;
      default:
        break;
    }*/
    currentButton = action;
    comGo();
  }

  private void comGo() {
    Map<String, Map<String, Command>> menu = uiMap.get(ui);
    //System.out.println(menu.getClass().getName());
    Map<String, Command> screen = menu.get(currentScreen);
    GuiCommand com = (GuiCommand) screen.getOrDefault(currentButton, null);

    if (com == null) {
      v.printLine("Please be sure to enter one of the available selections.");
    } else {
      com.go(f, p, api);
    }
  }

  class BackCommandGui implements GuiCommand {

    @Override
    public void go(JFrameView f, PortfolioManager p, API api) {
      currentScreen = "WS";
      f.showWelcomeScreen();
    }
  }

  class LoadScreenCommandGui implements GuiCommand {

    @Override
    public void go(JFrameView f, PortfolioManager p, API api) {
      currentScreen = "LS";
      f.showLoadScreen();
    }
  }

  class BuildScreenCommandGui implements GuiCommand {

    @Override
    public void go(JFrameView f, PortfolioManager p, API api) {
      currentScreen = "BS";
      f.showBuildScreen();
    }
  }

  class ViewScreenCommandGui implements GuiCommand {

    @Override
    public void go(JFrameView f, PortfolioManager p, API api) {
      currentScreen = "PS";
      f.showPortfolioScreen();
    }
  }

  class SaveScreenCommandGui implements GuiCommand {

    @Override
    public void go(JFrameView f, PortfolioManager p, API api) {
      currentScreen = "SS";
      f.showSaveScreen();
    }
  }

  class BackCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      currentScreen = "WS";
      v.showWelcomeScreen();
    }
  }

  class LoadScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      currentScreen = "LS";
      v.showLoadScreen();
    }
  }

  class BuildScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      currentScreen = "BS";
      v.showBuildScreen();
    }
  }

  class ViewScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      currentScreen = "PS";
      v.showPortfolioScreen();
    }
  }

  class SaveScreenCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      currentScreen = "SS";
      v.showSaveScreen();
    }
  }

  class ExitCommand implements TextCommand {

    @Override
    public void go(Scanner sc, ViewInterface v, PortfolioManager p, API api) {
      flag = false;
    }
  }

}
