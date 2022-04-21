package com.Group9.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.Group9.controller.PlayerController;
import com.Group9.model.Player;

class IncometaxTest {

	@Test
	void taxTest1() {
		// Instantiate
		PlayerController controller = new PlayerController(null, null, null, 1);
		// Player(String name, double money, int token, double startingSalary, ArrayList<Propertysquare> playerProperty, boolean isInJail, int playerTurnTries,int rolledDoublesResult)
		// setting the player money as 1500 "simple case"
    	Player testplayer = new Player(null, 1500, 3, 0, null, false, 1, 1,1);
		controller.evaluateRollAction(3, testplayer);
		
		// check whether the tax calculation is right in simple example (without rounding down)
        assertTrue(testplayer.getMoney() == 1500*0.9 );            	
	}

	@Test
	void taxTest2() {
		// Instantiate
		PlayerController controller = new PlayerController(null, null, null, 1);
		// Player(String name, double money, int token, double startingSalary, ArrayList<Propertysquare> playerProperty, boolean isInJail, int playerTurnTries,int rolledDoublesResult)
		// setting the player money as 1550
    	Player testplayer = new Player(null, 1550, 3, 0, null, false, 1, 1,1);
		controller.evaluateRollAction(3, testplayer);
		
		// check whether the tax calculation is right in a specific example (need to be rounded down)
        assertTrue(testplayer.getMoney() == 1390 );
	}

}
