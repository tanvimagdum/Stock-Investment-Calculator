import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

import controller.BasePortfolioControllerImpl;
import controller.SwingController;
import controller.SwingControllerImpl;
import model.FlexiblePortfolio;
import model.FlexiblePortfolioImpl;
import model.PortfolioModel;
import model.PortfolioModelImpl;
import view.DisplayPortfolio;
import view.DisplayPortfolioImpl;
import view.SwingView;
import view.SwingViewImpl;

/**
 * Class to implement the main method of the MVC architecture.
 * This class contains main method which calls the controller and passes objects of Model and View.
 */
public class PortfolioMain {
  /**
   * Main method to instantiate object of View and Model to be passed to Controller.
   *
   * @param args String array to hold commandline inputs.
   * @throws ParseException           if error in json parsing.
   * @throws java.text.ParseException if unable to parse string.
   * @throws IOException              if input mismatch occurs.
   * @throws NoSuchFieldException     if field is not found.
   */
  public static void main(String[] args) throws IOException, ParseException,
          java.text.ParseException, NoSuchFieldException {
    if (args[0].equals("Text")) {
      FlexiblePortfolio flexiblePortfolio = new FlexiblePortfolioImpl();
      DisplayPortfolio displayPortfolio = new DisplayPortfolioImpl();
      PortfolioModel portfolioModel = new PortfolioModelImpl();
      try {
        new BasePortfolioControllerImpl(new InputStreamReader(System.in),
                System.out, flexiblePortfolio, portfolioModel, displayPortfolio).startProgram();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (args[0].equals("Swing")) {
      FlexiblePortfolio flexiblePortfolio = new FlexiblePortfolioImpl();
      SwingView swingView = new SwingViewImpl();
      SwingController swingController = new SwingControllerImpl(flexiblePortfolio, swingView);
    } else {
      System.out.println("Invalid Argument Provided");
    }
  }
}
