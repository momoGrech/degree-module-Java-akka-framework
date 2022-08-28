package com.example;

import akka.actor.ActorRef;

public class SimulatorObject {
    //Attributes
    private int transactions;
    private ActorRef bankAccount;

    //Getters
    public int getTransactions(){
        return transactions;
    }
    public  ActorRef getBankAccount(){
        return bankAccount;
    }

    //Constructor
    public SimulatorObject(ActorRef bankAccount, int transactions) {
        this.bankAccount = bankAccount;
        this.transactions = transactions;
    }
}
