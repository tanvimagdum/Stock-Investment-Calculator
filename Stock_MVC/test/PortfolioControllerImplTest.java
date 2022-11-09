import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.Persistence;
import model.PortfolioManagerImpl;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the portfolio controller implementation.
 */
public class PortfolioControllerImplTest {

  @Test
  public void testReadGetPortfolio() throws IOException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }

  @Test
  public void testPortfolioLatestValue() throws IOException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

    assertEquals("Value of Portfolio: port as of 10/31/2022",
            pc.getPortfolioValueLatest("port")[0] );
    assertEquals("Ticker: A; Count: 10.0; Value per: 140.89; Total Value: 1408.90",
            pc.getPortfolioValueLatest("port")[1]);
    assertEquals("Ticker: AAPL; Count: 12.0; Value per: 150.65; Total Value: 1807.80",
            pc.getPortfolioValueLatest("port")[2]);
    assertEquals("Total value of portfolio: 3216.70",
            pc.getPortfolioValueLatest("port")[3]);
  }

}