Changelog:
1. Portfolio: removed returnList() signature and code
Why: because the method was unused, and we don't want to pass the actual list anyway

2. Portfolio Manager: removed returnPortfolio() signature and made private in code
Why: because only the model should have access to the portfolio objects themselves

3. Portfolio Controller: removed returnPortfolio() signature and code
Why: same reason as above

4. Input Controller: Added contentsHelper()
Why: to reduce bloat in the switch statement

5. Portfolio Manager: removed getPortfolioContents() signature and code
Why: now only raw data is passed from model to controller, and the controller formats it

6. Portfolio Controller: removed getPortfolioContents() signature and code
Why: same as reason 5

7. Portfolio Manager: readPortfolioFile() and buildPortfolio() now return strings
Why: these methods now return the names given to the portfolios, so that the input controller can then use them

8. Portfolio Controller: readPortfolioFile() and buildPortfolio() now return strings
Why: to accommodate change 7

9. Input Controller: scanner "sc" is now a field in the class
Why: we had issues during testing owing to creating multiple scanners in different places, this resolves those issues

10. Portfolio Controller: buildPortfolio(), manualValuation(), and selectPortfolio() now take a scanner as input
Why: in keeping with change 9

11. Persistence: added Persistence class
Why: persistence goes beyond just read/write from/to disc, and having it as a class makes it more flexible

12. Portfolio Manager: now takes a persistence object in construction
Why: offloads persistence selection to the main method, allowing different persistence objects to be passed

13. Portfolio Controller: now takes the model in via constructor
Why: this change allows easier testing of the PortfolioController class

14. Input Controller: now constructs a model and passes it to the Portfolio Controller
Why: this change allows easier testing of the PortfolioController class

15. Portfolio Manager: getPortfolioValue now returns floats representing prices,
    string formatting moved to Input Controller
Why: it is preferable for communications between model and controller to consist of raw data, let the controller format

16. Portfolio Manager: removed getPortfolioValueLatest()
Why: this function was made irrelevant by the incorporation of the working API. It was very limited to begin with

17. Portfolio Manager: added getFlexPortfolioNames()
Why: there are times we want to choose from among only the flexible portfolios, rather than all the portfolios

18. Portfolio Controller: removed references to getPortfolioValueLatest()
Why: to accommodate change 16

19. API: removed public validateTicker() method
Why: to reduce API calls, we have moved this to portfolio manager which uses a list of tickers valid on Nov 11, '22

20. API: was moved to the controller package
Why: it is more appropriate to have the API in the controller, because API is IO

21. Portfolio Controller: API calls passed to the model now also pass the API to the model
Why: to accommodate the above change

22. Portfolio Manager: getCostBasis(), portfolioPerformance(), and getPortfolioValue() now take an API object as input
Why: to accommodate moving the API from the model to the controller, later will move all API-stuff out of model

23. Input Controller: moved some functionality from switch statements to private helpers
Why: to make the switch statements somewhat more readable, future plans to further reduce the switch statements by
migrating functionality to helpers (asking for dates and selecting portfolios, e.g.)

24. Persistence: was moved to controller package
Why: Persistence is IO, thus a job for the controller

APPLICATION DESIGN --
The program's SRC folder structure is below (we also list our test files) -

Stocks_MVC
  src
    controller
        API
       	APIImpl
	    InputController
	    InputControllerImpl
	    Persistence
	    PersistenceInterface
	    PortfolioController
	    PortfolioControllerImpl
    model
	    FlexPortfolioImpl
	    FlexStock
	    Persistence
	    Portfolio
	    PortfolioImpl
	    PortfolioManager
	    PortfolioManagerImpl
	    Stock
	    StockInterface
    view
	    ViewImpl
	    ViewInterface
  test
    FlexPortfolioImplTest
    FlexStockTest
    InputControllerImplTest
    PortfolioControllerImplTest
    PortfolioImplTest
    PortfolioManagerImplTest
    StockTest
    ViewImplTest


Overall Design:
We follow an MVC architecture.
    The VIEW is the simplest aspect. It is a single class. It has a few pre-built menus to show, and then has
two methods for printing additional messages to the screen. The view is commanded only by the two controllers.

    The MODEL is primarily represented by the 'Portfolio Manager' which maintains a list of Portfolios (which
themselves have lists of Stock objects) and is responsible for the building, editing, and querying of portfolios. The
model is passed a persistence object by the controller in construction, and is also passed an API object when using
methods that value portfolios (e.g. cost basis). The portfolios themselves only have methods to get their own name and
the values held by their stock objects. The stock objects only have methods to return the data within; these objects
take generic data, but are created with <String, Float> or <String, Float, Date>. The model only "speaks" to the
'Portfolio Controller.'

    The CONTROLLER has two parts. The 'Input Controller' contains the main method which calls the primary loop. This
loop and a String field representing the menu state are the primary method of menu navigation. Most of the time the
view is receiving commands, they come from the 'Input Controller'. This controller handles taking in much of the
user input, with a few exceptions, handling errors from lower in the hierarchy, and constructing the informative
displays such as portfolio value, cost basis, and portfolio contents. The 'Portfolio Controller' has a mostly separate
function, though, since it alone talks to the model it primarily acts as a go-between for the 'Input Controller' and
the 'Portfolio Manager'. Its function beyond that role is to take looping input from the user related to building,
editing, and manually valuing portfolios. The reason was to divest 'Input Controller' from so many looping input
methods.

    The 'Input Controller' is constructed in the main loop, requiring a view, a portfolio controller, an input stream,
an output stream, and an API. The 'View' is passed the output stream. The 'Portfolio Controller' is passed the input
stream and a new 'Portfolio Manager' which itself is passed a 'Persistence' object.

    The flexible portfolio has two rules to keep in mind while constructing it. You cannot sell stock you have not
already bought in the portfolio. You cannot sell stock on a date before if not enough of that stock was previously
purchased.