package com.example;

public class Deposit {
    //Attribute deposit is set to 0
    private double deposit = 0;

    //Getter
    public double getDeposit() {
        return deposit;
    }

    //Setter
    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    //Constructor
    public Deposit(double deposit) {
        this.deposit = deposit;
    }

}
