package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import view.JFrameView;

import java.util.ArrayList;

public class LoadCommandGui implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {

    //get name from view
    Object[] o = f.getOperationalStuff();
    String name = o[0].toString();

    try {
      boolean problem = false;
      String[] existing = p.getPortfolioNames();
      for (int i = 0; i < existing.length; i++) {
        if (existing[i].equals(name.substring(0, name.length() - 4))) {
          f.printLine("A portfolio with that name already exists. Please try again.");
          problem = true;
        }
      }
      if (problem) {
        return;
      }
    } catch (Exception e) {
      //there are no portfolios
    }

    try {
      p.readPortfolioFile(name);
      f.printLine("The file was uploaded successfully!");
      //name = name.substring(0, name.length() - 4);
      //v.printLines(contentsHelper(name, p));
    } catch (Exception e) {
      f.printLine("The file was either not found, or not in the right format.");
    }

  }
}