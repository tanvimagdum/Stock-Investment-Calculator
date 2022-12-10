package view;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * TestClass to implement tests for Display component methods.
 */
public class DisplayPortfolioImplTest {
  private DisplayPortfolio dp;

  @Before
  public void setUp() throws Exception {
    dp = new DisplayPortfolioImpl();
  }

  @Test
  public void displayMenuTest() throws IOException {
    StringBuffer out = new StringBuffer();
    String expected = "Select From Below Options\n"
            + "1. Create Portfolio\n"
            + "2. Examine Portfolio\n"
            + "3. Get Valuation of Portfolio\n"
            + "4. Load Portfolio\n"
            + "5. Exit\n\n"
            + "Please Enter Choice\n";
    dp.displayMenu(out);
    assertEquals(expected, out.toString());
  }

  @Test
  public void invalidDateTest() throws IOException {
    StringBuilder out = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    String date = "2022-100-30";
    expected.append("Invalid date provided 2022-100-30\n");
    dp.invalidDateError(date, out);
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void invalidInputTest() throws IOException {
    StringBuilder out = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    expected.append("Invalid Input Provided\n");
    dp.displayMessage(out, "Invalid Input Provided\n");
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void fileNotFoundTest() throws IOException {
    StringBuilder out = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    expected.append("*******************************************************************\n");
    expected.append(String.format("%5s", "\nFile not found at the location."
            + " Please enter correct file path\n"));
    expected.append("\n*******************************************************************\n");
    dp.printFileNotFound(out);
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void displayExamineFileTest() throws IOException {
    File file1 = new File("data/Portfolio_1.txt");
    File file2 = new File("data/Portfolio_2.txt");
    file1.createNewFile();
    file2.createNewFile();
    StringBuilder out = new StringBuilder();
    Map<Integer, File> filemap = new HashMap<>();
    filemap.put(1, file1);
    filemap.put(2, file2);
    dp.displayExamineFile(filemap, out);
    StringBuilder expected = new StringBuilder();
    expected.append("Press : " + 0 + " to exit\n");
    expected.append("Press : " + "1" + " to examine : " + "Portfolio_1.txt" + "\n");
    expected.append("Press : " + "2" + " to examine : " + "Portfolio_2.txt" + "\n");
    assertEquals(expected.toString(), out.toString());
    file1.delete();
    file2.delete();
  }

  @Test
  public void displayExamineCompositionTest() throws IOException {
    String stockdata1 = "GOOG,100";
    String stockdata2 = "TSLA,200";
    StringBuilder out = new StringBuilder();
    List<List<String>> dummy = new ArrayList<>();
    dummy.add(0, new ArrayList<>(List.of(stockdata1.split(","))));
    dummy.add(1, new ArrayList<>(List.of(stockdata2.split(","))));
    dp.displayExamineComposition(dummy, out);
    StringBuilder expected = new StringBuilder();
    expected.append("*******************************************\n");
    expected.append(String.format("%5s%20s", "Symbol", "Quantity"));
    expected.append("\n");
    expected.append(String.format("%5s%20s", "GOOG", "100"));
    expected.append("\n");
    expected.append(String.format("%5s%20s", "TSLA", "200"));
    expected.append("\n");
    expected.append("*******************************************\n");
    expected.append("\n");
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void displayValuationTest() throws IOException {
    String stockdata1 = "GOOG,100,2022-10-28,96.58,9658.00";
    String totalValuation = "9658.00";
    List<List<String>> dummy = new ArrayList<>();
    StringBuilder expected = new StringBuilder();
    StringBuilder out = new StringBuilder();
    dummy.add(0, new ArrayList<>(List.of(stockdata1.split(","))));
    dp.displayValuation(dummy, totalValuation, out);
    expected.append("**********************************************************************"
            + "*************************************************\n");
    expected.append(String.format("%5s%20s%20s%20s%20s\n", "Symbol", "Quantity", "Date", "Price($)",
            "Value($)"));
    expected.append("\n");
    expected.append(String.format("%5s%20s%20s%20s%20s\n", "GOOG", "100", "2022-10-28", "96.58",
            "9658.00"));
    expected.append("\n");
    expected.append("Total Valuation of the Portfolio in Dollars : " + totalValuation + "\n");
    expected.append("**********************************************************************"
            + "*************************************************\n");
    expected.append("\n");
    assertEquals(expected.toString(), out.toString());

  }


  @Test
  public void displayExamineCompositionInvalidTest() throws IOException {
    StringBuilder out = new StringBuilder();
    String expected_invalid = "Invalid File Index\n";
    dp.displayExamineComposition(null, out);
    assertEquals(expected_invalid, out.toString());
  }

  @Test
  public void displayValuationInvalidTest() throws IOException {
    StringBuilder out = new StringBuilder();
    String expected_invalid = "Data for this date doesn't exist.\n";
    dp.displayValuation(null, "9658.00", out);
    assertEquals(expected_invalid, out.toString());
  }

  @Test
  public void testFileNotFound() throws IOException {
    StringBuilder expected = new StringBuilder();
    expected.append("*******************************************************************\n");
    expected.append(String.format("%5s", "\nFile not found at the location."
            + " Please enter correct file path\n"));
    expected.append("\n*******************************************************************\n");
    StringBuilder actual = new StringBuilder();
    dp.printFileNotFound(actual);
    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void testDisplayNotPortfolioFound() throws IOException {
    StringBuilder expected = new StringBuilder();
    expected.append("*******************************************************************\n");
    expected.append(String.format("%5s", "\nNo Portfolio Found. Please create new portfolio."));
    expected.append("\n*******************************************************************\n");
    StringBuilder actual = new StringBuilder();
    dp.displayNoPortfolioFound(actual);
    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void testDisplayMessage() throws IOException {
    StringBuilder expected = new StringBuilder();
    expected.append("Enter 1 to continue and q to exit \n");
    StringBuilder actual = new StringBuilder();
    dp.displayMessage(actual, "Enter 1 to continue and q to exit \n");
    assertEquals(expected.toString(), actual.toString());
  }
}