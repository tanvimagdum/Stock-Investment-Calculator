import model.FlexPortfolioImpl;
import model.FlexStock;
import model.Portfolio;
import model.Stock;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class FlexPortfolioImplTest {

  @Test
  public void testGetPortfolioName() {
    Portfolio port = new FlexPortfolioImpl("FlexPort");
    assertEquals("FlexPort", port.getPortfolioName());
  }

  @Test
  public void testGetTickerCountDate() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

    ArrayList<FlexStock<String, Float, Date>> flexStockList = new ArrayList<>();
    /*flexStockList.add(new FlexStock<>("GOOG", (float) 10.20, formatter.parse("01-01-2018")));
    flexStockList.add(new FlexStock<>("AAPL", (float) 11.20, formatter.parse("01-24-2019")));
    flexStockList.add(new FlexStock<>("MSF", (float) 14.80, formatter.parse("12-31-2020")));

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSF"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.20,
            (float) 11.20, (float) 14.80));
    ArrayList<Date> dateList = new ArrayList<>(Arrays.asList(formatter.parse("01-01-2018"),
            formatter.parse("01-24-2019"),formatter.parse("12-31-2020")));*/

    Portfolio port = new FlexPortfolioImpl("FlexPort");

    assertEquals("FlexPort", port.getPortfolioName());
    /*assertEquals("GOOG", port.getTickers()[0]);
    assertEquals("AAPL", port.getTickers()[1]);
    assertEquals("MSF", port.getTickers()[2]);

    assertEquals((float) 10.20, port.getCounts()[0], 0.0001);
    assertEquals((float) 11.20, port.getCounts()[1], 0.0001);
    assertEquals((float) 14.80, port.getCounts()[2], 0.0001);

    assertEquals("01-01-2018", ((FlexPortfolioImpl) port).getDates()[0].toString());
    assertEquals("01-24-2019", ((FlexPortfolioImpl) port).getDates()[1].toString());
    assertEquals("12-31-2020", ((FlexPortfolioImpl) port).getDates()[2].toString());*/

  }

  @Test
  public void testCheckEdit() {
  }

  @Test
  public void testAddFlexStock() {
  }
}