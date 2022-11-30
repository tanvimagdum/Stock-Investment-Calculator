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

public class StrategyValidateStockGuiCommand implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date start = null;
    String year = o[2].toString();
    String month = o[3].toString();
    String day = o[4].toString();
    String ticker = o[o.length-2].toString();
    try {
      start = formatter.parse(year + "-" + month + "-" + day);
    } catch (Exception e) {
      f.printLine("This is a bug an should never be possible. "
          + "The date should have previously been validated.");
    }
    try {
      if (!p.validateTicker(ticker, start)) {
        throw new RuntimeException();
      }
    } catch (Exception e) {
      f.printLine("That ticker was either not recognized or was not available by the given"
          + " starting date.");
    }

  }
}
