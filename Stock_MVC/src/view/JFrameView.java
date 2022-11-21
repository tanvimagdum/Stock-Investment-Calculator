package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JFrameView extends JFrame implements ViewInterface {
  private JPanel mainPanel;
  private JPanel menuPanel;
  private JScrollPane mainScrollPane;
  private JScrollPane menuScrollPane;
  private JLabel header;
  private JButton loadButton;
  private JButton buildButton;
  private JButton viewButton;
  private JButton saveButton;
  private JButton saveAllButton;


  public JFrameView() {
    super();
    setTitle("GROW MONEY INVESTMENT PLANNER");
    setSize(800, 500);
    setLocation(200, 200);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    Border paddingMain = BorderFactory.createEmptyBorder(30, 30, 30, 30);
    mainPanel.setBorder(paddingMain);
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    header = new JLabel("Welcome to GROW MONEY!!!");
    header.setFont(new Font("Calibri", Font.BOLD, 20));

    mainPanel.add(header);

    menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
    Border paddingMenu = BorderFactory.createEmptyBorder(30, 0, 30, 30);
    menuPanel.setBorder(paddingMenu);
    mainPanel.add(menuPanel);

    loadButton = new JButton("Load Portfolio");
    loadButton.setActionCommand("Load Portfolio Button");
    menuPanel.add(loadButton);

    buildButton = new JButton("Build/Edit Portfolio");
    buildButton.setActionCommand("Build Or Edit Portfolio Button");
    menuPanel.add(buildButton);

    viewButton = new JButton("View Portfolio");
    viewButton.setActionCommand("View Portfolio Button");
    menuPanel.add(viewButton);

    saveButton = new JButton("Save Portfolio");
    saveButton.setActionCommand("Save Portfolio Button");
    menuPanel.add(saveButton);

    saveAllButton = new JButton("Save All Portfolios");
    saveAllButton.setActionCommand("Save All Portfolios Button");
    menuPanel.add(saveAllButton);


  }

  @Override
  public void showWelcomeScreen() {

  }

  @Override
  public void showLoadScreen() {

  }

  @Override
  public void showBuildScreen() {

  }

  @Override
  public void showPortfolioScreen() {

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
}
