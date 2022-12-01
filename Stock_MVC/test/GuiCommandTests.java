import controller.API;
import controller.PersistenceInterface;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.FlexPortfolioImpl;
import model.Portfolio;
import model.PortfolioManager;
import model.Stock;
import org.junit.Test;
import view.GuiInterface;
import view.ViewInterface;

public class GuiCommandTests {

  /**
   * A mock model.
   */
  public class MockPortfolioManager implements PortfolioManager {

    private StringBuilder log;

    /**
     * A constructor for the mock model.
     *
     * @param log a stringbuilder for logging
     */
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
   * A mock class for the GUI for testing.
   */
  public class MockGui implements GuiInterface {

    private StringBuilder log;

    /**
     * A constructor for the mock GUI
     *
     * @param log a stringbuilder for logging
     */
    public MockGui(StringBuilder log) {
      this.log = log;
    }

    @Override
    public String printWarning(String line) {
      log.append("printWarning called with " + line + " ");
      return null;
    }

    @Override
    public Object[] getOperationalStuff() {
      log.append("getOperationalStuff called ");
      return new Object[0];
    }

    @Override
    public String getPortfolioName() {
      log.append("getPortfolioName called ");
      return "dummy";
    }

    @Override
    public void setConStuff(Object[] o) {
      log.append("setConStuff called ");
    }

    @Override
    public void setCurrScreen(String str) {
      log.append("setCurrScreen called ");
    }

    @Override
    public String getCurrScreen() {
      log.append("getCurrScreen called ");
      return null;
    }

    @Override
    public void showWelcomeScreen() {
      log.append("showWelcomeScreen called ");
    }

    @Override
    public void showLoadScreen() {
      log.append("showLoadScreen called ");
    }

    @Override
    public void showBuildScreen() {
      log.append("showBuildScreen called ");
    }

    @Override
    public void showPortfolioScreen() {
      log.append("showPortfolioScreen called ");
    }

    @Override
    public void showSaveScreen() {
      log.append("showSaveScreen called ");
    }

    @Override
    public void printLine(String line) {
      log.append("printLine called ");
    }

    @Override
    public void printLines(String[] lines) {
      log.append("printLines called ");
    }

    @Override
    public void displayError() {
      log.append("displayError called ");
    }
  }

  /**
   * A mock API for testing.
   */
  public class MockAPI implements API {

    private StringBuilder log;

    /**
     * A constructor for the mock API.
     *
     * @param log a stringbuilder for logging
     */
    public MockAPI(StringBuilder log) {
      this.log = log;
    }

    @Override
    public float[] getPrices(String[] tickerList, Date date) throws IOException, ParseException {
      log.append("getPrices method called ");
      return new float[tickerList.length];
    }

    @Override
    public float[] getPricesAfter(String[] tickerList, Date date)
        throws IOException, ParseException {
      log.append("getPricesAfter method called ");
      float[] out = new float[tickerList.length];
      for (int i = 0; i < tickerList.length; i++) {
        out[i] = 1;
      }
      return out;
    }
  }

  /**
   * A mock class for the persistence object.
   */
  public class MockPersistence implements PersistenceInterface {

    private StringBuilder log;

    /**
     * A constructor for the mock persistence object.
     *
     * @param log a stringbuilder for logging
     */
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
  public void buildEditCommandTest() {

  }

  @Test
  public void buildFlexibleCommandTest() {

  }

  @Test
  public void costBasisCommandTest() {

  }

  @Test
  public void dollarCostBuyCommandTest() {

  }

  @Test
  public void dollarCostStartCommandTest() {

  }

  @Test
  public void editFlexibleCommandTest() {

  }

  @Test
  public void loadCommandTest() {

  }

  @Test
  public void portfolioValueCommandTest(){

  }

  @Test
  public void saveCommandTest() {

  }

  @Test
  public void strategyCommandTest() {

  }

  @Test
  public void strategyValidateInfoCommandTest() {

  }

  @Test
  public void strategyValidateStockCommandTest() {

  }

  @Test
  public void viewCommandGuiTest() {

  }

  @Test
  public void viewContentsCommandTest() {

  }


}