package view;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

import controller.SwingController;

/**
 * This interface represents view section of architecture.
 */
public interface SwingView {
  /**
   * This function creates the split panel vertically to place two panels.
   *
   * @param panel represents the panel to be added in the split view based on option selection.
   */
  void splitViewPanel(JPanel panel);

  /**
   * This function represents the callback methods from the view to be invoked from controller.
   *
   * @param swingController represents the controller that performs the operation based on action.
   */
  void addFeatures(SwingController swingController);

  /**
   * This function represents generic implementation to display message in dialog box.
   *
   * @param message messaged to be displayed.
   */
  void displayMessage(String message);

  /**
   * This function displays the cost basis of the portfolio in the specified format.
   *
   * @param costBasis costBasis of a portfolio.
   */
  void displayCostBasis(Double costBasis);

  /**
   * This function generates the cost basis panel by adding swing components.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   * @return panel consisting of components required for displaying cost basis.
   */
  JPanel costBasisPanel(JPanel panel);

  /**
   * This function populates the portfolios in the combo box along with date picker component.
   *
   * @return panel consisting of file picker and date picker.
   */
  JPanel displayPortfolios();

  /**
   * This function populates the files in the combobox.
   *
   * @param fileMap represents the key value pair of index and filename of the portfolios.
   */
  void fillComboBox(Map<Integer, File> fileMap);

  /**
   * This function generates panel for examining portfolio along with file picker and date picker.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   * @return panel consisting of components required for examining portfolio.
   */
  JPanel examinePanel(JPanel panel);

  /**
   * This function generates valuation panel for calculating total valuation of portoflio.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   * @return panel consisting of components required for examining portfolio.
   */
  JPanel valuationPanel(JPanel panel);

  /**
   * This function represents the view display for uploading the portfolio files.
   *
   * @return panel consisting of choose and upload with action associated with each button.
   */
  JPanel uploadPanel();

  /**
   * This function creates the panel for implementing dollar cost averaging in existing file.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   */
  void existingDollarAvg(JPanel panel);

  /**
   * This function display the examination result in the form of JTable.
   *
   * @param result represents the symbol, quantity of each stock in the portfolio.
   */
  void displayExamineComposition(List<List<String>> result);

  /**
   * This function display the valuation of the portfolio in the JTable.
   *
   * @param valuation represents the symbol, quantity, price for each stock
   * @param result    represents the cumulative valuation of the portfolio.
   */
  void displayTotalValuation(String valuation, List<List<String>> result);

  /**
   * This function display the result of the external portfolio upload.
   *
   * @param newFile represents the filename to be displayed to the user as result.
   */
  void displayPortfolioName(String newFile);

  /**
   * This function generates the panel for the buy shares functionality.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   */
  void buySharesPanel(JPanel panel);

  /**
   * This function generates the panel the sell share functionality.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   */
  void sellSharesPanel(JPanel panel);

  /**
   * This function is used to reset the field of the panel such as JLabel, JText.
   */
  void clearFields();

  /**
   * This function is used to clear the inputs of examine portfolio panel.
   */
  void clearFieldsForExamine();

  /**
   * This function generates panel for the create portfolio function with symbol, quantity, fees.
   *
   * @return panel consisting of labels and input fields required for creation of portoflio.
   */
  JPanel createPortfolioPanel();

  /**
   * This function displays the created portfolio filename to the user.
   *
   * @param filename represents the file generated onclick of the create portfolio button.
   */
  void displayCreatedPortfolio(String filename);

  /**
   * This function generates panel for displaying performance of the graph between two dates.
   *
   * @param panel represents the generic panel consisting of file picker and date picker.
   * @return panel consisting of date pickers and button associated with generating graph action.
   */
  JPanel displayPerformancePanel(JPanel panel);

  /**
   * This function generates the view of the graph where x-axis represents the dates and y-axis
   * represents the normalized score of the portfolio between 0 and 50.
   *
   * @param xData        represents the scaled intervals calculated between two dates.
   * @param yData        represents the normalized portfolio value between 0 and 50 for
   *                     each interval.
   * @param intervalSize represents the scales of the interval.
   */
  void displayPerformanceGraph(List<String> xData, List<String> yData, List<Double> intervalSize);

  /**
   * This function generates view for fixed investment that add stock until weight adds to 100.
   */
  void displayFixedInvestmentMenu();

  /**
   * This function represents the generic view for investment into new portfolio.
   */
  void createNewInvestmentMenu();

  /**
   * This function represents the generic view for investment into existing portfolio.
   *
   * @param panel represents the generic display panel that consist of file picker and date picker.
   */
  void investInExistingMenu(JPanel panel);

  /**
   * This function resets the field in the performance graph after graph processing is completed.
   */
  void clearPerformanceFields();

  /**
   * This function resets the field in the fixed investment strategy after the result is completed.
   */
  void clearFixedStrategyFields();

  /**
   * This function resets the view after the stock validation and addition is successful.
   */
  void clearAddStockFields();

  /**
   * This function resets the view panel for portfolio and date selection
   * in re-balance portfolio choice.
   */
  void clearSelectPortfolioFields();

  /**
   * This function resets the view panel for re-balance portfolio fields.
   */
  void clearRebalanceFields();

  /**
   * A method to show a panel containing portfolio selection option and
   * date selection option.
   * @param panel a new panel to represent select portfolio and select date options
   */
  void selectPortfolioPanel(JPanel panel);

  /**
   * A method to display the re-balance portfolio panel,
   * containing fields to take inputs for ticker, share and commission fees.
   * @param panel containing the mentioned fields
   */
  void displayRebalancePanel(JPanel panel);

  /**
   * A method to let controller send the display contents to the view.
   * @param str String array of the contents
   */
  void setControllerStuff(String[] str);

}
