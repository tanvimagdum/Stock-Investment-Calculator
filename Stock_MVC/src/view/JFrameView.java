package view;

import controller.ControllerImpl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private Object opStuff;


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
    backButton.setActionCommand("Back");
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
    disableButtons();
    content.setText("Load a Portfolio");

    addSubContentPanel();
    JLabel lblFile = new JLabel("Enter the filename : ");
    subContentPanel.add(lblFile);
    JTextField txtFile = new JTextField(15);
    txtFile.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtFile);

    JButton uploadButton = new JButton("Upload File");
    uploadButton.setActionCommand("Upload Button");
    uploadButton.addActionListener(this.actionListner);
    uploadButton.addActionListener(evt -> { opStuff = txtFile.getText();
                                            txtFile.setText("");});
    subContentPanel.add(uploadButton);

  }

  @Override
  public void showBuildScreen() {
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

    JButton uploadButton = new JButton("Submit");
    uploadButton.setActionCommand("Upload Button");
    uploadButton.addActionListener(evt -> { subContentPanel.setVisible(false);
                                            buildFlexPortScreen(options.getSelectedItem());});
    uploadButton.addActionListener(this.actionListner);
    uploadButton.addActionListener(evt -> opStuff = options.getSelectedItem());
    subContentPanel.add(uploadButton);
  }

  private void buildFlexPortScreen(Object item) {
    addSubContentPanel();
    JLabel lblPortName = new JLabel("Enter portfolio name: ");
    subContentPanel.add(lblPortName);
    JTextField txtPortName = new JTextField(15);
    txtPortName.setFont(new Font("Calibri", Font.PLAIN, 12));
    subContentPanel.add(txtPortName);
    JLabel lblBuySell = new JLabel("Choose whether to buy or sell the stock :");
    subContentPanel.add(lblBuySell);
    JRadioButton txtBuy = new JRadioButton("Buy Stock");
    subContentPanel.add(txtBuy);
    JRadioButton txtSell = new JRadioButton("Sell Stock");
    subContentPanel.add(txtSell);
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

  }

  @Override
  public void printLines(String[] lines) {

  }

  @Override
  public void displayError() {

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
    subContentPanel.setLayout(new FlowLayout());
    Border paddingSubCon = BorderFactory.createEmptyBorder(0,0,10,50);
    subContentPanel.setBorder(paddingSubCon);
    contentPanel.add(subContentPanel);
  }

  public Object getOperationalStuff() {
    System.out.println("in oper stuff");
    return opStuff;
  }

}
