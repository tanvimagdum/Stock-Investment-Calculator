package controller;

<<<<<<< HEAD
import model.Pair;
=======
import model.portfolioManager;
import model.portfolioManagerImpl;
>>>>>>> 7c5c66a (new commit 12.36)
import model.portfolio;

import java.util.ArrayList;
import java.util.Date;


public class portfolioControllerImpl implements portfolioController {

  portfolioManager model = new portfolioManagerImpl();
  @Override
  public void portBuilder(String ticker, float count) {
    model.portBuilder(ticker, count);
  }

<<<<<<< HEAD
    @Override
    public void portBuilder(ArrayList<Pair<String, Float>> list, String name) {
=======
  @Override
  public void buildPortfolio() {
    model.buildPortfolio();
  }

  @Override
  public portfolio getPortfolio(String name) {
    return model.getPortfolio(name);
  }

  @Override
  public void readPortfolioFile(String filename) {
    model.readPortfolioFile(filename);
  }
>>>>>>> 7c5c66a (new commit 12.36)

  @Override
  public ArrayList<String> getPortfolioNames() {
    return model.getPortfolioNames();
  }
  @Override
  public String[] getPortfolioValue(String name, Date date) {
    return model.getPortfolioValue(name,date);
  }


}
