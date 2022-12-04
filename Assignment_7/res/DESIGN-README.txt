The implementation of the application is based on MVC Architecture.

----------Changes in Assignment 6----------------------------------------------------------
In this Assignment we added UI implementation and strategy based investment to our project.
*   To add the Swing UI integration in our project, we added one interface named SwingView in our view
*   module and is implemented by a view class SwingViewImpl.
*   Similarly, we added another interface in controller named SwingController and is implemented by
*   SwingControllerImpl.
*   Inorder to handle the strategy based investment, we created another Interface named FixedInvestment
*   in our model which is implemented by the model class FixedInvestmentImpl.
*   For future strategy, another method could be added to the interface and the respective class
*   could implement it, no further changes to the code would be needed.
*   In order to handle both the text based and Swing UI we added an argument  to the main method as
*   "Text" and "Swing" to switch between both the UI.
*   An additional functionality of caching data is incorporated into our project to reduce API Calls
    throughout the duration of the running program.
*   Also for this, assignment we implemented an additional feature of incorporating the Swing based
*   Performance Graph into our current project.
*   For the performance graph UI, we added another interface in our view module named PerformanceGraphView
*   which is implemented by the class PerformanceGraphViewImpl.
------------------------------------------------------------------------------------------
*   The source folder contains packages for model, controller and view.
*   Inside the packages respective interfaces are present.
*   Controller:
*   The controller package has 3 classes namely, BasePortfolioControllerImpl,FlexiblePortfolioControllerImpl
*   PortfolioControllerImpl and all 3 classes implement a single interface PortfolioController.

    Changes in controller Design
*   We incorporated a new controller class for the flexible portfolio and also created a base controller
*   for the entry point of the program.
*   The base controller will keep switching calls to and fro from FlexiblePortfolio and Non Flexible Portfolio.
*   The attached class diagram is also provided for better understanding.

    Model:
*   The model contains the classes for Flexible and Non Flexible portfolios namely,
    FlexiblePortfolioImpl and PortfolioModelImpl and they have their respective interfaces FlexiblePortfolio and
    PortfolioModel.

    Changes in model design.
*   Inorder to use the existing functionality of non flexible portfolio inside the flexible portfolio,
*   we have created an abstract class StockPortfolioImpl which implements the StockPortfolio interface.
*   This interface contains only the common functionalities between the two classes and both the classes
    extend the abstract class.
*   Apart from this, we have kept a seperate API interface APIData, which contains API methods.
*   VantageAPIData class implements this interface.
*   This interface thus, provides the feature to use multiple API inside the model, only an independent
    class would be added which implements the APIData interface.
*   Similar to this we have created seperate FileIO interface, and FileIOImpl implements this interface.
*   With this interface, the IO operations are seperated from the model and the interface provides the feature to
*   work with files with different data format.

    View:
*   The view interface DisplayPortfolio is used for implemtation of DisplayPortfolioImpl.
*   Only single class and interface are used to handle the UI for both Flexible and Non Flexible.
*   For view the existing class and interface were reused, with no major changes.
