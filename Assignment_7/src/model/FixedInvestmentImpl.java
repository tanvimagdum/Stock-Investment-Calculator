package model;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents the implementation of fixed investment interface.
 * This class perform operation of fixed investment strategy by deducting the commission fee
 * from the amount ratio of each stock and then calculating the quantity based on derived amount.
 * This class also perform operation of dollar cost averaging where each transaction is looped
 * over the dates and perform the processing in the portfolio.
 */
public class FixedInvestmentImpl implements FixedInvestment {

  private APIData apiData;

  /**
   * This constructs the fixed investment object by initializing API.
   *
   * @throws IOException if the API fails.
   */
  public FixedInvestmentImpl() throws IOException {
    this.apiData = new VantageAPIData();
  }

  @Override
  public boolean fixedInvestmentStrategy(double amount, double weight, String symbol, String date,
                                         double commissionfee, String filename)
          throws IOException, ParseException, java.text.ParseException {

    double actualPrice = amount * (weight / 100);
    actualPrice -= commissionfee;
    apiData = new VantageAPIData();
    String[] reader = apiData.getInputStream(symbol);
    StringBuilder sb = apiData.getPriceForDate(reader, date, "daily");

    Double priceOfOneStock = 0.0;
    if (!sb.toString().equals("")) {
      priceOfOneStock = Double.parseDouble(sb.toString());
    } else {
      return false;
    }

    double quantity = actualPrice / priceOfOneStock;
    quantity = Math.round(quantity * 100) / 100.0;

    FlexiblePortfolio flexiblePortfolio = new FlexiblePortfolioImpl();
    return flexiblePortfolio.buyShares(symbol, quantity, date, commissionfee, filename);
  }

  @Override
  public boolean dollarCostAveraging(double amount, double weight, String symbol,
                                     String startDate, String endDate, int interval,
                                     double commissionFee, String filename)
          throws IOException, ParseException, java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    c.setTime(sdf.parse(startDate));
    String intervalDate = startDate;
    String currentDate = sdf.format(new Date());
    if (endDate == null || endDate.equals("")
            || sdf.parse(endDate).after(sdf.parse(currentDate))) {
      endDate = currentDate;
    }
    while (sdf.parse(intervalDate).before(sdf.parse(endDate))
            || sdf.parse(intervalDate).equals(sdf.parse(endDate))) {
      if (!fixedInvestmentStrategy(amount, weight, symbol, intervalDate, commissionFee,
              filename)) {
        return false;
      }
      c.setTime(sdf.parse(intervalDate));
      c.add(Calendar.DATE, interval);
      intervalDate = sdf.format(c.getTime());
    }
    return true;

  }
}


