package com.Group9.model;

import com.Group9.GameConstants;

import java.io.Serializable;
import java.util.Random;

public class ChanceSquare extends Square implements Serializable {
    private int chance; // value 1 means gain money , value -1 means lose money
    private int lossAmount;
    private int gainAmount;
    private int amount; // the resultant amount from the chance square

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ChanceSquare(String name, int chance, int lossAmount, int gainAmount, int amount) {
        super(name);
        this.chance = chance;
        this.lossAmount = lossAmount;
        this.gainAmount = gainAmount;
        this.amount = amount;
    }

    // create chance weather gain or lose money
    public void createChance(){
        double chancetype = Math.random();
        if(chancetype > 0.5){
            setChance(1);
        }
        else{
            setChance(-1);
        }
    }
    //Calculate amount based on the chance type
    public void calculateAmount(){

        if (chance == 1) {
            calculateGainAmount(); // Calculate the random amount gained by the player
        }
        else{
            calculateLossAmount(); // calculate the random amount lost by the player
        }

    }
    // method to calculate the amount to be gained
    public void calculateGainAmount(){
        Random rand = new Random();
        int amount = rand.nextInt(201);
        // check the random amount is a multiple of 10
        while(amount % 10 != 0 || amount == 0) {
            amount = rand.nextInt(201);
        }
        setGainAmount(amount);
        setAmount(amount);
    }
    // method to calculate the amount to be lost
    public void calculateLossAmount(){
        Random rand = new Random();
        int amount = rand.nextInt(300);
        // check the random amount is a multiple of 10
        while(amount % 10 != 0 || amount == 0) {
            amount = rand.nextInt(300);
        }

        amount = amount * -1;

        setLossAmount(amount);
        setAmount(amount);
    }

    public String getSquareType(){
        return GameConstants.CHANCE_SQUARE;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getLossAmount() {
        return lossAmount;
    }

    public void setLossAmount(int lossAmount) {
        this.lossAmount = lossAmount;
    }

    public int getGainAmount() {
        return gainAmount;
    }

    public void setGainAmount(int gainAmount) {
        this.gainAmount = gainAmount;
    }

    @Override
    public String toString() {
        return "ChanceSquare{" +
                "chance=" + chance +
                ", lossAmount=" + lossAmount +
                ", gainAmount=" + gainAmount +
                ", amount=" + amount +
                '}';
    }
}
