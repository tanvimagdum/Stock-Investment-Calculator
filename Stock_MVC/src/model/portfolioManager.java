package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * An interface
 */

public interface portfolioManager {

  //public portfolio[] portfolioList;

  /**
   *
   * @param list
   * @param name
   */
  public void portBuilder(ArrayList<Pair<String, Float>> list, String name);

  /**
   *
   * @param name
   * @return
   */
  public portfolio getPortfolio(String name);

  /**
   *
   * @param filename
   */
  public void readPortfolioFile(String filename);

  /**
   *
   * @return
   */
  public String[] getPortfolioNames();

  /**
   *
   * @param name
   * @param date
   * @return
   */
  public String[] getPortfolioValue(String name, Date date);

  /**
   *
   * @param name
   * @return
   */
  public String[] getPortfolioContents(String name);

  /**
   *
   * @param name
   * @return
   */
  public String[] getTickers(String name);

  /**
   *
   * @param name
   * @return
   */
  public Float[] getCounts(String name);

}