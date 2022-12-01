package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import view.JFrameView;

import java.util.ArrayList;

public class BuildEditCommandGui implements GuiCommand {
  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    switch(o[0].toString()) {
      case "Begin building a flexible portfolio" :
        f.setCurrScreen("Build Portfolio");
        break;
      case "Begin a flexible portfolio with a strategy"  :
        f.setCurrScreen("Build Strategy");
        break;
      case "Edit a flexible portfolio" :
        try {
          selectFlexPortfolio(f, p);
          f.setCurrScreen("Edit Portfolio");
        } catch (Exception e) {
          //System.out.println(e.getMessage());
          f.printLine("There are either no flexible portfolios "
              + "yet or the input was out of bounds.");
          f.setCurrScreen("Error");
        }
        break;
      case "Add a strategy to a flexible portfolio" :
        try {
          selectFlexPortfolio(f, p);
          f.setCurrScreen("Edit Strategy");
        } catch (Exception e) {
          //System.out.println(e.getMessage());
          f.printLine("There are either no flexible portfolios "
                  + "yet or the input was out of bounds.");
          f.setCurrScreen("Error");
        }
        break;
      case "Add a fixed cost buy across a flexible portfolio" :
        f.setCurrScreen("Dollar Cost");
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

    //ArrayList<Object> portArray = new ArrayList<>();
    f.setConStuff(portNames);
    //System.out.println("setConStuff set");
  }
}
