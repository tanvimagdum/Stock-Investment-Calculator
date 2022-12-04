package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * {@code VantageAPIData} represents implementation of {@code APIData} interface.
 * This class incorporates VantageAPI service to extract the data either daily or monthly for
 * any particular symbol;
 */
public class VantageAPIData implements APIData {
  private Properties properties;
  private static Map<String, String[]> stockData = new HashMap<>();

  /**
   * Constructs the VantageAPIData and loads configuration properties stored in src folder.
   * This configuration includes API, Resource as well as Symbol list configuration.
   *
   * @throws IOException if configuration is not found.
   */
  public VantageAPIData() throws IOException {
    this.properties = new Properties();
    FileInputStream ip = new FileInputStream("src/config.properties");
    properties.load(ip);
  }

  @Override
  public String[] getInputStream(String symbol) throws IOException {
    String apiKey = properties.getProperty("api_key");
    StringBuilder reader = new StringBuilder();
    String[] list = null;
    URL url = null;
    InputStream in = null;
    if (!stockData.containsKey(symbol)) {
      url = new URL(properties.getProperty("api_url")
              + symbol + "&apikey=" + apiKey + "&datatype=csv");
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        reader.append((char) b);
      }
      list = reader.toString().split(System.lineSeparator());
      stockData.put(symbol, list);
    } else {
      return stockData.get(symbol);
    }
    return list;
  }


  @Override
  public List<String> getInputStreamMonthly(String symbol, String date) throws IOException {
    String apiKey = properties.getProperty("api_key");
    URL url = null;
    url = new URL(properties.getProperty("api_url_monthly")
            + symbol + "&apikey=" + apiKey + "&datatype=csv");
    InputStream in = null;
    in = url.openStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    List<String> output = new ArrayList<>();
    reader.readLine();
    String line;
    while ((line = reader.readLine()) != null) {
      String single_record = line;
      if ((single_record.split(",")[0]).contains(date)) {
        Double price = Double.parseDouble(single_record.split(",")[4]);
        output.add(String.format("%.2f", price));
        output.add(single_record.split(",")[0]);
        break;
      }
    }
    reader.close();
    return output;
  }

  @Override
  public StringBuilder getPriceForDate(String[] reader, String date, String operation)
          throws IOException, ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    StringBuilder output = new StringBuilder("");
    for (int i = 1; i < reader.length; i++) {
      String single_record = reader[i];
      if (operation.equals("daily")) {
        if (date.equals(single_record.split(",")[0])
                || sdf.parse(date).after(sdf.parse(single_record.split(",")[0]))) {
          Double price = Double.parseDouble(single_record.split(",")[4]);
          output.append(String.format("%.2f", price));
          break;
        }
      } else {
        if ((single_record.split(",")[0]).contains(date)) {
          Double price = Double.parseDouble(single_record.split(",")[4]);
          output.append(String.format("%.2f", price));
          break;
        }
      }
    }
    return output;
  }

}

