/**
 * Created by eitannoy on 10/11/16.
 */

package test.account;

import account.DateProvider;
import account.DateProviderImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import account.Account;
import org.mockito.Mockito;

public class AccountTest {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private Account account;
    private DateProvider mockDateProvider;

    @Before
    public void setupStream() {
        System.setOut(new PrintStream(outStream));
    }

    @Before
    public void setupAccount() {
        mockDateProvider = Mockito.mock(DateProvider.class);
        Mockito.when(mockDateProvider.now()).thenReturn(LocalDate.parse("01/01/2016", FORMATTER));
        account = new Account(mockDateProvider);
    }

    @After
    public void resetStream() {
        System.setOut(null);
    }

    @Test
    public void shouldPrintStatementForEmptyAccount() {
        validatePrint("Date | Amount | Balance");
    }

    @Test
    public void shouldTrackDeposits() {
        account.deposit(124);
        account.deposit(212);
        account.deposit(2);
        account.deposit(1663);
        validatePrint(
                "Date       | Amount | Balance",
                "01/01/2016 | 1663   | 2001",
                "01/01/2016 | 2      | 338",
                "01/01/2016 | 212    | 336",
                "01/01/2016 | 124    | 124");
    }

    @Test
    public void shouldAlignAmountColumnByLongestAmount() {
        account.deposit(1);
        validatePrint(
                "Date       | Amount | Balance",
                "01/01/2016 | 1      | 1");
        account.deposit(2222222);
        validatePrint(
                "Date       | Amount  | Balance",
                "01/01/2016 | 2222222 | 2222223",
                "01/01/2016 | 1       | 1");
    }

    @Test
    public void shouldPrintStatementAfterEachTransaction() {
        account.deposit(1);
        validatePrint("Date       | Amount | Balance",
                "01/01/2016 | 1      | 1");
        account.deposit(2);
        validatePrint("Date       | Amount | Balance",
                "01/01/2016 | 2      | 3",
                "01/01/2016 | 1      | 1");
        account.deposit(3);
        validatePrint("Date       | Amount | Balance",
                "01/01/2016 | 3      | 6",
                "01/01/2016 | 2      | 3",
                "01/01/2016 | 1      | 1");
    }

    @Test
    public void shouldTrackDepositsFromDifferentDays() {
        mockDateProvider = Mockito.mock(DateProvider.class);
        account = new Account(mockDateProvider);

        Mockito.when(mockDateProvider.now()).thenReturn(LocalDate.parse("11/22/2016", FORMATTER));
        account.deposit(3);
        Mockito.when(mockDateProvider.now()).thenReturn(LocalDate.parse("12/21/2016", FORMATTER));
        account.deposit(4);
        validatePrint(
                "Date       | Amount | Balance",
                "12/21/2016 | 4      | 7",
                "11/22/2016 | 3      | 3");
    }

    @Test
    public void shouldTrackWithdrawal() {
        account.withdraw(42);
        validatePrint(
                "Date       | Amount | Balance",
                "01/01/2016 | -42    | -42");
    }

    @Test
    public void shouldTrackDepositThenWithdrawal() {
        account.deposit(112);
        account.withdraw(35);
        validatePrint(
                "Date       | Amount | Balance",
                "01/01/2016 | -35    | 77",
                "01/01/2016 | 112    | 112");
    }

    private void validatePrint(String... expected) {
        account.printStatement();
        Assert.assertArrayEquals("Expected: " + Arrays.asList(expected) + "\nActual: " + outStream.toString(), expected, outStream.toString().split("\n"));
        outStream.reset();
    }

}
