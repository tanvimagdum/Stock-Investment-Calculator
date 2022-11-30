package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import model.PortfolioManager;
import view.JFrameView;

public class CostBasisGuiCommand implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    String name = f.getPortfolioName();
    Object[] o = f.getOperationalStuff();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String year = o[0].toString();
    String month = o[1].toString();
    String day = o[2].toString();
    String cf = o[3].toString();
    Date upperLimit = new Date();
    upperLimit = new Date(upperLimit.getTime() - 1000L * 60 * 60 * 24);
    Date target = null;
    float comFee;
    float[] values;

    try {
      target = formatter.parse(year + "-" + month + "-" + day);
      if (!target.before(upperLimit)) {
        f.printLine("The date entered does not have information available. Please try again.");
        f.setCurrScreen("Error");
        return;
      }
    } catch (ParseException e) {
      f.printLine("The date entered is invalid. Please try again.");
      f.setCurrScreen("Error");
      return;
    }

    try {
      comFee = Float.parseFloat(cf);
      if (comFee < 0) {
        f.printLine("Commission fee must be positive. Please try again.");
        f.setCurrScreen("Error");
        return;
      }
      p.setCommissionFee(comFee);
    } catch (Exception e) {
      f.printLine("The given commission fee was invalid.");
      f.setCurrScreen("Error");
      return;
    }

    //get info only up to target date
    String[] startTickers = p.getTickers(name);
    Float[] startCounts = p.getCounts(name);
    Date[] startDates = p.getDates(name);
    int comTracker = 0;
    int j = 0;
    for (int i = 0; i < startDates.length; i++) {
      if (startDates[i].compareTo(target) < 1 && startCounts[i] > 0) {
        j++;
        comTracker++;
      } else if (startDates[i].compareTo(target) < 1) {
        comTracker++;
      }
    }
    String[] tickers = new String[j];
    Float[] counts = new Float[j];
    Date[] dates = new Date[j];

    int k = 0;
    int l = 0;
    while (k < j) {
      if (startDates[l].compareTo(target) < 1 && startCounts[l] > 0) {
        tickers[k] = startTickers[l];
        counts[k] = startCounts[l];
        dates[k] = startDates[l];
        k++;
      }
      l++;
    }

    try {
      values = p.getCostBasis(name, target, api);
    } catch (ParseException | IOException e) {
      f.printLine("There was an error retreiving that information. Please try again.");
      f.setCurrScreen("Error");
      return;
    }
    Object[] sendToView = new Object[3 + 3 * tickers.length];
    sendToView[0] = formatter.format(target);
    float sum = 0;
    for (int i = 0; i < sendToView.length-3; i += 3) {
      sum += values[i] * counts[i];
      if (counts[i] > 0) {
        sendToView[i+1] = tickers[i];
        sendToView[i+2] = String.valueOf(counts[i]);
        sendToView[i+3] = formatter.format(dates[i]);
      }
    }
    sendToView[sendToView.length-2] = String.valueOf(p.getCommissionFee() * comTracker);
    sendToView[sendToView.length-1] = String.valueOf(sum - p.getCommissionFee() * comTracker);
    f.setConStuff(sendToView);
  }
}