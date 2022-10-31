package model;

import java.util.Date;

/**
 * A class representing methods to
 * retrieve and validate portfolio
 * information using API.
 */

public class APIImpl implements API {
  @Override
  public float[] getPrices(String[] tickerList, Date date) {
    return new float[0];
  }
  @Override
  public boolean validateTicker(String ticker) {
    return false;
  }
}

