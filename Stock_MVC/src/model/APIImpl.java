package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

    String apiKey = "BS58H4QP10QUOOQJ";
    String nasKey = "yVv3jAbTKC6Hn6JsstqX";
    URL url = null;

    for (int i = 0; i < tickerList.size(); i++) {
      try {
        url = new URL("https://data.nasdaq.com/api/v3/"
                + "datasets/WIKI/" + tickerList.get(i) + ".csv?api_key=" + nasKey);
      } catch (MalformedURLException e) {
        throw new RuntimeException("the alphavantage API has either changed or "
                + "no longer works");
      }

      InputStream in = null;
      StringBuilder output = new StringBuilder();

      try {
        in = url.openStream();

        FileWriter writer = new FileWriter(".\\Stocks\\" + tickerList.get(i) + ".csv");

        in = url.openStream();
        int b;

        while ((b = in.read()) != -1) {
          writer.append((char) b);
        }
        writer.flush();
        writer.close();

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

