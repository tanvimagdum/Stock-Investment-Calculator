package model;

import org.junit.Test;


import static org.junit.Assert.*;

public class StockTest {

  @Test
  public void testStockBuilder() {
      Stock<String, Float> stock = new Stock<String, Float>("GOOG", (float) 10.20);
      assertEquals("GOOG", stock.getS());
      assertEquals((float) 10.20, stock.getF(), 0.0001);
  }

}