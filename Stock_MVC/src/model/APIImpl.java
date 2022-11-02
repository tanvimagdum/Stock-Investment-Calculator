package model;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
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
    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/
    String apiKey = "BS58H4QP10QUOOQJ";
    String nasKey = "yVv3jAbTKC6Hn6JsstqX";
    URL url = null;

    for (int i = 0; i < tickerList.size(); i++) {
      try {
    /*
    create the URL. This is the query to the web service. The query string
    includes the type of query (DAILY stock prices), stock symbol to be
    looked up, the API key and the format of the returned
    data (comma-separated values:csv). This service also supports JSON
    which you are welcome to use.
     */
        /*url = new URL("https://www.alphavantage"
                + ".co/query?function=TIME_SERIES_DAILY"
                + "&outputsize=full"
                + "&symbol"
                + "=" + tickerList.get(i) + "&apikey=" + apiKey + "&datatype=csv");*/
        url = new URL("https://data.nasdaq.com/api/v3/"
                + "datasets/WIKI/" + tickerList.get(i) + ".csv?api_key=" + nasKey);
      } catch (MalformedURLException e) {
        throw new RuntimeException("the alphavantage API has either changed or "
                + "no longer works");
      }

      InputStream in = null;
      StringBuilder output = new StringBuilder();

      try {
    /*
    Execute this query. This returns an InputStream object.
    In the csv format, it returns several lines, each line being separated
    by commas. Each line contains the date, price at opening time, highest
    price for that date, lowest price for that date, price at closing time
    and the volume of trade (no. of shares bought/sold) on that date.

    This is printed below.
     */
        in = url.openStream();

        FileWriter writer = new FileWriter(".\\Stocks\\" + tickerList.get(i) + ".csv");

        in = url.openStream();
        int b;

        while ((b=in.read())!=-1) {
          writer.append((char)b);
        }
        writer.flush();
        writer.close();

      } catch (IOException e) {
        System.out.println("No price data found for " + tickerList.get(i));
      }
      System.out.println("Return value: " + tickerList.get(i));
      //System.out.println(output.toString());
    }
    return new float[0];
  }
  @Override
  public boolean validateTicker(String ticker) {
    return false;
  }
}

