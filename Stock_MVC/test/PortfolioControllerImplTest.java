import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.Persistence;
import model.PortfolioManager;
import model.PortfolioManagerImpl;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
      return false;
    }

    @Override
    public boolean validateTicker(String ticker, Date date) throws IOException, ParseException {
      log.append("validateTicker method called with " + ticker + " and " + date + " ");
      return false;
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
      log.append("setCommissionFee method called with ");
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
  public void testPortfolioPerformance() {
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