import model.Stock;
import model.portfolio;
import model.portfolioManager;
import model.portfolioManagerImpl;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

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
    stockList.add(new Stock<>("MSF", (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(stockList, "My Portfolio");

    portfolio portfolio = portManager.getPortfolio("My Portfolio");
    assertEquals(stockList, portfolio.returnList());
    assertEquals("My Portfolio", portfolio.getPortfolioName());

    assertEquals("GOOG", portManager.getTickers("My Portfolio")[0]);
    assertEquals("AAPL", portManager.getTickers("My Portfolio")[1]);
    assertEquals("MSF", portManager.getTickers("My Portfolio")[2]);

    assertEquals((float)10.20, portManager.getCounts("My Portfolio")[0], 0.0001);
    assertEquals((float)11.20, portManager.getCounts("My Portfolio")[1], 0.0001);
    assertEquals((float)14.80, portManager.getCounts("My Portfolio")[2], 0.0001);

  }

  @Test
  public void testGetPortfolioNames() {
    ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
    stockList.add(new Stock<>("GOOG", (float)10.20));
    stockList.add(new Stock<>("AAPL", (float)11.20));
    stockList.add(new Stock<>("MSF", (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(stockList, "My Portfolio_1");
    portManager.portBuilder(stockList, "My Portfolio_2");

    assertEquals("My Portfolio_1", portManager.getPortfolioNames()[0]);
    assertEquals("My Portfolio_2", portManager.getPortfolioNames()[1]);
  }

  /**
  @Test
  public void testGetPortfolioValue() {
      ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
      stockList.add(new Stock<>("GOOG", (float)10.20));
      stockList.add(new Stock<>("AAPL", (float)11.20));
      stockList.add(new Stock<>("MSF", (float)14.80));

      portfolioManager portManager = new portfolioManagerImpl();
      portManager.portBuilder(stockList, "My Portfolio");


  }
  */

  @Test
  public void testGetPortfolioContents() {
    ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
    stockList.add(new Stock<>("GOOG", (float)10.20));
    stockList.add(new Stock<>("AAPL", (float)11.22));
    stockList.add(new Stock<>("MSF", (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(stockList, "My Portfolio");

    String[] out = portManager.getPortfolioContents("My Portfolio");

    assertEquals("Contents of Portfolio: My Portfolio", out[0]);
    assertEquals("Ticker: GOOG; Count: 10.20", out[1]);
    assertEquals("Ticker: AAPL; Count: 11.22", out[2]);
    assertEquals("Ticker: MSF; Count: 14.80", out[3]);
  }

  @Test
  public void testReadWritePortfolio() {
    ArrayList<Stock<String, Float>> stockList = new ArrayList<>();
    stockList.add(new Stock<>("GOOG", (float)10.20));
    stockList.add(new Stock<>("AAPL", (float)11.22));
    stockList.add(new Stock<>("MSF", (float)14.80));

    portfolioManager portManager = new portfolioManagerImpl();
    portManager.portBuilder(stockList, "My Portfolio");
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
    assertEquals("MSF", writtenFile.getTickers()[2]);

    assertEquals((int)10.20, writtenFile.getCounts()[0], 0.0001);
    assertEquals((int)11.22, writtenFile.getCounts()[1], 0.0001);
    assertEquals((int)14.80, writtenFile.getCounts()[2], 0.0001);

  }

}