import static org.junit.Assert.assertEquals;

import controller.API;
import controller.PersistenceInterface;
import controller.TextCommand;
import controller.textcoms.BuildSimpleCommand;
import controller.textcoms.LoadCommand;
import controller.textcoms.ManualValuationCommand;
import controller.textcoms.SaveAllCommand;
import controller.textcoms.SaveCommand;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import model.FlexPortfolioImpl;
import model.Portfolio;
import model.PortfolioManager;
import model.PortfolioManagerImpl;
import model.Stock;
import org.junit.Test;
import view.ViewInterface;

public class textCommandTests {


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
    public float[] getPortfolioValue(String name, Date date, controller.API api) {
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
    public float[] getCostBasis(String name, Date date, controller.API api) {
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

    @Override
    public void addStrategy(String portfolioName, ArrayList<Stock<String, Float>> list, Date start,
        Date end, int frequency) {
      log.append("addStrategy method called ");
    }

    @Override
    public void updateFromStrategy(String portfolioName, API api)
        throws IOException, ParseException {
      log.append("updateFromStrategy method called ");
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
    public void showSaveScreen() {
      log.append("showSaveScreen method called ");
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

  public class MockAPI implements API {

    private StringBuilder log;

    public MockAPI(StringBuilder log) {
      this.log = log;
    }

    @Override
    public float[] getPrices(String[] tickerList, Date date) throws IOException, ParseException {
      log.append("getPrices method called ");
      return new float[0];
    }

    @Override
    public float[] getPricesAfter(String[] tickerList, Date date)
        throws IOException, ParseException {
      log.append("getPricesAfter method called ");
      return new float[0];
    }
  }

  public class MockPersistence implements PersistenceInterface {
    private StringBuilder log;

    public MockPersistence(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void saveSimpleCSV(Portfolio simplePort) throws IOException {
      log.append("saveSimpleCSV method called ");
    }

    @Override
    public void saveFlexCSV(Portfolio flexPort) throws IOException {
      log.append("saveFlexCSV method called ");
    }

    @Override
    public Portfolio loadCSV(String filename) throws IOException, ParseException {
      log.append("loadCSV method called with " + filename + " ");
      return new FlexPortfolioImpl("dummy");
    }
  }

  //TESTS

  @Test
  public void buildSimpleCommandTest() {

  }

  @Test
  public void buildFlexibleCommandTest() {

  }

  @Test
  public void strategyBuildCommandTest() {

  }

  @Test
  public void editFlexibleCommandTest() {

  }

  @Test
  public void strategyCommandTest() {

  }

  @Test
  public void dollarCostBuyCommandTest() {

  }

  @Test
  public void viewContentsCommandTest() {

  }

  @Test
  public void portfolioValueCommandTest() {

  }

  @Test
  public void costBasisCommandTest() {

  }

  @Test
  public void portfolioPerformanceCommandTest() {

  }

  @Test
  public void manualValuationCommandTest() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockP = new MockPortfolioManager(log);
    API mockA = new MockAPI(log);
    ViewInterface mockV = new MockView(log);
    TextCommand manualC = new ManualValuationCommand();
    manualC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("printLine method called showSaveScreen method called ", log.toString());
  }

  @Test
  public void saveCommandTest() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockP = new PortfolioManagerImpl(new MockPersistence(log));
    API mockA = new MockAPI(log);
    ViewInterface mockV = new MockView(log);
    TextCommand saveC = new SaveCommand();
    saveC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("printLine method called showSaveScreen method called ", log.toString());

    mockP.portFlexBuilder("dummy");
    in = new StringReader("2\n ");
    log.delete(0, log.toString().length());

    saveC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("printLines method called printLine method called "
        +"printLine method called showSaveScreen method called ", log.toString());

    in = new StringReader("1\n");
    log.delete(0, log.toString().length());

    saveC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("printLines method called printLine method called "
        + "saveFlexCSV method called printLine method called "
        + "showSaveScreen method called ", log.toString());
  }

  @Test
  public void saveAllCommandTest() {
    Readable in = new StringReader(" ");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockP = new MockPortfolioManager(log);
    API mockA = new MockAPI(log);
    ViewInterface mockV = new MockView(log);
    TextCommand saveC = new SaveAllCommand();
    saveC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("getPortfolioNames method called savePortfolio method "
        + "called with null printLine method called "
        + "showSaveScreen method called ", log.toString());

  }

  @Test
  public void loadCommandTest() {
    Readable in = new StringReader("flex.csv");
    StringBuilder log = new StringBuilder();
    PortfolioManager mockP = new PortfolioManagerImpl(new MockPersistence(log));
    API mockA = new MockAPI(log);
    ViewInterface mockV = new MockView(log);
    TextCommand loadC = new LoadCommand();
    loadC.go(new Scanner(in), mockV, mockP, mockA);
    assertEquals("printLine method called loadCSV method called with flex.csv "
        + "printLine method called showLoadScreen method called ", log.toString());
  }
}
