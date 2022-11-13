package model;

import java.util.ArrayList;
import java.util.Date;

public class FlexPortfolioImpl implements Portfolio{
    private ArrayList<FlexStock<String, Float, Date>> stockList = new ArrayList<>();
    private final String portfolioName;

    public FlexPortfolioImpl(String portfolioName) {
        this.portfolioName = portfolioName;
    }


    /**
     * Get the name of portfolio.
     *
     * @return the portfolio name as a string
     */
    @Override
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * Get the list of ticker numbers in the portfolio.
     *
     * @return string array of ticker numbers
     */
    @Override
    public String[] getTickers() {
        String[] tickers = new String[stockList.size()];
        for (int i = 0; i < stockList.size(); i++) {
            tickers[i] = stockList.get(i).getS();
        }
        return tickers;
    }

    /**
     * Get the list of counts of each stock in the portfolio.
     *
     * @return float array of counts of stocks
     */
    @Override
    public Float[] getCounts() {
        Float[] counts = new Float[stockList.size()];
        for (int i = 0; i < stockList.size(); i++){
            counts[i] = stockList.get(i).getF();
        }
        return counts;
    }

    /**
     * Get the list of purchase dates of each stock in the portfolio.
     *
     * @return date array of purchase dates
     */
    public Date[] getDates() {
        Date[] dates = new Date[stockList.size()];
        for (int i = 0; i < stockList.size(); i++){
            dates[i] = stockList.get(i).getD();
        }
        return dates;
    }

    public boolean checkEdit(String ticker, float count, Date date) {
        String[] tickers = getTickers();
        Float[] counts =  getCounts();
        Date[] dates = getDates();
        float sum = 0;
        for (int i = 0; i < tickers.length; i++) {
            if (tickers[i].equals(ticker)){
                sum += counts[i];
            }
        }
        if (sum < count) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < tickers.length; i++) {
            if (tickers[i].equals(ticker)){
                sum += counts[i];
            }
        }
        if (sum < count) {
            return false;
        }

        return true;
    }

    /**
     * A method to add new stock items to the portfolio's list
     * @param ticker
     * @param count
     * @param date
     */
    public void addFlexStock(String ticker, float count, Date date) {
        stockList.add(new FlexStock<>(ticker, count, date));
    }
}
