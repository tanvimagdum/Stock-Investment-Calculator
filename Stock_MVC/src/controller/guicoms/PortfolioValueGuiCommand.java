package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.PortfolioManager;
import view.JFrameView;

public class PortfolioValueGuiCommand implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    String name = f.getPortfolioName();
    Object[] o = f.getOperationalStuff();
    String year = o[0].toString();
    String month = o[1].toString();
    String day = o[2].toString();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date upperLimit = new Date();
    upperLimit = new Date(upperLimit.getTime()-1000L*60*60*24);
    Date target = null;
    try {
      target = formatter.parse(year + "-" + month + "-" + day);
      if (!target.before(upperLimit)) {
        f.printLine("The date entered does not have information available. Please try again.");
        return;
      }
    } catch (ParseException e) {
      f.printLine("The date entered is invalid. Please try again.");
      return;
    }

    try {
      p.getPortfolioValue(name, target, api);
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
