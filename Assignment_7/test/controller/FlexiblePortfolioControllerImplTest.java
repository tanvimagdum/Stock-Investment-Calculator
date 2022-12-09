package controller;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import model.FlexiblePortfolio;
import model.FlexiblePortfolioImpl;
import view.DisplayPortfolio;
import view.DisplayPortfolioImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Junit test cases for Flexible Portfolio Controller Implementation.
 */
public class FlexiblePortfolioControllerImplTest {
  private FlexiblePortfolio flexiblePortfolio;

  private DisplayPortfolio displayPortfolio;

  private StringBuffer out;

  @Before
  public void setUp() throws IOException {
    flexiblePortfolio = new FlexiblePortfolioImpl();
    displayPortfolio = new DisplayPortfolioImpl();
    out = new StringBuffer();
    Properties prop = flexiblePortfolio.getProperties();
    prop.setProperty("resource_file", "temp");

  }

  @After
  public void finish() throws IOException {
    flushTempDirectory(new File("temp"));
  }

  private void flushTempDirectory(File directory) {
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          file.delete();
        }
      }
    }
  }

  @Test
  public void testForBuyMockModel() throws IOException, ParseException, java.text.ParseException {
    StringBuilder sb = new StringBuilder();
    FlexiblePortfolio flexiblePortfolio = new MockFlexiblePortfolio(sb);
    assertTrue(flexiblePortfolio.buyShares("GOOG", 100, "2020-10-12",
            3.5, "dummy.txt"));
    assertEquals(sb.toString(), "GOOG 100.0 2020-10-12 3.5 dummy.txt");
  }

  @Test
  public void testForSellMockModel() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    StringBuilder sb = new StringBuilder();
    FlexiblePortfolio flexiblePortfolio = new MockFlexiblePortfolio(sb);
    assertTrue(flexiblePortfolio.sellShares("GOOG", 100, "2020-10-12",
            3.5, "dummy.txt"));
    assertEquals(sb.toString(), "GOOG 100.0 2020-10-12 3.5 dummy.txt");
  }

  @Test
  public void testCalculateCostBasisMockModel() throws IOException, ParseException,
          java.text.ParseException {
    StringBuilder sb = new StringBuilder();
    FlexiblePortfolio flexiblePortfolio = new MockFlexiblePortfolio(sb);
    assertEquals(0, flexiblePortfolio.calculateCostBasis("dummy.txt",
            "2020-12-12"), 0.1);
    assertEquals("dummy.txt 2020-12-12", sb.toString());
  }


  @Test
  public void testGoForValidMenuCase() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("2\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("9. Exit", temp[9]);
  }


  @Test
  public void testForUploadFile() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in = new StringReader("8\nflexible_portfolios/flexible_portfolio_1.json\n9\n");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("File flexible_portfolio_1.json successfully uploaded ", temp[13]);
    in.close();
  }

  @Test
  public void testForUploadFileInvalid() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("8\n/abc\n9\n");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("File not found at the location. Please enter correct file path",
            temp[15]);
    in.close();
  }


  @Test
  public void testStartProgramForCaseOneClose() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(
            in, out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Select From Below Options", temp[17]);
  }

  @Test
  public void testBuyCaseValid() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(
            in, out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Successfully bought shares for symbol GOOG", temp[21]);
  }


  @Test
  public void testBuyCaseInvalidComissionNegative() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3\n2020-10-12\n-1\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid Commission fee.", temp[21]);
  }

  @Test
  public void testBuyCaseInvalidComissionGreaterThan20() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3\n2020-10-12\n21\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid Commission fee.", temp[21]);
  }

  @Test
  public void testSellCaseValid() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in =
            new StringReader("1\n1\nGOOG\n3\n2020-10-12\n4\n2\nGOOG\n3\n2020-10-14\n4\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Successfully sold shares for symbol GOOG", temp[30]);
  }

  @Test
  public void testSellCaseInvalidSymbol() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3\n2020-10-12\n4\n2\nGOO\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid ticker symbol", temp[27]);
  }

  @Test
  public void testSellCaseInvalidSellDate() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader(
            "1\n1\nGOOG\n3\n2020-10-12\n4\n2\nGOOG\n3\n2020-10-11\n4\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Unable to sell shares for symbol GOOG", temp[30]);
  }

  @Test
  public void testBuyInvalidDateFormat() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3\nasdasda\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid Date format.", temp[20]);
  }

  @Test
  public void testBuyInvalidDate() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3\n2023-10-10\n3\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Unable to buy shares for symbol GOOG", temp[21]);
  }

  @Test
  public void testSellInvalidDateFormat() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n2\nGOOG\n3\nasdasda\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid Date format.", temp[20]);
  }

  @Test
  public void testExaminePortfolio() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n2\n1\n2020-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    String s1 = String.format("%5s%20s", "Symbol", "Quantity");
    String s2 = String.format("%5s%20s", "GOOG", "100");
    assertEquals(s1, temp[43]);
    assertEquals(s2, temp[44]);
  }

  @Test
  public void testExaminePortfolioForPastDate() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n2\n1\n2019-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    String s1 = String.format("%5s%20s", "Symbol", "Quantity");
    String s2 = String.format("%5s%20s", "GOOG", "0");
    assertEquals(s1, temp[43]);
    assertEquals(s2, temp[44]);
  }

  @Test
  public void testExaminePortfolioForFutureDate() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n2\n1\n2021-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    String s1 = String.format("%5s%20s", "Symbol", "Quantity");
    String s2 = String.format("%5s%20s", "GOOG", "100");
    assertEquals(s1, temp[43]);
    assertEquals(s2, temp[44]);
  }

  @Test
  public void testExaminePortfolioForEmptyFile() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n3\n2\n1\n2021-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("File is empty. Please Buy/Sell to view the composition.", temp[34]);
  }

  @Test
  public void testCostBasisDate() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n5\n1\n2021-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Cost Basis for Portfolio flexible_portfolio_1.json is $148598.00",
            temp[42]);
  }

  @Test
  public void testCostBasisPreviousDate() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n100\n2020-10-08\n5\n3\n5\n1\n2019-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Cost Basis for Portfolio flexible_portfolio_1.json is $0.00", temp[42]);
  }

  @Test
  public void testCostBasisEmptyFile() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n3\n5\n1\n2019-11-10\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("File is empty. Please Buy/Sell to view the cost basis.", temp[34]);
  }


  @Test
  public void testBuyCaseInvalidTicker() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOO\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid ticker symbol", temp[18]);
  }

  @Test
  public void testBuyCaseInvalidQuantity() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n3.5\n3\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Invalid quantity provided.", temp[19]);
  }

  @Test
  public void testForDisplayGraph() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader(
            "1\n1\nGOOG\n300\n2020-10-12\n3.5\n3\n7\n1\n2020-10-01\n2020-10-12\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in,
            out, flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    String s = "Performance of portfolio flexible_portfolio_1.json from 2020-10-01 to 2020-10-12";
    assertEquals(s,
            temp[43]);
    assertEquals("Scale: * = $470745.0", temp[56]);
  }

  @Test
  public void testForTotalValuation() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n300\n2020-10-12\n3.5\n3\n6\n1\n2020-10-12\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Total Valuation of the Portfolio in Dollars : 470745.00", temp[47]);
  }

  @Test
  public void testForGetCostBasis() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    Reader in = new StringReader("1\n1\nGOOG\n300\n2020-10-12\n3.5\n3\n5\n1\n2020-10-12\n0\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("Cost Basis for Portfolio flexible_portfolio_1.json is $470748.50",
            temp[42]);
  }

  @Test
  public void testForGetCostBasisInvalid() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("5\n9");
    PortfolioController basePortfolioController = new FlexiblePortfolioControllerImpl(in, out,
            flexiblePortfolio, displayPortfolio);
    basePortfolioController.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    in.close();
    assertEquals("No Portfolio Found. Please create new portfolio.", temp[14]);
  }


}