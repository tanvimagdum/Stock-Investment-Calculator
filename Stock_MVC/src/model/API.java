package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * An interface that represents methods to retrieve,
 * validate information using API.
 */

public interface API {

  /**
   * Get the prices of stocks on a certain date from API.
   * @param ticketList array of ticker numbers for several stocks
   * @param date date at which price is to be known
   * @return the list of prices of every stock on the certain date
   */
  public float[] getPrices(ArrayList<String> ticketList, Date date);

  /**
   * Validate the ticker entered by user with the ticker
   * number present in API.
   * @param ticker ticker number entered by the user
   * @return true if the ticker number entered by user is present in API
   *         false if the ticker entered by user is incorrect and does not
   *              match any ticker in API
   */
  public boolean validateTicker(String ticker);

}
