package com.Group9.Tests;

import com.Group9.GameConstants;
import com.Group9.model.ChanceSquare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceSquareTest {

    @Test
    void createChance() {
        // Instantiate a Chance Square object
        ChanceSquare chanceSquare = new ChanceSquare(GameConstants.CHANCE_SQUARE,0,0,0,0);
        // create the chance. The chance will tell us wheather the player will gain amount or lose amount
        // chance of 1 means the user will gain amount
        // chance of -1 means the user will lose amount
        // the chance of 1 or -1 is created using a random number.
        chanceSquare.createChance();
        // to see that the chance is successfully set to either 1 or -1, assertEqual is used.
        assertTrue(chanceSquare.getChance() ==1 || chanceSquare.getChance() == -1);
        // the output of this  test will confirm weather the chance of player getting a random amount or losing a random
        // amount has been successfully created or not.
    }
}