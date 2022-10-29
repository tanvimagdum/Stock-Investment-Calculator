package controller;

import model.Pair;
import model.portfolio;

import java.util.ArrayList;
import java.util.Date;


public class portfolioControllerImpl implements portfolioController {


    @Override
    public void portBuilder(ArrayList<Pair<String, Float>> list, String name) {

    }

    @Override
    public portfolio getPortfolio(String name) {
        return null;
    }

    @Override
    public void readPortfolioFile(String filename) {

    }

    @Override
    public String[] getPortfolioNames() {
        return new String[0];
    }

    @Override
    public String[] getPortfolioValue(String name, Date date) {
        return new String[0];
    }
}
