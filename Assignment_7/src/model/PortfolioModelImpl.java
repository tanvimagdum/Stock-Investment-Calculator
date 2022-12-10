package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * {@code PortfolioModelImpl} class represent the implementation of the PortfolioMode.
 * Functionality includes creating the file, creating portfolio, loading file for examining,
 * calculation of valuation and processing the user input file.
 */
public class PortfolioModelImpl extends StockPortfolioImpl implements PortfolioModel {

  private String filename;
  private final Properties prop;


  /**
   * Constructs the object of the PortfolioModel implementation.
   * This constructor initializes the properties from the config.properties.
   *
   * @throws IOException if the input stream is unable to load the properties file.
   */
  public PortfolioModelImpl() throws IOException {
    prop = new Properties();
    FileInputStream ip = new FileInputStream("src/config.properties");
    prop.load(ip);
    prop.setProperty("resource_file", "portfolios");
  }

  @Override
  public void createFile() throws IOException {
    File directory = new File(prop.getProperty("resource_file"));
    int fileCount = Objects.requireNonNull(directory.list()).length + 1;
    filename = prop.getProperty("resource_file") + "/portfolio_" + fileCount + ".txt";
    File file1 = new File(filename);
    while (file1.exists()) {
      fileCount++;
      filename = prop.getProperty("resource_file") + "/portfolio_" + fileCount + ".txt";
      file1 = new File(filename);
    }
    FileWriter file = new FileWriter(filename);
    file.close();
  }

  @Override
  public boolean createPortfolio(String stockName, int quantity) throws IOException {
    if (validateSymbol(stockName) && isNumber(String.valueOf(quantity))) {
      FileWriter fw = new FileWriter(filename, true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(stockName + "," + quantity);
      bw.newLine();
      bw.close();
      return true;
    }
    return false;
  }


  @Override
  public List<List<String>> displayPortfolioContent(File file, String functionType)
          throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    List<List<String>> resultList = new ArrayList<>();
    while ((line = br.readLine()) != null) {
      List<String> result = examineComposition(line, functionType);
      resultList.add(new ArrayList<>((result)));
    }
    br.close();
    return resultList;
  }

  private List<String> examineComposition(String stockData, String functionType)
          throws IOException {
    List<String> rec = new ArrayList<>();
    if (stockData == null || stockData.equals("")) {
      return rec;
    }
    String[] stocks = stockData.split(",");
    String symbol = stocks[0];
    String quantity = stocks[1];
    rec.add(symbol);
    rec.add(quantity);
    if (functionType.equalsIgnoreCase("examine")) {
      return rec;
    } else if (functionType.equalsIgnoreCase("valuation")) {
      String result = "date,open,close,volume,price";
      String[] temp = result.split(",");
      rec.add(temp[0]);
      rec.add(temp[4]);
    }
    return rec;
  }

  @Override
  public List<List<String>> getExamineComposition(int index, String functionType)
          throws IOException {
    Map<Integer, File> fileMap = loadPortfolioFiles();
    List<List<String>> result = null;
    if (fileMap.containsKey(index)) {
      result = displayPortfolioContent(fileMap.get(index), functionType);
    }
    return result;
  }


  @Override
  public Map<String, Integer> processFile(File file) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    Map<String, Integer> map = new HashMap<>();
    boolean clearFlag = false;
    while ((line = br.readLine()) != null) {
      String[] temp = line.split(",");
      if (performValidation(temp) && validateSymbol(temp[0])) {
        clearFlag = true;
        map.put(temp[0], Integer.parseInt(temp[1]));
      } else {
        clearFlag = false;
      }
    }
    br.close();
    return clearFlag ? map : new HashMap<>();
  }

  @Override
  public Properties getProperties() {
    return this.prop;
  }

  @Override
  public boolean validateSymbol(String symbol) throws IOException {
    File file = new File(prop.getProperty("symbol_path"));
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
      if (symbol.equals(line)) {
        return true;
      }
    }
    return false;
  }

  private boolean performValidation(String[] temp) {
    return temp.length == 2 && isString(temp[0]) && isNumber(temp[1]);
  }

  private boolean isString(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isUpperCase(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isNumber(String s) {
    if (s == null) {
      return false;
    }
    try {
      int quantity = Integer.parseInt(s);
      if (quantity < 0) {
        return false;
      }
    } catch (NumberFormatException f) {
      return false;
    }
    return true;
  }

  @Override
  public String getTotalValuation(List<List<String>> result) {
    double sum = 0;
    if (result == null) {
      return "";
    }
    for (List<String> rec : result) {
      double quantity = Double.parseDouble(rec.get(1));
      double price = Double.parseDouble(rec.get(3));
      sum += quantity * price;
    }
    return String.format("%.2f", sum);
  }

  @Override
  protected String getResourcePath() {
    return prop.getProperty("resource_file");
  }

}
