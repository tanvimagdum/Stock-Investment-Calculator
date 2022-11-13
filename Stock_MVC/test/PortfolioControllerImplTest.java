import controller.PortfolioController;
import controller.PortfolioControllerImpl;
import model.Persistence;
import model.PortfolioManagerImpl;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test for the portfolio controller implementation.
 */
public class PortfolioControllerImplTest {

  @Test
  public void testReadGetPortfolio() throws IOException, ParseException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }

  @Test
  public void testPortfolioLatestValue() throws IOException, ParseException {

    ArrayList<String> str = new ArrayList<>(Arrays.asList("A", "AAPL"));
    ArrayList<Float> flt = new ArrayList<>(Arrays.asList((float)10.00, (float)12.00));

    Readable in = new StringReader("Port AAPL 10 A 20 Done");

    PortfolioController pc = new PortfolioControllerImpl(in, new PortfolioManagerImpl(new Persistence()));
    pc.readPortfolioFile("port.csv");

    assertEquals("port",pc.getPortfolioNames()[0]);

  }

}