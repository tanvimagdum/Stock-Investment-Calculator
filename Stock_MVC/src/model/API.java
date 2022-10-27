package model;

import java.util.Date;

public interface API {

  public float[] getPrices(String[] ticketList, Date date);
  public boolean validateTicker(String ticker);

}
