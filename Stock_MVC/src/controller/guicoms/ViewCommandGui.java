package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.util.ArrayList;
import model.PortfolioManager;
import view.JFrameView;

public class ViewCommandGui implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    switch(o[0].toString()) {
      case "View contents of a portfolio" :

        break;
      case "View value of a portfolio on a certain date"  :

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

    ArrayList<Object> portArray = new ArrayList<>();
    for(int i = 0; i < portNames.length; i++) {
      portArray.add(portNames[i]);
    }
    f.setConStuff(portArray);
    //System.out.println("setConStuff set");
  }
}
