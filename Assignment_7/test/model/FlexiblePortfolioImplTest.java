package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Junit test cases for Flexible Portfolio Model Implementation.
 */
public class FlexiblePortfolioImplTest {
  private Properties prop = null;

  private FlexiblePortfolio flexiblePortfolio;

  private static final double DELTA = 1e-15;

  @Before
  public void setUp() throws IOException {

    flexiblePortfolio = new FlexiblePortfolioImpl();

    prop = flexiblePortfolio.getProperties();
    FileInputStream ip = new FileInputStream("src/config.properties");
    prop.load(ip);
    prop.setProperty("resource_file", "temp");

    FileOutputStream out = new FileOutputStream("src/config.properties");
    prop.store(out, "Model changes");

  }

  @After
  public void finish() throws IOException {

    FileInputStream ip = new FileInputStream("src/config.properties");
    prop.load(ip);
    prop.setProperty("resource_file", "flexible_portfolios");

    FileOutputStream out = new FileOutputStream("src/config.properties");
    prop.store(out, "Model changes reverted");
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
  public void testValidBuyShares() throws ParseException, IOException, java.text.ParseException {
    String symbol = "GOOG";
    int quantity = 100;
    String date = "2020-12-10";
    double commissionFee = 4;
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    boolean actual = flexiblePortfolio.buyShares(symbol, quantity, date, commissionFee, filename);
    assertTrue(actual);
    file.delete();
  }

  @Test
  public void testInvalidSymbolBuyShares() throws ParseException, IOException,
          java.text.ParseException {
    String symbol = "GOG";
    int quantity = 100;
    String date = "2020-12-10";
    double commissionFee = 4;
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    boolean actual = flexiblePortfolio.buyShares(symbol, quantity, date, commissionFee, filename);
    assertFalse(actual);
    file.delete();
  }

  @Test
  public void testInvalidDateBuyShares() throws ParseException, IOException,
          java.text.ParseException {
    String symbol = "GOOG";
    int quantity = 100;
    String date = "2021-11-061";
    double commissionFee = 4;
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    boolean actual = flexiblePortfolio.buyShares(symbol, quantity, date, commissionFee, filename);
    assertFalse(actual);
    file.delete();
  }

  @Test
  public void testFutureDateBuyShares() throws ParseException, IOException,
          java.text.ParseException {
    String symbol = "GOOG";
    int quantity = 100;
    String date = "2029-11-06";
    double commissionFee = 4;
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    boolean actual = flexiblePortfolio.buyShares(symbol, quantity, date, commissionFee, filename);
    assertFalse(actual);
    file.delete();
  }

  @Test
  public void testGetFlexiblePortfolioValuationEmptyFile() throws IOException, ParseException,
          java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioValuation(filename,
            "2020-10-12");
    assertNull(actual);
    file.delete();
  }

  @Test
  public void testGetFlexiblePortfolioValuationValidFile() throws IOException, ParseException,
          java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();

    JSONObject portfolioObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject stockObject = new JSONObject();
    stockObject.put("Fee", 5);
    stockObject.put("Quantity", 100);
    stockObject.put("Operation", "BUY");
    stockObject.put("Date", "2020-11-11");
    stockArray.add(stockObject);
    portfolioObject.put("MSFT", stockArray);

    FileWriter fw = new FileWriter("temp/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioObject.toJSONString());
    bw.newLine();
    bw.close();

    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioValuation(
            "temp_flexible.json", "2021-02-23");
    List<List<String>> expected = new ArrayList<>();
    List<String> stockData = new ArrayList<>(Arrays.asList("MSFT", "100", "2021-02-23",
            "233.27", "23327.00"));
    expected.add(new ArrayList<>(stockData));

    assertEquals(expected, actual);

    file.delete();

  }

  @Test
  public void testGetFlexiblePortfolioValuationNonWorkingDay()
          throws IOException, ParseException, java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();

    JSONObject portfolioObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject stockObject = new JSONObject();
    stockObject.put("Fee", 5);
    stockObject.put("Quantity", 100);
    stockObject.put("Operation", "BUY");
    stockObject.put("Date", "2020-11-11");
    stockArray.add(stockObject);
    portfolioObject.put("MSFT", stockArray);

    FileWriter fw = new FileWriter("temp/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioObject.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();

    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioValuation(
            "temp_flexible.json", "2021-02-21");
    assertEquals(actual.toString(), "[[MSFT, 100, 2021-02-21, 240.97, 24097.00]]");
    //assertNull(actual);
    file.delete();

  }

  @Test
  public void testGetPortfolioComposition() throws IOException, ParseException,
          java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();

