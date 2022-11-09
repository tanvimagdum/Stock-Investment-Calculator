import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import model.API;
import model.APIImpl;

public class SideOps {

  public static void main(String[] args) throws IOException {
    API api = new APIImpl();
    SideOps side = new SideOps();
    ArrayList<String> testTickers = side.getTickers();
    float[] prices = api.getPrices(testTickers, new Date());
    System.out.println(prices);
    //side.makeList();
  }

  public ArrayList<String> getTickers() throws IOException {
    ArrayList<String> out = new ArrayList();
    BufferedReader reader = new BufferedReader(new FileReader("My Portfolio.csv"));
    String row;
    row = reader.readLine();
    int i = 0;
    while (row != null) {

      String[] elements = row.split(",");
      out.add(elements[0]);
      row = reader.readLine();
      i++;
    }

    return out;
  }

  public void makeList() throws IOException {

    FileWriter writer = new FileWriter("TickerListSP500.csv");
    BufferedReader reader = new BufferedReader(new FileReader("sp-500-index-11-01-2022.csv"));
    String row;
    row = reader.readLine();
    row = reader.readLine();
    while (row != null) {
      String[] elements = row.split(",");

      if (elements.length == 1) {
        break;
      }
      System.out.println(elements[0]);
      writer.append(elements[0]);
      writer.append(",");
      writer.append(elements[1]);
      writer.append(",");
      writer.append(elements[2]);
      writer.append("\n");
      row = reader.readLine();
    }
    writer.flush();
    writer.close();
    reader.close();
  }
}
