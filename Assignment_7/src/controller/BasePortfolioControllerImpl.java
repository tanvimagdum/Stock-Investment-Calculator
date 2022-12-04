package controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

import model.FlexiblePortfolio;
import model.PortfolioModel;
import view.DisplayPortfolio;

/**
 * {@code BasePortfolioControllerImpl} implements {@code PortfolioController} Interface.
 * This class has the driver method to run the menu operation as per user input.
 * The menu inside the driver delegates the operations to the Flexible and Non-flexible portfolios
 * operations.
 */
public class BasePortfolioControllerImpl implements PortfolioController {
  private final FlexiblePortfolio flexiblePortfolio;
  private final DisplayPortfolio displayPortfolio;

  private final PortfolioModel portfolioModel;

  private final Readable in;
  private final Appendable out;

  /**
   * Constructs the BasePortfolioControllerImpl with the input, output, models and view.
   * The methods from models and view are then used by the controller to perform the operation.
   * for example - examining portfolio will load the files in the database using the method from
   * model and view will perform the duty to display the files.
   *
   * @param in                represents the sequence of characters. This uses Readable
   *                          interface which acts as Readable source to the Scanner class.
   * @param out               represents the output log to be printed on the console.
   *                          This uses Appendable which appends the sequence of character to object
   * @param flexiblePortfolio represents the model interface of flexible portfolio.
   * @param portfolioModel    represents the model interface of the portfolio.
   * @param displayPortfolio  represents the view interface of the portfolio.
   */
  public BasePortfolioControllerImpl(Readable in, Appendable out,
                                     FlexiblePortfolio flexiblePortfolio,
                                     PortfolioModel portfolioModel,
                                     DisplayPortfolio displayPortfolio) {
    this.in = in;
    this.out = out;
    this.flexiblePortfolio = flexiblePortfolio;
    this.displayPortfolio = displayPortfolio;
    this.portfolioModel = portfolioModel;
  }

  @Override
  public void startProgram() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    int input = 0;
    Scanner sc = new Scanner(this.in);

    do {
      displayPortfolio.displayMainMessage(out);
      input = Integer.parseInt(sc.nextLine());
      switch (input) {
        case 1: {
          PortfolioController fc = new FlexiblePortfolioControllerImpl(in, out, flexiblePortfolio,
                  displayPortfolio);
          fc.startProgram();
          break;
        }
        case 2: {
          PortfolioController pc = new PortfolioControllerImpl(in, out, portfolioModel,
                  displayPortfolio);
          pc.startProgram();
          break;
        }
        case 3:
          displayPortfolio.displayMessage(out, "Main Program terminated successfully\n");
          break;
        default:
          displayPortfolio.displayMessage(out, "Invalid option\n");
          break;

      }
    }
    while (input < 3);
  }
}