    JSONObject portfolioObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject stockObject = new JSONObject();
    stockObject.put("Fee", 5);
    stockObject.put("Quantity", 100);
    stockObject.put("Operation", "BUY");
    stockObject.put("Date", "2020-11-11");
    stockArray.add(stockObject);
    portfolioObject.put("MSFT", stockArray);

    FileWriter fw = new FileWriter("temp/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioObject.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();

    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioComposition(filename,
            "2021-02-01");
    String symbol = "MSFT";
    String quantity = "100";

    assertEquals(symbol, actual.get(0).get(0));
    assertEquals(quantity, actual.get(0).get(1));
    file.delete();
  }

  @Test
  public void testGetPortfolioCompositionPreviousToBuy()
          throws IOException, ParseException, java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();

    JSONObject portfolioObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject stockObject = new JSONObject();
    stockObject.put("Fee", 5);
    stockObject.put("Quantity", 100);
    stockObject.put("Operation", "BUY");
    stockObject.put("Date", "2020-11-11");
    stockArray.add(stockObject);
    portfolioObject.put("MSFT", stockArray);

    FileWriter fw = new FileWriter("temp/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioObject.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();

    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioComposition(filename,
            "2016-02-01");
    String symbol = "MSFT";
    String quantity = "0";

    assertEquals(symbol, actual.get(0).get(0));
    assertEquals(quantity, actual.get(0).get(1));
    file.delete();
  }

