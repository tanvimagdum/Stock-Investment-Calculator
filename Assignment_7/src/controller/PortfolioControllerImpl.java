package controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.PortfolioModel;
import view.DisplayPortfolio;

/**
 * {@code PortfolioControllerImpl} is the implementation of {@code PortfolioController} Interface.
 * This class has the driver method to run the menu operation as per user input.
 * The menu inside the driver delegates the operations to the model and the view.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel portfolioModel;
  private final DisplayPortfolio displayPortfolio;

  final Readable in;
  final Appendable out;

  /**
   * Constructs the PortfolioControllerImpl with the input, output, model and view.
   * The methods from model and view are then used by the controller to perform the operation.
   * for example - examining portfolio will load the files in the database using the method from
   * model and view will perform the duty to display the files.
   *
   * @param in               represents the sequence of characters.
   *                         This uses Readable interface which acts as
   *                         Readable source to the Scanner class.
   * @param out              represents the output log to be printed on the console.
   *                         This uses Appendable which
   *                         appends the sequence of characters to the object
   * @param portfolioModel   represents the model interface of the portfolio.
   * @param displayPortfolio represents the view interface of the portfolio.
   * @throws IOException if the inputs to the menu are invalid.
   */
  public PortfolioControllerImpl(Readable in, Appendable out, PortfolioModel portfolioModel,
                                 DisplayPortfolio displayPortfolio) throws IOException {
    this.in = in;
    this.out = out;
    this.displayPortfolio = displayPortfolio;
    this.portfolioModel = portfolioModel;
  }

  /**
   * This method is the driver method to execute case wise operations.
   * The cases inside this method includes features such as creating portfolio, examining portfolio,
   * Getting the valuation of the portfolio and allowing user to load the portfolio.
   *
   * @throws IOException throws exception for input datatype mismatch.
   */
  @Override
  public void startProgram() throws IOException {
    int input = 0;
    //Properties prop = portfolioModel.getProperties();
    //prop.setProperty("resource_file","portfolios");
    Scanner sc = new Scanner(this.in);
    do {
      try {
        displayPortfolio.displayMenu(this.out);
        input = Integer.parseInt(sc.nextLine());
        if (input < 0) {
          displayPortfolio.displayMessage(out, "Negative Number Provided");
          break;
        }
        switch (input) {
          case 1:
            portfolioModel.createFile();
            String loop = "1";
            while (loop.equals("1")) {
              displayPortfolio.displayMessage(out, "Enter stock symbol :\n");
              String symbol = sc.nextLine();
              displayPortfolio.displayMessage(out, "Enter stock quantity :\n");
              String quantity = (sc.nextLine());
              int numberOfShares = -1;
              try {
                numberOfShares = Integer.parseInt(quantity);
              } catch (NumberFormatException e) {
                displayPortfolio.displayMessage(out, "Invalid symbol or quantity "
                        + "provided\n");
                continue;
              }
              if (portfolioModel.createPortfolio(symbol, numberOfShares)) {
                displayPortfolio.displayMessage(out, "Enter 1 to continue "
                        + "and q to exit \n");
                loop = sc.nextLine();
              } else {
                displayPortfolio.displayMessage(out, "Invalid symbol or "
                        + "quantity provided\n");
              }
            }
            break;
          case 2:
            while (true) {
              if (!this.displayList()) {
                break;
              }
              int index = -1;
              displayPortfolio.displayMessage(out, "Enter portfolio index : \n");
              index = Integer.parseInt(sc.nextLine());
              if (index != 0) {
                this.displayFile(index);
              } else {
                break;
              }
            }
            break;
          case 3:
            while (true) {
              if (!this.displayList()) {
                break;
              }
              displayPortfolio.displayMessage(out, "Enter portfolio index :\n");
              int listIndex = Integer.parseInt(sc.nextLine());
              if (listIndex == 0) {
                break;
              }
              displayPortfolio.displayMessage(out, "Enter date for valuation in yyyy-mm-dd"
                      + " :\n");
              String date = sc.nextLine();
              this.getPortFolioValuation(listIndex, date);
            }
            break;
          case 4:
            displayPortfolio.displayMessage(out, "Enter path of loaded file :\n");
            String path = sc.nextLine();
            File file = portfolioModel.getFile(path);
            if (file == null) {
              displayPortfolio.printFileNotFound(out);
              break;
            }
            boolean result = this.getPath(file);
            if (result) {
              displayPortfolio.displayMessage(out, "File saved successfully\n");
            } else {
              displayPortfolio.displayMessage(out, "Unable to save the file." +
                      " Check the file format (Symbol,"
                      + " Quantity)\n");
            }
            displayPortfolio.displayMessage(out, "\n");
            break;
          case 5:
            displayPortfolio.displayMessage(out, "Program terminated successfully\n");
            break;
          default:
            displayPortfolio.displayMessage(out, "Invalid option\n");
            break;
        }
      } catch (Exception e) {
        displayPortfolio.displayMessage(out, "Invalid Input Provided\n");
      }
    }
    while (input < 5);
  }


  private void displayFile(int index) throws IOException {
    String functionType = "examine";
    List<List<String>> result = portfolioModel.getExamineComposition(index, functionType);
    displayPortfolio.displayExamineComposition(result, out);
  }


  private boolean displayList() throws IOException {

    Map<Integer, File> fileMap = portfolioModel.loadPortfolioFiles();

    if (fileMap.isEmpty()) {
      displayPortfolio.displayNoPortfolioFound(out);
      return false;
    } else {
      displayPortfolio.displayExamineFile(fileMap, out);
      return true;
    }
  }


  private boolean getPortFolioValuation(int listIndex, String date) throws IOException,
          ParseException {
    String functionType = "valuation";
    List<List<String>> result = portfolioModel.getExamineComposition(listIndex, functionType);
    if (result == null) {
      displayPortfolio.displayMessage(out, "Invalid Index Provided\n");
      return false;
    }
    if (portfolioModel.validateDate(date)) {
      result = portfolioModel.getUpdatedRecord(result, date);
      if (result == null) {
        displayPortfolio.displayMessage(out, "No data found for this date\n");
        return false;
      }
      String totalValuation = portfolioModel.getTotalValuation(result);
      displayPortfolio.displayValuation(result, totalValuation, out);
    } else {
      displayPortfolio.invalidDateError(date, out);
      return false;
    }
    return true;
  }

  private boolean getPath(File file) throws IOException {
    Map<String, Integer> map = portfolioModel.processFile(file);
    if (map.size() > 0) {
      portfolioModel.createFile();
      for (Map.Entry<String, Integer> entry : map.entrySet()) {
        portfolioModel.createPortfolio(entry.getKey(), entry.getValue());
      }
      return true;
    } else {
      return false;
    }
  }
}

