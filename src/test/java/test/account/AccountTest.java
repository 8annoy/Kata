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

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private Account account;
    private DateProvider mockDateProvider;

    @Before
    public void setupStream() {
        System.setOut(new PrintStream(outStream));
    }

    @Before
    public void setupAccount() {
        account = new Account(new DateProviderImpl());
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
                today() + " | 124    | 124",
                today() + " | 212    | 336",
                today() + " | 2      | 338",
                today() + " | 1663   | 2001");
    }

    @Test
    public void shouldAllignAmountColumnByLongestAmount() {
        account.deposit(1);
        validatePrint(
                "Date       | Amount | Balance",
                today() + " | 1      | 1");
        account.deposit(2222222);
        validatePrint(
                "Date       | Amount  | Balance",
                today() + " | 1       | 1",
                today() + " | 2222222 | 2222223");
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
                "11/22/2016 | 3      | 3",
                "12/21/2016 | 4      | 7");
    }

    private String today() {
        return LocalDate.now().format(FORMATTER);
    }

    private void validatePrint(String... expected) {
        account.printStatement();
        Assert.assertArrayEquals("Expected: " + Arrays.asList(expected) + "\nActual: " + outStream.toString(), expected, outStream.toString().split("\n"));
        outStream.reset();
    }

}
