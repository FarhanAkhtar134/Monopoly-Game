package com.Group9.model;

import java.util.Random;

public class Dice {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void rollDice(){
        Random random = new Random();
        int rollValue = random.nextInt(4) +1; // value of the dice is between 1-4
        setValue(rollValue);
    }
}
