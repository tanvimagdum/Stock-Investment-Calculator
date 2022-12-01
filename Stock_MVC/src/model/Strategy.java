package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * An interface to describe the general properties of an investment strategy.
 */
public interface Strategy {

  /**
   * Gets the breakdown of apportioning to each unique stock in the portfolio.
   *
   * @return
   */
  ArrayList<Stock<String, Float>> getList();
  Float getAmount();
  Date getStartDate();
  Date getEndDate();
  int getFrequency();
}
