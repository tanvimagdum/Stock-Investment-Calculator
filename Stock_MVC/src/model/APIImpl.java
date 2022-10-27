package model;

import java.util.Date;

public class APIImpl implements API {
    @Override
    public float[] getPrices(String[] ticketList, Date date) {
        return new float[0];
    }

    @Override
    public boolean validateTicker(String ticker) {
        return false;
    }
}