  @Test
  public void testGetPortfolioCompositionForEmptyFile() throws IOException, ParseException,
          java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    List<List<String>> actual = flexiblePortfolio.getFlexiblePortfolioComposition(filename,
            "2020-10-12");
    file.delete();
    assertNull(actual);

  }

  @Test
  public void testCreateFile() throws IOException {
    String actual = flexiblePortfolio.createFlexiblePortfolio();
    String expected = "flexible_portfolio_1.json";
    assertEquals(expected, actual);
    File file = new File("temp/" + expected);
    file.delete();
  }

  @Test
  public void testGenerateAndDisplayStockGraph() throws IOException, ParseException,
          java.text.ParseException {
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();

    JSONObject portfolioObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject stockObject1 = new JSONObject();
    stockObject1.put("Fee", 5);
    stockObject1.put("Quantity", 100);
    stockObject1.put("Operation", "BUY");
    stockObject1.put("Date", "2020-11-11");
    JSONObject stockObject2 = new JSONObject();
    stockObject2.put("Fee", 5);
    stockObject2.put("Quantity", 100);
    stockObject2.put("Operation", "SELL");
    stockObject2.put("Date", "2020-12-11");
    stockArray.add(stockObject1);
    stockArray.add(stockObject2);
    portfolioObject.put("MSFT", stockArray);

    FileWriter fw = new FileWriter("temp/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioObject.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();
    List<String> actual = flexiblePortfolio.generateAndDisplayStockGraph("2020-09-11",
            "2020-12-30", filename);
    List<String> expected = Arrays.asList("2020-09-17:0.00, 2020-09-23:0.00," +
            " 2020-09-29:0.00, 2020-10-05:0.00, 2020-10-11:0.00, 2020-10-17:0.00, " +
            "2020-10-23:0.00, 2020-10-29:0.00, 2020-11-04:0.00, 2020-11-11:21655.00, " +
            "2020-11-17:21446.00, 2020-11-23:21011.00, 2020-11-29:21523.00, 2020-12-05:21436.00, " +
            "2020-12-11:0.00, 2020-12-17:0.00, 2020-12-23:0.00, 2020-12-30:0.00");
    assertEquals(actual.toString(), expected.toString());
    file.delete();
  }

  @Test
  public void testSellSharesValid() throws ParseException, IOException, java.text.ParseException,
          NoSuchFieldException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    fp.writeFileData("GOOG", 100, "2020-10-10", 4,
            "BUY", filename);
    boolean actual = flexiblePortfolio.sellShares("GOOG", 100,
            "2020-12-11", 4, filename);
    assertTrue(actual);
    file.delete();
  }

  @Test
  public void testSellSharesInvalidCase1() throws ParseException, IOException,
          java.text.ParseException, NoSuchFieldException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    fp.writeFileData("GOOG", 100, "2020-10-10", 4,
            "BUY", filename);
    boolean actual = flexiblePortfolio.sellShares("GOOG", 100,
            "2020-10-09", 4, filename);
    assertFalse(actual);
    file.delete();
  }

  @Test
  public void testSellSharesInvalidCase2() throws ParseException, IOException,
          java.text.ParseException, NoSuchFieldException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    fp.writeFileData("GOOG", 100, "2020-10-10", 4,
            "BUY", filename);
    boolean actual = flexiblePortfolio.sellShares("GOOG", 200,
            "2020-10-11", 4, filename);
    assertFalse(actual);
    file.delete();
  }

  @Test
  public void testForCostBasisCaseOne() throws ParseException, IOException,
          java.text.ParseException, NoSuchFieldException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    fp.writeFileData("GOOG", 100, "2020-10-12", 4,
            "BUY", filename);
    fp.writeFileData("IBM", 100, "2020-10-14", 4,
            "BUY", filename);
    assertEquals(169517.0, flexiblePortfolio.calculateCostBasis(filename,
            "2020-10-14"), 0.2);
    file.delete();
  }

  @Test
  public void testForCostBasisCaseTwo() throws ParseException, IOException,
          java.text.ParseException, NoSuchFieldException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    String filename = "temp_flexible.json";
    File file = new File("temp/" + filename);
    file.createNewFile();
    fp.writeFileData("GOOG", 100, "2020-10-12", 4,
            "BUY", filename);
    fp.writeFileData("IBM", 100, "2020-10-12", 4,
            "BUY", filename);
    assertEquals(0.0, flexiblePortfolio.calculateCostBasis(filename,
            "2020-10-11"), DELTA);
    file.delete();
  }

  @Test
  public void testForMinMaxPrice() {
    List<String> pricelist = new ArrayList<>();
    pricelist.add("Jan 2022 : 100.12");
    pricelist.add("Feb 2022 : 200.12");
    pricelist.add("Mar 2022 : 233.12");
    pricelist.add("Apr 2022 : 522.12");
    List<Double> expected = new ArrayList<>(Arrays.asList(100.12, 522.12, 100.12));
    List<Double> actual = flexiblePortfolio.getMinMaxPrice(pricelist);
    assertEquals(expected, actual);
  }


  @Test
  public void testForDollarCostAvgCostBasisOne() throws IOException, ParseException,
          java.text.ParseException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    FlexiblePortfolio flexiblePortfolio1 = new FlexiblePortfolioImpl();
    String filename = "temp_flexible.json";
    File file = new File("flexible_portfolios/" + filename);
    file.createNewFile();
    flexiblePortfolio1.dollarCostAveraging(1000, 100, "GOOG", "2022-11-17",
            30, "2022-11-29", 10, "temp_flexible.json");
    double costBasisBefore = flexiblePortfolio1.calculateCostBasis("temp_flexible.json",
            "2022-11-15");
    double costBasisAfter = flexiblePortfolio1.calculateCostBasis("temp_flexible.json",
            "2022-11-29");
    assertEquals("0.00", String.format("%.2f", costBasisBefore));
    assertEquals("999.93", String.format("%.2f", costBasisAfter));
    file.delete();


  }

  @Test
  public void testForDollarCostAvgCostBasisTwo() throws IOException, ParseException,
          java.text.ParseException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    FlexiblePortfolio flexiblePortfolio1 = new FlexiblePortfolioImpl();
    String filename = "temp_flexible.json";
    File file = new File("flexible_portfolios/" + filename);
    file.createNewFile();
    flexiblePortfolio1.dollarCostAveraging(1000, 100, "GOOG", "2022-11-17",
            5, "2022-11-29", 10, "temp_flexible.json");
    double costBasisBefore = flexiblePortfolio1.calculateCostBasis("temp_flexible.json",
            "2022-11-15");
    double costBasisAfter = flexiblePortfolio1.calculateCostBasis("temp_flexible.json",
            "2022-11-29");
    assertEquals("0.00", String.format("%.2f", costBasisBefore));
    assertEquals("2999.44", String.format("%.2f", costBasisAfter));
    file.delete();


  }

  @Test
  public void testForDollarCostAvgValuationOne() throws IOException, ParseException,
          java.text.ParseException {
    FileIOImpl fp = new FileIOImpl(prop, flexiblePortfolio);
    FlexiblePortfolio flexiblePortfolio1 = new FlexiblePortfolioImpl();
    String filename = "temp_flexible.json";
    File file = new File("flexible_portfolios/" + filename);
    file.createNewFile();
    flexiblePortfolio1.dollarCostAveraging(1000, 100, "GOOG", "2022-11-17",
            5, "2022-11-29", 10, "temp_flexible.json");
    List<List<String>> totalValuation =
            flexiblePortfolio1.getFlexiblePortfolioValuation("temp_flexible.json",
                    "2022-11-29");
    String valuation = flexiblePortfolio1.getTotalValuation(totalValuation);
    assertEquals("2863.20", valuation);
    file.delete();


  }

}