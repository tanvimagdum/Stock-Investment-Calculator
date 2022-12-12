package model;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * {@code FileIOImpl} represents the implementation of FileIO interface. This class has methods such
 * as create file, read file, write to file and validate json file.
 */
class FileIOImpl implements FileIO {

  private final Properties prop;
  private final FlexiblePortfolio flexiblePortfolio;

  /**
   * Constructs the FileIOImpl with the properties.
   *
   * @param prop              Object of Properties class.
   * @param flexiblePortfolio Object of the flexible portfolio class
   */
  public FileIOImpl(Properties prop, FlexiblePortfolio flexiblePortfolio) {
    this.flexiblePortfolio = flexiblePortfolio;
    this.prop = prop;
  }


  @Override
  public String createFile() throws IOException {

    File directory = new File(prop.getProperty("resource_file"));
    int fileCount = Objects.requireNonNull(directory.list()).length + 1;
    String filename = prop.getProperty("resource_file") + "/flexible_portfolio_"
        + fileCount + ".json";
    File file1 = new File(filename);
    while (file1.exists()) {
      fileCount++;
      filename = prop.getProperty("resource_file") + "/flexible_portfolio_" + fileCount + ".json";
      file1 = new File(filename);
    }
    FileWriter file = new FileWriter(filename);
    file.close();
    return file1.getName();

  }


  @Override
  public Map<String, Object> readFileData(String filename) throws ParseException, IOException {
    JSONParser jsonParser = new JSONParser();
    BufferedReader br = new BufferedReader(
        new FileReader(prop.getProperty("resource_file")
            + "/" + filename));
    if (br.readLine() == null) {
      br.close();
      return null;
    }
    FileReader reader = new FileReader(prop.getProperty("resource_file") +
        "/" + filename);
    Object obj = jsonParser.parse(reader);
    JSONObject portFolioData = (JSONObject) obj;

    Map<String, Object> portfolioMap = new Gson().fromJson(String.valueOf(portFolioData),
        HashMap.class);

    reader.close();
    br.close();
    return portfolioMap;
  }

