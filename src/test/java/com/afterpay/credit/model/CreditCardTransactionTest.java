package com.afterpay.credit.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class CreditCardTransactionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldCreateValidCreditCardTransaction() {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", creditCardTransaction.getCreditCardNumber());
        assertEquals(LocalDateTime.parse("2014-04-29T13:15:54"), creditCardTransaction.getTransactionTimestamp());
        assertEquals(new BigDecimal("10.00"), creditCardTransaction.getPrice());
    }

    @Test
    public void shouldThrowErrorWithNullTransaction() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Transaction has no content.");
        new CreditCardTransaction(null);
    }

    @Test
    public void shouldThrowErrorWithEmptyTransaction() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Transaction has no content.");
        new CreditCardTransaction("");
    }

    @Test
    public void shouldThrowErrorWithInsufficientItemsInTransaction() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Transaction must have a format : [creditCardNumber, transactionTimestamp, price]");
        new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54");
    }

    @Test
    public void shouldThrowErrorWithExtraItemsInTransaction() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Transaction must have a format : [creditCardNumber, transactionTimestamp, price]");
        new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 12.00, EXTRA_ELEMENT");
    }

    @Test
    public void shouldThrowErrorWithInvalidTransactionTimeStamp() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid timestamp.");
        new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2, 2014-12-32T13:15:54, 10.00");
    }

    @Test
    public void shouldThrowErrorWithInvalidPrice() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid price.");
        new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.1012m");
    }

}
