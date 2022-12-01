package controller.guicoms;

import controller.API;
import controller.GuiCommand;
import model.PortfolioManager;
import model.Stock;
import view.JFrameView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StrategyGuiCommand implements GuiCommand {

  @Override
  public void go(JFrameView f, PortfolioManager p, API api) {
    try {
      editStrategy(f.getPortfolioName(), f, p, api);
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private void editStrategy(String name, JFrameView f, PortfolioManager p, API api)
      throws IllegalArgumentException, IOException, ParseException {

    Object[] o = f.getOperationalStuff();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    float amount = Float.parseFloat(o[0].toString());
    int frequency = Integer.parseInt(o[1].toString());
    String year1 = o[2].toString();
    String month1 = o[3].toString();
    String day1 = o[4].toString();
    String year2 = o[5].toString();
    String month2 = o[6].toString();
    String day2 = o[7].toString();
    Date start = formatter.parse(year1 + "-" + month1 + "-" + day1);
    Date end = formatter.parse(year2 + "-" + month2 + "-" + day2);
    ArrayList<String> tickerList = new ArrayList<>();
    ArrayList<Stock<String, Float>> list = new ArrayList<>();

    if (o.length <= 8) {
      f.printLine("A strategy cannot be applied without specified stocks, please try again.");
      f.setCurrScreen("Error");
      return;
    }

    float[] percentages = new float[(o.length - 8)];
    float sum = 0;
    int j = 0;
    for (int i = 0; i < (o.length - 8); i += 2) {
      Float percent = Float.parseFloat(o[i+9].toString());
      tickerList.add(o[i+8].toString());
      percentages[j] = percent;
      sum += percent;
      j++;
    }

    if (Math.abs(100 - sum) > 0.1) {
      f.printLine("The given apportioning does not add up to 100%. Please try again.");
      f.setCurrScreen("Error");
      return;
    }

    f.printLine("Please wait while API is loading the information...");
    for (j = 0; j < tickerList.size(); j++) {
      list.add(new Stock(tickerList.get(j), amount * percentages[j] * 0.01f));
    }
    p.addStrategy(name, list, start, end, frequency);
    p.updateFromStrategy(name, api);
    //SHOW RESULTS
    new ViewContentsGuiCommand().go(f, p, api);
  }
}
