import controller.APIImpl;
import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.PortfolioManager;
import org.junit.Test;
import view.ViewInterface;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the portfolio controller implementation.
 */
public class PortfolioControllerImplTest {

  /**
   * A mock portfolio manager to be passed to the portfolio controller.
   */
  public class MockPortfolioManager implements PortfolioManager {

    private StringBuilder log;

    public MockPortfolioManager(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void portBuilder(ArrayList<String> tickerList, ArrayList<Float> floatList, String name) {
      log.append("portBuilder method called with " + tickerList + ", "
          + floatList + ", " + name + " ");
    }

    @Override
    public void portFlexBuilder(String name) {
      log.append("portFlexBuilder method called with " + name + " ");
    }

    @Override
    public String readPortfolioFile(String filename) {
      log.append("readPortfolioFile method called with " + filename + " ");
      return null;
    }

    @Override
    public void savePortfolio(String portfolioName) {
      log.append("savePortfolio method called with " + portfolioName + " ");
    }

    @Override
    public String[] getPortfolioNames() {
      log.append("getPortfolioNames method called ");
      return new String[1];
    }

    @Override
    public String[] getFlexPortfolioNames() {
      log.append("getFlexPortfolioNames method called ");
      return new String[1];
    }

    @Override
    public float[] getPortfolioValue(String name, String date, controller.API api) {
      log.append("getPortfolioValue method called with " + name + " and " + date + " ");
      return new float[0];
    }

    @Override
    public boolean checkFlexEdit(String name, String ticker, float count, Date date) {
      log.append("checkFlexEdit method called with " + name + ", " + ticker + ", "
          + count + ", " + date + " ");
      return false;
    }

    @Override
    public boolean validateTicker(String ticker) {
      log.append("validateTicker method called with " + ticker + " ");
      return true;
    }

    @Override
    public boolean validateTicker(String ticker, Date date) {
      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
      log.append(
          "validateTicker method called with " + ticker + " and " + formatter.format(date) + " ");
      return true;
    }

    @Override
    public void editFlexPortfolio(String name, String ticker, Float count, Date date)
        throws IllegalArgumentException {
      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
      log.append("editFlexPortfolio method called with " + name + ", " + ticker + ", "
          + count + ", " + formatter.format(date) + " ");
    }

    @Override
    public float[] getCostBasis(String name, String date, controller.API api) {
      log.append("getCostBasis method called with " + name + " and " + date + " ");
      return new float[0];
    }

    @Override
    public float[] portfolioPerformance(String name, Date[] dates, controller.API api) {
      log.append("portfolioPerformance method called with " + name + " ");
      return new float[0];
    }

    @Override
    public String[] getTickers(String name) {
      log.append("getTickers method called with " + name + " ");
      return new String[0];
    }

    @Override
    public Float[] getCounts(String name) {
      log.append("getCounts method called with " + name + " ");
      return new Float[0];
    }

    @Override
    public Date[] getDates(String name) throws IllegalArgumentException {
      log.append("getDates method called with " + name + " ");
      return new Date[0];
    }

    @Override
    public float getCommissionFee() {
      log.append("getCommissionFee method called ");
      return 0;
    }

    @Override
    public void setCommissionFee(float fee) {
      log.append(String.format("setCommissionFee method called with %.2f", fee) + " ");
    }
  }

  /**
   * A mock view to be passed to the portfolio controller for testing purposes.
   */
  public class MockView implements ViewInterface {

    private StringBuilder log;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void showWelcomeScreen() {
      log.append("showWelcomeScreen method called ");
    }

    @Override
    public void showLoadScreen() {
      log.append("showLoadScreen method called ");
    }

    @Override
    public void showBuildScreen() {
      log.append("showBuildScreen method called ");
    }

    @Override
    public void showPortfolioScreen() {
      log.append("showPortfolioScreen method called ");
    }

    @Override
    public void printLine(String line) {
      log.append("printLine method called ");
    }

    @Override
    public void printLines(String[] lines) {
      log.append("printLines method called ");
    }

    @Override
    public void displayError() {
      log.append("displayError method called ");
    }
  }


  @Test
  public void testReadPortfolio() throws IOException, ParseException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.readPortfolioFile("port.csv");
    assertEquals("readPortfolioFile method called with port.csv ", log.toString());
  }

