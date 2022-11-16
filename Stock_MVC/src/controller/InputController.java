package controller;

/**
 * This interface represents the input method for controller which asks the user input
 * to select an option from provided menu continuously.
 *
 * Changes:
 * 1. The class now has a scanner as a field. So that the same scanner can be passed around and for
 *    tests.
 * 2. Moved much functionality from switch statements to private helpers to improve readability.
 */

public interface InputController {

  /**
   * A method that starts the program and asks the user input and sends it for processing.
   */
  public void start();

}