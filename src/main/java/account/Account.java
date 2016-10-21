/**
 * Created by eitannoy on 10/11/16.
 */
package account;

import java.util.LinkedList;

public class Account {

    private final DateProvider dp;
    private LinkedList<AccountTransaction> transactions = new LinkedList<>();
    private int balance = 0;

    /*
    * I really hate this. Why should Account be aware of a date provider?
    * When will it ever need anything other than the real 'now'?
    * Adding this collaborator just for the sake of testability seems wrong.
    * */
    public Account(DateProvider dp) {
        this.dp = dp;
    }

    public void printStatement() {
        String[] rows = new StatementBuilder(transactions).build();
        for(String row : rows) {
            System.out.println(row);
        }
    }

    public void deposit(int amount) {
        transactions.add(new AccountTransaction(dp.now(), amount, balance += amount));
    }

}
