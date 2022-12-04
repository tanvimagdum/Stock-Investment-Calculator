# PDPAssignment4
# Group Member
# Bhupesh Patil
# Hemraj Mahadeshwar

For Flexible Portfolio
Our program offers the following utilities to the user for flexible portfolios.
1.Create Portfolio
2.Examine Portfolio
3.Buy Shares
4.Sell Shares
5.Get Cost Basis
6.Get Total Valuation
7.Display Flexible Portfolio Graph
8.Upload Flexible Portfolio

In create portfolio we ask the user to input the symbol and quantity,date and commission fee of the stock of his/her choice.
After performing validations, the portfolio file is created for all valid stocks.

In examine portfolio, we offer the user a list of existing portfolios and the user can choose any one file to display at a time.
while examining file user has to give the date up till which the portfolio data will be displayed.
we then display the stock symbols and quantity for the corresponding portfolio.

In buy and sell shares, we input the stock symbol, quantity, buy/sell date and commission from the user and perform validations.
Upon successful validations we display the success or error message to the user accordingly.

In cost basis, we input the date from the user up till which the cost basis is to be displayed.
The cost basis includes the amount invested in the portfolio and the commission fee for each buy and sell transaction.
The cost basis of the portfolio is then displayed in one single line.

In Get Valuation, we offer the user to get valuation of a portfolio from a list of portfolios.
After that we ask the user to provide a date to get the portfolio valuation on that particular date.
User is able to see the symbol,quantity,valuation date,price,price*quantity.
And lastly the total valuation of the portfolio is displayed at the bottom of the table.

In display graph option, we  ask the user for two interval dates, then we display the valuation of the portfolio 
between those dates using asterisk pattern.

Loading a portfolio, we ask the user to provide the path of the file he/she wants to load (in Symbol,Quantity format).
Upon path validation and file parsing, we create a portfolio of the provided file, which the user can access by doing examine portfolio.

All the invalid checks for the sequence of operations are provided.



For Inflexible Portfolio

Our program offers the following utilities to the user for inflexible portfolios.
1.Create Portfolio
2.Examine Portfolio
3.Get Valuation
4.Load a Portfolio

In create portfolio we ask the user to input the symbol and quantity of the stock of his/her choice.
After performing validations, the portfolio file is created for all valid stocks.

In examine portfolio, we offer the user a list of existing portfolios and the user can choose any one file to display at a time.
we then display the stock symbols and quantity for the corresponding portfolio.

In Get Valuation, we offer the user to get valiuation of a portfolio from a list of portfolios.
After that we ask the user to provide a date to get the portfolio valuation on that particular date.
User is able to see the symbol,quantity,valuation date,price,price*quantity.
And lastly the total valauation of the portfolio is displayed at the bottom of the table.

Loading a portfolio, we ask the user to provide the path of the file he/she wants to load (in Symbol,Quantity format).
Upon path validation and file parsing, we create a portfolio of the provided file, which the user can access by doing examine portfolio.

All the invalid checks for the sequence of operations are provided.
