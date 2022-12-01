package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.util.ArrayList;
import model.PortfolioManager;
import view.GuiInterface;
import view.JFrameView;

public class SaveCommandGui implements GuiCommand {

  @Override
  public void go(GuiInterface f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    switch(o[0].toString()) {
      case "Save a specific portfolio" :
        try {
          selectFlexPortfolio(f, p);
          f.setCurrScreen("Save Portfolio");
        } catch (Exception e) {
          f.printLine(
                  "There are either no flexible portfolios yet or the input was out of bounds.");
          f.setCurrScreen("Error");
          return;
        }
        break;
      case "Save all portfolios"  :
        new SaveAllGuiCommand().go(f, p, api);
        f.setCurrScreen("Save All Portfolios");
        break;
      default :
        f.printLine("Please select one option");
        f.setCurrScreen("Error");
        break;
    }
  }

  private void selectFlexPortfolio(GuiInterface f, PortfolioManager p) {
    String[] portNames = p.getFlexPortfolioNames();
    //System.out.println(portNames[0] + " in selectflex");

    f.setConStuff(portNames);
    //System.out.println("setConStuff set");
  }
}
