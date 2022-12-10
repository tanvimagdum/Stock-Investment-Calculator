package view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@code DisplayPortfolioImpl} represents the implementation of the DisplayPortfolio view.
 * This view implements method to display the message or menu on the console by using
 * Appendable interface.
 */
public class DisplayPortfolioImpl implements DisplayPortfolio {
  @Override
  public void displayExamineFile(Map<Integer, File> fileMap, Appendable out) throws IOException {
    if (fileMap.isEmpty()) {
      out.append("No portfolios found. Please create a new portfolio");
      out.append("\n");
      return;
    }
    out.append("Press : " + 0 + " to exit\n");
    for (Map.Entry<Integer, File> entry : fileMap.entrySet()) {
      String fileName = entry.getValue().getName();
      out.append("Press : " + entry.getKey() + " to examine : " + fileName + "\n");
    }
  }

  @Override
  public void displayExamineComposition(List<List<String>> result, Appendable out)
          throws IOException {
    if (result == null) {
      out.append("Invalid File Index\n");
    } else {
      out.append("*******************************************\n");
      out.append(String.format("%5s%20s", "Symbol", "Quantity"));
      for (List<String> examine : result) {
        out.append("\n");
        out.append(String.format("%5s%20s", examine.get(0), examine.get(1)));
      }
      out.append("\n");
      out.append("*******************************************\n");
      out.append("\n");
    }
  }

  @Override
  public void displayValuation(List<List<String>> result, String totalValuation, Appendable out)
          throws IOException {
    if (result == null) {
      out.append("Data for this date doesn't exist.\n");
    } else {
      out.append("**********************************************************************" +
              "*************************************************\n");
      out.append(String.format("%5s%20s%20s%20s%20s\n", "Symbol", "Quantity", "Date", "Price($)",
              "Value($)"));
      for (List<String> examine : result) {
        out.append("\n");
        out.append(String.format("%5s%20s%20s%20s%20s\n",
                examine.get(0), examine.get(1), examine.get(2), examine.get(3), examine.get(4)));
      }
      out.append("\n");
      out.append("Total Valuation of the Portfolio in Dollars : " + totalValuation + "\n");
      out.append("**********************************************************************" +
              "*************************************************\n");
      out.append("\n");
    }
  }

  @Override
  public void displayMenu(Appendable out) throws IOException {
    out.append("Select From Below Options\n"
            + "1. Create Portfolio\n"
            + "2. Examine Portfolio\n"
            + "3. Get Valuation of Portfolio\n"
            + "4. Load Portfolio\n"
            + "5. Exit\n\n"
            + "Please Enter Choice\n");
  }

  @Override
  public void invalidDateError(String date, Appendable out) throws IOException {
    out.append("Invalid date provided " + date + "\n");
  }

  @Override
  public void printFileNotFound(Appendable out) throws IOException {
    out.append("*******************************************************************\n");
    out.append(String.format("%5s", "\nFile not found at the location." +
            " Please enter correct file path\n"));
    out.append("\n*******************************************************************\n");
  }

  @Override
  public void displayNoPortfolioFound(Appendable out) throws IOException {
    out.append("*******************************************************************\n");
    out.append(String.format("%5s", "\nNo Portfolio Found. Please create new portfolio."));
    out.append("\n*******************************************************************\n");
  }

  @Override
  public void displayMessage(Appendable out, String s) throws IOException {
    out.append(s);
  }

  @Override
  public void displayGraph(Appendable out, List<String> res) throws IOException {
    for (String str : res) {
      out.append(str.split(":")[0] + ": ");
      out.append("*".repeat(Integer.parseInt(str.split(":")[1].trim())));
      out.append("\n");
    }
  }

  @Override
  public void displayFlexibleMenu(Appendable out) throws IOException {
    out.append("Select From Below Options\n"
            + "1. Create Portfolio\n"
            + "2. Examine Portfolios\n"
            + "3. Buy Shares\n"
            + "4  Sell Shares\n"
            + "5  Get Cost Basis\n"
            + "6  Get Total Valuation\n"
            + "7. Display Flexible Portfolio Graph\n"
            + "8. Upload Flexible Portfolio\n"
            + "9. Rebalance a Flexible Portfolio\n"
            + "10. Exit\n\n"
            + "Please Enter Choice\n");
  }

  @Override
  public void displayMainMessage(Appendable out) throws IOException {
    out.append("Please enter valid input\n"
            + "1.Flexible Portfolio\n"
            + "2.Fixed Portfolio\n"
            + "3.Exit\n");
  }
}
