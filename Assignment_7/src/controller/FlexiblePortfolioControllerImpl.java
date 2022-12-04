package controller;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.FlexiblePortfolio;
import view.DisplayPortfolio;

/**
 * {@code FlexiblePortfolioControllerImpl} implements {@code PortfolioController} Interface.
 * This class has the driver method to run the menu operation as per user input.
 * The menu inside the driver delegates the operations to the flexible portfolio model and the view.
 */
public class FlexiblePortfolioControllerImpl implements PortfolioController {
  private final FlexiblePortfolio flexiblePortfolio;
  private final DisplayPortfolio displayPortfolio;


  private final Readable in;
  private final Appendable out;

  /**
   * Constructs the FlexiblePortfolioControllerImpl with the input, output, model and view.
   * The methods from model and view are then used by the controller to perform the operation.
   * for example - examining portfolio will load the files in the database using the method from
   * model and view will perform the duty to display the files.
   *
   * @param in                represents the sequence of characters. This uses Readable interface
   *                          which acts as Readable source to the Scanner class.
   * @param out               represents the output log to be printed on the console.
   *                          This uses Appendable which appends sequence of characters to object
   * @param flexiblePortfolio represents the model interface of the flexiportfolio.
   * @param displayPortfolio  represents the view interface of the portfolio.
   */
  public FlexiblePortfolioControllerImpl(Readable in, Appendable out,
                                         FlexiblePortfolio flexiblePortfolio,
                                         DisplayPortfolio displayPortfolio) {
    this.in = in;
    this.out = out;
    this.flexiblePortfolio = flexiblePortfolio;
    this.displayPortfolio = displayPortfolio;
  }

