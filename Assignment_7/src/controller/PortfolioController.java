package controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * {@code PortfolioController} represents the controller part of the architecture.
 * This controller has the single driver method that executes the menu-driven operations.
 */
interface PortfolioController {
  /**
   * This method is the driver method to execute case wise operations.
   * The cases inside this method includes features such as creating portfolio, examining portfolio,
   * Getting the valuation of the portfolio and allowing user to load the portfolio.
   *
   * @throws ParseException if error in json parsing.
   * @throws IOException    if unable to parse string.
   * @throws NoSuchFieldException if json field is not found..
   * @throws ParseException if error in json parsing.
   */
  void startProgram() throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException;



}
