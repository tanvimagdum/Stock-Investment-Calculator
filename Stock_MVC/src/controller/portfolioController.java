package controller;

import model.Stock;
import model.portfolio;
import view.viewInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public interface portfolioController {

    public void portBuilder(ArrayList<Stock<String, Float>> list, String name);
    public portfolio getPortfolio(String name);
    public void readPortfolioFile(String filename) throws FileNotFoundException;
    public void savePortfolio(String filename) throws IOException;
    public String[] getPortfolioNames();
    public String selectPortfolio(viewInterface view);
    public String[] getPortfolioValue(String name, Date date);
    public String[] getPortfolioContents(String name);
    public void buildPortfolio(viewInterface v);
    public String[] manualValuation(String name, viewInterface v);
    public String[] getTickers(String name);
    public Float[] getCounts(String name);

}