  @Override
  public void writeFileData(String symbol, double quantity, String date, double commissionFee,
      String operation, String filename, boolean overwriteSells)
      throws IOException, ParseException {
    JSONParser jsonParser = new JSONParser();
    BufferedReader br = new BufferedReader(
        new FileReader(prop.getProperty("resource_file")
            + "/" + filename));
    JSONObject portfolioData = null;
    if (br.readLine() == null) {
      portfolioData = new JSONObject();
      portfolioData.put(symbol, new JSONArray());
      JSONObject newStock = stockObjectHelper(quantity, date, commissionFee, operation);
      JSONArray tickerArray = (JSONArray) portfolioData.get(symbol);
      tickerArray.add(newStock);
      br.close();
    } else {
      FileReader reader = new FileReader(prop.getProperty("resource_file") +
          "/" + filename);
      Object obj = jsonParser.parse(reader);
      portfolioData = (JSONObject) obj;
      if (portfolioData.containsKey(symbol)) {
        if (!overwriteSells || operation.equals("BUY")) {
          JSONArray tickerArray = (JSONArray) portfolioData.get(symbol);
          JSONObject newStock = stockObjectHelper(quantity, date, commissionFee, operation);
          tickerArray.add(newStock);
          reader.close();
        } else {
          //erase future sells such that the rules are obeyed
          JSONArray tickerArray = (JSONArray) portfolioData.get(symbol);
          int elements = tickerArray.size();
          String[] operations = new String[elements];
          String[] dates = new String[elements];
          String[] quantities = new String[elements];
          DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
          float futureSum = 0;
          float priorSum = 0;
          for (int i = 0; i < elements; i++) {
            String[] parts = tickerArray.get(i).toString().split(",");
            operations[i] = parts[2].subSequence(13, 16).toString();
            dates[i] = parts[3].subSequence(8, 18).toString();
            quantities[i] = parts[1].substring(11);

            try {
              if (formatter.parse(dates[i]).after(formatter.parse(date))) {
                if (operations[i].equals("BUY")) {
                  futureSum += Float.parseFloat(quantities[i]);
                } else {
                  futureSum -= Float.parseFloat(quantities[i]);
                }
              } else {
                if (operations[i].equals("BUY")) {
                  priorSum += Float.parseFloat(quantities[i]);
                } else {
                  priorSum -= Float.parseFloat(quantities[i]);
                }
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }

          ArrayList<Integer> considered = new ArrayList<>();
          ArrayList<Integer> removed = new ArrayList<>();
          float surplus = (float) (priorSum - quantity);
          try {
            for (int i = 0; i < elements; i++) {
              String minDate = "2100-01-01";
              int minInd = -1;

              for (int j = 0; j < elements; j++) {
                if (considered.contains(j)) {
                  continue;
                }
                if (!formatter.parse(dates[j]).before(formatter.parse(date))) {
                  if (formatter.parse(dates[j]).before(formatter.parse(minDate))) {
                    minDate = dates[j];
                    minInd = j;
                  }
                }
              }
              if (minInd == -1) {
                break;
              }
              considered.add(minInd);
              if (!operations[minInd].equals("BUY")) {
                if (surplus - Float.parseFloat(quantities[minInd]) < 0) {
                  removed.add(minInd);
                } else {
                  surplus -= Float.parseFloat(quantities[minInd]);
                }
              } else {
                surplus += Float.parseFloat(quantities[minInd]);
              }
            }
          } catch (Exception e) {
            //This really should not be happening if the JSON is formatted properly.
          }
          for (int i = 0; i < removed.size(); i++) {
            //System.out.println("Removed: " + removed.get(i));
            int remove = removed.get(i);
            tickerArray.remove(remove);
          }
          JSONObject newStock = stockObjectHelper(quantity, date, commissionFee, operation);
          tickerArray.add(newStock);
          reader.close();
        }
      } else {
        portfolioData.put(symbol, new JSONArray());
        JSONObject newStock = stockObjectHelper(quantity, date, commissionFee, operation);
        JSONArray tickerArray = (JSONArray) portfolioData.get(symbol);
        tickerArray.add(newStock);
        reader.close();
      }
    }
    FileWriter fw = new FileWriter(prop.getProperty("resource_file") +
        "/" + filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioData.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();
    br.close();

  }

  @Override
  public boolean validateJSONFile(String filename, String newFile) throws IOException,
      ParseException, java.text.ParseException, NoSuchFieldException {
    JSONParser jsonParser = new JSONParser();
    FileReader reader = new FileReader(filename);
    Object obj;
    try {
      obj = jsonParser.parse(reader);
    } catch (Exception e) {
      return false;
    }
    reader.close();
    JSONObject portfolioData = (JSONObject) obj;
    File file = new File(prop.getProperty("resource_file") + "/" + newFile);

    for (Object key : portfolioData.keySet()) {
      if (!flexiblePortfolio.validateSymbol(key.toString())) {
        file.delete();
        return false;
      }
      if (!flexiblePortfolio.validateSymbol(key.toString())) {
        return false;
      }
      JSONArray tickerArray = (JSONArray) portfolioData.get(key.toString());
      for (Object stockObj : tickerArray) {
        JSONObject jsonStockObj = (JSONObject) stockObj;
        Double fee = Double.parseDouble(jsonStockObj.get("Fee").toString());
        String operation = jsonStockObj.get("Operation").toString();
        String date = jsonStockObj.get("Date").toString();
        int quantity = (int) Double.parseDouble(jsonStockObj.get("Quantity").toString());
        if (validateJSONObject(date, operation, fee, quantity)) {
          if (operation.equals("BUY")) {
            if (!flexiblePortfolio.buyShares(key.toString(), quantity, date, fee, newFile)) {
              file.delete();
              return false;
            }
          } else if (operation.equals("SELL")) {
            if (!flexiblePortfolio.sellShares(key.toString(), quantity, date, fee, newFile)) {
              file.delete();
              return false;
            }
          }
        } else {
          file.delete();
          return false;
        }
      }

    }

    return true;
  }

  @Override
  public boolean writeStrategy(double amount, List<Double> weight,
      List<String> symbol, String currentDate,
      int timeInterval, String endDate, List<Double> commissionfee,
      String filename) throws IOException, ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String presentDate = sdf.format(new Date());
    JSONParser jsonParser = new JSONParser();
    BufferedReader br = new BufferedReader(
        new FileReader(prop.getProperty("strategy_file")
            + "/" + "dollarCostAveraging.json"));
    JSONObject portfolioData = null;
    if (br.readLine() == null) {
      portfolioData = new JSONObject();
      JSONArray newStock = new JSONArray();
      for (int i = 0; i < weight.size(); i++) {
        JSONObject object = stockArrayHelper(amount, weight.get(i), symbol.get(i),
            presentDate, timeInterval, endDate, commissionfee.get(i));
        newStock.add(object);
      }
      portfolioData.put(filename, newStock);
      br.close();
    } else {
      FileReader reader = new FileReader(prop.getProperty("strategy_file") +
          "/" + "dollarCostAveraging.json");
      Object obj = jsonParser.parse(reader);
      portfolioData = (JSONObject) obj;
      if (portfolioData.containsKey(filename)) {
        JSONArray fileData = (JSONArray) portfolioData.get(filename);
        for (int i = 0; i < weight.size(); i++) {
          JSONObject object = stockArrayHelper(amount, weight.get(i), symbol.get(i), presentDate,
              timeInterval, endDate, commissionfee.get(i));
          fileData.add(object);
        }
        portfolioData.put(filename, fileData);
        reader.close();
      } else {

        JSONArray array = new JSONArray();
        for (int i = 0; i < weight.size(); i++) {
          JSONObject object = stockArrayHelper(amount, weight.get(i), symbol.get(i), presentDate,
              timeInterval, endDate, commissionfee.get(i));
          array.add(object);
        }
        portfolioData.put(filename, array);
        reader.close();
      }
    }

    FileWriter fw = new FileWriter(prop.getProperty("strategy_file") +
        "/" + "dollarCostAveraging.json");
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portfolioData.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();
    br.close();

    return true;
  }

  @Override
  public void checkForStrategy(String fileName, String date)
      throws IOException, ParseException, java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(new Date());
    JSONParser jsonParser = new JSONParser();
    JSONObject portFolioData = null;
    BufferedReader br = new BufferedReader(
        new FileReader(prop.getProperty("strategy_file") +
            "/" + "dollarCostAveraging.json"));
    if (br.readLine() == null) {
      br.close();
      return;
    } else {
      FileReader reader = new FileReader(prop.getProperty("strategy_file") +
          "/" + "dollarCostAveraging.json");
      Object obj = jsonParser.parse(reader);
      portFolioData = (JSONObject) obj;

      reader.close();
      br.close();
      if (portFolioData.containsKey(fileName)) {
        JSONArray jarray = (JSONArray) portFolioData.get(fileName);
        JSONArray toWrite = new JSONArray();
        for (int i = 0; i < jarray.size(); i++) {
          JSONObject object = (JSONObject) jarray.get(i);
          if (sdf.parse(object.get("currentDate").toString()).before(sdf.parse(currentDate))) {
            double amount = Double.parseDouble(String.valueOf(object.get("amount")));
            double weight = Double.parseDouble(String.valueOf(object.get("weight")));
            double fee = Double.parseDouble(String.valueOf(object.get("commissionFee")));
            int interval = Integer.parseInt(String.valueOf(object.get("timeInterval")));

            flexiblePortfolio.dollarCostAveraging(amount, weight, object.get("symbol").toString(),
                object.get("currentDate").toString(), interval,
                object.get("endDate").toString(),
                fee, fileName);
            object.replace("currentDate", object.get("currentDate"), currentDate);
          }
          toWrite.add(object);
        }
        portFolioData.put(fileName, toWrite);
      }
    }

    FileWriter fw = new FileWriter(prop.getProperty("strategy_file") +
        "/" + "dollarCostAveraging.json");
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(portFolioData.toJSONString());
    bw.newLine();
    bw.close();
    fw.close();
    br.close();

  }


  private boolean validateJSONObject(String date, String operation, Double fee, int quantity) {
    return flexiblePortfolio.validateDate(date)
        && flexiblePortfolio.isNumber(String.valueOf(quantity))
        && (operation.equals("BUY") || (operation.equals("SELL"))) && (fee > 0);
  }


  private JSONObject stockObjectHelper(double quantity, String date, double commissionFee,
      String operation) {
    JSONObject object = new JSONObject();
    object.put("Quantity", quantity);
    object.put("Date", date);
    object.put("Fee", commissionFee);
    object.put("Operation", operation);
    return object;
  }

  private JSONObject stockArrayHelper(double amount, double weight, String symbol,
      String currentDate, int timeInterval, String endDate,
      double commissionfee) {
    JSONObject object = new JSONObject();
    object.put("amount", amount);
    object.put("weight", weight);
    object.put("symbol", symbol);
    object.put("currentDate", currentDate);
    object.put("timeInterval", timeInterval);
    object.put("endDate", endDate);
    object.put("commissionFee", commissionfee);
    return object;
  }

}
