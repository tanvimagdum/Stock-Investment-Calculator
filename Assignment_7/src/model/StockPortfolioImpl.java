package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents the abstract class and contains methods that are required across models.
 */
public abstract class StockPortfolioImpl implements StockPortfolio {
  private final APIData apiData;

  /**
   * This represents the abstract constructor for the class.
   *
   * @throws IOException if the file dependency is not found for example : SNP500 list.
   */
  public StockPortfolioImpl() throws IOException {
    apiData = new VantageAPIData();
  }

  /**
   * This represents the abstract constructor for the class.
   *
   * @throws IOException if the file dependency is not found for example : SNP500 list.
   */


  @Override
  public boolean validateSymbol(String symbol) throws IOException {
    String symbolPath = "data/Symbol_List.txt";
    File file = new File(symbolPath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
      if (symbol.equals(line)) {
        return true;
      }
    }
    return false;
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
  public Map<Integer, File> loadPortfolioFiles() {
    File folder = new File(this.getResourcePath());
    List<File> fileMapList = new ArrayList<>();
    Map<Integer, File> result = new HashMap<>();
    if (folder.listFiles() == null) {
      return result;
    }

    fileMapList.addAll(Arrays.asList(Objects.requireNonNull(folder.listFiles())));

    Collections.sort(fileMapList, (f1, f2) -> {
      int length1 = f1.getName().length();
      int length2 = f2.getName().length();
      if (length1 != length2) {
        return length1 > length2 ? 1 : -1;
      }
      return f1.getName().compareTo(f2.getName());
    });

    int i = 1;
    for (File file : fileMapList) {
      result.put(i, file);
      i++;
    }
    return result;
  }

  @Override
  public boolean validateDate(String date) {
    if (date == null) {
      return false;
    }
    try {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      df.setLenient(false);
      df.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  @Override
  public List<List<String>> getUpdatedRecord(List<List<String>> result, String date) throws
          IOException, ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(new Date());
    if (sdf.parse(date).after(sdf.parse(currentDate))) {
      return null;
    }
    StringBuilder output;
    InputStream res;
    for (List<String> rec : result) {
      String symbol = rec.get(0);
      double quantity = Double.parseDouble(rec.get(1));
      String[] reader = apiData.getInputStream(symbol);
      output = apiData.getPriceForDate(reader, date, "daily");
      if (output.toString().equals("")) {
        return null;
      }
      rec.set(3, output.toString());
      double price = Double.parseDouble(rec.get(3));
      String total_valuation = String.format("%.2f", quantity * price);
      rec.set(2, date);
      rec.add(4, total_valuation);
    }
    return result;
  }

  @Override
  public File getFile(String filePath) {
    if (filePath == null || filePath.equals("")) {
      return null;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      return null;
    }
    return file;
  }


  protected abstract String getResourcePath();


}
