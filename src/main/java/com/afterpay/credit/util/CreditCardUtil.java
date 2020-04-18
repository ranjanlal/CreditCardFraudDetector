package com.afterpay.credit.util;

import com.afterpay.credit.model.CreditCardTransaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.util.stream.Collectors.toList;

/**
 * Utility class for common methods
 */
public class CreditCardUtil {

    /**
     * Parses a List of credit card transactions into a List of Model (CreditCardTransaction) objects
     *
     * @param transactions List of credit card transactions
     * @return List of Model objects
     */
    public static List<CreditCardTransaction> parseCreditCardTransactions(List<String> transactions) {
        if (isEmpty(transactions)) throw new IllegalArgumentException("Invalid transactions list.");
        return transactions.stream().map(CreditCardTransaction::new).collect(toList());
    }

    /**
     * Parses a credit card transaction (CSV) into a List of items (String)
     *
     * @param transaction string representing a credit card transaction
     * @return List of items in the transaction
     */
    public static List<String> parseTransaction(final String transaction) {
        if (transaction == null || transaction.isEmpty())
            throw new IllegalArgumentException("Transaction has no content.");
        List<String> transactionItems = Stream.of(transaction.split(","))
                .map(String::trim)
                .collect(toList());
        if (transactionItems.size() != 3)
            throw new IllegalArgumentException("Transaction must have a format : [creditCardNumber, transactionTimestamp, price]");
        return transactionItems;
    }

    /**
     * Parses a string representing transaction date into LocalDate object
     *
     * @param transactionDate string representing transaction date in the format "year-month-day"
     * @return LocalDate object for the given date as string
     */
    public static LocalDate parseTransactionDate(final String transactionDate) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(transactionDate, ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date.");
        }
        return parsedDate;
    }

    /**
     * Parses a string representing transaction date/timestamp into LocalDateTime object
     *
     * @param transactionTimestamp string representing transaction date in the format "year-month-dayThour:minute:second"
     * @return LocalDateTime object for the given date/timestamp as string
     */
    public static LocalDateTime parseTransactionTimeStamp(final String transactionTimestamp) {
        LocalDateTime parsedTimestamp;
        try {
            parsedTimestamp = LocalDateTime.parse(transactionTimestamp, ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp.");
        }
        return parsedTimestamp;
    }

    /**
     * Parses a string representing price into a BigDecimal object
     *
     * @param price string representing in the format "dollars.cents"
     * @return BigDecimal object for given price
     */
    public static BigDecimal parsePrice(final String price) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        BigDecimal parsedPrice;
        try {
            decimalFormat.setParseBigDecimal(true);
            String formattedPrice = decimalFormat.format(Double.parseDouble(price));
            parsedPrice = (BigDecimal) decimalFormat.parse(formattedPrice);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid price.");
        }
        return parsedPrice;
    }

    /**
     * Checks if a collection is empty (or null)
     *
     * @param collection collection to test for being empty
     * @return boolean true | false
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
