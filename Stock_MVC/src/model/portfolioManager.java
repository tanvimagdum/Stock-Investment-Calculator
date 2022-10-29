package model;

import java.util.ArrayList;
import java.util.Date;


public interface portfolioManager {

  //public portfolio[] portfolioList;

  public void portBuilder(ArrayList<Pair<String, Float>> list, String name);
  public portfolio getPortfolio(String name);
  public void readPortfolioFile(String filename);
  public ArrayList<String> getPortfolioNames();
  public String[] getPortfolioValue(String name, Date date);

}
