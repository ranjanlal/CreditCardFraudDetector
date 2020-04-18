package com.afterpay.credit.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static com.afterpay.credit.service.CreditCardFraudDetectorService.identifyFraudulentCreditCards;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.assertEquals;

public class CreditCardFraudDetectorServiceTest {

    private List<String> creditCardTransactions;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        creditCardTransactions = new ArrayList<>();
        creditCardTransactions.add("01d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.10");
        creditCardTransactions.add("02d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 20.97");
        creditCardTransactions.add("03d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 37.22");
        creditCardTransactions.add("04d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("01d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("06d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("07d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("08d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 30.00");
        creditCardTransactions.add("01d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
    }

    @Test
    public void shouldIdentifyFraudulentCreditCards() {

        List<String> fraudulentCreditCards = identifyFraudulentCreditCards(creditCardTransactions, "2014-04-29", "29.99");

        assertEquals(3, fraudulentCreditCards.size());
        assertEquals("01d7ce2f43e35fa57d1bbf8b1e2", fraudulentCreditCards.get(0));
        assertEquals("03d7ce2f43e35fa57d1bbf8b1e2", fraudulentCreditCards.get(1));
        assertEquals("08d7ce2f43e35fa57d1bbf8b1e2", fraudulentCreditCards.get(2));
    }

    @Test
    public void shouldReturnEmptyListWithNoFraudulentCreditCards() {

        creditCardTransactions = new ArrayList<>();
        creditCardTransactions.add("01d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.10");
        creditCardTransactions.add("02d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 20.97");
        creditCardTransactions.add("08d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 30.00");
        creditCardTransactions.add("01d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        creditCardTransactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");

        List<String> fraudulentCreditCards = identifyFraudulentCreditCards(creditCardTransactions, "2014-04-29", "50.00");

        assertEquals(0, fraudulentCreditCards.size());
    }

    @Test
    public void shouldThrowErrorWithNullTransactionsList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid transactions list.");
        identifyFraudulentCreditCards(null, "2014-04-29", "12.00");
    }

    @Test
    public void shouldThrowErrorWithEmptyTransactionsList() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid transactions list.");
        identifyFraudulentCreditCards(EMPTY_LIST, "2014-04-29", "12.00");
    }

    @Test
    public void shouldThrowErrorWithInvalidSearchDate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid date.");
        identifyFraudulentCreditCards(creditCardTransactions, "2014-04-32", "12.00");
    }

    @Test
    public void shouldThrowErrorWithInvalidThresholdPrice() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid price.");
        identifyFraudulentCreditCards(creditCardTransactions, "2017-07-28", "12.0T");
    }
}