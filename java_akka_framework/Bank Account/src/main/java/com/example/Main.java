package com.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.io.IOException;

class Main {

    public static void main(String[] args) {

        // Create an actor system
        ActorSystem system = ActorSystem.create();

        // Create simulator and Bank Account Actors
        ActorRef simulator = system.actorOf(Props.create(Simulator.class));
        ActorRef bankAccount = system.actorOf(Props.create(BankAccount.class),"MLT1234");

        //Pass the bank account and the number of transactions to the SimulatorObject as param
        SimulatorObject simulatorObject = new SimulatorObject(bankAccount,10);

        //Tell simulator the number of transactions and the bank account
        simulator.tell(simulatorObject, simulator);

        //Terminate the system
        simulator.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());

        try {
            System.out.println("Press ENTER twice to end program.");
            System.in.read();
        }
        catch (IOException ignored) { }
        finally {
            system.terminate();
            System.out.println("Terminated.");
        }
    }

}