  @Override
  public void startProgram() throws IOException {
    int input = 0;
    Scanner sc = new Scanner(this.in);
    do {
      try {
        displayPortfolio.displayFlexibleMenu(out);
        input = Integer.parseInt(sc.nextLine());
        if (input < 0) {
          displayPortfolio.displayMessage(out, "Negative Number Provided");
          break;
        }
        switch (input) {
          case 1: {
            String filename = flexiblePortfolio.createFlexiblePortfolio();
            displayPortfolio.displayMessage(out, "Portfolio " + filename
                    + " created Successfully\n");
            int choice = -1;
            do {
              try {
                displayPortfolio.displayMessage(out, "Enter\n1. Buy Shares\n"
                        + "2. Sell Shares\n" + "3. Exit\n");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                  case 1:
                    buySellHelper(sc, filename, 1);
                    break;
                  case 2:
                    buySellHelper(sc, filename, 2);
                    break;
                  case 3:
                    break;
                  default:
                    displayPortfolio.displayMessage(out, "Invalid Choice\n");
                    break;
                }
              } catch (Exception e) {
                displayPortfolio.displayMessage(out, "Invalid Input Provided\n");
              }
            }
            while (choice != 3);
          }
          break;
          case 2:
            menuHelper(sc, 'e');
            break;
          case 3:
            menuHelper(sc, 'b');
            break;
          case 4:
            menuHelper(sc, 's');
            break;
          case 5:
            menuHelper(sc, 'c');
            break;
          case 6:
            menuHelper(sc, 'v');
            break;
          case 7:
            menuHelper(sc, 'g');
            break;
          case 8:
            displayPortfolio.displayMessage(out, "Enter path of loaded file :\n");
            String path = sc.nextLine();
            File file = flexiblePortfolio.getFile(path);
            if (file == null) {
              displayPortfolio.printFileNotFound(out);
              continue;
            }
            String newfile = flexiblePortfolio.createFlexiblePortfolio();
            if (flexiblePortfolio.uploadFlexiblePortfolio(path, newfile)) {
              displayPortfolio.displayMessage(out, "File " + newfile + " "
                      + "successfully uploaded \n\n");
            } else {
              displayPortfolio.displayMessage(out, "Error in uploading File\n\n");
            }
            break;
          case 9:
            break;
          default:
            displayPortfolio.displayMessage(out, "Invalid option\n");
            break;
        }
      } catch (Exception e) {
        displayPortfolio.displayMessage(out, "Invalid Input Provided\n");
      }
    }
    while (input != 9);

  }

  private boolean displayList() throws IOException {

    Map<Integer, File> fileMap = flexiblePortfolio.loadPortfolioFiles();

    if (fileMap.isEmpty()) {
      displayPortfolio.displayNoPortfolioFound(out);
      return false;
    } else {
      displayPortfolio.displayExamineFile(fileMap, out);
      return true;
    }
  }

  private String getFilename(int index) {
    Map<Integer, File> fileMap = flexiblePortfolio.loadPortfolioFiles();
    if (fileMap.containsKey(index)) {
      return fileMap.get(index).getName();
    } else {
      return null;
    }
  }

  private void buySellHelper(Scanner sc, String filename, int choice)
          throws IOException, ParseException, java.text.ParseException, NoSuchFieldException {
    displayPortfolio.displayMessage(out, "Enter Symbol\n");
    String symbol = sc.nextLine();
    if (!flexiblePortfolio.validateSymbol(symbol)) {
      displayPortfolio.displayMessage(out, "Invalid ticker symbol\n");
      return;
    }
    displayPortfolio.displayMessage(out, "Enter Quantity\n");
    int quantity = 0;
    try {
      quantity = Integer.parseInt(sc.nextLine());
      if (quantity < 0) {
        displayPortfolio.displayMessage(out, "Invalid quantity provided\n");
        return;
      }
    } catch (Exception e) {
      displayPortfolio.displayMessage(out, "Invalid quantity provided.\n");
      return;
    }
    displayPortfolio.displayMessage(out, "Enter Date\n");
    String date = sc.nextLine();
    if (!flexiblePortfolio.validateDate(date)) {
      displayPortfolio.displayMessage(out, "Invalid Date format.\n");
      return;
    }
    displayPortfolio.displayMessage(out, "Enter Commission Fee (Minimum Fee $1"
            + " and Maximum Fee $20)\n");
    Double commission = Double.parseDouble(sc.nextLine());
    if (commission < 1 || commission > 20) {
      displayPortfolio.displayMessage(out, "Invalid Commission fee.\n");
      return;
    }
    if (choice == 1) {
      if (flexiblePortfolio.buyShares(symbol, quantity, date, commission, filename)) {
        displayPortfolio.displayMessage(out, "Successfully bought shares for symbol "
                + symbol + "\n");
      } else {
        displayPortfolio.displayMessage(out, "Unable to buy shares for symbol "
                + symbol + "\n");
      }
    } else if (choice == 2) {
      if (flexiblePortfolio.sellShares(symbol, quantity, date, commission, filename)) {
        displayPortfolio.displayMessage(out, "Successfully sold shares for symbol "
                + symbol + "\n");
      } else {
        displayPortfolio.displayMessage(out, "Unable to sell shares for symbol "
                + symbol + "\n");
      }
    }

  }

  private void fixedInvestmentMenuHelper(String fileName, Scanner sc) throws IOException,
          ParseException, java.text.ParseException {
    List<String> symbolList = new ArrayList<>();
    List<Double> weightList = new ArrayList<>();
    displayPortfolio.displayMessage(out, "\nEnter the amount to invest\n");
    double amount = Double.parseDouble(sc.nextLine());
    displayPortfolio.displayMessage(out, "Enter the commission fee (Minimum Fee $1" +
            " and Maximum Fee $20)\n");
    double commissionFee = Double.parseDouble(sc.nextLine());
    if (commissionFee < 1 || commissionFee > 20) {
      displayPortfolio.displayMessage(out, "Invalid Commission fee.\n");
      return;
    }
    displayPortfolio.displayMessage(out, "Enter the date\n");
    String date = sc.nextLine();
    if (flexiblePortfolio.validateDate(date)) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date today = new Date();
      if (df.parse(date).after(df.parse(df.format(today)))) {
        displayPortfolio.displayMessage(out, "\nFuture date entered."
                + " Please enter the valid date.\n\n");
      } else {
        double remainingWeight = 100.0;
        do {
          displayPortfolio.displayMessage(out, "Remaining weights : " + remainingWeight
                  + "\n");
          displayPortfolio.displayMessage(out, "Enter stock symbol\n");
          String symbol = sc.nextLine();
          if (!flexiblePortfolio.validateSymbol(symbol)) {
            displayPortfolio.displayMessage(out, "Invalid ticker symbol\n");
            break;
          }
          symbolList.add(symbol);
          displayPortfolio.displayMessage(out, "Enter weight for " + symbol + "\n");
          double weight = Double.parseDouble(sc.nextLine());
          weightList.add(weight);
          remainingWeight -= weight;
        }
        while (remainingWeight > 0);

        if (remainingWeight == 0) {
          boolean result = true;
          for (int i = 0; i < symbolList.size(); i++) {
            result &= flexiblePortfolio.fixedInvestmentStrategy(amount, weightList.get(i),
                    symbolList.get(i), date, commissionFee, fileName);
          }
          if (result) {
            displayPortfolio.displayMessage(out, "Successfully invested " + amount +
                    " in " + fileName + "\n");
          } else {
            displayPortfolio.displayMessage(out, "Unable to invest " + amount + " in " +
                    "" + fileName + "\n");
          }
        } else {
          displayPortfolio.displayMessage(out, "Invalid operation\n");
        }
      }

    } else {
      displayPortfolio.displayMessage(out, "Invalid Date\n");
    }

  }

  private void menuHelper(Scanner sc, char c) throws IOException,
          ParseException, java.text.ParseException, NoSuchFieldException {
    String date = "";
    while (true) {
      if (!this.displayList()) {
        break;
      }
      int index;
      displayPortfolio.displayMessage(out, "Enter portfolio index : \n");
      try {
        index = Integer.parseInt(sc.nextLine());
      } catch (Exception e) {
        displayPortfolio.displayMessage(out, "Invalid input\n");
        continue;
      }
      if (index != 0) {
        String fileName = getFilename(index);
        if (fileName == null) {
          displayPortfolio.displayMessage(out, "\nFile not found for the index."
                  + " Please provide correct input.\n\n");
          continue;
        }
        switch (c) {
          case 'e': {
            displayPortfolio.displayMessage(out, "Enter date for examination : \n");
            date = sc.nextLine();
            if (flexiblePortfolio.validateDate(date)) {
              DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              Date today = new Date();
              if (df.parse(date).after(df.parse(df.format(today)))) {
                displayPortfolio.displayMessage(out, "\nFuture date entered."
                        + " Please enter the valid date.\n\n");
              } else {
                List<List<String>> result =
                        flexiblePortfolio.getFlexiblePortfolioComposition(fileName, date);
                if (result == null) {
                  displayPortfolio.displayMessage(out, "\nFile is empty. "
                          + "Please Buy/Sell to view the composition.\n\n");
                } else {
                  displayPortfolio.displayExamineComposition(result, out);
                }
              }
            } else {
              displayPortfolio.displayMessage(out, "Invalid date format\n");
            }
          }
          break;
          case 'b':
            buySellHelper(sc, fileName, 1);
            break;
          case 's':
            buySellHelper(sc, fileName, 2);
            break;
          case 'c': {
            displayPortfolio.displayMessage(out, "Enter cost basis date: \n");
            date = sc.nextLine();
            if (flexiblePortfolio.validateDate(date)) {
              DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              df.parse(date);
              Date today = new Date();
              if (df.parse(date).after(df.parse(df.format(today)))) {
                displayPortfolio.displayMessage(out, "\nFuture date entered."
                        + " Please enter the valid date.\n\n");
              } else {
                double composition = flexiblePortfolio.calculateCostBasis(fileName, date);
                if (composition == -1) {
                  displayPortfolio.displayMessage(out, "\nFile is empty. "
                          + "Please Buy/Sell to view the cost basis.\n\n");
                } else {
                  displayPortfolio.displayMessage(out, "Cost Basis for Portfolio "
                          + fileName + " is $" + String.format("%.2f", composition) + "\n");
                }
              }
            } else {
              displayPortfolio.displayMessage(out, "Invalid date format\n");
            }
          }
          break;
          case 'v': {
            displayPortfolio.displayMessage(out, "Enter total valuation date (yyyy-mm-dd) "
                    + ": \n");
            date = sc.nextLine();
            if (flexiblePortfolio.validateDate(date)) {
              List<List<String>> res =
                      flexiblePortfolio.getFlexiblePortfolioValuation(fileName, date);
              if (res == null) {
                displayPortfolio.displayMessage(out, "\nData not present for the "
                        + "given Date\n\n");
              } else {
                String totalValue = flexiblePortfolio.getFlexiblePortfolioTotalValuation(res);
                if (totalValue.equals("")) {
                  displayPortfolio.displayMessage(out, "Data not found for the given date "
                          + date + "\n");
                  break;
                }
                displayPortfolio.displayValuation(res, totalValue, out);
              }
            } else {
              displayPortfolio.displayMessage(out, "Invalid date format\n");
            }
          }
          break;
          case 'g': {
            displayPortfolio.displayMessage(out, "Enter Start Date for Graph (yyyy-mm-dd) "
                    + ": \n");
            String startDate = sc.nextLine();
            displayPortfolio.displayMessage(out, "Enter End Date for Graph (yyyy-mm-dd) : "
                    + "\n");
            String endDate = sc.nextLine();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date inputStartDate = df.parse(startDate);
            Date inputEndDate = df.parse(endDate);
            if (inputStartDate.before(inputEndDate)) {
              if (flexiblePortfolio.validateDate(startDate) &&
                      flexiblePortfolio.validateDate(endDate)) {
                List<String> result = flexiblePortfolio.generateAndDisplayStockGraph(startDate,
                        endDate, fileName);
                if (result == null) {
                  displayPortfolio.displayMessage(out, "\nFile is empty. "
                          + "Please Buy/Sell to view the performance graph.\n\n");
                } else {
                  List<String> asterisks = flexiblePortfolio.generateAsterisk(result);
                  List<Double> intervalSize = flexiblePortfolio.getMinMaxPrice(result);
                  displayPortfolio.displayMessage(out, "Performance of portfolio "
                          + fileName + " from "
                          + startDate + " to " +
                          "" + endDate + "\n\n");
                  displayPortfolio.displayGraph(out, asterisks);
                  if (intervalSize.get(0).equals(intervalSize.get(2))) {
                    displayPortfolio.displayMessage(out, "Scale: * = $"
                            + intervalSize.get(0) + "\n\n");
                  } else {
                    displayPortfolio.displayMessage(out, "Base: $"
                            + intervalSize.get(0) +
                            " interval $" + intervalSize.get(2) + "\n\n");
                  }
                }
              } else {
                displayPortfolio.displayMessage(out, "Invalid date format\n");
              }
            } else {
              displayPortfolio.displayMessage(out, "Start date cannot be greater "
                      + "than End date\n");
            }

          }
          break;
          case 'f':
            fixedInvestmentMenuHelper(fileName, sc);
            break;
          default:
            break;
        }
      } else {
        return;
      }
    }

  }
}


