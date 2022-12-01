package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.util.ArrayList;
import model.PortfolioManager;
import view.GuiInterface;
import view.JFrameView;

public class ViewCommandGui implements GuiCommand {

  @Override
  public void go(GuiInterface f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    switch(o[0].toString()) {
      case "View contents of a portfolio" :
        try {
          selectFlexPortfolio(f, p);
          f.setCurrScreen("Show Contents");
        } catch (Exception e) {
          //System.out.println(e.getMessage());
          f.printLine("There are either no flexible portfolios "
                  + "yet or the input was out of bounds.");
          f.setCurrScreen("Error");
        }
        break;
      case "View value of a portfolio on a certain date"  :
        f.setCurrScreen("Show Value");
        break;
      case "View cost basis of a portfolio on a certain date" :
        f.setCurrScreen("Show Cost Basis");
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
