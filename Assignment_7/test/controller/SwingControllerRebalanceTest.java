package controller;

import model.FlexiblePortfolio;
import model.FlexiblePortfolioImpl;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import view.SwingView;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SwingControllerRebalanceTest {

  class mockView implements SwingView {

    private StringBuilder log;

    public mockView(StringBuilder log) {
      this.log = log;
    }
    @Override
    public void splitViewPanel(JPanel panel) {
      log.append("splitViewPanel method called\n");
    }

    @Override
    public void addFeatures(SwingController swingController) {
      log.append("addFeatures method called\n");
    }

    @Override
    public void displayMessage(String message) {
      log.append("displayMessage method called\n");
    }

    @Override
    public void displayCostBasis(Double costBasis) {
      log.append("displayCostBasis method called\n");
    }

    @Override
    public JPanel costBasisPanel(JPanel panel) {
      log.append("costBasisPanel method called\n");
      return null;
    }

    @Override
    public JPanel displayPortfolios() {
      log.append("displayPortfolios method called\n");
      return null;
    }

    @Override
    public void fillComboBox(Map<Integer, File> fileMap) {
      log.append("fillComboBox method called\n");
    }

    @Override
    public JPanel examinePanel(JPanel panel) {
      log.append("examinePanel method called\n");
      return null;
    }

    @Override
    public JPanel valuationPanel(JPanel panel) {
      log.append("valuationPanel method called\n");
      return null;
    }

    @Override
    public JPanel uploadPanel() {
      log.append("uploadPanel method called\n");
      return null;
    }

    @Override
    public void existingDollarAvg(JPanel panel) {
      log.append("existingDollarAvg method called\n");
    }

    @Override
    public void displayExamineComposition(List<List<String>> result) {
      log.append("displayExamineComposition method called\n");
    }

    @Override
    public void displayTotalValuation(String valuation, List<List<String>> result) {
      log.append("displayTotalValuation method called\n");
    }

    @Override
    public void displayPortfolioName(String newFile) {
      log.append("displayPortfolioName method called\n");
    }

    @Override
    public void buySharesPanel(JPanel panel) {
      log.append("buySharesPanel method called\n");
    }

    @Override
    public void sellSharesPanel(JPanel panel) {
      log.append("sellSharesPanel method called\n");
    }

    @Override
    public void clearFields() {
      log.append("clearFields method called\n");
    }

    @Override
    public void clearFieldsForExamine() {
      log.append("clearFieldsForExamine method called\n");
    }

    @Override
    public JPanel createPortfolioPanel() {
      log.append("createPortfolioPanel method called\n");
      return null;
    }

    @Override
    public void displayCreatedPortfolio(String filename) {
      log.append("displayCreatedPortfolio method called\n");
    }

    @Override
    public JPanel displayPerformancePanel(JPanel panel) {
      log.append("displayPerformancePanel method called\n");
      return null;
    }

    @Override
    public void displayPerformanceGraph(List<String> xData, List<String> yData, List<Double> intervalSize) {
      log.append("displayPerformanceGraph method called\n");
    }

    @Override
    public void displayFixedInvestmentMenu() {
      log.append("displayFixedInvestmentMenu method called\n");
    }

    @Override
    public void createNewInvestmentMenu() {
      log.append("createNewInvestmentMenu method called\n");
    }

    @Override
    public void investInExistingMenu(JPanel panel) {
      log.append("investInExistingMenu method called\n");
    }

    @Override
    public void clearPerformanceFields() {
      log.append("clearPerformanceFields method called\n");
    }

    @Override
    public void clearFixedStrategyFields() {
      log.append("clearFixedStrategyFields method called\n");
    }

    @Override
    public void clearAddStockFields() {
      log.append("clearAddStockFields method called\n");
    }

    @Override
    public void clearSelectPortfolioFields() {
      log.append("clearSelectPortfolioFields method called\n");
    }

    @Override
    public void clearRebalanceFields() {
      log.append("clearRebalanceFields method called\n");
    }

    @Override
    public void selectPortfolioPanel(JPanel panel) {
      log.append("selectPortfolioPanel method called\n");
    }

    @Override
    public void displayRebalancePanel(JPanel panel) {
      log.append("displayRebalancePanel method called\n");
    }

    @Override
    public void setControllerStuff(String[] str) {
      log.append("setControllerStuff method called\n");
    }
  }

  @Test
  public void testRebalanceValidation1() throws IOException, ParseException, java.text.ParseException {
    String port = "flexible_portfolio_5.json";
    String date = "2050-01-01";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);
    rb.validatePortfolio(port, date);
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n" +
            "clearSelectPortfolioFields method called\n", log.toString());

  }

  @Test
  public void testRebalanceValidation2() throws IOException, ParseException, java.text.ParseException {
    String port = "";
    String date = "2020-01-01";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);
    rb.validatePortfolio(port, date);
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n" +
            "clearSelectPortfolioFields method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidation3() throws IOException, ParseException, java.text.ParseException {
    String port = "flexible_portfolio_5.json";
    String date = "1987-01-01";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);
    rb.validatePortfolio(port, date);
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n" +
            "clearSelectPortfolioFields method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidation4() throws IOException, ParseException, java.text.ParseException {
    String port = "flexible_portfolio_5.json";
    String date = null;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);
    rb.validatePortfolio(port, date);
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n" +
            "clearSelectPortfolioFields method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidation5() throws IOException, ParseException, java.text.ParseException {
    String port = "flexible_portfolio_5.json";
    String date = "2020-01-01";
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);
    rb.validatePortfolio(port, date);
    assertEquals("addFeatures method called\n" +
            "setControllerStuff method called\n" +
            "displayRebalancePanel method called\n" +
            "splitViewPanel method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidateShares1() throws IOException, java.text.ParseException {
    ArrayList<String> ticks = new ArrayList<>(Arrays.asList("GOOG", "AAPL"));
    ArrayList<String> pers = new ArrayList<>(Arrays.asList("40", "101"));
    ArrayList<String> coms = new ArrayList<>(Arrays.asList("5", "5"));
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    for (int i = 0; i < ticks.size(); i++) {
      rb.validateShare(ticks.get(i), pers.get(i), coms.get(i));
    }
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidateShares2() throws IOException, java.text.ParseException {
    ArrayList<String> ticks = new ArrayList<>(Arrays.asList("GOOG", "AAPL"));
    ArrayList<String> pers = new ArrayList<>(Arrays.asList("-40", "60"));
    ArrayList<String> coms = new ArrayList<>(Arrays.asList("5", "5"));
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    for (int i = 0; i < ticks.size(); i++) {
      rb.validateShare(ticks.get(i), pers.get(i), coms.get(i));
    }
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidateShares3() throws IOException, java.text.ParseException {
    ArrayList<String> ticks = new ArrayList<>(Arrays.asList("GOOG", "AAPL"));
    ArrayList<String> pers = new ArrayList<>(Arrays.asList("40", "60"));
    ArrayList<String> coms = new ArrayList<>(Arrays.asList("-5", "5"));
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    for (int i = 0; i < ticks.size(); i++) {
      rb.validateShare(ticks.get(i), pers.get(i), coms.get(i));
    }
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidateShares4() throws IOException, java.text.ParseException {
    ArrayList<String> ticks = new ArrayList<>(Arrays.asList("GOOG", "AAPL"));
    ArrayList<String> pers = new ArrayList<>(Arrays.asList("40", "60"));
    ArrayList<String> coms = new ArrayList<>(Arrays.asList("25", "5"));
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    for (int i = 0; i < ticks.size(); i++) {
      rb.validateShare(ticks.get(i), pers.get(i), coms.get(i));
    }
    assertEquals("addFeatures method called\n" +
            "displayMessage method called\n", log.toString());
  }

  @Test
  public void testRebalanceValidateShares5() throws IOException, java.text.ParseException {
    ArrayList<String> ticks = new ArrayList<>(Arrays.asList("GOOG", "AAPL"));
    ArrayList<String> pers = new ArrayList<>(Arrays.asList("40", "60"));
    ArrayList<String> coms = new ArrayList<>(Arrays.asList("5", "5"));
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    for (int i = 0; i < ticks.size(); i++) {
      rb.validateShare(ticks.get(i), pers.get(i), coms.get(i));
    }
    assertEquals("addFeatures method called\n", log.toString());
  }

  @Test
  public void testRebalancing() throws IOException, ParseException, java.text.ParseException {
    FlexiblePortfolio model = new FlexiblePortfolioImpl();
    StringBuilder log = new StringBuilder();
    SwingView mockView = new mockView(log);
    SwingController rb = new SwingControllerImpl(model, mockView);

    String port = "flexible_portfolio_1.json";
    String date = "2022-11-30";
    rb.validatePortfolio(port, date);

    ArrayList<String> arr = new ArrayList<>(Arrays.asList("MSFT:80:5", "GOOG:20:5"));
    rb.saveShares(arr);
    rb.rebalancePortfolio();
    List<List<String>> contents = model.getFlexiblePortfolioComposition(port,date);
    assertEquals("MSFT", contents.get(0).get(0));
    assertEquals("223", contents.get(0).get(1));
    assertEquals("GOOG", contents.get(1).get(0));
    assertEquals("140", contents.get(1).get(1));
  }

}
