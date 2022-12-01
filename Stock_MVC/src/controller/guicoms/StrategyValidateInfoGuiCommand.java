package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.PortfolioManager;
import view.GuiInterface;
import view.JFrameView;

public class StrategyValidateInfoGuiCommand implements GuiCommand {

  @Override
  public void go(GuiInterface f, PortfolioManager p, API api) {
    Object[] o = f.getOperationalStuff();
    String amountString = o[0].toString();
    String frequencyString = o[1].toString();
    String year1 = o[2].toString();
    String month1 = o[3].toString();
    String day1 = o[4].toString();
    String year2 = o[5].toString();
    String month2 = o[6].toString();
    String day2 = o[7].toString();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date startingDate = new Date();
    Date endingDate = new Date();
    float amount;
    int frequency;

    try{
      amount = Float.parseFloat(amountString);
      if (amount < 1) {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      f.printLine("The amount entered was not a dollar amount greater than or equal to $1.");
      f.setCurrScreen("Error");
      return;
    }

    try{
      frequency = Integer.parseInt(frequencyString);
      if (frequency < 1) {
        throw new IllegalArgumentException();
      }
    } catch (Exception e) {
      f.printLine("The interval entered was not at least 1 day.");
      f.setCurrScreen("Error");
      return;
    }

    try{
      startingDate = formatter.parse(year1 + "-" + month1 + "-" + day1);
    } catch (Exception e) {
      f.printLine("The starting date entered was invalid.");
      f.setCurrScreen("Error");
      return;
    }
    try {
      endingDate = formatter.parse(year2 + "-" + month2 + "-" + day2);
    } catch (Exception e) {
      f.printLine("The ending date entered was invalid.");
      f.setCurrScreen("Error");
      return;
    }
    if (!startingDate.before(endingDate)) {
      f.printLine("Please be sure the dates entered are chronological.");
      f.setCurrScreen("Error");
      return;
    }
    f.setCurrScreen("Proceed");
  }
}
