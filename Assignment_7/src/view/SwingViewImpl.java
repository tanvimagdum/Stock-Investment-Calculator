package view;

import org.jdesktop.swingx.JXDatePicker;
import org.json.simple.parser.ParseException;


import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import controller.SwingController;

/**
 * This class represents implementation of the SwingView interface.
 * This class generates the split view panel in the single frame and toggles the right panel based
 * on the user action performed on the left panel.
 */
public class SwingViewImpl implements SwingView, ActionListener {
  private JFrame frame;

  private String selectedDate;
  private String portfolioName;

  private String selectedEndDate;

  private String path;

  // create a panel
  private JPanel panelOne;
  private JPanel panelTwo;

  private JLabel radioDisplay;

  private JLabel symbolLabel;

  private JLabel quantityLabel;

  private JLabel commissionLabel;

  private JRadioButton[] radioButtons;

  private JButton examineButton;
  private JButton costBasisButton;

  private JButton valuationButton;
  private JButton uploadButton;

  private JButton addStockButton;
  private JButton sellStockButton;

  private JButton fileOpenButton;

  private JButton createPortfolioButton;
  private JButton newDollarCostAvgButton;
  private JButton existingDollarCostAvgButton;

  private final JButton portfolioAddStock;

  private JButton displayPerformance;

  private JLabel comboboxDisplay;

  private JTextArea symbolText;

  private JTextArea quantityText;

  private JTextArea commissionText;

  private JTextArea intervalTime;

  private JLabel intervalTimeLabel;

  private JLabel dateLabel;

  private JXDatePicker picker;

  private JXDatePicker endDate;


  private JComboBox<String> combobox;

  private JSplitPane sl;

  private final String[] stockOptions = {
      "Create Portfolio", "Buy Stock", "Sell Stock", "Examine Portfolio",
      "Get Total Valuation", "Get Cost Basis", "Upload Portfolio", "Get Portfolio Performance",
      "Fixed Investment Strategy", "Dollar Cost Averaging"
  };
  private JLabel filePathDisplay;

  private JLabel createPortfolioLabel;

  private JLabel endDateLabel;

  private JButton createNewInvestmentStrategy;
  private JButton investInExisiting;
  private JButton investDollarCostButton;
  private String newPortfolio;

  private JLabel amountLabel;

  private JLabel remainingAmountLabel;
  private JTextArea amountText;

  private JButton addStock;
  private JButton investNow;

  private JLabel weightLabel;
  private JTextArea weightText;

  private DefaultTableModel model;

  private JTable table;

  private JPanel displayExaminePanel;

  private JDialog genericDialog;

  private JSplitPane valuationPane;

