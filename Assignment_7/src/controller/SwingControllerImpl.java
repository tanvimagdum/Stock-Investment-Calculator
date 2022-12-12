package controller;

import model.APIData;
import model.VantageAPIData;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JPanel;
import model.FlexiblePortfolio;
import view.SwingView;

/**
 * This class represent the implementation of the SwingController.
 * This class maintains the stock list for the multiple stock addition operation.
 * This controller consist of call back methods that view use to fetch the result based on actions.
 * This controller performs operations such as buying stock, selling stock, examine portfolio,
 * get portfolio valuation, performing investment strategy such as dollar cost averaging as well
 * as fixed investment strategy, generating graph as uploading external flexible portfolio.
 */
public class SwingControllerImpl implements SwingController {
  private FlexiblePortfolio model;
  private SwingView view;

  private List<String> stockList;
  private String[] viewStuff;

  /**
   * This constructs the SwingControllerImplementation class.
   * In addition to the injection of FlexiblePortfolio and SwingView, it also initialized call back
   * method addFeature of the view.
   *
   * @param flexiblePortfolio represents the object of the FlexiblePortfolio.
   * @param view              represents the view represented by SwingView.
   */
  public SwingControllerImpl(FlexiblePortfolio flexiblePortfolio, SwingView view) {
    this.model = flexiblePortfolio;
    this.view = view;
    this.view.addFeatures(this);
    stockList = new ArrayList<>();
  }


  @Override
  public JPanel getPortfolioList() {
    JPanel panel = new JPanel();
    Map<Integer, File> fileMap = model.loadPortfolioFiles();
    if (fileMap.isEmpty()) {
      view.displayMessage("No files present");
    } else {
      panel = view.displayPortfolios();
      view.fillComboBox(fileMap);
    }
    return panel;
  }

  @Override
  public void examinePortfolio() {
    view.clearFieldsForExamine();
    JPanel panel = getPortfolioList();
    view.examinePanel(panel);
    view.splitViewPanel(panel);

  }

  @Override
  public void costBasis() {
    view.clearFieldsForExamine();
    JPanel panel = getPortfolioList();
    view.costBasisPanel(panel);
    view.splitViewPanel(panel);
  }

  @Override
  public void valuation() {
    view.clearFieldsForExamine();
    JPanel panel = getPortfolioList();
    view.valuationPanel(panel);
    view.splitViewPanel(panel);

  }

  @Override
  public void uploadPortfolio() {
    JPanel panel = view.uploadPanel();
    view.splitViewPanel(panel);
  }

  @Override
  public void buyShares() {
    view.clearFields();
    JPanel panel = getPortfolioList();
    view.buySharesPanel(panel);
    view.splitViewPanel(panel);

  }

  @Override
  public void sellShares() {
    view.clearFields();
    JPanel panel = getPortfolioList();
    view.sellSharesPanel(panel);
    view.splitViewPanel(panel);

  }

  @Override
  public void createPortfolio() {
    view.clearFields();
    JPanel panel = view.createPortfolioPanel();
    view.splitViewPanel(panel);
  }

  @Override
  public void sellStock(String symbol, String quantity, String date, String commissionFee,
                        String filename) {
    try {
      if (!validateData(symbol, commissionFee, quantity, date, filename)) {
        return;
      }
      if (model.sellShares(symbol, Integer.parseInt(quantity), date,
              Double.parseDouble(commissionFee), filename)) {
        view.displayMessage(symbol + " " + quantity + " sold successfully");
      } else {
        view.displayMessage("Unable to sell " + symbol + " " + quantity);
      }
      view.clearFields();
    } catch (ParseException | IOException | NoSuchFieldException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }

  }


