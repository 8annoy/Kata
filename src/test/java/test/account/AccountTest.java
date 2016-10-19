/**
 * Created by eitannoy on 10/11/16.
 */

package test.account;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import account.Account;

public class AccountTest {

    private final String EMPTY_STATEMENT = "New account, balance is 0.";
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private Account account;

    @Before
    public void setupStream() {
        System.setOut(new PrintStream(outStream));
    }

    @Before
    public void setupAccount() {
        account = new Account();
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
        validatePrint(
                "Date       | Amount | Balance",
                today() + " | 124    | 124",
                today() + " | 212    | 336");
    }

    private String today() {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    }

    private void validatePrint(String... expected) {
        account.printStatement();
        Assert.assertArrayEquals("Expected: " + Arrays.asList(expected) + "\nActual: " + outStream.toString(), expected, outStream.toString().split("\n"));
    }
}
