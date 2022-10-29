package model;


import java.util.ArrayList;

public interface portfolio {
    ArrayList<Pair<String, Float>> returnList();
    String getPortfolioName();
    String[] getTickers();
    Float[] getCounts();

}

