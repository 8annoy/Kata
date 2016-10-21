package account;

import java.time.LocalDate;

/**
 * Created by eitannoy on 10/19/16.
 */
public class AccountTransaction {

    private LocalDate date;
    private int amount;
    private int balance;

    public LocalDate getDate() {return date;}

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    public AccountTransaction(LocalDate date, int amount, int newBalance) {
        this.date = date;
        this.amount = amount;
        this.balance = newBalance;
    }
}