  @Override
  public String getNewPortfolio() {
    String fileName = null;
    stockList.clear();
    try {
      String filename = model.createFlexiblePortfolio();
      view.displayCreatedPortfolio(filename);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return fileName;
  }

  @Override
  public void addNewStocks(String symbol, String quantity, String selectedDate,
                           String commissionFee, String newPortfolio) {
    try {
      if (validateData(symbol, commissionFee, quantity, selectedDate, newPortfolio)) {
        addStock(symbol, quantity, selectedDate, commissionFee, newPortfolio);
      }
    } catch (IOException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void getPortfolioPerformance() {
    view.clearPerformanceFields();
    JPanel panel = getPortfolioList();
    view.displayPerformancePanel(panel);
    view.splitViewPanel(panel);
  }

  @Override
  public void getPortfolioPerformanceData(String portfolioName, String selectedDate,
                                          String selectedEndDate) throws ParseException,
          java.text.ParseException, IOException {
    if (portfolioName == null || portfolioName.equals("None") || portfolioName.equals("")
            || isInvalidDate(selectedDate) || isInvalidDate(selectedEndDate) ||
            !isValidInterval(selectedDate, selectedEndDate)) {
      view.displayMessage("Invalid Data provided");
      view.clearPerformanceFields();
    } else {
      List<String> result = model.generateAndDisplayStockGraph(selectedDate, selectedEndDate,
              portfolioName);
      List<String> xData = new ArrayList<>();
      List<String> yData = new ArrayList<>();
      List<String> asteriskResult = model.generateAsterisk(result);
      for (String s : asteriskResult) {
        String xValue = s.split(":")[0].trim();
        String yValue = s.split(":")[1].trim();
        xData.add(xValue);
        yData.add(yValue);
      }
      String scale = "";
      List<Double> intervalSize = model.getMinMaxPrice(result);
      view.displayPerformanceGraph(xData, yData, intervalSize);
    }
  }

  @Override
  public void saveStocks(String portfolioName, String amount, String symbol, String weight,
                         String selectedDate, String commissionText) {
    stockList.add(portfolioName + ":" + amount + ":" + symbol + ":" + weight + ":" +
            selectedDate + ":" + commissionText);
  }

  @Override
  public void investStocks() throws IOException, ParseException, java.text.ParseException {

    boolean result = true;
    double amount = 0;
    String fileName = "";

    for (int i = 0; i < stockList.size(); i++) {
      String[] stocks = stockList.get(i).split(":");

      amount = Double.parseDouble(stocks[1]);
      double weight = Double.parseDouble(stocks[3]);
      double commissionFee = Double.parseDouble(stocks[5]);
      String symbol = stocks[2];
      String date = stocks[4];
      fileName = stocks[0];
      result &= model.fixedInvestmentStrategy(amount, weight, symbol, date,
              commissionFee, fileName);
    }

    if (result) {
      view.displayMessage("Invested $" + amount + " in " + fileName);
      view.clearFixedStrategyFields();
    } else {
      view.displayMessage("Unable to invest $" + amount + " in " + fileName);
      view.clearFixedStrategyFields();
    }

  }

  @Override
  public void investDollarCost(int interval, String endDate)
          throws IOException, ParseException {
    boolean result = true;
    double amount = 0;
    List<Double> stockWeights = new ArrayList<>();
    List<String> stockSymbols = new ArrayList<>();
    List<Double> stockCommission = new ArrayList<>();
    String fileName = "";
    String date = "";
    for (int i = 0; i < stockList.size(); i++) {
      String[] stocks = stockList.get(i).split(":");

      amount = Double.parseDouble(stocks[1]);
      double weight = Double.parseDouble(stocks[3]);
      double commissionFee = Double.parseDouble(stocks[5]);
      String symbol = stocks[2];
      stockSymbols.add(symbol);
      stockWeights.add(weight);
      stockCommission.add(commissionFee);
      date = stocks[4];
      fileName = stocks[0];
      try {
        result &= model.dollarCostAveraging(amount, weight, symbol, date, interval, endDate,
                commissionFee, fileName);
      } catch (IOException | ParseException | java.text.ParseException e) {
        throw new RuntimeException(e);
      }
    }

    if (result) {
      view.displayMessage("Invested $" + amount + " in " + fileName);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String currentDate = sdf.format(new Date());
      model.writeToStrategy(amount, stockWeights, stockSymbols, currentDate, interval,
              endDate, stockCommission, fileName);
      view.clearFixedStrategyFields();
    } else {
      view.displayMessage("Unable to invest $" + amount + " in " + fileName);
      view.clearFixedStrategyFields();
    }

  }

  @Override
  public void investInExistingPortfolio() {
    JPanel panel = getPortfolioList();
    view.clearFixedStrategyFields();
    view.investInExistingMenu(panel);
    view.splitViewPanel(panel);
  }

  @Override
  public boolean validateStock(String portfolioName, String amountText,
                               String symbolText, String weightText,
                               String selectedDate, String commissionText)
          throws IOException {
    if (isInValidPortfolio(portfolioName)) {
      view.displayMessage("No portfolio selected. Please select portfolio");
      return false;
    }

    if (isInvalidDate(selectedDate)) {
      view.displayMessage("Invalid Date. Please Select Date");
      return false;
    }

    if (isInValidAmount(amountText)) {
      view.displayMessage("Please enter correct amount > 0");
      return false;
    }

    if (isInvalidSymbol(symbolText)) {
      view.displayMessage("Invalid Ticker Symbol");
      return false;
    }


    double weight = parseDoubleValue(weightText);

    if (weight <= 0) {
      view.displayMessage("Please enter positive weight");
      return false;
    } else if (weight > 100) {
      view.displayMessage("Weight cannot be greater than 100");
      return false;
    }

    double commission = parseDoubleValue(commissionText);

    if (commission < 1 || commission > 20) {
      view.displayMessage("Invalid commission. Please enter commission between 1 and 20");
      return false;
    }

    return true;
  }

  @Override
  public void createNewInvestmentMenu() {
    view.clearFixedStrategyFields();
    view.createNewInvestmentMenu();
  }

  private boolean isInValidPortfolio(String portfolioName) {
    return portfolioName.equals("") || portfolioName.equals("None") || portfolioName == null;
  }

  private boolean isValidInterval(String startDate, String endDate)
          throws java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return (sdf.parse(startDate).before(sdf.parse(endDate)));
  }

  private boolean isHoliday(String selectedDate) throws java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date weekend = sdf.parse(selectedDate);
    Calendar cal = Calendar.getInstance();
    cal.setTime(weekend);
    int day = cal.get(Calendar.DAY_OF_WEEK);
    return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
  }

  private boolean isInvalidDate(String selectedDate) {
    String date = selectedDate == null ? "" : selectedDate;
    return !model.validateDate(date);
  }

  private boolean isInValidAmount(String amountText) {
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(amountText);
    } catch (Exception e) {
      amount = 0.0;
    }
    return amount <= 0;
  }

