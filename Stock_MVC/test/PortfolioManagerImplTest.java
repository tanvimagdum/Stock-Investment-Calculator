import controller.APIImpl;
import controller.Persistence;
import model.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test class for portfolio manager implementation
 * class.
 */

public class PortfolioManagerImplTest {
  Persistence pers = new Persistence();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testPortfolioBuilder() {
    ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
    stockList.add(new Stock<>("GOOG", (float) 10.20));
    stockList.add(new Stock<>("AAPL", (float) 11.20));
    stockList.add(new Stock<>("MSFT", (float) 14.80));

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.20,
            (float) 11.20, (float) 14.80));


    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");

    assertEquals(stockList.size(), portManager.getTickers("My Portfolio").length);
    assertEquals("My Portfolio", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio", portManager.getFlexPortfolioNames()[0]);

    assertEquals("GOOG", portManager.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager.getTickers("My Portfolio")[1]);
    assertEquals("MSFT", portManager.getTickers("My Portfolio")[2]);

    assertEquals((float) 10.20, portManager.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((float) 11.20, portManager.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((float) 14.80, portManager.getCounts("My Portfolio")[2], 0.0001);

  }

  @Test
  public void testFlexPortfolioBuilder() {
    ArrayList<FlexStock<String, Float, Date>> flexStockList = new ArrayList<>();
    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portFlexBuilder("My Portfolio");

    assertEquals("My Portfolio", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio", portManager.getFlexPortfolioNames()[0]);
  }

  @Test
  public void testGetSimplePortfolioNames() throws IllegalArgumentException {

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.20,
            (float) 11.20, (float) 14.80));

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio_1");
    portManager.portBuilder(tickerList, floatList, "My Portfolio_2");

    assertEquals("My Portfolio_1", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio_2", portManager.getPortfolioNames()[1]);

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("There are no flexible portfolios yet.");
    portManager.getFlexPortfolioNames();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("There are no flexible portfolios yet.");
    portManager.getFlexPortfolioNames();
  }

  @Test
  public void testGetFlexPortfolioNames() {
    ArrayList<FlexStock<String, Float, Date>> flexStockList = new ArrayList<>();
    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portFlexBuilder("My Portfolio");

    assertEquals("My Portfolio", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio", portManager.getFlexPortfolioNames()[0]);
  }

  @Test
  public void testGetPortfolioValue() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.00,
            (float) 11.00, (float) 15.00));

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    float[] output = new float[0];
    try {
      output = portManager.getPortfolioValue("My Portfolio", "2012-05-24", new APIImpl());
    } catch (IOException e) {
      fail();
    } catch (ParseException e) {
      fail();
    }
    assertEquals(10, output[0], 0.001);
    assertEquals(10, output[1], 0.001);
    assertEquals(10, output[2], 0.001);

    tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    floatList = new ArrayList<>(Arrays.asList((float) 10.00,
            (float) 11.00, (float) 15.00));

    portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");

    boolean passed = true;

    try {
      output = portManager.getPortfolioValue("My Pofolio", "2012-05-24", new APIImpl());
      passed = false;
    } catch (Exception e) {
      passed = true;
    }

    if (!passed) {
      fail();
    }
  }

  @Test
  public void testReadWritePortfolio() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.00,
            (float) 11.00, (float) 14.00));

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    File file = new File("My Portfolio.csv");
    if (file.exists()) {
      file.delete();
    }

    try {
      portManager.savePortfolio("My Portfolio");
    } catch (Exception e) {
      fail();
    }

    PortfolioManager portManager2 = new PortfolioManagerImpl(pers);
    try {
      portManager2.readPortfolioFile("My Portfolio.csv");
    } catch (Exception e) {
      fail();
    }

    assertEquals("GOOG", portManager2.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager2.getTickers("My Portfolio")[1]);
    assertEquals("MSFT", portManager2.getTickers("My Portfolio")[2]);

    assertEquals((int) 10.00, portManager2.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((int) 11.00, portManager2.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((int) 14.00, portManager2.getCounts("My Portfolio")[2], 0.0001);


    tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    floatList = new ArrayList<>(Arrays.asList((float) 10.20,
            (float) 11.00, (float) 14.00));

    portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    file = new File("My Portfolio2.csv");
    if (file.exists()) {
      file.delete();
    }

    boolean passed = false;
    portManager2 = new PortfolioManagerImpl(pers);
    try {
      portManager2.readPortfolioFile("My Portfolio2.csv");
    } catch (Exception e) {
      passed = true;
    }

    if (!passed) {
      fail();
    }
  }

  @Test
  public void validateTickerTest() throws IOException {
    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    boolean trueBool = portManager.validateTicker("GOOG");
    boolean falseBool = portManager.validateTicker("GEEG");
    if (!trueBool) {
      fail();
    }
    if (falseBool) {
      fail();
    }
  }

  @Test
  public void validateDateTickerTest() throws IOException {
    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    boolean trueBool = portManager.validateTicker("GOOG");
    boolean falseBool = portManager.validateTicker("GEEG");
    if (!trueBool) {
      fail();
    }
    if (falseBool) {
      fail();
    }
  }

  @Test
  public void testEditFlexPortfolio() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
    Date d1 = formatter.parse("01-01-2018");
    Date d2 = formatter.parse("01-31-2019");
    Date d3 = formatter.parse("11-11-2020");

    String[] tickerList = new String[3];
    tickerList[0] = "GOOG";
    tickerList[1] = "AAPL";
    tickerList[2] = "MSF";
    Float[] floatList = new Float[3];
    floatList[0] = (float) 10.20;
    floatList[1] = (float) 11.20;
    floatList[2] = (float) 14.80;
    Date[] dateList = new Date[3];
    dateList[0] = d1;
    dateList[1] = d2;
    dateList[2] = d3;

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portFlexBuilder("My Portfolio");

    for (int i = 0; i < tickerList.length; i++){
      portManager.editFlexPortfolio("My Portfolio", tickerList[i], floatList[i], dateList[i]);
    }

    assertEquals("GOOG", portManager.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager.getTickers("My Portfolio")[1]);
    assertEquals("MSF", portManager.getTickers("My Portfolio")[2]);

    assertEquals((float) 10.20, portManager.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((float) 11.20, portManager.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((float) 14.80, portManager.getCounts("My Portfolio")[2], 0.0001);

    assertEquals("01-01-2018", formatter.format(portManager.getDates("My Portfolio")[0]));
    assertEquals("01-31-2019", formatter.format(portManager.getDates("My Portfolio")[1]));
    assertEquals("11-11-2020", formatter.format(portManager.getDates("My Portfolio")[2]));

  }

}
