package controller;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import model.APIData;
import model.PortfolioModel;
import model.PortfolioModelImpl;
import model.VantageAPIData;
import view.DisplayPortfolio;
import view.DisplayPortfolioImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class represents junit testcases for PortfolioController.
 */
public class PortfolioControllerImplTest {
  private final Properties prop = null;

  private PortfolioModel portfolioModel;

  /**
   * Constructs the portfolio controller test class.
   *
   * @throws IOException if unable to handle input output operation.
   */
  public PortfolioControllerImplTest() throws IOException {
    APIData apiData = new VantageAPIData();
  }

  @Before
  public void setUp() throws IOException {
    portfolioModel = new PortfolioModelImpl();
    Properties prop = portfolioModel.getProperties();
    prop.setProperty("resource_file", "temp");

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
  public void checkCreatePortfolio() throws IOException {
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    pm.createPortfolio("GOOG", 123);
    assertEquals("GOOG,123", sb.toString());
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void checkDisplay() throws IOException {

    List<List<String>> expected = Arrays.asList(Arrays.asList("GOOG", "123", "2020-10-10", "1345"));

    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    File file = new File("dummy.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("this is a test file");
    fw.close();

    List<List<String>> actual = pm.displayPortfolioContent(file, "valuation");
    StringBuilder expectedLog = new StringBuilder("valuation\n"
            + "this is a test file");
    assertEquals(expectedLog.toString(), sb.toString());
    assertEquals(expected, actual);
    file.delete();
  }

  @Test
  public void testForValidateDate() throws IOException {
    String date = "2022-10-28";
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    assertTrue(pm.validateDate(date));
    assertEquals(date, sb.toString());
  }

  @Test
  public void testForGetTotalValuation() throws IOException {
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    List<List<String>> expected = List.of(Arrays.asList("GOOG", "100", "2022-10-28", "96.58",
            "9658.00"), Arrays.asList("MSFT", "200", "2022-10-28", "235.87",
            "47174.00"));
    String actualString = pm.getTotalValuation(expected);
    assertEquals("this is sample string", actualString);
    assertEquals(expected.toString(), sb.toString());
  }

  @Test
  public void testForGetFile() throws IOException {
    //flushTempDirectory(new File("temp));
    String expectedString = "this is sample input";
    String expectedFileData = "GOOG,100";
    StringBuilder actualFileData = new StringBuilder();
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    File actualFile = pm.getFile(expectedString);
    BufferedReader br = new BufferedReader(new FileReader(actualFile));
    String line;
    while ((line = br.readLine()) != null) {
      actualFileData.append(line);
    }
    br.close();
    assertEquals(expectedFileData, actualFileData.toString());
    assertEquals(expectedString, sb.toString());
  }

  @Test
  public void testForUpdatedRecord() throws IOException, java.text.ParseException {
    //flushTempDirectory(new File("temp));
    List<List<String>> argument = List.of(Arrays.asList("GOOG", "100", "2022-10-28", "96.58",
            "9658.00"), Arrays.asList("MSFT", "200", "2022-10-28", "235.87",
            "47174.00"));
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    List<List<String>> actual = pm.getUpdatedRecord(argument, "2022-10-28");
    StringBuffer actualString = new StringBuffer();
    actualString.append(argument);
    actualString.append("2022-10-28");
    assertEquals(actualString.toString(), sb.toString());
    assertEquals(argument, actual);

  }

  @Test
  public void testForCreateFile() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    //flushTempDirectory(new File("temp));
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\nGOOG\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, pm, dp);
    pc.startProgram();
    assertEquals("GOOG," + "100", sb.toString());
    String[] temp;
    temp = out.toString().split("\n");
  }

  @Test
  public void testForIsolationTestingForCase4() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    StringBuilder sb = new StringBuilder();
    PortfolioModel pm = new MockPortfolioModel(sb);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4\nres/portfolio_1.txt\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, pm, dp);
    pc.startProgram();
    StringBuilder expected = new StringBuilder();
    expected.append("GOOG,100");
    assertEquals(expected.toString(), sb.toString());
  }

  @Test
  public void testGoForInvalidMenuCase() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("7\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Invalid option", temp[8]);
  }

  @Test
  public void testGoForNegativeMenuCase() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    //flushTempDirectory(new File("temp));
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("-7\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Negative Number Provided", temp[8]);
  }

  @Test
  public void testGoForExitMenu() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Program terminated successfully", temp[8]);
  }

  @Test
  public void testGoForValidMenuCase() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    //flushTempDirectory(new File("temp));
    File file = new File("temp/portfolio_1.txt");
    file.createNewFile();
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2\n0\n5\nq");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Program terminated successfully", temp[19]);
    file.delete();
  }

  @Test
  public void testGoForSingleValidStock() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\nGOOG\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[18]);
    assertEquals("Enter stock symbol :", temp[8]);
    assertEquals("Enter stock quantity :", temp[9]);
    assertEquals("Select From Below Options", temp[0]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidSymbol() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\nacc\n100\nGOOG\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[21]);
    assertEquals("Invalid symbol or quantity provided", temp[10]);
    assertEquals("Enter 1 to continue and q to exit ", temp[13]);
    assertEquals("Select From Below Options", temp[0]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidQuantity() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\nGOOG\n-100\nGOOG\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[21]);
    assertEquals("Invalid symbol or quantity provided", temp[10]);
    assertEquals("Enter 1 to continue and q to exit ", temp[13]);
    assertEquals("Select From Below Options", temp[0]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForLowerCaseSymbol() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\ngoog\n100\nIBM\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[21]);
    assertEquals("Invalid symbol or quantity provided", temp[10]);
    assertEquals("Enter 1 to continue and q to exit ", temp[13]);
    assertEquals("Select From Below Options", temp[0]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForMultipleStocks() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("1\nGOOG\n100\nIBM\n100\nq\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[7]);
    assertEquals("Enter 1 to continue and q to exit ", temp[10]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForValidValuation() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3\n1\n2020-10-12\n0\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    StringBuilder expectedString = new StringBuilder();
    assertEquals("Press : 1 to examine : portfolio_1.txt", temp[9]);
    assertEquals("Enter portfolio index :", temp[10]);
    assertEquals("Enter date for valuation in yyyy-mm-dd :", temp[11]);
    expectedString.append(String.format("%5s%20s%20s%20s%20s", "GOOG", "100", "2020-10-12",
            "1569.15", "156915.00"));
    assertEquals(expectedString.toString(), temp[15]);
    assertEquals("Total Valuation of the Portfolio in Dollars : 156915.00", temp[17]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForValidValuation2() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    flushTempDirectory(new File("temp"));
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    portfolioModel.createPortfolio("IBM", 100);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3\n1\n2020-10-08\n0\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");

    StringBuilder expectedString = new StringBuilder();
    assertEquals("Press : 1 to examine : portfolio_1.txt", temp[9]);
    assertEquals("Enter portfolio index :", temp[10]);
    assertEquals("Enter date for valuation in yyyy-mm-dd :", temp[11]);
    expectedString.append(String.format("%5s%20s%20s%20s%20s", "GOOG", "100", "2020-10-08",
            "1485.93", "148593.00"));
    expectedString.append(String.format("%5s%20s%20s%20s%20s", "IBM", "100", "2020-10-08",
            "131.49", "13149.00"));
    assertEquals(expectedString.toString(), temp[15] + temp[17]);
    assertEquals("Total Valuation of the Portfolio in Dollars : 161742.00", temp[19]);
    flushTempDirectory(new File("temp"));
  }


  @Test
  public void testGoForInvalidDateValuation() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3\n1\n2022sjfbj\n0\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Invalid date provided 2022sjfbj", temp[12]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidIndexValuation() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("3\n-1\n2022sjfbj\n0\n5\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");
    assertEquals("Invalid Index Provided", temp[12]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidStringValuation() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {

    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    DisplayPortfolio dp = new DisplayPortfolioImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("2\n1\nacc\n5\n3\n");
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel, dp);
    pc.startProgram();
    String[] temp;
    temp = out.toString().split("\n");

    assertEquals("Invalid Input Provided", temp[19]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForCaseTwoExit() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    //flushTempDirectory(new File("temp));
    File file = new File("temp/dummy.txt");
    file.createNewFile();
    FileWriter fw = new FileWriter(file);
    fw.write("GOO,123");
    fw.close();
    Reader in = new StringReader("2\n0\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("Please Enter Choice", temp[7]);
    assertEquals("Select From Below Options", temp[11]);
    assertEquals("1. Create Portfolio", temp[12]);
    assertEquals("2. Examine Portfolio", temp[13]);
    assertEquals("3. Get Valuation of Portfolio", temp[14]);
    assertEquals("4. Load Portfolio", temp[15]);
    assertEquals("5. Exit", temp[16]);
    assertEquals("Please Enter Choice", temp[18]);
    file.delete();
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForValidExamaineMenu() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {

    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    Reader in = new StringReader("2\n1\n0\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");

    assertEquals("*******************************************", temp[11]);
    assertEquals(String.format("%5s%20s", "Symbol", "Quantity"), temp[12]);
    assertEquals(String.format("%5s%20s", "GOOG", "100"), temp[13]);
    assertEquals("*******************************************", temp[14]);
    File file = new File("portfolios/portfolio_1.txt");
    file.delete();
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidFileIndex() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    Reader in = new StringReader("2\n-1\n0\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("Invalid File Index", temp[11]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidStringFileIndex() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {

    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    Reader in = new StringReader("2\nacc\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");

    assertEquals("Invalid Input Provided", temp[11]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidGreaterFileIndex() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    portfolioModel.createFile();
    portfolioModel.createPortfolio("GOOG", 100);
    Reader in = new StringReader("2\n100\n0\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");

    assertEquals("Invalid File Index", temp[11]);
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForNoPortfolioFound() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("2\na\n0\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");

    assertEquals("No Portfolio Found. Please create new portfolio.", temp[10]);
  }

  @Test
  public void testGoForCaseFourInvalidPath() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    Reader in = new StringReader("4\nres/portfolio_1.txt\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("File not found at the location. Please enter correct file path",
            temp[11]);
  }

  @Test
  public void testGoForInvalidFormatFile() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    File file = new File("temp/dummy.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("GOO,123");
    fw.close();
    Reader in = new StringReader("4\ntemp/dummy.txt\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("Unable to save the file. Check the file format (Symbol, Quantity)",
            temp[9]);
    in.close();
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidFormatFile2() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    File file = new File("temp/dummy.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("GOOG123");
    fw.close();
    Reader in = new StringReader("4\ntemp/dummy.txt\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("Unable to save the file. Check the file format (Symbol, Quantity)",
            temp[9]);
    in.close();
    flushTempDirectory(new File("temp"));
  }

  @Test
  public void testGoForInvalidFormatFile3() throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    File file = new File("temp/dummy.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("goog,123");
    fw.close();
    Reader in = new StringReader("4\ntemp/dummy.txt\n5");
    StringBuilder out = new StringBuilder();
    PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
            new DisplayPortfolioImpl());
    pc.startProgram();
    String[] temp = out.toString().split("\n");
    assertEquals("Unable to save the file. Check the file format (Symbol, Quantity)",
            temp[9]);
    in.close();
    flushTempDirectory(new File("temp"));
  }


}