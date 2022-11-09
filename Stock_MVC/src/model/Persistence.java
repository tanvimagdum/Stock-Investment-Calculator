package model;

import java.io.FileWriter;
import java.io.IOException;

public class Persistence{

    public void saveToDisc(Portfolio thisPortfolio, String portfolioName) throws IOException {
        String[] tickers = thisPortfolio.getTickers();
        Float[] counts = thisPortfolio.getCounts();

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

}
