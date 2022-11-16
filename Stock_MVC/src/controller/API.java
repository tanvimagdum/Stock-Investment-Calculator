package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * An interface that represents methods to retrieve, validate information using API.
 *
 * Changes:
 * 1. Made validateTickers private, moved the public version to the model.
 * 2. Moved API from the model to the controller to match IO responsibilities.
 */

public interface API {

  /**
   * Get the prices of stocks on a certain date from API.
   * @param tickerList array of ticker numbers for several stocks
   * @param date date at which price is to be known
   * @return the list of prices of every stock on the certain date
   */
  public float[] getPrices(String[] tickerList, Date date) throws IOException, ParseException;

}
