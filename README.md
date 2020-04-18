# CreditCardFraudDetector

Consider the following credit card fraud detection algorithm:

## Requirements

1. A credit card transaction is comprised of the following elements:

    - hashed credit card number
    - timestamp - of format 'year-month-dayThour:minute:second'
    - price - of format 'dollars.cents'

2. Transactions are to be received as a comma separated string of elements 

    - eg. '10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00'

3. A credit card will be identified as fraudulent if:

    - the sum of prices for a unique hashed credit card number, for a given day, exceeds the price threshold T

4. Write a method on a class, which, when given:

    - a list transactions
    - a date, and 
    - a price threshold T

    returns:

    - a list of hashed credit card numbers that have been identified as fraudulent for that day 

>>> Expectations

- We expect to see tests that prove your code works.

- We are looking for pragmatic, testable and maintainable code. 

- If in doubt, refer to the KISS principle. Good luck!

>>> Quote

- Any fool can write code that a computer can understand. Good programmers write code that humans can understand.

    ----- Martin Fowler
