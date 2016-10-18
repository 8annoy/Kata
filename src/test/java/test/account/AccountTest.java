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

import account.Account;

public class AccountTest {

    private final String EMPTY_STATEMENT = "New account, balance is 0.\n";
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @Before
    public void setupStream() {
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void resetStream() {
        System.setOut(null);
    }

    @Test
    public void shouldPrintStatementForEmptyAccount() {
        Account account = new Account();
        account.printStatement();
        Assert.assertEquals(EMPTY_STATEMENT, outStream.toString());
    }
}