  /**
   * This represents the constructor of the SwingViewImpl.
   * This constructs the frames, panels and labels with the default value.
   * Ths split view panel consist of the radio buttons on the left panel on which user performs
   * action. Based on the action right panel is displayed with the below default values.
   */
  public SwingViewImpl() {
    frame = new JFrame();
    panelOne = new JPanel();
    panelTwo = new JPanel();
    panelOne.setBorder(BorderFactory.createTitledBorder("Stock Options"));
    panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.PAGE_AXIS));
    radioButtons = new JRadioButton[stockOptions.length];
    JRadioButton button = new JRadioButton("Test");
    ButtonGroup rGroup1 = new ButtonGroup();
    weightLabel = new JLabel("Enter Weight");
    weightText = new JTextArea();

    JLabel displayMessageLabel = new JLabel("");

    model = new DefaultTableModel();
    model.addColumn("Symbol");
    model.addColumn("Quantity");
    table = new JTable(model);
    displayExaminePanel = new JPanel();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton((i + 1) + ". " + stockOptions[i]);
      radioButtons[i].setActionCommand(stockOptions[i]);
      radioButtons[i].addActionListener(this);

      rGroup1.add(radioButtons[i]);
      panelOne.add(radioButtons[i]);

    }
    examineButton = new JButton("Examine");
    costBasisButton = new JButton("Cost Basis");
    valuationButton = new JButton("Total Valuation");
    uploadButton = new JButton("Upload Portfolio");
    addStockButton = new JButton("Buy Shares");
    sellStockButton = new JButton("Sell Shares");
    radioDisplay = new JLabel("No options selected yet !");
    createPortfolioButton = new JButton("Create Portfolio");
    portfolioAddStock = new JButton("Add Stocks");
    displayPerformance = new JButton("Display Portfolio Performance");
    newDollarCostAvgButton = new JButton("Create Dollar Cost Averaging in new portfolio");
    existingDollarCostAvgButton = new JButton("Create Dollar Cost Averaging in existing " +
            "portfolio");
    addStock = new JButton("Add Stock");
    investNow = new JButton("Invest Now");
    investDollarCostButton = new JButton("Invest Now");

    symbolLabel = new JLabel("Enter Symbol");
    quantityLabel = new JLabel("Enter Quantity");
    commissionLabel = new JLabel("Enter Commission between $1 - $20");
    dateLabel = new JLabel("Select Date");
    JLabel startDateLabel = new JLabel("Select Start Date");
    endDateLabel = new JLabel("Select End date");
    remainingAmountLabel = new JLabel("Remaining Weight: 100.0");
    createNewInvestmentStrategy = new JButton("Create investment strategy in new portfolio");
    investInExisiting = new JButton("Create investment Strategy in existing portfolio");
    amountLabel = new JLabel("Enter amount");
    intervalTimeLabel = new JLabel("Enter Interval in Days");
    filePathDisplay = new JLabel("No file selected");
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");

    createPortfolioLabel = new JLabel("None");
    picker = new JXDatePicker();
    JXDatePicker startDate = new JXDatePicker();
    endDate = new JXDatePicker();
    symbolText = new JTextArea();
    quantityText = new JTextArea();
    commissionText = new JTextArea();
    amountText = new JTextArea();
    intervalTime = new JTextArea();
    panelOne.add(radioDisplay);
    panelTwo.add(displayMessageLabel);
    sl = new JSplitPane(SwingConstants.VERTICAL, panelOne, panelTwo);
    frame.add(sl);
    frame.setSize(800, 600);
    frame.setLocation(200, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    panelOne.setFocusable(true);
    panelOne.requestFocus();
    combobox = new JComboBox<String>();
    combobox.addItem("None");
  }

  @Override
  public void splitViewPanel(JPanel panel) {
    frame.remove(sl);
    JPanel panelTwo = new JPanel();
    sl = new JSplitPane(SwingConstants.VERTICAL, panelOne, panelTwo);
    panelTwo.add(panel);
    frame.add(sl);
    frame.setVisible(true);
    panelOne.setFocusable(true);
    panelOne.requestFocus();
  }


  @Override
  public void addFeatures(SwingController swingController) {

    radioButtons[3].addActionListener(evt -> swingController.examinePortfolio());
    radioButtons[5].addActionListener(evt -> swingController.costBasis());
    radioButtons[4].addActionListener(evt -> swingController.valuation());
    radioButtons[6].addActionListener(evt -> swingController.uploadPortfolio());
    radioButtons[1].addActionListener(evt -> swingController.buyShares());
    radioButtons[2].addActionListener(evt -> swingController.sellShares());
    radioButtons[0].addActionListener(evt -> swingController.createPortfolio());
    radioButtons[7].addActionListener(evt -> swingController.getPortfolioPerformance());
    radioButtons[8].addActionListener(evt -> this.displayFixedInvestmentMenu());
    radioButtons[9].addActionListener(evt -> this.displayDollarCostAveraging());

    addStockButton.addActionListener(evt -> swingController.addStock(symbolText.getText(),
            quantityText.getText(), selectedDate, commissionText.getText(),
            portfolioName));
    sellStockButton.addActionListener(evt -> swingController.sellStock(symbolText.getText(),
            quantityText.getText(), selectedDate, commissionText.getText(),
            portfolioName));
    costBasisButton.addActionListener(evt -> swingController.getCostBasis(portfolioName,
            selectedDate));
    valuationButton.addActionListener(evt -> swingController.getValuation(portfolioName,
            selectedDate));
    uploadButton.addActionListener(evt -> swingController.getUploadedPortfolio(path));
    portfolioAddStock.addActionListener(evt -> {
      String portfolioName = "";
      try {
        portfolioName = createPortfolioLabel.getText().split(" ")[1];
      } catch (Exception e) {
        portfolioName = "";
      }
      swingController.addNewStocks(symbolText.getText(),
              quantityText.getText(), selectedDate, commissionText.getText(),
              portfolioName);
    });
    createPortfolioButton.addActionListener(evt -> {
      newPortfolio = swingController.getNewPortfolio();
    });
    examineButton.addActionListener(evt -> {
      swingController.examinePortfolioList(portfolioName,
              selectedDate);
    });
    createNewInvestmentStrategy.addActionListener(evt -> {
      swingController.createNewInvestmentMenu();
    });

    investInExisiting.addActionListener(evt -> {
      swingController.investInExistingPortfolio();
    });
    newDollarCostAvgButton.addActionListener(evt -> {
      this.createNewDollarCostAvg();
    });
    existingDollarCostAvgButton.addActionListener(evt -> {
      swingController.existingDollarCostAvgPanel();
    });

    displayPerformance.addActionListener(evt -> {
      try {
        swingController.getPortfolioPerformanceData(portfolioName, selectedDate, selectedEndDate);
      } catch (ParseException | java.text.ParseException | IOException e) {
        throw new RuntimeException(e);
      }
    });

    addStock.addActionListener(evt -> {
      String portfolioName = "";
      try {
        portfolioName = createPortfolioLabel.getText().split(" ")[1];
      } catch (Exception e) {
        portfolioName = "";
      }

      boolean result = false;
      try {
        result = swingController.validateStock(portfolioName, amountText.getText(),
                symbolText.getText(), weightText.getText(), selectedDate, commissionText.getText());
      } catch (IOException | java.text.ParseException e) {
        throw new RuntimeException(e);
      }

      if (result) {
        amountText.setEditable(false);
        amountText.setFocusable(false);
        amountText.setEnabled(false);
        picker.setEditable(false);
        picker.setFocusable(false);
        picker.setEnabled(false);

        endDate.setEditable(false);
        endDate.setFocusable(false);
        endDate.setEnabled(false);

        createPortfolioButton.setFocusable(false);
        createPortfolioButton.setEnabled(false);

        intervalTime.setEditable(false);
        intervalTime.setFocusable(false);
        intervalTime.setEnabled(false);

        remainingAmountLabel.setText("Remaining Weight: " +
                this.getRemainingAmount(weightText.getText()));
        double remainingAmount =
                Double.parseDouble(remainingAmountLabel.getText().split(":")[1]);
        if (remainingAmount < 0) {
          addStock.setFocusable(true);
          addStock.setEnabled(true);
          investNow.setFocusable(false);
          investNow.setEnabled(false);
          investDollarCostButton.setFocusable(false);
          investDollarCostButton.setEnabled(false);
          amountText.setEditable(true);
          amountText.setFocusable(true);
          amountText.setEnabled(true);
          amountText.setText("");
          weightText.setText("");
          picker.setEditable(true);
          picker.setFocusable(true);
          picker.setEnabled(true);
          endDate.setEditable(true);
          endDate.setFocusable(true);
          endDate.setEnabled(true);
          intervalTime.setEditable(true);
          intervalTime.setFocusable(true);
          intervalTime.setText("");
          createPortfolioButton.setFocusable(true);
          createPortfolioButton.setEnabled(true);
          createPortfolioLabel.setText("None");
          remainingAmountLabel.setText("Remaining Weight: 100.0");
          this.clearAddStockFields();
          this.displayMessage("Weight cannot be negative");
        } else if (remainingAmount == 0) {
          swingController.saveStocks(portfolioName, amountText.getText(),
                  symbolText.getText(), weightText.getText(),
                  selectedDate, commissionText.getText());
          addStock.setFocusable(false);
          addStock.setEnabled(false);
          investNow.setFocusable(true);
          investNow.setEnabled(true);
          investDollarCostButton.setFocusable(true);
          investDollarCostButton.setEnabled(true);

        }

      }

    });

    investNow.addActionListener(evt -> {
      try {
        swingController.investStocks();
        addStock.setFocusable(true);
        addStock.setEnabled(true);
        investNow.setFocusable(false);
        investNow.setEnabled(false);
        investDollarCostButton.setFocusable(false);
        investDollarCostButton.setEnabled(false);
        amountText.setEditable(true);
        amountText.setFocusable(true);
        amountText.setEnabled(true);
        picker.setEditable(true);
        picker.setFocusable(true);
        picker.setEnabled(true);
        createPortfolioButton.setFocusable(true);
        createPortfolioButton.setEnabled(true);
        createPortfolioLabel.setText("None");
        remainingAmountLabel.setText("Remaining Weight: 100.0");

      } catch (IOException | java.text.ParseException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    investDollarCostButton.addActionListener(evt -> {
      intervalTime.setEditable(true);
      intervalTime.setFocusable(true);
      intervalTime.setEnabled(true);
      try {
        swingController.investDollarCost(Integer.parseInt(intervalTime.getText()), selectedEndDate);
      } catch (java.text.ParseException | IOException | ParseException e) {
        throw new RuntimeException(e);
      }
    });

  }

  private String getRemainingAmount(String weightText) {
    double remainingWeight = Double.parseDouble(remainingAmountLabel.getText().split(":")[1]);
    double weight = Double.parseDouble(weightText);
    return String.valueOf(remainingWeight - weight);
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(sl, message, "INFO", JOptionPane.PLAIN_MESSAGE);
  }


  @Override
  public JPanel displayPortfolios() {
    JPanel comboboxPanel = new JPanel();
    picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    picker.setToolTipText("Select date");
    picker.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = picker.getDate();
      dateLabel.setText(df.format(d));
      selectedDate = df.format(d);
    });
    picker.setAlignmentX(200);
    dateLabel.setAlignmentX(50);

    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    panelTwo.add(comboboxPanel);

    comboboxDisplay = new JLabel("Please Select Portfolio");
    comboboxDisplay.setAlignmentX(100);

    comboboxPanel.add(comboboxDisplay);
    combobox.setActionCommand("Portfolio Options");
    combobox.addActionListener(this);
    comboboxPanel.add(Box.createVerticalStrut(10));
    combobox.setPreferredSize(new Dimension(500, 30));
    comboboxPanel.add(combobox);
    comboboxPanel.add(Box.createVerticalStrut(10));
    comboboxPanel.add(dateLabel);
    comboboxPanel.add(Box.createVerticalStrut(10));
    comboboxPanel.add(picker);

    return comboboxPanel;
  }

  @Override
  public void fillComboBox(Map<Integer, File> fileMap) {
    combobox.removeAllItems();
    combobox.addItem("None");
    for (int i = 1; i <= fileMap.size(); i++) {
      combobox.addItem(fileMap.get(i).getName());
    }
  }

  @Override
  public JPanel examinePanel(JPanel panel) {
    panel.add(Box.createVerticalStrut(10));
    examineButton.setAlignmentX(SwingConstants.RIGHT);
    panel.add(examineButton);
    return panel;
  }

  @Override
  public JPanel valuationPanel(JPanel panel) {
    panel.add(Box.createVerticalStrut(10));
    valuationButton.setAlignmentX(SwingConstants.RIGHT);
    panel.add(valuationButton);
    return panel;
  }

  @Override
  public JPanel costBasisPanel(JPanel panel) {
    panel.add(Box.createVerticalStrut(10));
    costBasisButton.setAlignmentX(SwingConstants.RIGHT);
    panel.add(costBasisButton);
    return panel;
  }

  @Override
  public JPanel uploadPanel() {
    GridBagLayout layout = new GridBagLayout();
    JPanel fileUploadPanel = new JPanel(layout);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    fileOpenButton.addActionListener(this);
    fileUploadPanel.add(fileOpenButton, gbc);
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weighty = 100;
    fileUploadPanel.add(filePathDisplay, gbc);
    gbc.gridx = 3;
    gbc.gridy = 0;
    fileUploadPanel.add(uploadButton, gbc);

    return fileUploadPanel;
  }

  @Override
  public void buySharesPanel(JPanel panel) {
    panel.add(Box.createVerticalStrut(10));
    symbolLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(symbolLabel);
    panel.add(symbolText);
    panel.add(Box.createVerticalStrut(10));
    quantityLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(quantityLabel);
    panel.add(quantityText);
    panel.add(Box.createVerticalStrut(10));
    commissionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(commissionLabel);
    panel.add(commissionText);
    panel.add(Box.createVerticalStrut(10));
    panel.add(addStockButton);
    addStockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
  }

  @Override
  public void sellSharesPanel(JPanel panel) {

    panel.add(Box.createVerticalStrut(10));
    symbolLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(symbolLabel);
    panel.add(symbolText);
    panel.add(Box.createVerticalStrut(10));
    quantityLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(quantityLabel);
    panel.add(quantityText);
    panel.add(Box.createVerticalStrut(10));
    commissionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    panel.add(commissionLabel);
    panel.add(commissionText);
    panel.add(Box.createVerticalStrut(10));
    panel.add(sellStockButton);
    sellStockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
  }

  @Override
  public void clearFields() {
    symbolText.setText("");
    quantityText.setText("");
    commissionText.setText("");
    dateLabel.setText("Select Date");
    picker.setDate(null);
    selectedDate = "";

  }

  @Override
  public void clearFieldsForExamine() {
    dateLabel.setText("Select Date");
    picker.setDate(null);
    selectedDate = "";
  }

  @Override
  public void clearPerformanceFields() {
    dateLabel.setText("Select Start Date");
    picker.setDate(null);
    endDateLabel.setText("Select End Date");
    endDate.setDate(null);
    selectedEndDate = "";
    selectedDate = "";
  }

  @Override
  public void clearFixedStrategyFields() {
    dateLabel.setText("Select Start Date");
    picker.setDate(null);
    endDateLabel.setText("Select End Date");
    endDate.setDate(null);
    selectedEndDate = "";
    selectedDate = "";
    amountText.setText("");
    intervalTime.setText("");
    weightText.setText("");
    symbolText.setText("");
    commissionText.setText("");
    quantityText.setText("");
    addStock.setFocusable(true);
    addStock.setEnabled(true);
    investNow.setFocusable(false);
    investNow.setEnabled(false);
    investDollarCostButton.setFocusable(false);
    investDollarCostButton.setEnabled(false);
    amountText.setEditable(true);
    amountText.setFocusable(true);
    amountText.setEnabled(true);
    picker.setEditable(true);
    picker.setFocusable(true);
    picker.setEnabled(true);
    createPortfolioButton.setFocusable(true);
    createPortfolioButton.setEnabled(true);
    createPortfolioLabel.setText("None");
    remainingAmountLabel.setText("Remaining Weight: 100");
    endDate.setEditable(true);
    endDate.setFocusable(true);
    endDate.setEnabled(true);
    intervalTime.setEditable(true);
    intervalTime.setFocusable(true);
    intervalTime.setEnabled(true);
  }

  @Override
  public void clearAddStockFields() {
    symbolText.setText("");
    quantityText.setText("");
    commissionText.setText("");
    dateLabel.setText("Select Date");
    endDateLabel.setText("Select End Date");
    endDate.setDate(null);
    picker.setDate(null);
    selectedDate = "";
  }

  @Override
  public JPanel createPortfolioPanel() {
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.setLayout(new BoxLayout(portfolioPanel, BoxLayout.PAGE_AXIS));
    panelTwo.add(portfolioPanel);
    portfolioPanel.setPreferredSize(new Dimension(400, 300));
    picker.setEditable(true);
    picker.setFocusable(true);
    picker.setEnabled(true);
    picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    picker.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = picker.getDate();
      dateLabel.setText(df.format(d));
      selectedDate = df.format(d);
    });
    picker.setAlignmentX(250);

    dateLabel.setAlignmentX(200);
    createPortfolioButton.setAlignmentX(250);
    createPortfolioLabel.setAlignmentX(300);

    createPortfolioButton.setEnabled(true);
    createPortfolioButton.setFocusable(true);
    portfolioPanel.add(createPortfolioButton);
    portfolioPanel.add(createPortfolioLabel);
    portfolioPanel.add(Box.createVerticalStrut(10));
    portfolioPanel.add(dateLabel);
    portfolioPanel.add(picker);
    portfolioPanel.add(Box.createVerticalStrut(10));
    portfolioPanel.add(symbolLabel);
    symbolLabel.setAlignmentX(SwingConstants.RIGHT);
    portfolioPanel.add(symbolText);
    portfolioPanel.add(Box.createVerticalStrut(10));
    portfolioPanel.add(quantityLabel);
    quantityLabel.setAlignmentX(SwingConstants.RIGHT);
    portfolioPanel.add(quantityText);
    portfolioPanel.add(Box.createVerticalStrut(10));
    portfolioPanel.add(commissionLabel);
    commissionLabel.setAlignmentX(SwingConstants.RIGHT);
    portfolioPanel.add(commissionText);
    portfolioPanel.add(Box.createVerticalStrut(10));
    portfolioPanel.add(portfolioAddStock);
    portfolioAddStock.setAlignmentX(SwingConstants.CENTER);
    return portfolioPanel;
  }

  @Override
  public void displayCreatedPortfolio(String filename) {
    createPortfolioLabel.setText("Portfolio " + filename + " created successfully");
  }

  @Override
  public JPanel displayPerformancePanel(JPanel panel) {
    endDate.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    endDate.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = endDate.getDate();
      endDateLabel.setText(df.format(d));
      selectedEndDate = df.format(d);
    });
    endDate.setAlignmentX(200);
    endDateLabel.setAlignmentX(50);
    panel.add(Box.createVerticalStrut(10));
    panel.add(endDateLabel);
    panel.add(endDate);
    panel.add(Box.createVerticalStrut(10));
    panel.add(displayPerformance);
    displayPerformance.setAlignmentX(SwingConstants.RIGHT);
    return panel;
  }

  @Override
  public void displayPerformanceGraph(List<String> xData, List<String> yData,
                                      List<Double> intervalSize) {
    PerformanceGraphView performanceGraphView = new PerformanceGraphViewImpl();
    JPanel panel = performanceGraphView.displayGraph(xData, yData, intervalSize);
    panel.setPreferredSize(new Dimension(900, 600));
    this.splitViewPanel(panel);
  }


  @Override
  public void displayFixedInvestmentMenu() {
    JPanel investmentMenu = new JPanel();
    investmentMenu.setLayout(new BoxLayout(investmentMenu, BoxLayout.PAGE_AXIS));
    createNewInvestmentStrategy.setActionCommand("Invest in New");
    investmentMenu.add(Box.createVerticalStrut(10));
    investmentMenu.add(createNewInvestmentStrategy);
    investmentMenu.add(Box.createVerticalStrut(10));
    investInExisiting.setActionCommand("Invest in Existing");
    investmentMenu.add(investInExisiting);
    this.splitViewPanel(investmentMenu);
  }


  private void displayDollarCostAveraging() {
    clearFixedStrategyFields();
    JPanel dollarCostAvgMenu = new JPanel();
    dollarCostAvgMenu.setLayout(new BoxLayout(dollarCostAvgMenu, BoxLayout.PAGE_AXIS));
    createNewInvestmentStrategy.setActionCommand("Invest in New");
    dollarCostAvgMenu.add(Box.createVerticalStrut(10));
    dollarCostAvgMenu.add(newDollarCostAvgButton);
    investInExisiting.setActionCommand("Invest in Existing");
    dollarCostAvgMenu.add(Box.createVerticalStrut(10));
    dollarCostAvgMenu.add(existingDollarCostAvgButton);
    this.splitViewPanel(dollarCostAvgMenu);
  }

  private void createNewDollarCostAvg() {
    clearFixedStrategyFields();
    JPanel createNewDollarCostPanel = new JPanel();
    createNewDollarCostPanel.setBorder(BorderFactory.createTitledBorder("Dollar Cost Averaging"));
    createNewDollarCostPanel.setLayout(
            new BoxLayout(createNewDollarCostPanel, BoxLayout.PAGE_AXIS));
    createNewDollarCostPanel.add(createPortfolioButton);
    createNewDollarCostPanel.add(createPortfolioLabel);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    amountLabel.setAlignmentX(50);
    createNewDollarCostPanel.add(amountLabel);
    createNewDollarCostPanel.add(amountText);
    picker.setEditable(true);
    picker.setFocusable(true);
    picker.setEnabled(true);
    picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    picker.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = picker.getDate();
      dateLabel.setText(df.format(d));
      selectedDate = df.format(d);
    });
    endDate.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    endDate.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = endDate.getDate();
      endDateLabel.setText(df.format(d));
      selectedEndDate = df.format(d);
    });
    picker.setAlignmentX(250);
    dateLabel.setAlignmentX(50);
    endDate.setAlignmentX(250);
    endDateLabel.setAlignmentX(50);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    createNewDollarCostPanel.add(picker);
    createNewDollarCostPanel.add(dateLabel);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    createNewDollarCostPanel.add(endDate);
    createNewDollarCostPanel.add(endDateLabel);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    intervalTimeLabel.setAlignmentX(50);
    intervalTime.setEditable(true);
    intervalTime.setFocusable(true);
    intervalTime.setEnabled(true);
    createNewDollarCostPanel.add(intervalTimeLabel);
    createNewDollarCostPanel.add(intervalTime);
    createNewDollarCostPanel.add(addStockForDollarCost());
    this.splitViewPanel(createNewDollarCostPanel);
  }

  @Override
  public void createNewInvestmentMenu() {
    clearFixedStrategyFields();
    JPanel createNewInvestmentMenuPanel = new JPanel();
    createNewInvestmentMenuPanel.setBorder(BorderFactory.createTitledBorder("Fixed Investment"));
    createNewInvestmentMenuPanel.setLayout(
            new BoxLayout(createNewInvestmentMenuPanel, BoxLayout.PAGE_AXIS));
    createNewInvestmentMenuPanel.add(createPortfolioButton);
    createNewInvestmentMenuPanel.add(createPortfolioLabel);
    createNewInvestmentMenuPanel.add(picker);
    createNewInvestmentMenuPanel.add(dateLabel);
    createNewInvestmentMenuPanel.add(Box.createVerticalStrut(10));
    amountText.setEditable(true);
    amountText.setFocusable(true);
    createNewInvestmentMenuPanel.add(amountLabel);
    createNewInvestmentMenuPanel.add(amountText);
    createNewInvestmentMenuPanel.add(Box.createVerticalStrut(10));
    picker.setEditable(true);
    picker.setFocusable(true);
    picker.setEnabled(true);
    picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    picker.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = picker.getDate();
      dateLabel.setText(df.format(d));
      selectedDate = df.format(d);
    });
    createNewInvestmentMenuPanel.add(addStock());
    this.splitViewPanel(createNewInvestmentMenuPanel);
  }

  @Override
  public void investInExistingMenu(JPanel panel) {
    clearFixedStrategyFields();
    JPanel addStockPanel = new JPanel();
    amountLabel.setAlignmentX(50);
    panel.add(amountLabel);
    panel.add(amountText);
    panel.add(Box.createVerticalStrut(10));
    panel.add(addStockPanel);

    addStockPanel.add(addStock());
  }

  @Override
  public void existingDollarAvg(JPanel panel) {
    clearFixedStrategyFields();
    JPanel createNewDollarCostPanel = new JPanel();
    createNewDollarCostPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    createNewDollarCostPanel.setBorder(BorderFactory.createTitledBorder("Dollar Cost Averaging"));
    createNewDollarCostPanel.setLayout(
            new BoxLayout(createNewDollarCostPanel, BoxLayout.PAGE_AXIS));
    amountLabel.setAlignmentX(20);
    createNewDollarCostPanel.add(amountLabel);
    createNewDollarCostPanel.add(amountText);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    picker.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = picker.getDate();
      dateLabel.setText("Date Selected " + df.format(d));
      selectedDate = df.format(d);
    });
    endDate.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
    endDate.addActionListener(evt -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date d = endDate.getDate();
      endDateLabel.setText(df.format(d));
      selectedEndDate = df.format(d);
    });
    endDate.setAlignmentX(250);
    endDateLabel.setAlignmentX(50);
    createNewDollarCostPanel.add(picker);
    createNewDollarCostPanel.add(dateLabel);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    createNewDollarCostPanel.add(endDate);
    createNewDollarCostPanel.add(endDateLabel);
    createNewDollarCostPanel.add(Box.createVerticalStrut(10));
    intervalTimeLabel.setAlignmentX(50);
    createNewDollarCostPanel.add(intervalTimeLabel);
    createNewDollarCostPanel.add(intervalTime);
    createNewDollarCostPanel.add(addStockForDollarCost());
    panel.add(createNewDollarCostPanel);
  }

  private JPanel addStock() {
    JPanel addStockPanel = new JPanel();
    addStockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    addStockPanel.setBorder(BorderFactory.createTitledBorder("Add Stocks"));
    addStockPanel.setLayout(new BoxLayout(addStockPanel, BoxLayout.PAGE_AXIS));
    addStockPanel.setPreferredSize(new Dimension(400, 250));
    addStockPanel.add(remainingAmountLabel);
    addStockPanel.add(Box.createVerticalStrut(10));
    addStockPanel.add(Box.createVerticalStrut(10));
    symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(symbolLabel);
    addStockPanel.add(symbolText);
    addStockPanel.add(Box.createVerticalStrut(10));
    weightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(weightLabel);
    addStockPanel.add(weightText);
    addStockPanel.add(Box.createVerticalStrut(10));
    commissionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(commissionLabel);
    addStockPanel.add(commissionText);
    addStockPanel.add(Box.createVerticalStrut(10));
    addStock.setEnabled(true);
    addStock.setFocusable(true);
    addStockPanel.add(addStock);
    investNow.setEnabled(false);
    investNow.setFocusable(false);
    addStockPanel.add(investNow);
    return addStockPanel;
  }

  private JPanel addStockForDollarCost() {
    JPanel addStockPanel = new JPanel();
    addStockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    addStockPanel.setBorder(BorderFactory.createTitledBorder("Add Stocks"));
    addStockPanel.setLayout(new BoxLayout(addStockPanel, BoxLayout.PAGE_AXIS));
    addStockPanel.setPreferredSize(new Dimension(400, 250));
    addStockPanel.add(remainingAmountLabel);
    addStockPanel.add(Box.createVerticalStrut(10));
    addStockPanel.add(Box.createVerticalStrut(10));
    symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(symbolLabel);
    addStockPanel.add(symbolText);
    addStockPanel.add(Box.createVerticalStrut(10));
    weightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(weightLabel);
    addStockPanel.add(weightText);
    addStockPanel.add(Box.createVerticalStrut(10));
    commissionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    addStockPanel.add(commissionLabel);
    addStockPanel.add(commissionText);
    addStockPanel.add(Box.createVerticalStrut(10));

    addStockPanel.add(addStock);
    investDollarCostButton.setEnabled(false);
    investDollarCostButton.setFocusable(false);
    addStockPanel.add(investDollarCostButton);
    return addStockPanel;
  }

  @Override
  public void displayExamineComposition(List<List<String>> result) {
    String resultString = String.format("%5s%20s", "Symbol", "Quantity");
    resultString += "\n";
    for (List<String> res : result) {
      String symbol = res.get(0);
      String quantity = res.get(1);
      resultString += String.format("%5s%20s", symbol, quantity);
      resultString += "\n";
    }

    if (model.getRowCount() != 0) {
      model.setRowCount(0);
    }

    if (model.getColumnCount() != 0) {
      model.setColumnCount(0);
    }
    model.addColumn("Symbol");
    model.addColumn("Quantity");

    for (List<String> res : result) {
      String symbol = res.get(0);
      String quantity = res.get(1);
      model.addRow(new Object[]{symbol, quantity});
    }
    displayExaminePanel = new JPanel();
    displayExaminePanel.add(new JScrollPane(table));
    genericDialog = new JDialog();
    genericDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    genericDialog.setContentPane(displayExaminePanel);
    genericDialog.pack();
    genericDialog.setVisible(true);
  }

  @Override
  public void displayCostBasis(Double costBasis) {
    String message = "Cost Basis of the portfolio is $" + String.format("%.2f", costBasis);
    JOptionPane.showMessageDialog(sl, message, "Cost Basis", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void displayTotalValuation(String valuation, List<List<String>> result) {
    if (model.getRowCount() != 0) {
      model.setRowCount(0);
    }
    if (model.getColumnCount() != 0) {
      model.setColumnCount(0);
    }
    model.addColumn("Symbol");
    model.addColumn("Quantity");
    model.addColumn("Date");
    model.addColumn("Price($)");
    model.addColumn("Value($)");

    for (List<String> res : result) {
      model.addRow(new Object[]{res.get(0), res.get(1), res.get(2), res.get(3), res.get(1)});
    }
    displayExaminePanel = new JPanel();
    displayExaminePanel.setLayout(new BoxLayout(displayExaminePanel, BoxLayout.PAGE_AXIS));
    JScrollPane pane = new JScrollPane(table);
    pane.setPreferredSize(new Dimension(500, 400));
    displayExaminePanel.add(pane);
    JLabel portfolioValuation =
            new JLabel("Total Valuation of the Portfolio in Dollars : " + valuation);
    portfolioValuation.setBorder(new EmptyBorder(50, 10, 50, 10));
    displayExaminePanel.add(portfolioValuation);
    genericDialog = new JDialog();
    genericDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    genericDialog.setContentPane(displayExaminePanel);
    genericDialog.pack();
    genericDialog.setVisible(true);

  }

  @Override
  public void displayPortfolioName(String newFile) {
    String message = "Portfolio Uploaded with filename " + newFile;
    JOptionPane.showMessageDialog(sl, message, "Upload File", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Method to perform actions.
   *
   * @param argument argument to be used to perform action.
   */
  public void actionPerformed(ActionEvent argument) {
    // TODO Auto-generated method stub
    switch (argument.getActionCommand()) {
      case "Create Portfolio":
        radioDisplay.setText("Create Portfolio is selected");
        break;
      case "Buy Stock":
        radioDisplay.setText("Buy Stock is selected");
        break;
      case "Sell Stock":
        radioDisplay.setText("Sell Stock is selected");
        break;
      case "Examine Portfolio":
        radioDisplay.setText("Examine Portfolio is selected");
        break;
      case "Get Total Valuation":
        radioDisplay.setText("Get Total Valuation is selected");
        break;
      case "Get Cost Basis":
        radioDisplay.setText("Get Cost Basis is selected");
        break;
      case "Upload Portfolio":
        radioDisplay.setText("Upload Portfolio is selected");
        break;
      case "Open file": {
        final JFileChooser fchooser = new JFileChooser("");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(panelTwo);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          filePathDisplay.setText(f.getName());
          path = f.getPath();
        }
      }
      break;
      case "Portfolio Options":
        if (argument.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) argument.getSource();
          //portfolioName = (String) box.getSelectedItem();
          comboboxDisplay.setText("You selected: " + (String) box.getSelectedItem());
          portfolioName = comboboxDisplay.getText().split(":")[1].trim();
          createPortfolioLabel.setText("Portfolio " +
                  comboboxDisplay.getText().split(":")[1].trim());
        }
        break;
      case "Get Portfolio Performance":
        radioDisplay.setText("Get Portfolio Performance is selected");
        break;
      case "Fixed Investment Strategy":
        radioDisplay.setText("Fixed Investment Strategy is Selected");
        break;
      case "Invest in New":
        radioDisplay.setText("Invest in New Portfolio is Selected");
        break;
      case "Invest in Existing":
        radioDisplay.setText("Invest in Existing Portfolio is Selected");
        break;
      case "Dollar Cost Averaging":
        radioDisplay.setText("Dollar Cost Averaging is Selected");
        break;
      default:break;
    }
  }
}
