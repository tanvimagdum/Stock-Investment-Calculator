import org.junit.Test;
import view.viewImpl;
import view.viewInterface;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * A JUnit test class to test the view panel.
 */
public class viewImplTest {
  @Test
  public void testShowWelcomeScreen() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.showWelcomeScreen();
    assertEquals("\n=================================\n" +
              "    Welcome to 'GROW MONEY'!\n" +
              "=================================\n" +
              "\n" +
              "Please enter a choice number\n" +
              "\n" +
              "1. Load a portfolio\n" +
              "2. Build a new portfolio\n" +
              "3. View a portfolio\n" +
              "4. Save a portfolio\n" +
              "5. Save all portfolios\n" +
              "6. Exit\n", out.toString());
  }

  @Test
  public void testLoadScreen() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.showLoadScreen();
    //System.out.println(out);
    assertEquals("\n" +
              "====== Load a portfolio ======\n" +
              "\n" +
              "Please enter a choice number\n" +
              "\n" +
              "1. Enter the portfolio filename\n" +
              "2. Go Back\n", out.toString());
  }

  @Test
  public void testBuildScreen() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.showBuildScreen();
    String temp = "\n" +
    "====== Build a new portfolio ======\n" +
            "\n" +
            "Please enter a choice number\n" +
            "\n" +
            "1. Begin building the portfolio\n" +
            "2. Go Back\n";
    if (temp.equals(out.toString())) {
      fail();
    }
    assertEquals("\n" +
            "====== Build a new portfolio ======\n" +
            "\n" +
            "Please enter a choice number\n" +
            "\n" +
            "1. Begin building the portfolio\n" +
            "2. Go Back\n", out.toString());

  }

  @Test
  public void testPortfolioScreen() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.showPortfolioScreen();
    //System.out.println(out);
    assertEquals("\n====== View a portfolio ======\n" +
            "\n" +
            "Please enter a choice number\n" +
            "\n" +
            "1. View the stocks list in the portfolio\n" +
            "2. View the value of a portfolio on a certain date (2010-2018)\n" +
            "3. View the value of a portfolio as of 2022-10-31\n" +
            "4. View the value of a portfolio with manually input prices.\n" +
            "5. Go back\n", out.toString());

  }

  @Test
  public void testPrintLine() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.printLine("PrintLine method");
    assertEquals("PrintLine method\n", out.toString());
  }

  @Test
  public void testPrintLines() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    String[] str = new String[2];
    str[0] = "PrintLine1";
    str[1] = "PrintLine2";
    v.printLines(str);
    assertEquals("PrintLine1\nPrintLine2\n", out.toString());
  }

  @Test
  public void testDisplayError() {
    OutputStream out = new ByteArrayOutputStream();
    viewInterface v = new viewImpl(new PrintStream(out));
    v.displayError();
    assertEquals("Please re-enter a choice number from the given list\n", out.toString());
  }

}