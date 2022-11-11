package model;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class representing methods to
 * retrieve and validate portfolio
 * information using API.
 */

public class APIImpl implements API {
  @Override
  public float[] getPrices(ArrayList<String> tickerList, Date date) throws IOException, ParseException {

    String apiKey = "4U3NNSG5OHR1CBIG";
    URL url = null;
    float[] out = new float[tickerList.size()];
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    date = format.parse(format.format(date));

    for (int i = 0; i < tickerList.size(); i++) {
      try {
        url = new URL("https://alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol="
                + tickerList.get(i) + "&outputsize=full&apikey=" + apiKey + "&datatype=csv");
      } catch (MalformedURLException e) {
        throw new RuntimeException("There was an error retrieving that information from the API.");
      }

      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = reader.readLine();

        while (line != null) {
          String[] elements = line.split(",");

          Date rowDate = null;
          System.out.println(i);
          System.out.println(line);
          try {
            rowDate = format.parse(elements[0]);
          } catch (Exception e) {
            line = reader.readLine();
            continue;
          }

          if (rowDate.compareTo(date) < 1) {
            System.out.println(elements[5]);
            out[i] = Float.parseFloat(elements[5]);
            break;
          }
          System.out.println(reader.ready());
          line = reader.readLine();
        }

      } catch (IOException e) {
        throw new IOException("There was difficulty reading the input stream for " + tickerList.get(i));
      }
    }
    return out;
  }

  @Override
  public float[][] getPerformance(ArrayList<String> tickerList, Date[] dates){
    String apiKey = "4U3NNSG5OHR1CBIG";
    URL url = null;
    float[][] out = new float[tickerList.size()][dates.length];
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    for (int i = 0; i  < tickerList.size(); i++) {
      //api call
      try {
        url = new URL("https://alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol="
                + tickerList.get(i) + "&apikey=" + apiKey + "&datatype=csv");
      } catch (MalformedURLException e) {
        throw new RuntimeException("There was an error retrieving that information from the API.");
      }


      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        int j = 0;
        while (reader.ready()) {
          boolean read = false;
          dates[j] = format.parse(dates[j].toString());

          String[] elements = reader.readLine().split(",");

          Date rowDate = null;

          try {
            rowDate = format.parse(elements[0]);
          } catch (Exception e) {
            continue;
          }

          if (dates[j].compareTo(rowDate) < 1) {
            out[i][j] = Float.parseFloat(elements[1]);
            read = true;
          }

          if (read) {
            j++;
          }
        }

        if (j < dates.length-1) {
          for (int k = j; k < dates.length; k++) {
            out[i][k] = 0;
          }
        }

      } catch (IOException | ParseException e) {
        throw new RuntimeException("There was difficulty reading the input stream for " + tickerList.get(i));
      }
    }
    return out;
  }
}

