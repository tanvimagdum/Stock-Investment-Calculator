package view;

import java.io.PrintStream;

/**
 * A class to represent the contents
 * of the user interface including the
 * menu, error messages and help texts.
 */
public class ViewImpl implements ViewInterface {

  private PrintStream out;

  /**
   * This constructor allows the view's output to be given as an argument.
   *
   * @param out the output stream or object
   */
  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showWelcomeScreen() {

    out.println("\n" + "================================="
            + "\n" + "    Welcome to 'GROW MONEY'!" + "\n"
            + "=================================" + "\n");
    out.println("Please enter a choice number" + "\n");
    out.println("1. Load a portfolio");
    out.println("2. Build/Edit a portfolio");
    out.println("3. View a portfolio");
    out.println("4. Save a portfolio");
    out.println("5. Save all portfolios");
    out.println("6. Exit");

  }

  @Override
  public void showLoadScreen() {

    out.println("\n" + "====== Load a portfolio ======" + "\n");
    out.println("Please enter a choice number" + "\n");
    out.println("1. Enter the portfolio filename");
    out.println("2. Go Back");

  }

  @Override
  public void showBuildScreen() {

    out.println("\n" + "====== Build/Edit a portfolio ======" + "\n");
    out.println("Please enter a choice number" + "\n");
    out.println("1. Begin building a simple portfolio");
    out.println("2. Begin building a flexible portfolio");
    out.println("3. Edit a flexible portfolio");
    out.println("4. Go Back");

  }

  @Override
  public void showPortfolioScreen() {

    out.println("\n" + "====== View a portfolio ======" + "\n");
    out.println("Please enter a choice number" + "\n");
    out.println("1. View the stocks list in the portfolio");
    out.println("2. View the value of a portfolio on a certain date");
    out.println("3. View the cost basis of a flexible portfolio");
    out.println("4. View the performance over time for flexible portfolio");
    out.println("5. View the value of a portfolio with manually input prices");
    out.println("6. Go back");

  }

  @Override
  public void printLine(String line) {
    out.println(line);
  }

  @Override
  public void printLines(String[] lines) {
    for (int i = 0; i < lines.length; i++) {
      out.println(lines[i]);
    }
  }

  @Override
  public void displayError() {
    out.println("Please re-enter a choice number from the given list");
  }

}