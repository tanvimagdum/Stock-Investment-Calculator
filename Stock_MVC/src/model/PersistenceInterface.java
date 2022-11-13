package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public interface PersistenceInterface {

    public void saveSimpleCSV(Portfolio simplePort) throws IOException;

    public void saveFlexCSV(Portfolio flexPort) throws IOException;

    public Portfolio loadCSV(String filename) throws IOException, ParseException;
}
