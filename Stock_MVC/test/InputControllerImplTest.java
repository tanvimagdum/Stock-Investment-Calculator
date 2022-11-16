import controller.APIImpl;
import controller.InputController;
import controller.InputControllerImpl;
import controller.PortfolioController;
import org.junit.Test;
import view.ViewInterface;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the input controller implementation.
 */
public class InputControllerImplTest {

  /**
   * A mock portfolio controller to be passed to the tested input controller.
   */
  public class MockPortfolioController implements PortfolioController {

    private StringBuilder log;

    public MockPortfolioController(StringBuilder log) {
      this.log = log;
    }

    @Override
    public String readPortfolioFile(String filename) {
      log.append("readPortfolio method called with " + filename + " ");
      return "";
    }

    @Override
    public void savePortfolio(String filename) {
      log.append("savePortfolio method called with " + filename + " ");
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
    public void editFlexPortfolio(String name, ViewInterface v, Scanner sc)
        throws IllegalArgumentException {
      log.append("editFlexPortfolio method called ");
    }

    @Override
    public String selectPortfolio(ViewInterface view, Scanner sc) {
      log.append("selectPortfolio method called ");
      return null;
    }

    @Override
    public String selectFlexPortfolio(ViewInterface v, Scanner sc) {
      log.append("selectFlexPortfolio method called ");
      return null;
    }

    @Override
    public float[] getPortfolioValue(String name, String date, controller.API api) {
      log.append("getPortfolioValue method called with " + name + "and" + date + " ");
      return new float[0];
    }

    @Override
    public float[] getCostBasis(String name, String date, controller.API api) {
      return new float[0];
    }

    @Override
    public String buildPortfolio(ViewInterface v, Scanner sc) {
      log.append("buildPortfolio method called ");
      return null;
    }

    @Override
    public String buildFlexPortfolio(ViewInterface v, Scanner sc) {
      log.append("buildFlexPortfolio method called ");
      return null;
    }

    @Override
    public String[] manualValuation(String name, ViewInterface v, Scanner sc) {
      log.append("manualValuation method called with " + name + " ");
      return new String[0];
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
      return 0;
    }

    @Override
    public void setCommissionFee(float fee) {
      //does nothing
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
      log.append("printLine method called with " + line + " ");
    }

    @Override
    public void printLines(String[] lines) {
      for (int i = 0; i < lines.length; i++) {
        log.append("printLines method called with " + lines[i] + " ");
      }
    }

    @Override
    public void displayError() {
      log.append("displayError method called ");
    }
  }

  @Test
  public void testPlainStartExit() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called ", log.toString());

  }

  @Test
  public void testLoadFileScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("1\n 2\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called "
        + "showLoadScreen method called "
        + "showWelcomeScreen method called ", log.toString());
  }

  @Test
  public void testBuildScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("2\n 4\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called "
        + "showBuildScreen method called "
        + "showWelcomeScreen method called ", log.toString());
  }

  @Test
  public void testPortfolioScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("3\n 6\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called "
        + "showPortfolioScreen method called "
        + "showWelcomeScreen method called ", log.toString());
  }

  @Test
  public void testSaveFileScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("4\n 5\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called "
            + "selectPortfolio method called "
            + "savePortfolio method called with null "
            + "printLine method called with Portfolio saved. "
            + "showWelcomeScreen method called "
            + "getPortfolioNames method called "
            + "printLine method called with All portfolios saved. "
            + "showWelcomeScreen method called ",
        log.toString());
  }

  @Test
  public void testMultipleScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("1\n 2\n 3\n 6\n 2\n 4\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    assertEquals("showWelcomeScreen method called "
            + "showLoadScreen method called "
            + "showWelcomeScreen method called "
            + "showPortfolioScreen method called "
            + "showWelcomeScreen method called "
            + "showBuildScreen method called "
            + "showWelcomeScreen method called ",
        log.toString());
  }

  @Test
  public void testErrors() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("2\n 2\n port\n q\n done\n 4\n 6\n");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out),
        new APIImpl());
    input.start();

    System.out.println(log);

    assertEquals("showWelcomeScreen method called "
            + "showBuildScreen method called "
            + "buildFlexPortfolio method called "
            + "getTickers method called with null "
            + "getCounts method called with null "
            + "getDates method called with null "
            + "printLines method called with "
            + "Contents of Flexible Portfolio: null "
            + "printLine method called with "
            + "Enter any key to return to the previous menu. "
            + "showBuildScreen method called "
            + "printLine method called with "
            + "Please be sure to enter an integer for menu selection. "
            + "showBuildScreen method called "
            + "printLine method called with "
            + "Please be sure to enter an integer for menu selection. "
            + "showBuildScreen method called "
            + "showWelcomeScreen method called ",
        log.toString());
  }
}
