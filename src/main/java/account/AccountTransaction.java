package account;

import java.util.Date;

/**
 * Created by eitannoy on 10/19/16.
 */
public class AccountTransaction {

    private Date date;
    private int amount;
    private int balance;

    public Date getDate() {return date;}

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    public AccountTransaction(Date date, int amount, int newBalance) {
        this.date = date;
        this.amount = amount;
        this.balance = newBalance;
    }
}