  private boolean isInvalidSymbol(String symbol) throws IOException {
    return !model.validateSymbol(symbol);
  }

  private Double parseDoubleValue(String weightText) {
    Double weight = 0.0;
    try {
      weight = Double.parseDouble(weightText);
    } catch (Exception e) {
      weight = 0.0;
    }
    return weight;
  }


  @Override
  public void existingDollarCostAvgPanel() {
    JPanel panel = getPortfolioList();
    view.clearFixedStrategyFields();
    view.existingDollarAvg(panel);
    view.splitViewPanel(panel);
  }

  @Override
  public void getCostBasis(String fileName, String date) {
    if (!validateDataForExamine(fileName, date)) {
      view.clearFieldsForExamine();
      return;
    }
    try {
      double costBasis = model.calculateCostBasis(fileName, date);
      view.displayCostBasis(costBasis);

    } catch (ParseException | IOException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void getValuation(String fileName, String date) {
    if (!validateDataForExamine(fileName, date)) {
      view.clearFieldsForExamine();
      return;
    }
    try {
      List<List<String>> res = model.getFlexiblePortfolioValuation(fileName, date);
      String valuation = model.getTotalValuation(res);
      view.displayTotalValuation(valuation, res);

    } catch (ParseException | IOException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examinePortfolioList(String fileName, String date) {
    if (!validateDataForExamine(fileName, date)) {
      view.clearFieldsForExamine();
      return;
    }
    List<List<String>> result = null;
    try {
      result = model.getFlexiblePortfolioComposition(fileName, date);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    if (result == null) {
      view.displayMessage("File is empty. "
              + "Please Buy/Sell to view the composition.");
    } else {
      view.displayExamineComposition(result);
    }
  }

  private boolean validateDataForExamine(String fileName, String date) {
    if (fileName == null || fileName.equals("None") || fileName.equals("")) {
      view.displayMessage("Please Select Portfolio");
      view.clearFieldsForExamine();
      return false;
    }
    if (date == null || date.equals("")) {
      view.displayMessage("Please Enter Date");
      view.clearFieldsForExamine();
      return false;
    }
    return true;
  }

  @Override
  public void getUploadedPortfolio(String path) {
    File file = model.getFile(path);
    if (file == null) {
      view.displayMessage("Incorrect file uploaded");
      return;
    }

    try {
      String newFile = model.createFlexiblePortfolio();
      model.uploadFlexiblePortfolio(path, newFile);
      view.displayPortfolioName(newFile);

    } catch (IOException | ParseException | java.text.ParseException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public void addStock(String symbol, String quantity, String date, String commissionFee,
                       String filename) {
    try {
      if (!validateData(symbol, commissionFee, quantity, date, filename)) {
        return;
      }
      if (model.buyShares(symbol, Integer.parseInt(quantity), date,
              Double.parseDouble(commissionFee), filename)) {
        view.displayMessage(symbol + " " + quantity + " bought successfully");
      } else {
        view.displayMessage("Unable to buy " + symbol + " " + quantity);
      }
      view.clearAddStockFields();
    } catch (ParseException | IOException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }

  }

  private boolean validateData(String symbol, String commissionFee, String quantity,
                               String date, String filename)
          throws IOException, java.text.ParseException {
    if (filename == null || filename.equals("None") || filename.equals("")) {
      view.displayMessage("No Portfolio selected/created");
      view.clearFields();
      return false;
    }
    if (date == null || date.equals("") || isHoliday(date)) {
      view.displayMessage("Please Select Valid Date");
      view.clearFields();
      return false;
    }
    if (!model.validateSymbol(symbol)) {
      view.displayMessage("Invalid Symbol");
      view.clearFields();
      return false;
    }
    try {
      int result = Integer.parseInt(quantity);
      if (result < 0) {
        view.displayMessage("Invalid Quantity provided");
        view.clearFields();
        return false;
      }
    } catch (NumberFormatException e) {
      view.displayMessage("Invalid Quantity provided");
      view.clearFields();
      return false;
    }
    if (Double.parseDouble(commissionFee) < 1 || Double.parseDouble(commissionFee) > 20) {
      view.displayMessage("Invalid Commission Fee Entered");
      view.clearFields();
      return false;
    }
    return true;
  }


  @Override
  public void rebalancePortfolioChoice() {
    view.clearSelectPortfolioFields();
    view.clearRebalanceFields();
    JPanel panel = getPortfolioList();
    view.selectPortfolioPanel(panel);
    view.splitViewPanel(panel);
  }

  @Override
  public void validatePortfolio(String portfolioName, String selectedDate)
          throws ParseException, java.text.ParseException, IOException {
    if (!validateDataForSelectPortfolio(portfolioName, selectedDate)) {
      view.clearSelectPortfolioFields();
      return;
    }

    List<List<String>> contents =
        model.getFlexiblePortfolioComposition(portfolioName, selectedDate);
    String[] allTickers = new String[contents.size()];

    for (int i = 0; i < contents.size(); i++) {
      allTickers[i] = contents.get(i).get(0);
    }

    //get unique tickers
    ArrayList<String> tempTickers = new ArrayList<>();
    for (int i = 0; i < allTickers.length; i++) {
      if (!tempTickers.contains(allTickers[i])) {
        tempTickers.add(allTickers[i]);
      }
    }

    String[] tickers = new String[tempTickers.size()];
    for (int i = 0; i < tempTickers.size(); i++) {
      tickers[i] = tempTickers.get(i);
    }

    viewStuff = new String[2];
    viewStuff[0] = portfolioName;
    viewStuff[1] = selectedDate;
    view.setControllerStuff(tickers);

    JPanel panel = new JPanel();
    view.displayRebalancePanel(panel);
    view.splitViewPanel(panel);
  }

  private boolean validateDataForSelectPortfolio(String fileName, String date) {
    if (fileName == null || fileName.equals("None") || fileName.equals("")) {
      view.displayMessage("Please Select Portfolio");
      return false;
    }
    if (date == null || date.equals("")) {
      view.displayMessage("Please Enter a Valid Date");
      return false;
    }

    Date target;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setLenient(false);
    try {
      target = formatter.parse(date);
      Date upperLimit = new Date();
      upperLimit = new Date(upperLimit.getTime() - (1000L * 60 * 60 * 24));
      Date lowerLimit = formatter.parse("2000-01-01");
      if (target.compareTo(upperLimit) > -1 || target.before(lowerLimit)) {
        view.displayMessage("The date entered is out of bounds "
                + "(2000-01-01 to yesterday). Please try again.");
        return false;
      }
    } catch (Exception e) {
      view.displayMessage("The date provided was not valid."
              + " Please try again.");
      return false;
    }

    return true;
  }

  @Override
  public boolean validateShare(String txtSymbol, String txtQuantity, String txtCommission)
          throws IOException {

    double weight = parseDoubleValue(txtQuantity);

    if (weight <= 0) {
      view.displayMessage("Please enter positive weight");
      return false;
    } else if (weight > 100) {
      view.displayMessage("Weight cannot be greater than 100");
      return false;
    }

    double commission = parseDoubleValue(txtCommission);

    if (commission < 1 || commission > 20) {
      view.displayMessage("Invalid commission. Please enter commission between 1 and 20");
      return false;
    }

    return true;
  }


  @Override
  public void saveShares(ArrayList<String> arr) {
    stockList = arr;
  }

  @Override
  public void rebalancePortfolio() {
    String name = viewStuff[0];
    String dateString = viewStuff[1];
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    List<List<String>> contents = null;
    try {
      contents = model.getFlexiblePortfolioComposition(name, dateString);
    } catch (ParseException | IOException | java.text.ParseException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> tickersTemp= new ArrayList<>();
    ArrayList<String> countsTemp = new ArrayList<>();

    for (int i = 0; i < contents.size(); i++) {
      if (!contents.get(i).get(1).equals("0")){
        tickersTemp.add(contents.get(i).get(0));
        countsTemp.add(contents.get(i).get(1));
      }
    }
    String[] allTickers = new String[tickersTemp.size()];
    String[] counts = new String[countsTemp.size()];
    for (int i = 0; i < tickersTemp.size(); i++) {
      allTickers[i] = tickersTemp.get(i);
      counts[i] = countsTemp.get(i);
    }

    ArrayList<String> ticks = new ArrayList<>();
    ArrayList<Double> pers = new ArrayList<>();
    ArrayList<Double> coms = new ArrayList<>();
    for (int i = 0; i < stockList.size(); i++) {
      String stuff = stockList.get(i);
      String[] stuffs = stuff.split(":");
      ticks.add(stuffs[0]);
      pers.add(Double.parseDouble(stuffs[1]));
      coms.add(Double.parseDouble(stuffs[2]));
    }

    float[] prices = new float[allTickers.length];
    Map<String, Float> priceMap = new HashMap<>();
    APIData api = null;
    try {
      api = new VantageAPIData();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      for (int i = 0; i < allTickers.length; i++) {
        String[] reader = api.getInputStream(allTickers[i]);
        String price = api.getPriceForDate(reader, dateString,
            "daily").toString();
        prices[i] = Float.parseFloat(price);
        priceMap.put(allTickers[i], prices[i]);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    float sum = 0;
    for (int i = 0; i < allTickers.length; i++) {
      sum += Float.parseFloat(counts[i])*priceMap.get(allTickers[i]);
    }
    //for each stock, find out how much to buy or sell
    for (int i = 0; i < allTickers.length; i++) {
      double diff = sum*pers.get(i)*0.01f - priceMap.get(allTickers[i])
          *Float.parseFloat(counts[i]);
      if (diff > 0) {
        try {
          model.buyShares(allTickers[i], diff/priceMap.get(allTickers[i]),
              dateString, coms.get(i), name);
        } catch (ParseException | IOException | java.text.ParseException e) {
          throw new RuntimeException(e);
        }
      } else if (diff < 0){
        try {
          model.rebalanceSell(allTickers[i], Math.abs(diff)/priceMap.get(allTickers[i]),
              dateString, coms.get(i), name);
        } catch (ParseException | IOException
                 | NoSuchFieldException | java.text.ParseException e) {
          throw new RuntimeException(e);
        }
      }
    }
    view.displayMessage("Rebalance Successful!");
  }
}
