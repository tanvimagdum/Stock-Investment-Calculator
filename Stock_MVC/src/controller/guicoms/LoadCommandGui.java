package controller.guicoms;

import controller.GuiCommand;
import model.PortfolioManager;
import view.JFrameView;
import view.ViewInterface;
/*
public class LoadCommandGui implements GuiCommand {

@Override
  public void go(JFrameView f, PortfolioManager p) {
    //get name from view
    String name = f.getOperationalStuff().toString();
    //System.out.println(fin);

    try {
      boolean problem = false;
      String[] existing = p.getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name.substring(0, name.length() - 4))) {
          //v.printLine("A portfolio with that name already exists. Please try again.");
          problem = true;
        }
      }
      if (problem) {
        //v.showLoadScreen();
        return;
      }
    } catch (Exception e) {
      //there are no portfolios
    }

    try {
      p.readPortfolioFile(name);
      name = name.substring(0, name.length() - 4);
      //v.printLines(contentsHelper(name, p));
      //v.printLine("Enter any key to return to the previous menu.");
      //sc.nextLine();
    } catch (Exception e) {
      //v.printLine("The file was either not found, or not in the right format.");
    }
    //v.showLoadScreen();

    //code to load file into model
  }
}*/