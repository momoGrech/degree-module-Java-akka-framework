package com.example;

import akka.actor.AbstractActor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankAccount extends AbstractActor {
    //Attribute balance is set to 0
    private double balance = 0;

    //Function which sets the balance to 100 and return the value
    private double initiateBalance() {
        balance = 100;
        return balance;
    }

    //This override method will be invoked as soon as the Bank account actor is created
    @Override
    public void preStart(){
        System.out.println("\nBalance starts at: £"+roundValue(initiateBalance()));
    }
    //This override method will be invoked as soon as the Bank account actor is stopped
    @Override
    public void postStop(){
        System.out.println("\n***   ***   ***");
        System.out.println("\nThe program terminated.\nFinal Balance: £"+roundValue(balance));
    }
    //This method defines BankAccount actor's initial receive behaviour
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Deposit.class, this::deposit)
            .match(Withdrawal.class, this::withdrawal)
            .build();
    }

    //Method to round up a number to 2 decimal places
    public static double roundValue(double value) {
        BigDecimal roundBalance=new BigDecimal(value).setScale(2,RoundingMode.HALF_DOWN);
        return roundBalance.doubleValue();
    }

    //Method to deposit a value to the balance and print the update
    public synchronized void deposit(Deposit number){
        balance += number.getDeposit();
        System.out.println("\nDeposit: £"+ roundValue(number.getDeposit()) + " >> Current Balance: £"+ roundValue(balance));
    }

    //Method to withdraw a value from the balance and print the update
    public synchronized void withdrawal(Withdrawal number){
        balance += number.getWithdrawal();
        System.out.println("\nWithdrawal: £"+ roundValue(number.getWithdrawal()) + " >> Current Balance: £"+ roundValue(balance));
    }
}
