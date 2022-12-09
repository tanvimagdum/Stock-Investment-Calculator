package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class to implement testcases for Model Component methods.
 */
public class PortfolioModelImplTest {

  private PortfolioModel pm;
  private Properties prop = null;

  @Before
  public void setUp() throws IOException {

    pm = new PortfolioModelImpl();
    prop = pm.getProperties();
    prop.setProperty("resource_file", "temp");

  }

  @After
  public void finish() throws IOException {

    FileInputStream ip = new FileInputStream("src/config.properties");
    prop.load(ip);
    prop.setProperty("resource_file", "portfolios");

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
  public void checkIfFileExist() throws IOException {
    String path = prop.getProperty("resource_file") + "/dummy.txt";
    File expected = new File(path);
    expected.createNewFile();
    File actual = pm.getFile(path);
    assertTrue(actual.exists());
    expected.delete();
  }

  @Test
  public void checkIfFileExistInvalidPath() {
    String path = prop.getProperty("resource_file") + "/dummy.txt";
    File actual = pm.getFile(path);
    assertNull(actual);
  }

  @Test
  public void checkIfFileExistNullPath() {
    String path = null;
    File actual = pm.getFile(path);
    assertNull(actual);
  }

  @Test
  public void checkIfFileExistBlankPath() {
    String path = "";
    File actual = pm.getFile(path);
    assertNull(actual);
  }

  @Test
  public void checkLoadPortfolioFileValid() throws IOException {

    File file = new File("temp" + "/portfolio_1.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("GOOG,123");
    fw.close();
    Map<Integer, File> expectedResult = new HashMap<>();
    expectedResult.put(1, file);
    file.delete();
    pm.createFile();
    pm.createPortfolio("GOOG", 123);
    Map<Integer, File> actualResult = pm.loadPortfolioFiles();
    assertEquals(expectedResult, actualResult);

    File filetemp = new File("temp" + "/portfolio_1.txt");
    filetemp.delete();
  }

  @Test
  public void checkLoadPortfolioFileValidForMultiple() throws IOException {

    File file1 = new File("temp" + "/portfolio_2.txt");
    FileWriter fw1 = new FileWriter(file1);
    fw1.write("GOOG,123");
    fw1.close();

    File file2 = new File("temp" + "/portfolio_1.txt");
    FileWriter fw2 = new FileWriter(file1);
    fw2.write("GOOG,456");
    fw2.close();

    Map<Integer, File> expectedResult = new HashMap<>();
    expectedResult.put(1, file2);
    expectedResult.put(2, file1);

    file1.delete();
    file2.delete();

    pm.createFile();
    pm.createPortfolio("GOOG", 456);
    pm.createFile();
    pm.createPortfolio("GOOG", 123);

    Map<Integer, File> actualResult = pm.loadPortfolioFiles();
    assertEquals(expectedResult, actualResult);

    File filetemp1 = new File("temp" + "/portfolio_1.txt");
    filetemp1.delete();
    File filetemp2 = new File("temp" + "/portfolio_2.txt");
    filetemp2.delete();
  }


  @Test
  public void checkLoadPortfolioFileEmpty() throws IOException {
    Map<Integer, File> fileMap = pm.loadPortfolioFiles();
    assertTrue(fileMap.isEmpty());
  }

  @Test
  public void checkDisplayPortFolioFunction() throws IOException {
    List<List<String>> expectedResult = Arrays.asList(Arrays.asList("GOOG", "123"));
    String path = prop.getProperty("resource_file") + "/dummy.txt";
    File tempFile = new File(path);
    FileWriter fw = new FileWriter(tempFile, true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("GOOG" + "," + "123");
    bw.newLine();
    bw.close();
    List<List<String>> actualResult = pm.displayPortfolioContent(tempFile, "examine");
    tempFile.delete();
    assertEquals(expectedResult, actualResult);

  }

  @Test
  public void checkDisplayPortFolioFunctionForEmpty() throws IOException {
    List<List<String>> expectedResult = Arrays.asList(new ArrayList<>());
    String path = prop.getProperty("resource_file") + "\\dummy.txt";
    File tempFile = new File(path);
    FileWriter fw = new FileWriter(tempFile, true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("");
    bw.newLine();
    bw.close();
    List<List<String>> actualResult = pm.displayPortfolioContent(tempFile, "examine");
    tempFile.delete();
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void checkDisplayFunctionForMoreThanOneStock() throws IOException {
    List<List<String>> expectedResult = Arrays.asList(Arrays.asList("GOOG", "123"),
            Arrays.asList("TSLA", "123"));
    String path = prop.getProperty("resource_file") + "/dummy.txt";
    File tempFile = new File(path);
    FileWriter fw = new FileWriter(tempFile, true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("GOOG" + "," + "123");
    bw.newLine();
    bw.write("TSLA" + "," + "123");
    bw.newLine();
    bw.close();
    List<List<String>> actualResult = pm.displayPortfolioContent(tempFile, "examine");
    tempFile.delete();
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void createFileTest() throws IOException {
    pm.createFile();
    pm.createPortfolio("GOOG", 100);
    File directory = new File(prop.getProperty("resource_file"));
    int fileCount = Objects.requireNonNull(directory.list()).length + 1;
    String filename = prop.getProperty("resource_file") + "/portfolio_" + fileCount + ".txt";
    File file1 = new File(filename);
    FileWriter fw = new FileWriter(file1);
    fw.write("GOOG,100");
    fw.close();
    assertTrue(file1.exists());
    List<List<String>> expectedResult = Arrays.asList(Arrays.asList("GOOG", "100"));
    List<List<String>> actualResult = pm.displayPortfolioContent(file1, "examine");
    assertEquals(expectedResult, actualResult);
    file1.delete();
    String fileDelete = prop.getProperty("resource_file") + "/portfolio_" + (fileCount - 1) +
            ".txt";
    File file = new File(fileDelete);
    file.delete();
  }


  @Test
  public void updatedRecordTest() throws IOException, ParseException {
    List<String> temp = new ArrayList<>();
    temp.add("GOOG");
    temp.add("100");
    temp.add("2022-10-28");
    temp.add("96.58");
    List<List<String>> expectedResult = List.of(Arrays.asList("GOOG", "100", "2020-10-08", "1485.93"
            , "148593.00"));
    List<List<String>> result = new ArrayList<>();
    result.add(temp);
    result = pm.getUpdatedRecord(result, "2020-10-08");
    assertEquals(expectedResult, result);
    assertEquals(expectedResult.size(), result.size());
  }

  @Test
  public void updatedRecordTestForInvalidDate() throws IOException, ParseException {
    List<String> temp = new ArrayList<>();
    temp.add("GOOG");
    temp.add("100");
    temp.add("2022-10-28");
    temp.add("96.58");
    List<List<String>> result = new ArrayList<>();
    result.add(temp);
    result = pm.getUpdatedRecord(result, "2023-10-10");
    assertNull(result);
  }

  @Test
  public void checkDisplayForValuation() throws IOException {
    List<List<String>> expectedResult = Arrays.asList(Arrays.asList("GOOG", "123"));
    String path = prop.getProperty("resource_file") + "/dummy.txt";
    File tempFile = new File(path);
    FileWriter fw = new FileWriter(tempFile, true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("GOOG" + "," + "123");
    bw.newLine();
    bw.close();
    List<List<String>> actual = pm.displayPortfolioContent(tempFile, "valuation");
    tempFile.delete();
    assertEquals(expectedResult.size(), actual.size());
  }

  @Test
  public void validateDateTest() {
    String date1 = "2020-10-12";
    String date2 = "jsbcjsbc";
    String date3 = "2020-00-12";
    String date4 = "12-10-2020";
    assertTrue(pm.validateDate(date1));
    assertFalse(pm.validateDate(date2));
    assertFalse(pm.validateDate(date3));
    assertFalse(pm.validateDate(date4));
  }

  @Test
  public void checkGetExamineCompositionForInvalidIndex() throws IOException {
    int index = -1;
    String functionType = "examine";
    List<List<String>> actual = pm.getExamineComposition(index, functionType);
    assertNull(actual);
  }

  @Test
  public void checkGetExamineCompositionForValidIndex() throws IOException {
    int index = 1;
    String functionType = "examine";
    List<List<String>> expected = Arrays.asList(Arrays.asList("GOOG", "100"));

    File file = new File("temp/portfolio_1.txt");
    FileWriter fw = new FileWriter(file);
    fw.write("GOOG,100");
    fw.close();

    List<List<String>> actual = pm.getExamineComposition(index, functionType);
    assertEquals(expected, actual);
    file.delete();
  }

  @Test
  public void getTotalValuationTest() {
    List<List<String>> result = List.of(Arrays.asList("GOOG", "100", "2022-10-28", "96.58"
            , "9658.00"), Arrays.asList("MSFT", "200", "2022-10-28", "235.87"
            , "47174.00"));
    String expectedResult = "56832.00";
    assertEquals(expectedResult, pm.getTotalValuation(result));

  }

}