Features of the program that are completely working --

1. User is able to see the menu and select the operation by his choice. Operations shown to the user are (names of operations may vary) -

	a. Load Portfolio
		1. Enter filename to load
		2. Go to previous menu

	b. Build Portfolio
		1. Start building portfolio
		2. Go to previous menu

	c. View Portfolio
		1. View the contents of a portfolio
		2. View the value of portfolio on certain date
		3. View the value of portfolio when user enter stocks manually through TLI
		4. Go to previous menu

	d. Save Portfolio
	e. Save All Portfolios
	f. Exit from the application

2. The features that are working option-wise -

	a. Load Portfolio 
		1. User is able to load the file into code from local system. 
		2. After loading the screen displays the contents of loaded portfolio.
		3. User is able to navigate back to main menu.

	b. Build Portfolio
		1. User is able to give a name for the portfolio in order to distinguish between different portfolios.
		2. User is able to enter a ticker number and count of stocks for various tickers for multiple number of times.
		3. For ticker validation, user gets a warning if invalid ticker is entered.
		4. User can exit entering the contents of portfolio by entering 'Done'.
		5. User can navigate back to the main menu.

	c. View Portfolio 
		1. User can view contents for the created portfolios.
		2. User can find value of a portfolio by a certain date from 2010-01-01 to 2018-03-27 (YYYY-MM-DD).

		NOTE - for options 2 the price used is the closing price.

		3. User can find the value of a portfolio on 2022-10-31 (YYYY-MM-DD).

		NOTE - for option 3 the price is the last trade price.

		4. User has an option to enter stock prices manually and then view portfolio contents.
		5. User can navigate back to the main menu.

	d. Save Portfolio
		1. User can save a portfolio in .csv file.
	
	e. Save All Portfolios
		1. Save all the portfolios created by the user at once in their respective .csv files.

3. There is an API interface and implementation in the model package. But currently it is unfinished. No class calls the API. 

4. The following are the only stocks that the program would recognize and get the value by date. Although, user can enter custom ticker symbols after a warning.

A,
AAL,
AAP,
AAPL,
ABC,
ABMD,
ABT,
ACN,
ADBE,
ADI,
ADM,
ADP,
ADSK,
AEE,
AEP,
AES,
AFL,
AIG,
AIZ,
AJG,
AKAM,
ALB,
ALGN,
ALK,
ALL,
AMAT,
AMD,
AME,
AMGN,
AMP