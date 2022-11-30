package view;

import controller.ControllerImpl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JFrameView extends JFrame implements ViewInterface {
  private JPanel mainPanel;
  private JPanel headerPanel;
  private JPanel subMainPanel;
  private JPanel menuPanel;
  private JPanel contentPanel;
  private JPanel contentHeaderPanel;
  private JPanel subContentPanel;
  private JScrollPane mainScrollPane;
  private JLabel header;
  private JLabel menu;
  private JLabel content;
  private JButton loadButton;
  private JButton buildButton;
  private JButton viewButton;
  private JButton saveButton;
  private JButton backButton;
  private ActionListener actionListner;
  private Object[] opStuff = new Object[10];
  private String portfolioName = "";
  private ArrayList<Object> conStuff = new ArrayList<>();
  private String currScreen;


  public JFrameView(ActionListener a) {
    super();
    this.actionListner = a;
    setTitle("GROW MONEY INVESTMENT PLANNER");
    setSize(1000, 600);
    setLocation(100, 100);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    Border paddingMain = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    mainPanel.setBorder(paddingMain);
    //mainScrollPane = new JScrollPane(mainPanel);
    add(mainPanel);

    headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel,FlowLayout.LEFT));
    Border paddingHeader = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    headerPanel.setBorder(paddingHeader);
    mainPanel.add(headerPanel);
    header = new JLabel("Welcome to GROW MONEY!!!");
    header.setFont(new Font("Calibri", Font.BOLD, 20));
    headerPanel.add(header);

    subMainPanel = new JPanel();
    subMainPanel.setLayout(new GridLayout());
    Border paddingSubMain = BorderFactory.createEmptyBorder(20,50,20,50);
    subMainPanel.setBorder(paddingSubMain);
    mainPanel.add(subMainPanel);

    menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    Border paddingMenu = BorderFactory.createEmptyBorder(20, 0, 20, 0);
    menuPanel.setBorder(paddingMenu);
    subMainPanel.add(menuPanel);
    menu = new JLabel("MENU");
    Border padMenuLabel = BorderFactory.createEmptyBorder(0, 10, 20, 0);
    menu.setBorder(padMenuLabel);
    menu.setFont(new Font("Calibri", Font.BOLD, 15));
    menuPanel.add(menu);

    loadButton = new JButton("Load Portfolio");
    loadButton.setActionCommand("Load Button");
    loadButton.addActionListener(this.actionListner);
    menuPanel.add(loadButton);

    buildButton = new JButton("Build/Edit Portfolio");
    buildButton.setActionCommand("Build Button");
    buildButton.addActionListener(this.actionListner);
    menuPanel.add(buildButton);

    viewButton = new JButton("View Portfolio");
    viewButton.setActionCommand("View Button");
    viewButton.addActionListener(a);
    menuPanel.add(viewButton);

    saveButton = new JButton("Save Portfolio");
    saveButton.setActionCommand("Save Button");
    saveButton.addActionListener(this.actionListner);
    menuPanel.add(saveButton);

    backButton = new JButton("Go Back");
    backButton.setActionCommand("Back Button");
    backButton.addActionListener(this.actionListner);
    menuPanel.add(backButton);
    backButton.setVisible(false);

    contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
    Border paddingContent = BorderFactory.createEmptyBorder(20,0,20,20);
    contentPanel.setBorder(paddingContent);
    subMainPanel.add(contentPanel);

    contentHeaderPanel = new JPanel();
    contentHeaderPanel.setLayout(new BoxLayout(contentHeaderPanel,FlowLayout.LEFT));
    Border paddingConHeader = BorderFactory.createEmptyBorder(0, 0, 20, 50);
    contentHeaderPanel.setBorder(paddingConHeader);
    contentPanel.add(contentHeaderPanel);
    content = new JLabel("ABOUT US");
    content.setFont(new Font("Calibri", Font.BOLD, 16));
    contentHeaderPanel.add(content);

  }

  @Override
  public void showWelcomeScreen() {
    enableButtons();
    content.setText("ABOUT US");
    subContentPanel.setVisible(false);
  }

  @Override
  public void showLoadScreen() {
    opStuff = new Object[1];
    disableButtons();
    content.setText("Load a Portfolio");

    addSubContentPanel();
    JLabel lblFile = new JLabel("Enter the filename : ");
    subContentPanel.add(lblFile);
    JTextField txtFile = new JTextField(15);
    txtFile.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtFile);

    JButton upload= new JButton("Upload File");
    upload.setActionCommand("Upload Port");
    upload.addActionListener(this.actionListner);
    upload.addActionListener(evt -> { opStuff[0] = txtFile.getText();
                                            txtFile.setText("");});
    subContentPanel.add(upload);

  }

  @Override
  public void showBuildScreen() {
    opStuff = new Object[1];
    disableButtons();
    content.setText("Build/Edit a Portfolio");

    addSubContentPanel();
    JLabel selectOption = new JLabel("Please select one of the options- ");
    subContentPanel.add(selectOption);
    String[] items = new String[5];
    items[0] = "Select Option";
    items[1] = "Begin building a flexible portfolio";
    items[2] = "Begin a flexible portfolio with a strategy";
    items[3] = "Edit a flexible portfolio";
    items[4] = "Add a fixed cost buy across a flexible portfolio";
    JComboBox<String> options = new JComboBox<>(items);
    subContentPanel.add(options);

    JButton submit = new JButton("Submit");
    submit.setActionCommand("Build/Edit Port");
    //submit.setActionCommand("Edit Flex Port");
    submit.addActionListener(evt -> { subContentPanel.setVisible(false);
                                      switch(currScreen) {
                                        case "Build Portfolio" :
                                        case "Build Strategy" :
                                          setFlexNameScreen();
                                          break;
                                        case "Edit Portfolio":
                                          editFlexPortScreen();
                                          break;
                                        case "Dollar Cost" :
                                          fixedCostBuyScreen();
                                          break;
                                        case "Error" :
                                          subContentPanel.setVisible(true);
                                          break;
                                        default :
                                          subContentPanel.setVisible(true);
                                          break;
                                      }});
    submit.addActionListener(this.actionListner);
    submit.addActionListener(evt -> opStuff[0] = options.getSelectedItem());
    subContentPanel.add(submit);
  }

  private void setFlexNameScreen() {
    addSubContentPanel();
    JLabel lblPortName = new JLabel("Enter portfolio name : ");
    subContentPanel.add(lblPortName);
    JTextField txtPortName = new JTextField(15);
    txtPortName.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtPortName);
    JButton portName = new JButton("Set Portfolio Name");
    portName.setActionCommand("Set Portfolio Name");
    portName.addActionListener(evt -> { subContentPanel.setVisible(false);
                                        switch(currScreen) {
                                          case "Error" :
                                            subContentPanel.setVisible(true);
                                            break;
                                          case "Add Stock" :
                                            addStocks();
                                            break;
                                          case "Add Strategy" :
                                            addStrategy();
                                            break;
                                        }
                                      });
    portName.addActionListener(this.actionListner);
    portName.addActionListener(evt -> portfolioName = txtPortName.getText());
    subContentPanel.add(portName);
  }

  private void addStocks() {
    opStuff = new Object[6];
    addSubContentPanel();
    JLabel lblBuySell = new JLabel("Buy Or Sell this stock? :");
    subContentPanel.add(lblBuySell);
    String[] items = new String[2];
    items[0] = "Buy Stock";
    items[1] = "Sell Stock";
    JComboBox<String> options = new JComboBox<>(items);
    subContentPanel.add(options);

    JLabel lblTicker = new JLabel("Enter the Ticker : ");
    subContentPanel.add(lblTicker);
    JTextField txtTicker = new JTextField(15);
    txtTicker.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtTicker);

    JLabel lblStocks = new JLabel("Enter the Stock Count : ");
    subContentPanel.add(lblStocks);
    JTextField txtStocks = new JTextField(15);
    txtStocks.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtStocks);

    JLabel lblDate = new JLabel("Enter the Date : ");
    subContentPanel.add(lblDate);
    JTextField txtYear= new JTextField(4);
    txtStocks.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtYear);
    JTextField txtMon= new JTextField(2);
    txtMon.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtMon);
    JTextField txtDay= new JTextField(2);
    txtDay.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtDay);

    subContentPanel.add(new JLabel("Please click 'Add Stock' to add stocks or "));
    subContentPanel.add(new JLabel( "alternatively, 'Done' to finish building portfolio."));
    JButton addStock = new JButton("Add Stock");
    addStock.setActionCommand("Add Stock");
    addStock.addActionListener(evt -> opStuff = new Object[6]);
    addStock.addActionListener(this.actionListner);
    addStock.addActionListener(evt -> {
      opStuff[0] = options.getSelectedItem();
      opStuff[1] = txtTicker.getText();
      opStuff[2] = txtStocks.getText();
      opStuff[3] = txtYear.getText();
      opStuff[4] = txtMon.getText();
      opStuff[5] = txtDay.getText();
      txtTicker.setText("");
      txtStocks.setText("");
      txtYear.setText("");
      txtMon.setText("");
      txtDay.setText("");
    });
    subContentPanel.add(addStock);

    JButton done = new JButton("Done");
    done.setActionCommand("Done build");
    //done.addActionListener(this.actionListner);
    //done.addActionListener();
    subContentPanel.add(done);
  }

  private void addStrategy() {
    opStuff = new Object[6];
    addSubContentPanel();

    JLabel lblAmount = new JLabel("Enter Dollar Amount : ");
    subContentPanel.add(lblAmount);
    JTextField txtAmount = new JTextField(10);
    txtAmount.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtAmount);

    JLabel lblFreq = new JLabel("No. of days between buys : ");
    subContentPanel.add(lblFreq);
    JTextField txtFreq = new JTextField(10);
    txtFreq.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtFreq);

    JLabel lblStartDate = new JLabel("Enter the Start Date : ");
    subContentPanel.add(lblStartDate);
    JTextField txtStartYear = new JTextField(4);
    txtStartYear.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtStartYear);
    JTextField txtStartMon = new JTextField(2);
    txtStartMon.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtStartMon);
    JTextField txtStartDay = new JTextField(2);
    txtStartDay.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtStartDay);

    JLabel lblEndDate = new JLabel("Enter the End Date : ");
    subContentPanel.add(lblEndDate);
    JTextField txtEndYear = new JTextField(4);
    txtEndYear.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtEndYear);
    JTextField txtEndMon = new JTextField(2);
    txtEndMon.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtEndMon);
    JTextField txtEndDay = new JTextField(2);
    txtEndDay.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtEndDay);

    //
    JLabel lblTicker = new JLabel("Enter the Ticker : ");
    subContentPanel.add(lblTicker);
    JTextField txtTicker = new JTextField(15);
    txtTicker.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtTicker);


  }

  private void editFlexPortScreen() {
    addSubContentPanel();
    JLabel lblPortName = new JLabel("Select a portfolio to edit : ");
    subContentPanel.add(lblPortName);

    ArrayList<String> itemArray = new ArrayList<>();
    ArrayList<Object> o = getConStuff();
    for (int i = 0; i < o.size(); i++) {
      itemArray.add(o.get(i).toString());
    }
    String[] item = new String[itemArray.size()];
    for (int i = 0; i < itemArray.size(); i++) {
      item[i] = itemArray.get(i);
    }
    JComboBox<String> portNames = new JComboBox<>(item);
    subContentPanel.add(portNames);
    JButton portName = new JButton("Get Portfolio");
    portName.setActionCommand("Get Portfolio Name");
    portName.addActionListener(evt -> { subContentPanel.setVisible(false);
      addStocks();
    });
    //portName.addActionListener(this.actionListner);
    portName.addActionListener(evt -> portfolioName = portNames.getSelectedItem().toString());
    subContentPanel.add(portName);
  }

  private void fixedCostBuyScreen() {

  }

  @Override
  public void showPortfolioScreen() {
    disableButtons();
    content.setText("View a Portfolio");
  }

  @Override
  public void showSaveScreen() {
    disableButtons();
    content.setText("Save a Portfolio");
  }

  @Override
  public void printLine(String line) {
    JOptionPane.showMessageDialog(subContentPanel, line);
  }

  @Override
  public void printLines(String[] lines) {
    for (int i = 0; i < lines.length; i++) {
      JOptionPane.showMessageDialog(subContentPanel, lines);
    }
  }

  @Override
  public void displayError() {

  }

  public String printWarning(String line) {
    int a = JOptionPane.showConfirmDialog(subContentPanel, line);
    if (a == JOptionPane.YES_OPTION) {
      return "y";
    }
    else {
      return "n";
    }
  }
  private void disableButtons() {
    loadButton.setVisible(false);
    buildButton.setVisible(false);
    viewButton.setVisible(false);
    saveButton.setVisible(false);
    backButton.setVisible(true);
  }

  private void enableButtons() {
    loadButton.setVisible(true);
    buildButton.setVisible(true);
    viewButton.setVisible(true);
    saveButton.setVisible(true);
    backButton.setVisible(false);
  }

  private void addSubContentPanel() {
    subContentPanel = new JPanel();
    subContentPanel.setLayout(new FlowLayout(10,5,5));
    Border paddingSubCon = BorderFactory.createEmptyBorder(0,0,10,50);
    subContentPanel.setBorder(paddingSubCon);
    contentPanel.add(subContentPanel);
  }

  public Object[] getOperationalStuff() {
    return opStuff;
  }

  public String getPortfolioName() {
    return portfolioName;
  }

  public void setConStuff(ArrayList<Object> o) {
    conStuff = o;
  }

  private ArrayList<Object> getConStuff() {
    return conStuff;
  }

  public void setCurrScreen(String str) {
    currScreen = str;
  }

  public String getCurrScreen() {
    return currScreen;
  }
}
