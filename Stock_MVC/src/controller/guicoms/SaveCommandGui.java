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

        break;
      case "Save all portfolios"  :

        break;
      case "View cost basis of a portfolio on a certain date" :

        break;
      default :
        f.printLine("Please select one option");
        f.setCurrScreen("Error");
        break;
    }
  }

  private void selectFlexPortfolio(JFrameView f, PortfolioManager p) {
    String[] portNames = p.getFlexPortfolioNames();
    //System.out.println(portNames[0] + " in selectflex");

    f.setConStuff(portNames);
    //System.out.println("setConStuff set");
  }
}
