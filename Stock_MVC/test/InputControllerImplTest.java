import controller.InputController;
import controller.InputControllerImpl;
import controller.PortfolioController;

import org.junit.Test;

import view.ViewInterface;

import java.io.IOException;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.ParseException;
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
    public String readPortfolioFile(String filename) throws IOException {
      log.append("readPortfolio method called with " + filename + " ");
      return "";
    }

    @Override
    public void savePortfolio(String filename) throws IOException {
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
    public void editFlexPortfolio(String name, ViewInterface v, Scanner sc) throws IllegalArgumentException, IOException, ParseException {
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
    public float[] getPortfolioValue(String name, String date) {
      log.append("getPortfolioValue method called with " + name + "and" + date + " ");
      return new float[0];
    }

    @Override
    public String buildPortfolio(ViewInterface v, Scanner sc) throws IOException {
      log.append("buildPortfolio method called ");
      return null;
    }

    @Override
    public String buildFlexPortfolio(ViewInterface v, Scanner sc) throws IOException {
      log.append("buildFlexPortfolio method called ");
      return null;
    }

    @Override
    public String[] manualValuation(String name, ViewInterface v, Scanner sc) {
      log.append("manualValuation method called with " + name);
      return new String[0];
    }

    @Override
    public String[] portfolioPerformance(String name, Date[] dates) {
      log.append("portfolioPerformance method called with " + name);
      return new String[0];
    }

    @Override
    public String[] getTickers(String name) {
      log.append("getTickers method called with " + name);
      return new String[0];
    }

    @Override
    public Float[] getCounts(String name) {
      log.append("getCounts method called with " + name);
      return new Float[0];
    }

    @Override
    public Date[] getDates(String name) throws IllegalArgumentException {
      log.append("getDates method called with " + name);
      return new Date[0];
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
  public void testPlainStartExit() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
    input.start();

    assertEquals("showWelcomeScreen method called ", log.toString());

  }

  @Test
  public void testLoadFileScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("1 2 6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
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

    Readable in = new StringReader("2 4 6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
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

    Readable in = new StringReader("3 6 6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
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

    Readable in = new StringReader("4 5 6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
    input.start();

    assertEquals("showWelcomeScreen method called "
                    + "selectPortfolio method called "
                    + "savePortfolio method called with null "
                    + "printLine method called "
                    + "showWelcomeScreen method called "
                    + "getPortfolioNames method called "
                    + "printLine method called "
                    + "showWelcomeScreen method called ",
            log.toString());
  }

  @Test
  public void testMultipleScreen() {

    StringBuilder log = new StringBuilder();
    PortfolioController mockC = new MockPortfolioController(log);
    ViewInterface mockV = new MockView(log);

    Readable in = new StringReader("1 2 3 6 2 4 6");
    OutputStream out = new ByteArrayOutputStream();

    InputController input = new InputControllerImpl(mockV, mockC, in, new PrintStream(out));
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
}
