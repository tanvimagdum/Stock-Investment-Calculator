package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.io.IOException;
import model.PortfolioManager;
import view.GuiInterface;

/**
 * A GuiCommand to save all portfolios present in the model.
 */
public class SaveAllGuiCommand implements GuiCommand {

  @Override
  public void go(GuiInterface f, PortfolioManager p, API api) {
    try {
      String[] names = p.getPortfolioNames();
      for (int i = 0; i < names.length; i++) {
        p.savePortfolio(names[i]);
      }
      f.printLine("All portfolios saved.");
    } catch (Exception e) {
      f.printLine("Unable to successfully save all portfolios.");
    }
  }
}
