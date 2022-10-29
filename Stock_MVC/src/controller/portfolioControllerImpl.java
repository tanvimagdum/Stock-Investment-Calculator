package controller;

import model.Pair;
import model.portfolioManager;
import model.portfolioManagerImpl;
import model.portfolio;

import java.util.ArrayList;
import java.util.Date;


public class portfolioControllerImpl implements portfolioController {

  portfolioManager model = new portfolioManagerImpl();
  @Override
  public void portBuilder(ArrayList<Pair<String, Float>> list, String name) {
    model.portBuilder(list, name);
  }

  @Override
  public portfolio getPortfolio(String name) {
    return model.getPortfolio(name);
  }

  @Override
  public void readPortfolioFile(String filename) {
    model.readPortfolioFile(filename);
  }

  @Override
  public ArrayList<String> getPortfolioNames() {
    return model.getPortfolioNames();
  }
  @Override
  public String[] getPortfolioValue(String name, Date date) {
    return model.getPortfolioValue(name,date);
  }


}
