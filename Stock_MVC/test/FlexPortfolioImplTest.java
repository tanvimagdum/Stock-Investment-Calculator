import model.FlexPortfolioImpl;
import model.FlexStock;
import model.Portfolio;
import model.Stock;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class FlexPortfolioImplTest {

  @Test
  public void testGetPortfolioName() {
    Portfolio port = new FlexPortfolioImpl("FlexPort");
    assertEquals("FlexPort", port.getPortfolioName());
  }

  @Test
  public void testGetTickerCountDate() {
    Portfolio port = new FlexPortfolioImpl("FlexPort");
    assertEquals("FlexPort", port.getPortfolioName());
  }

  @Test
  public void testCheckEdit() {
  }

  @Test
  public void testAddFlexStock() {
  }
}