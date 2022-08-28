package com.example;

import akka.actor.AbstractActor;
import java.util.concurrent.ThreadLocalRandom;

import akka.actor.ActorRef;

public class Simulator extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(SimulatorObject.class, this::transactions)
            .build();
    }

    //Method to generate random numbers
    public double randomNumbers(){
        double min = -1000;
        double max = 1000;
        double randomNum = ThreadLocalRandom.current().nextDouble(min, max + 1);

        return  randomNum;
    }

    //Method to display the bank account details and transaction numbers
    public void displayInfo(SimulatorObject object){
        System.out.println("Number of transactions: "+object.getTransactions()+"\nAccount number: "+object.getBankAccount().path().name());
    }

    //Method to handle transactions and pass them to Bank account Actor
    public void transactions(SimulatorObject object){
        //Display the bank account details
        displayInfo(object);

        //The number of transactions
        int length = object.getTransactions();

        //We use bankAccount actorRef which is referring to the BankAccount Actor created in the main
        ActorRef bankAccount = object.getBankAccount();

        /*Based on the number of transactions:
          differentiate withdrawal from deposit,
          pass each value as a message to the bank account*/
        for (int index = 0; index < length; index++){
            //Call generate number method and pass the value to random number variable
            double randomNumber = randomNumbers();

            //Condition to differentiate deposits from withdrawals
            if (randomNumber > 0){
                //Create a new deposit message and pass the random number as param
                Deposit deposit = new Deposit(randomNumber);
                //Send the message to the bankAccount
                bankAccount.tell(deposit, getSelf());
            }
            else if(randomNumber < 0){
                //Create a new withdrawal message and pass the random number as param
                Withdrawal withdrawal = new Withdrawal(randomNumber);
                //Send the message to the bankAccount
                bankAccount.tell(withdrawal, getSelf());
            }
        }
        //Terminate the system once the above transactions have finished
        bankAccount.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
    }
}
