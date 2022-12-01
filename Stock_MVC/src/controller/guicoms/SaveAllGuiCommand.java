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
    String[] names = p.getPortfolioNames();
    for (String name: names) {
      try {
        p.savePortfolio(name);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
