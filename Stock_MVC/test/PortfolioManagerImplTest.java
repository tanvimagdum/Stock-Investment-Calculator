import model.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test class for portfolio manager implementation
 * class.
 */

public class PortfolioManagerImplTest {
  Persistence pers = new Persistence();

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

    assertEquals("GOOG", portManager.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager.getTickers("My Portfolio")[1]);
    assertEquals("MSFT", portManager.getTickers("My Portfolio")[2]);

    assertEquals((float) 10.20, portManager.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((float) 11.20, portManager.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((float) 14.80, portManager.getCounts("My Portfolio")[2], 0.0001);

  }

  @Test
  public void testGetPortfolioNames() {

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.20,
            (float) 11.20, (float) 14.80));

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio_1");
    portManager.portBuilder(tickerList, floatList, "My Portfolio_2");

    assertEquals("My Portfolio_1", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio_2", portManager.getPortfolioNames()[1]);
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
      output = portManager.getPortfolioValue("My Portfolio", "2012-05-24");
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
      output = portManager.getPortfolioValue("My Pofolio", "2012-05-24");
      passed = false;
    } catch (Exception e) {
      passed = true;
    }

    if (!passed) {
      fail();
    }
  }

  @Test
  public void testGetPortfolioValueLatest() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float) 10.00,
            (float) 11.00, (float) 15.00));

    PortfolioManager portManager = new PortfolioManagerImpl(pers);
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    float[] output = new float[0];
    try {output = portManager.getPortfolioValue("My Portfolio","2022-02-02");
    } catch (IOException | ParseException e) {
      fail();
    }
    assertEquals("Value of Portfolio: My Portfolio as of 10/31/2022", output[0]);
    assertEquals("Ticker: GOOG; Count: 10.0; Value per: 90.50; Total Value: 905.00", output[1]);
    assertEquals("Ticker: AAPL; Count: 11.0; Value per: 150.65; Total Value: 1657.15", output[2]);
    assertEquals("Ticker: MSFT; Count: 15.0; Value per: 228.17; Total Value: 3422.55", output[3]);
    assertEquals("Total value of portfolio: 5984.70", output[4]);
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

  /*@Test
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
  }*/
}