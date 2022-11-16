package controller;

import model.Portfolio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * This interface describes a persistence object to broaden possible forms of persistence.
 */

/**
 * Changes:
 * 1. Moved to controller package to better match its IO responsibilities.
 */
public interface PersistenceInterface {

    /**
     * Saves a simple portfolio to disc as a CSV.
     *
     * @param simplePort the portfolio to save as a CSV
     * @throws IOException if there is trouble writing the file
     */
    public void saveSimpleCSV(Portfolio simplePort) throws IOException;

    /**
     *
     *
     * @param flexPort
     * @throws IOException
     */
    public void saveFlexCSV(Portfolio flexPort) throws IOException;

    public Portfolio loadCSV(String filename) throws IOException, ParseException;
}
