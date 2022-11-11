package model;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class representing methods to
 * retrieve and validate portfolio
 * information using API.
 */

public class APIImpl implements API {
  @Override
  public float[] getPrices(ArrayList<String> tickerList, Date date) {

    String apiKey = "4U3NNSG5OHR1CBIG";
    URL url = null;

    for (int i = 0; i < tickerList.size(); i++) {
      try {
        url = new URL("https://alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol="
                + tickerList.get(i) + "&apikey=" + apiKey + "&datatype=csv");
      } catch (MalformedURLException e) {
        throw new RuntimeException("the alphavantage API has either changed or "
                + "no longer works");
      }

      InputStream in = null;
      StringBuilder output = new StringBuilder();

      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        while (reader.ready()) {
          String[] elements = reader.readLine().split(",");
        }

      } catch (IOException e) {
        System.out.println("No price data found for " + tickerList.get(i));
      }
      System.out.println("Return value: " + tickerList.get(i));
    }
    return new float[0];
  }

  @Override
  public boolean validateTicker(String ticker) {
    return false;
  }
}

