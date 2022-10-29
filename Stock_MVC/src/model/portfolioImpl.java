package model;

import java.util.ArrayList;

public class portfolioImpl implements portfolio{
    private final ArrayList<Pair<String, Float>> stockList;
    private final String portfolioName;

    private portfolioImpl(ArrayList<Pair<String, Float>> tempStockList, String name){
        stockList = tempStockList;
        portfolioName = name;
    }

    public static portfolioBuilder builder() {
        return new portfolioBuilder();
    }

    public static final class portfolioBuilder {
        private ArrayList<Pair<String, Float>> tempStockList;
        private String tempName = "";

        public portfolioImpl build(ArrayList<Pair<String, Float>> list, String name) {
            tempName = name;
            tempStockList = list;
            return new portfolioImpl(tempStockList, tempName);
        }

    }
    @Override
    public ArrayList<Pair<String,Float>> returnList() {
        return stockList;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String[] getTickers() {
        int size = stockList.size();
        String[] tickers = new String[size];
        for (int i = 0; i < size; i++) {
            tickers[i] = stockList.get(i).getS();
        }
        return tickers;
    }

    public Float[] getCounts() {
        int size = stockList.size();
        Float[] counts = new Float[size];
        for (int i = 0; i < size; i++) {
            counts[i] = stockList.get(i).getF();
        }
        return counts;
    }
}


