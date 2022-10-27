package controller;

import model.portfolio;

import java.util.Date;

public interface portfolioController {

    public void portBuilder(String ticker, float count);
    public void buildPortfolio();
    public portfolio getPortfolio(String name);
    public void readPortfolioFile(String filename);
    public String[] getPortfolioNames();
    public String[] getPortfolioValue(String name, Date date);

}
