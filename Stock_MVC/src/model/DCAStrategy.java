package model;

import java.util.ArrayList;
import java.util.Date;

public class DCAStrategy implements Strategy{
  float amount;
  Date startDate;
  Date endDate;
  int frequency;
  ArrayList<Stock<String, Float>> list = new ArrayList<>();

  public DCAStrategy(ArrayList<Stock<String, Float>> lst, Date sd, Date ed, int freq) {
   this.list = lst;
   float amt = 0;
   for (int i = 0; i < lst.size(); i++) {
     amt += lst.get(i).getF();
   }
   this.amount = amt;
   this.startDate = sd;
   this.endDate = ed;
   this.frequency = freq;
  }

  @Override
  public ArrayList<Stock<String, Float>> getList() {
    return this.list;
  }

  @Override
  public Float getAmount() {
    return amount;
  }

  @Override
  public Date getStartDate() {
    return startDate;
  }

  @Override
  public Date getEndDate() {
    return endDate;
  }

  @Override
  public int getFrequency() {
    return frequency;
  }
}
