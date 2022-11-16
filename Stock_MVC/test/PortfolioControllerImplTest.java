import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.Persistence;
import model.PortfolioManager;
import model.PortfolioManagerImpl;
import org.junit.Test;
import view.ViewInterface;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test for the portfolio controller implementation.
 */
public class PortfolioControllerImplTest {

  /**
   * A mock portfolio manager to be passed to the tested input controller.
   */
  public class MockPortfolioManager implements PortfolioManager {

    private StringBuilder log;
    public MockPortfolioManager(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void portBuilder(ArrayList<String> tickerList, ArrayList<Float> floatList, String name) {

    }

    @Override
    public void portFlexBuilder(String name) {
      log.append("portFlexBuilder method called with " + name + " ");
    }

    @Override
    public String readPortfolioFile(String filename) throws IOException, ParseException {
      log.append("readPortfolioFile method called with " + filename + " ");
      return null;
    }

    @Override
    public void savePortfolio(String portfolioName) throws IOException {
      log.append("savePortfolio method called with " + portfolioName + " ");
    }

    @Override
    public String[] getPortfolioNames() {
      log.append("getPortfolioNames method called ");
      return new String[0];
    }

    @Override
    public String[] getFlexPortfolioNames() {
      log.append("getFlexPortfolioNames method called ");
      return new String[0];
    }

    @Override
    public float[] getPortfolioValue(String name, String date) throws IOException, ParseException {
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
    public boolean validateTicker(String ticker) throws IOException {
      log.append("validateTicker method called with " + ticker + " ");
      return true;
    }

    @Override
    public boolean validateTicker(String ticker, Date date) throws IOException, ParseException {
      log.append("validateTicker method called with " + ticker + " and " + date + " ");
      return true;
    }

    @Override
    public void editFlexPortfolio(String name, String ticker, Float count, Date date) throws IllegalArgumentException {
      log.append("editFlexPortfolio method called with " + name + ", " + ticker + ", "
              + count + ", " + date + " ");
    }

    @Override
    public float[] getCostBasis(String name, String date) throws ParseException, IOException {
      log.append("getCostBasis method called with " + name + " and " + date + " ");
      return new float[0];
    }

    @Override
    public float[] portfolioPerformance(String name, Date[] dates) {
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
   * A mock view to be passed to the input controller for testing purposes.
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
    pc.getPortfolioValue("port", "01-01-2018");
    assertEquals("getPortfolioValue method called with port and 01-01-2018 ", log.toString());
  }

  @Test
  public void testGetCostBasis() throws ParseException, IOException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.getCostBasis("port", "01-01-2018");
    assertEquals("getCostBasis method called with port and 01-01-2018 ", log.toString());
  }

  @Test
  public void testPortfolioPerformance() throws IOException, ParseException {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    Date[] dates = new Date[3];
    pc.portfolioPerformance("port", dates);
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
    Readable in = new StringReader("b\r AAPL\r 100 \n 2016\r 01\r 01\r");
    //"AAPL 100 2016 01 01"
    StringBuilder log = new StringBuilder();
    PortfolioManager mockM = new MockPortfolioManager(log);
    ViewInterface mockV = new MockView(log);
    PortfolioController pc = new PortfolioControllerImpl(in, mockM);
    pc.editFlexPortfolio("port", mockV, new Scanner(in));
    System.out.println();
    //System.out.println(log.toString());
    /*assertEquals("Please choose whether to buy or sell, by entering 'b' or 's'. Alternatively, "
            + "or enter 'Done' to finish."
            , log.toString());*/

  }

 /* @Test
  public void testReaPortfolio() throws IOException, ParseException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");
    StringBuilder log = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, new MockPortfolioManager(log));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }*/
}