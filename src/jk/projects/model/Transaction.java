package jk.projects.model;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by JK on 8/5/2018.
 * This is for personal use. All Rights Reserved.
 */
public abstract class Transaction {

    public static final int WITHDRAW = 1;
    public static final int DEPOSIT = 2;

    protected double amount;
    protected String dateTime;
    protected Account account;

    public Transaction(double amount, String time, Account account) {
        this.amount = amount;
        this.dateTime = time;
        this.account = account;
    }

    public abstract void updateAcctBalance() throws Exception;


}
