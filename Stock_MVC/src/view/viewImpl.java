package view;

public class viewImpl implements viewInterface {

  @Override
  public void showWelcomeScreen() {

    System.out.println("Welcome!");
    System.out.println("Please enter the option number.");
    System.out.println("1. Load a portfolio");
    System.out.println("2. Build a new portfolio");
    System.out.println("3. View a portfolio");
    System.out.println("4. Save a portfolio");
    System.out.println("5. Save all portfolios");

  }

  @Override
  public void showLoadScreen() {

    System.out.println("Load a portfolio");
    System.out.println("Please enter the option number.");
    System.out.println("1. Enter the portfolio filename");
    System.out.println("2. Go Back");

  }

  @Override
  public void showBuildScreen() {

    System.out.println("Build a new portfolio");
    System.out.println("Please enter the option number.");
    System.out.println("1. Enter the ticker number. Press enter. Enter count of shares.");
    System.out.println("2. Finalize the portfolio");
    System.out.println("3. Go Back");

  }

  @Override
  public void showPortfolioScreen() {

    System.out.println("View a portfolio");
    System.out.println("Please enter the option number.");
    System.out.println("1. View the stocks list in the portfolio");
    System.out.println("2. View the value of portfolio on a certain date");
    System.out.println("3. Save the viewed portfolio");
    System.out.println("4. Go Back");

  }

  @Override
  public void displayError() {
    System.out.println("Please re-enter the option number from the given list.");
  }

}
