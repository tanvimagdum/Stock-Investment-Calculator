package model;

import java.util.ArrayList;
import java.util.Date;


public class portfolioManagerImpl implements portfolioManager {
    private ArrayList<portfolioImpl> portfolios;
    private API api = new APIImpl();

    @Override
    public void portBuilder(String ticker, float count) {

    }

    @Override
    public void buildPortfolio() {

    }

    @Override
    public portfolio getPortfolio(String name) {
        int size = portfolios.size();
        for (int i = 0; i < size; i++) {
            if (portfolios.get(i).getPortfolioName().equals(name)) {
                return portfolios.get(i);
            }
        }
        throw new IllegalArgumentException("No portfolio by that name was found.");
    }

    @Override
    public void readPortfolioFile(String filename) {
        //filereader
    }

    @Override
    public ArrayList<String> getPortfolioNames() {
        ArrayList<String> listOfNames = null;
        int size = portfolios.size();
        if (size == 0) {
            throw new IllegalArgumentException("There are no portfolios yet.");
        }
        for (int i = 0; i < size; i++) {
            listOfNames.add(portfolios.get(i).getPortfolioName());
        }
        return listOfNames;
    }

    @Override
    public String[] getPortfolioValue(String name, Date date) {
        portfolio subject = getPortfolio(name);
        String[] tickers = subject.getTickers();
        Float[] counts = subject.getCounts();

        float[] values = api.getPrices(tickers, date);
        String[] out = new String[tickers.length+2];

        out[0] = "Value of Portfolio: " + name;

        float sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
            out[i+1] = "Ticker: " + tickers[i] + "; Count: " + counts + "; Value: " + values[i];
        }

        out[tickers.length+2] = "Total value of portfolio: " + sum;

        return out;
    }
}
