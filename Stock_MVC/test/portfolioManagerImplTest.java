import model.Stock;
import model.portfolio;
import model.portfolioManager;
import model.portfolioManagerImpl;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.portfolioImpl.builder;
import static org.junit.Assert.*;

/**
 * A JUnit test class for portfolio manager implementation
 * class.
 */

public class portfolioManagerImplTest {

  @Test
  public void testPortfolioBuilder() {
    ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
    stockList.add(new Stock<>("GOOG", (float)10.20));
    stockList.add(new Stock<>("AAPL", (float)11.20));
    stockList.add(new Stock<>("MSFT", (float)14.80));

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.20,
            (float)11.20, (float)14.80));


    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList, floatList, "My Portfolio");

    portfolio portfolio = portManager.getPortfolio("My Portfolio");
    assertEquals(stockList.size(), portfolio.returnList().size());
    assertEquals("My Portfolio", portfolio.getPortfolioName());

    assertEquals("GOOG", portManager.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager.getTickers("My Portfolio")[1]);
    assertEquals("MSFT", portManager.getTickers("My Portfolio")[2]);

    assertEquals((float)10.20, portManager.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((float)11.20, portManager.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((float)14.80, portManager.getCounts("My Portfolio")[2], 0.0001);

  }

  @Test
  public void testGetPortfolioNames() {

    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.20,
            (float)11.20, (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList, floatList, "My Portfolio_1");
    portManager.portBuilder(tickerList, floatList, "My Portfolio_2");

    assertEquals("My Portfolio_1", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio_2", portManager.getPortfolioNames()[1]);
  }


  @Test
  public void testGetPortfolioValue() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.00,
            (float)11.00, (float)15.00));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    String[] output = new String[0];
    try {
      output = portManager.getPortfolioValue("My Portfolio", "2012-05-24");
    } catch (IOException e) {
      fail();
    } catch (ParseException e) {
      fail();
    }
    assertEquals("Value of Portfolio: My Portfolio on 2012-05-24", output[0]);
    assertEquals("Ticker: GOOG; Count: 10.0; Value per: 558.46; Total Value: 5584.60", output[1]);
    assertEquals("Ticker: AAPL; Count: 11.0; Value per: 565.32; Total Value: 6218.52", output[2]);
    assertEquals("Ticker: MSFT; Count: 15.0; Value per: 29.07; Total Value: 436.05", output[3]);
    assertEquals("Total value of portfolio: 12239.17", output[4]);

    tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    floatList = new ArrayList<>(Arrays.asList((float)10.00,
            (float)11.00, (float)15.00));

    portManager = new portfolioManagerImpl();
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
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.00,
            (float)11.00, (float)15.00));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    String[] output = new String[0];
    try {
      output = portManager.getPortfolioValueLatest("My Portfolio");
    } catch (IOException e) {
      fail();
    }
    assertEquals("Value of Portfolio: My Portfolio as of 10/31/2022", output[0]);
    assertEquals("Ticker: GOOG; Count: 10.0; Value per: 90.50; Total Value: 905.00", output[1]);
    assertEquals("Ticker: AAPL; Count: 11.0; Value per: 150.65; Total Value: 1657.15", output[2]);
    assertEquals("Ticker: MSFT; Count: 15.0; Value per: 228.17; Total Value: 3422.55", output[3]);
    assertEquals("Total value of portfolio: 5984.70", output[4]);
  }
  @Test
  public void testGetPortfolioContents() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.20,
            (float)11.22, (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList,  floatList, "My Portfolio");

    String[] out = portManager.getPortfolioContents("My Portfolio");

    assertEquals("Contents of Portfolio: My Portfolio", out[0]);
    assertEquals("Ticker: GOOG; Count: 10.20", out[1]);
    assertEquals("Ticker: AAPL; Count: 11.22", out[2]);
    assertEquals("Ticker: MSFT; Count: 14.80", out[3]);
  }

  @Test
  public void testReadWritePortfolio() {
    ArrayList<String> tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    ArrayList<Float> floatList = new ArrayList<>(Arrays.asList((float)10.00,
            (float)11.00, (float)14.00));

    portfolioManager portManager = new portfolioManagerImpl();
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

    portfolioManager portManager2 = new portfolioManagerImpl();
    try {
      portManager2.readPortfolioFile("My Portfolio.csv");
    } catch(Exception e) {
      fail();
    }

    portfolio writtenFile = portManager2.getPortfolio("My Portfolio");

    assertEquals("GOOG", writtenFile.getTickers()[0]);
    assertEquals("AAPL", writtenFile.getTickers()[1]);
    assertEquals("MSFT", writtenFile.getTickers()[2]);

    assertEquals((int)10.00, writtenFile.getCounts()[0], 0.0001);
    assertEquals((int)11.00, writtenFile.getCounts()[1], 0.0001);
    assertEquals((int)14.00, writtenFile.getCounts()[2], 0.0001);


    tickerList = new ArrayList<>(Arrays.asList("GOOG", "AAPL", "MSFT"));
    floatList = new ArrayList<>(Arrays.asList((float)10.20,
            (float)11.00, (float)14.00));

    portManager = new portfolioManagerImpl();
    portManager.portBuilder(tickerList, floatList, "My Portfolio");
    file = new File("My Portfolio2.csv");
    if (file.exists()) {
      file.delete();
    }

    boolean passed = false;
    portManager2 = new portfolioManagerImpl();
    try {
      portManager2.readPortfolioFile("My Portfolio2.csv");
    } catch(Exception e) {
      passed = true;
    }

    if (!passed){
      fail();
    }
  }

  @Test
  public void validateTickerTest() throws IOException {
    portfolioManager portManager = new portfolioManagerImpl();
    boolean trueBool = portManager.validateTicker("GOOG");
    boolean falseBool = portManager.validateTicker("GEEG");
    if (!trueBool) {
      fail();
    }
    if (falseBool) {
      fail();
    }
  }
}