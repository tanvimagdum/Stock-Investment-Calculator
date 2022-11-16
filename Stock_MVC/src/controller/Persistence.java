package controller;

import model.Portfolio;
import model.FlexPortfolioImpl;
import model.Stock;
import model.PortfolioImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A specific instance of the persistence interface, focused on read/write to disc.
 */
public class Persistence implements PersistenceInterface {

    @Override
    public void saveSimpleCSV(Portfolio thisPortfolio) throws IOException {
        String[] tickers = thisPortfolio.getTickers();
        Float[] counts = thisPortfolio.getCounts();
        String portfolioName = thisPortfolio.getPortfolioName();

        try{
            Files.deleteIfExists(Path.of("./" + portfolioName + ".csv"));
        } catch (Exception e) {
            //do nothing
        }

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

        try{
            Files.deleteIfExists(Path.of("./" + portfolioName + ".csv"));
        } catch (Exception e) {
            //do nothing
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        FileWriter writer = new FileWriter(portfolioName + ".csv");
        for (int i = 0; i < tickers.length; i++) {
            writer.append(tickers[i]);
            writer.append(",");
            writer.append(counts[i].toString());
            writer.append(",");
            writer.append(formatter.format(dates[i]));
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }

    @Override
    public Portfolio loadCSV(String filename) throws IOException, ParseException {
        String name = filename.substring(0, filename.length() - 4);
        ArrayList<String> tickerList = new ArrayList<>();
        ArrayList<Float> floatList = new ArrayList<>();
        ArrayList<Date> dateList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("./" + filename));

        String row = reader.readLine();

        while (row != null) {
            String[] elements = row.split(",");

            if (!(elements.length == 2 || elements.length == 3)) {
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
            } else {
                try {
                    float newFloat = Float.parseFloat(elements[1]);
                    if ((newFloat != Math.ceil(newFloat))) {
                        throw new RuntimeException();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Only integers are allowed for stock counts.");
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tickerList.add(elements[0]);
                floatList.add(Float.valueOf((int) Float.parseFloat(elements[1])));
                dateList.add(format.parse(elements[2]));
                row = reader.readLine();
            }
        }
        reader.close();
        if (dateList.isEmpty()) {
            ArrayList<Stock<String, Float>> finalList = new ArrayList<>();
            for (int i = 0; i < tickerList.size(); i++) {
                finalList.add(new Stock<>(tickerList.get(i), floatList.get(i)));
            }
            PortfolioImpl newPort = PortfolioImpl.builder().build(finalList, name);
            return newPort;
        } else {
            FlexPortfolioImpl newPort = new FlexPortfolioImpl(name);
            for (int i = 0; i < tickerList.size(); i++) {
                if (floatList.get(i) < 0) {
                    if (!newPort.checkEdit(tickerList.get(i), Math.abs(floatList.get(i)), dateList.get(i))) {
                        throw new RuntimeException("Cannot load invalid flexible portfolios.");
                    }
                }
                newPort.addFlexStock(tickerList.get(i), floatList.get(i), dateList.get(i));
            }
            return newPort;
        }
    }
}