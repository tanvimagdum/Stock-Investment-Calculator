package controller;

import model.Pair;
import model.portfolio;

import java.util.ArrayList;
import java.util.Date;


public interface portfolioController {

    public void portBuilder(ArrayList<Pair<String, Float>> list, String name);
    public portfolio getPortfolio(String name);
    public void readPortfolioFile(String filename);
    public ArrayList<String> getPortfolioNames();
    public String[] getPortfolioValue(String name, Date date);

}