  @Test
  public void testSavePortfolio() throws IOException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.savePortfolio("port.csv");
    assertEquals("savePortfolio method called with port.csv ", log.toString());
  }

  @Test
  public void testGetPortfolioNames() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getPortfolioNames();
    assertEquals("getPortfolioNames method called ", log.toString());
  }

  @Test
  public void testGetFlexPortfolioNames() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getFlexPortfolioNames();
    assertEquals("getFlexPortfolioNames method called ", log.toString());
  }

  @Test
  public void testGetPortfolioValue() throws IOException, ParseException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getPortfolioValue("port", "01-01-2018", new APIImpl());
    assertEquals("getPortfolioValue method called with port and 01-01-2018 ", log.toString());
  }

  @Test
  public void testGetCostBasis() throws ParseException, IOException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getCostBasis("port", "01-01-2018", new APIImpl());
    assertEquals("getCostBasis method called with port and 01-01-2018 ", log.toString());
  }

  @Test
  public void testPortfolioPerformance() throws IOException, ParseException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    Date[] dates = new Date[3];
    pc.portfolioPerformance("port", dates, new APIImpl());
    assertEquals("portfolioPerformance method called with port ", log.toString());
  }

  @Test
  public void testGetTickers() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getTickers("port");
    assertEquals("getTickers method called with port ", log.toString());
  }

  @Test
  public void testGetCounts() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getCounts("port");
    assertEquals("getCounts method called with port ", log.toString());
  }

  @Test
  public void testGetDates() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getDates("port");
    assertEquals("getDates method called with port ", log.toString());
  }

  @Test
  public void testGetCommissionFee() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getCommissionFee();
    assertEquals("getCommissionFee method called ", log.toString());
  }

  @Test
  public void testSetCommissionFee() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.setCommissionFee(100.10f);
    assertEquals("setCommissionFee method called with 100.10 ", log.toString());
  }

  @Test
  public void testEditFlexPortfolio() throws IOException, ParseException {
    Readable in = new StringReader("b\n AAPL\n 100\n 2016\n 01\n 01\n done\n");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.editFlexPortfolio("port", mockV, new Scanner(in));
    assertEquals("printLine method called "
            + "printLine method called "
            + "validateTicker method called with AAPL "
            + "printLine method called "
            + "printLine method called "
            + "printLine method called "
            + "printLine method called "
            + "validateTicker method called with AAPL and 01-01-2016 "
            + "editFlexPortfolio method called with port, AAPL, 100.0, 01-01-2016 "
            + "printLine method called ",
        log.toString());

  }

  @Test
  public void testSelectPortfolio() {
    Readable in = new StringReader("1\n");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.selectPortfolio(mockV, new Scanner(in));
    assertEquals("getPortfolioNames method called "
            + "printLines method called "
            + "printLine method called ",
        log.toString());
  }

  @Test
  public void testSelectFlexPortfolio() {
    Readable in = new StringReader("1\n");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.selectFlexPortfolio(mockV, new Scanner(in));
    assertEquals("getFlexPortfolioNames method called "
            + "printLines method called "
            + "printLine method called ",
        log.toString());
  }

  @Test
  public void testBuildPortfolio() throws IOException {
    Readable in = new StringReader("port\n AAPL\n 100\n done\n");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.buildPortfolio(mockV, new Scanner(in));
    assertEquals("printLine method called "
            + "getPortfolioNames method called "
            + "printLine method called "
            + "validateTicker method called with AAPL "
            + "printLine method called "
            + "printLine method called "
            + "portBuilder method called with [AAPL], [100.0], port ",
        log.toString());
  }

  //System.out.println(log);

  @Test
  public void testBuildFlexPortfolio() throws IOException, ParseException {
    Readable in = new StringReader("port\n b\n AAPL\n 100\n 2016\n 01\n 01\n done\n");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.buildFlexPortfolio(mockV, new Scanner(in));
    assertEquals("printLine method called "
            + "getPortfolioNames method called "
            + "portFlexBuilder method called with port "
            + "printLine method called "
            + "printLine method called "
            + "validateTicker method called with AAPL "
            + "printLine method called "
            + "printLine method called "
            + "printLine method called "
            + "printLine method called "
            + "validateTicker method called with AAPL and 01-01-2016 "
            + "editFlexPortfolio method called with port, AAPL, 100.0, 01-01-2016 "
            + "printLine method called ",
        log.toString());
  }
}