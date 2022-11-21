package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrameView extends JFrame implements ViewInterface, ActionListener {
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
  private String operationalStuff;


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
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel,FlowLayout.LEFT));
    Border paddingHeader = BorderFactory.createEmptyBorder(20, 20, 0, 20);
    headerPanel.setBorder(paddingHeader);
    mainPanel.add(headerPanel);
    header = new JLabel("Welcome to GROW MONEY!!!");
    header.setFont(new Font("Calibri", Font.BOLD, 20));
    headerPanel.add(header);

    subMainPanel = new JPanel();
    subMainPanel.setLayout(new GridLayout());
    Border paddingSubMain = BorderFactory.createEmptyBorder(30,0,30,0);
    subMainPanel.setBorder(paddingSubMain);
    mainPanel.add(subMainPanel);

    menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    Border paddingMenu = BorderFactory.createEmptyBorder(30, 0, 30, 0);
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
    Border paddingContent = BorderFactory.createEmptyBorder(10,0,10,50);
    contentPanel.setBorder(paddingContent);
    subMainPanel.add(contentPanel);

    contentHeaderPanel = new JPanel();
    contentHeaderPanel.setLayout(new BoxLayout(contentHeaderPanel,FlowLayout.LEFT));
    Border paddingConHeader = BorderFactory.createEmptyBorder(10, 0, 10, 50);
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
    uploadButton.setActionCommand("Upload Portfolio Button");
    //uploadButton.addActionListener(this.actionListner -> txtFile.getText());
    subContentPanel.add(uploadButton);

  }

  @Override
  public void showBuildScreen() {
    disableButtons();
    content.setText("Build/Edit a Portfolio");
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

  public void disableButtons() {
    loadButton.setVisible(false);
    buildButton.setVisible(false);
    viewButton.setVisible(false);
    saveButton.setVisible(false);
    backButton.setVisible(true);
  }

  public void enableButtons() {
    loadButton.setVisible(true);
    buildButton.setVisible(true);
    viewButton.setVisible(true);
    saveButton.setVisible(true);
    backButton.setVisible(false);
  }

  public void addSubContentPanel() {
    subContentPanel = new JPanel();
    subContentPanel.setLayout(new FlowLayout());
    Border paddingSubCon = BorderFactory.createEmptyBorder(10,0,10,50);
    subContentPanel.setBorder(paddingSubCon);
    contentPanel.add(subContentPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Upload Portfolio Button" :

    }
  }
}
