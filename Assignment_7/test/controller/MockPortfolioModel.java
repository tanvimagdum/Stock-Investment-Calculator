package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.PortfolioModelImpl;

/**
 * This class represents Mock Model used to implement isolation testcases for Controller.
 */
class MockPortfolioModel extends PortfolioModelImpl {

  private StringBuilder log;

  /**
   * Constructor to initialize StringBuilder object to log inputs.
   *
   * @param log StringBuilder to log the arguments of the methods.
   */
  MockPortfolioModel(StringBuilder log) throws IOException {
    super();
    this.log = log;
  }

  @Override
  public void createFile() throws IOException {
    this.log.setLength(0);
  }

  @Override
  public boolean createPortfolio(String stockName, int quantity) throws IOException {
    this.log.setLength(0);
    this.log.append(stockName + "," + quantity);
    return true;
  }

  @Override
  public Map<Integer, File> loadPortfolioFiles() {
    // to generate fileMap
    File folder = new File("portfolios");
    List<File> expected = new ArrayList<>();
    for (File file : folder.listFiles()) {
      expected.add(file);
    }
    Collections.sort(expected);
    Map<Integer, File> expectedResult = new HashMap<>();
    int i = 1;
    for (File file : expected) {
      expectedResult.put(i, file);
      i++;
    }
    return expectedResult;
  }

  @Override
  public List<List<String>> displayPortfolioContent(File file, String functionType)
          throws IOException {
    this.log.setLength(0);
    this.log.append(functionType);
    this.log.append("\n");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
      this.log.append(line);
    }
    br.close();
    return Arrays.asList(Arrays.asList("GOOG", "123", "2020-10-10", "1345"));
  }


  @Override
  public boolean validateDate(String date) {
    log.append(date);
    return true;
  }

  @Override
  public List<List<String>> getUpdatedRecord(List<List<String>> result, String date) {
    log.append(result);
    log.append(date);
    List<List<String>> res = List.of(Arrays.asList("GOOG", "100", "2022-10-28", "96.58",
            "9658.00"), Arrays.asList("MSFT", "200", "2022-10-28", "235.87",
            "47174.00"));
    return res;
  }

  @Override
  public List<List<String>> getExamineComposition(int index, String functionType)
          throws IOException {
    log.setLength(0);
    log.append(String.valueOf(index) + functionType);
    List<List<String>> result = List.of(Arrays.asList("GOOG", "100", "2022-10-28", "96.58",
            "9658.00"), Arrays.asList("MSFT", "200", "2022-10-28", "235.87",
            "47174.00"));
    Map<Integer, File> map = loadPortfolioFiles();
    displayPortfolioContent(map.get(index), functionType);
    return result;
  }

  @Override
  public File getFile(String filePath) {
    log.append(filePath);
    File file = new File("dummy.txt");
    FileWriter fw = null;
    try {
      fw = new FileWriter(file);
      fw.write("GOOG,100");
      fw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return file;
  }

  @Override
  public Map<String, Integer> processFile(File file) throws IOException {
    Map<String, Integer> mp = new HashMap<>();
    mp.put("GOOG", 100);
    return mp;
  }


  @Override
  public String getTotalValuation(List<List<String>> result) {
    String res = "this is sample string";
    log.append(result);
    return res;
  }
}

