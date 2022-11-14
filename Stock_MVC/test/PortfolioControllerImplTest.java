import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.Persistence;
import model.PortfolioManager;
import model.PortfolioManagerImpl;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
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

    }

    @Override
    public String readPortfolioFile(String filename) throws IOException, ParseException {
      return null;
    }

    @Override
    public void savePortfolio(String portfolioName) throws IOException {

    }

    @Override
    public String[] getPortfolioNames() {
      return new String[0];
    }

    @Override
    public String[] getFlexPortfolioNames() {
      return new String[0];
    }

    @Override
    public float[] getPortfolioValue(String name, String date) throws IOException, ParseException {
      return new float[0];
    }

    @Override
    public boolean checkFlexEdit(String name, String ticker, float count, Date date) {
      return false;
    }

    @Override
    public boolean validateTicker(String ticker) throws IOException {
      return false;
    }

    @Override
    public boolean validateTicker(String ticker, Date date) throws IOException, ParseException {
      return false;
    }

    @Override
    public void editFlexPortfolio(String name, String ticker, Float count, Date date) throws IllegalArgumentException {

    }

    @Override
    public float[] getCostBasis(String name, String date) throws ParseException, IOException {
      return new float[0];
    }

    @Override
    public float[] portfolioPerformance(String name, Date[] dates) {
      return new float[0];
    }

    @Override
    public String[] getTickers(String name) {
      return new String[0];
    }

    @Override
    public Float[] getCounts(String name) {
      return new Float[0];
    }

    @Override
    public Date[] getDates(String name) throws IllegalArgumentException {
      return new Date[0];
    }

    @Override
    public float getCommissionFee() {
      return 0;
    }

    @Override
    public void setCommissionFee(float fee) {

    }
  }

  @Test
  public void testReadGetPortfolio() throws IOException, ParseException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }

  @Test
  public void testPortfolioLatestValue() throws IOException, ParseException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }

}