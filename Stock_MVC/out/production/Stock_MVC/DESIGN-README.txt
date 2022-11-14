Design of the application --

The program is designed using the MVC architecture. The folder structure is as below for design (src) -

Stocks_MVC
  src
    controller
	    inputController
	    inputControllerImpl
	    portfolioController
	    portfolioControllerImpl
    model
	    API
	    APIImpl
	    portfolio
	    portfolioImpl
	    portfolioManager
	    portfolioManagerImpl
	    Stock
	    StockInterface
    view
	    viewImpl
	    viewInterface
  test



1. The portfolioController interacts with the inputController and portfolioManager (in model). It sends the data to model for processing.

2. The inputController interface which represents the continuous input method for the menu options. This is where the main method lies. It does not interact with the model. It only talks to the model through the portfolioController.

3. The model has all the methods to build, view, load, save portfolios. The portfolioManager is responsible for virtually all operations relating to portfolios. 

4. The portfolio object will contain a list of Stock objects and a String identifier. The Stock object contains a String and a Float (it is the controller that enforces the integer values).

5. The portfolio is final on construction, and it is built only through its builder method.

6. The view represents the interaction with the user through the Text-line interface. It gets commands by both portfolioController and inputController.

7. The class and some code for working with API's is there, but currently unfinished and unused.