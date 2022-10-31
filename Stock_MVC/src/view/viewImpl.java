package view;

/**
 * A class to represent the contents
 * of the user interface including the
 * menu, error messages and help texts.
 */
public class viewImpl implements viewInterface {

  @Override
  public void showWelcomeScreen() {

    System.out.println("\n" + "=================================" +
            "\n" + "    Welcome to 'GROW MONEY'!" + "\n" +
            "=================================" + "\n");
    System.out.println("Please enter a choice number" + "\n");
    System.out.println("1. Load a portfolio");
    System.out.println("2. Build a new portfolio");
    System.out.println("3. View a portfolio");
    System.out.println("4. Save a portfolio");
    System.out.println("5. Save all portfolios");
    System.out.println("6. Exit");

  }

  @Override
  public void showLoadScreen() {

    System.out.println("\n" + "====== Load a portfolio ======" + "\n");
    System.out.println("Please enter a choice number" + "\n");
    System.out.println("1. Enter the portfolio filename");
    System.out.println("2. Go Back");

  }

  @Override
  public void showBuildScreen() {

    System.out.println("\n" + "====== Build a new portfolio ======" + "\n");
    System.out.println("Please enter a choice number");
    System.out.println("1. Begin building the portfolio");
    System.out.println("2. Go Back");

  }

  @Override
  public void showPortfolioScreen() {

    System.out.println("\n" + "====== View a portfolio ======" + "\n");
    System.out.println("Please enter a choice number");
    System.out.println("1. View the stocks list in the portfolio");
    System.out.println("2. View the value of portfolio on a certain date");
    System.out.println("3. View the value of portfolio with manually input prices.");
    System.out.println("4. Save the viewed portfolio");
    System.out.println("5. Go Back");

  }

  @Override
  public void printLine(String line) {
    System.out.println(line);
  }

  @Override
  public void printLines(String[] lines) {
    for (int i = 0; i < lines.length; i++) {
      System.out.println(lines[i]);
    }
  }

  @Override
  public void displayError() {
    System.out.println("Please re-enter a choice number from the given list");
  }

}