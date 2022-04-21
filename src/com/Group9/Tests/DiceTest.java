package com.Group9.Tests;

import com.Group9.model.Dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void rollDice() {
        //instantiate a Dice object
        Dice testDice = new Dice();
        // Roll the dice by calling its rollDice() method
        testDice.rollDice();
        // create the range bounds in which the random dice value must lie 1-4.
        int high = 4;
        int low = 1;
        // store random dice value in a int variable
        int randomDiceValue = testDice.getValue();
        // check the random value against the condition
        // here check that random dice roll value by comparing it with the higher bound 4
        assertTrue(randomDiceValue <=4);
        // here check the random dice roll value by comparing with the lower bound 1
        assertTrue(randomDiceValue>=1);

    }
}