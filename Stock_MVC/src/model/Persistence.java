package model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Persistence implements PersistenceInterface{

    public void saveSimpleCSV(Portfolio thisPortfolio) throws IOException {
        String[] tickers = thisPortfolio.getTickers();
        Float[] counts = thisPortfolio.getCounts();
        String portfolioName = thisPortfolio.getPortfolioName();

        FileWriter writer = new FileWriter(portfolioName + ".csv");
        for (int i = 0; i < tickers.length; i++) {
            writer.append(tickers[i]);
            writer.append(",");
            writer.append(counts[i].toString());
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }

    @Override
    public void saveFlexCSV(Portfolio flexPort) throws IOException {
        String[] tickers = flexPort.getTickers();
        Float[] counts = flexPort.getCounts();
        Date[] dates = ((FlexPortfolioImpl) flexPort).getDates();
        String portfolioName = flexPort.getPortfolioName();

        FileWriter writer = new FileWriter(portfolioName + ".csv");
        for (int i = 0; i < tickers.length; i++) {
            writer.append(tickers[i]);
            writer.append(",");
            writer.append(counts[i].toString());
            writer.append(",");
            writer.append(dates[i].toString());
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }

    @Override
    public Portfolio loadCSV(String filename) throws IOException {
        String name = filename.substring(0, filename.length() - 4);
        ArrayList<String> tickerList = new ArrayList<>();
        ArrayList<Float> floatList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("./" + filename));

        String row = reader.readLine();

        while (row != null) {
            String[] elements = row.split(",");

            if (!(elements.length == 2 || elements.length ==3)) {
                throw new RuntimeException("File not properly formatted. Please ensure there"
                        + "are no headers and only one string and one value per line.");
            }

            if (elements.length == 2) {

                try {
                    float newFloat = Float.parseFloat(elements[1]);
                    if ((newFloat != Math.ceil(newFloat) || (newFloat < 0))) {
                        throw new RuntimeException();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Only integers are allowed for stock counts.");
                }
                tickerList.add(elements[0]);
                floatList.add(Float.valueOf((int) Float.parseFloat(elements[1])));
                row = reader.readLine();
            }
        }
        reader.close();
}
