package com.afterpay.credit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.afterpay.credit.util.CreditCardUtil.*;

/**
 * Model class, represents a credit card transaction
 */
public class CreditCardTransaction {

    private String creditCardNumber;
    private LocalDateTime transactionTimestamp;
    private BigDecimal price;

    public CreditCardTransaction(final String transaction) {
        List<String> transactionItems = parseTransaction(transaction);
        this.creditCardNumber = transactionItems.get(0);
        this.transactionTimestamp = parseTransactionTimeStamp(transactionItems.get(1));
        this.price = parsePrice(transactionItems.get(2));
    }

    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public LocalDateTime getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
