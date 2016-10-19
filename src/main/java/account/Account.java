/**
 * Created by eitannoy on 10/11/16.
 */
package account;

import java.util.Date;
import java.util.LinkedList;

public class Account {

    private LinkedList<AccountTransaction> transactions = new LinkedList<>();
    private int balance = 0;

    public void printStatement() {
        String[] rows = new StatementBuilder(transactions).build();
        for(String row : rows) {
            System.out.println(row);
        }
    }

    public void deposit(int amount) {
        transactions.add(new AccountTransaction(new Date(), amount, balance += amount));
    }

}
