package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public interface portfolioManager {

  //public portfolio[] portfolioList;

  public void portBuilder(ArrayList<Pair<String, Float>> list, String name);
  public portfolio getPortfolio(String name);
  public void readPortfolioFile(String filename) throws FileNotFoundException;
  public void savePortfolio(String portfolioName) throws IOException;
  public String[] getPortfolioNames();
  public String[] getPortfolioValue(String name, Date date);
  public String[] getPortfolioContents(String name);
  public String[] getTickers(String name);
  public Float[] getCounts(String name);
}
