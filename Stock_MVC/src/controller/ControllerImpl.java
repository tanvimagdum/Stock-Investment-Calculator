package controller;

import java.io.PrintStream;
import java.lang.ModuleLayer.Controller;
import java.util.Map;
import java.util.Scanner;
import javax.naming.ldap.Control;
import view.ViewImpl;
import view.ViewInterface;

public class ControllerImpl implements InputController{
  String ui;
  String currentScreen;
  ViewInterface v;
  PortfolioController p;
  Scanner sc;
  Map<String, Map<String, Map>> uiMap;
  Map<String, Map> textMenus;
  Map<String, Map> guiMenus;
  Map<String, TextCommand> textWelcomeScreen;
  Map<String, TextCommand> textLoadScreen;
  Map<String, TextCommand> textBuildScreen;
  Map<String, TextCommand> textViewScreen;

  public ControllerImpl(ViewInterface view, PortfolioController portCon,
      Readable in, PrintStream out, API api){
    this.v = view;
    this.p = portCon;

  }

  /**
   * A method that starts the program and asks the user input and sends it for processing.
   */
  @Override
  public void start() {
    boolean flag = true;
    boolean uiSelect = true;
    while(uiSelect) {
      v.printLine("Please enter 'text' for a text-based interface. Alternatively, enter 'gui' "
          + "for a graphics interface.");
      String selection = sc.nextLine();
      if (selection.equals("gui")) {
        //set view to gui
        ui = "gui";
        uiSelect = false;
      }
      if (selection.equals("text")) {

      }
    }

    if (ui.equals("text")) {

    }
    while (flag) {
      if (!flag) {
        break;
      }


    }
  }

}
