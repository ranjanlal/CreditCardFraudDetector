package com.afterpay.credit.service;

import com.afterpay.credit.model.CreditCardTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.afterpay.credit.util.CreditCardUtil.*;
import static java.util.stream.Collectors.toList;

/**
 * Service class for Fraud Detection algorithm
 */
public class CreditCardFraudDetectorService {

    /**
     * Identifies fraudulent credit cards from a given list of credit card transactions (CSV)
     *
     * @param transactions   List of credit card transactions (comma separated string of elements)
     * @param searchDate     date for which to look up for fraudulent credit cards
     * @param thresholdPrice upper limit for sum of prices for a credit card's transactions
     * @return a list of hashed credit card numbers that have been identified as fraudulent for given searchDate
     */
    public static List<String> identifyFraudulentCreditCards(final List<String> transactions, final String searchDate, final String thresholdPrice) {
        List<CreditCardTransaction> creditCardTransactions = parseCreditCardTransactions(transactions);
        LocalDate transactionDate = parseTransactionDate(searchDate);
        BigDecimal priceThreshold = parsePrice(thresholdPrice);

        List<CreditCardTransaction> transactionsOnSearchDate = creditCardTransactions.stream()
                .filter(cct -> cct.getTransactionTimestamp().toLocalDate().isEqual(transactionDate))
                .collect(toList());

        Map<String, BigDecimal> sumPricesPerCard = transactionsOnSearchDate.stream()
                .collect(Collectors.toMap(txn -> txn.getCreditCardNumber(), txn -> txn.getPrice(),
                        (price1, price2) -> price1.add(price2), LinkedHashMap::new));

        List<String> cardsAbovePriceThreshold = sumPricesPerCard.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(priceThreshold) > 0)
                .map(entry -> entry.getKey())
                .collect(toList());

        return cardsAbovePriceThreshold;
    }
}
