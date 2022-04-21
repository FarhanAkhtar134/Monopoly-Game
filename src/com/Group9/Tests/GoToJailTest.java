package com.Group9.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.Group9.controller.PlayerController;
import com.Group9.model.GoToJail;
import com.Group9.model.Player;

import org.junit.jupiter.api.Test;

class GoToJailTest {

	@Test
	void jailGoToTest1() {
		// Instantiate
		PlayerController controller = new PlayerController(null, null, null, 1);
		// Player(String name, double money, int token, double startingSalary, ArrayList<Propertysquare> playerProperty, boolean isInJail, int playerTurnTries,int rolledDoublesResult)
		// setting the player position on GoToJail and isInJail into false at first
    	Player testplayer = new Player(null, 1500, 15, 0, null, false, 1, 1,1);
		controller.evaluateRollAction(15, testplayer);
		
		// check whether the player position moved to jail, 5, and isInJail is set into true
        assertTrue(testplayer.getToken() == 5 && testplayer.isInJail() == true );
	}

	@Test
	void jailVisitingTest2() {
		// Instantiate
		PlayerController controller = new PlayerController(null, null, null, 1);
		// Player(String name, double money, int token, double startingSalary, ArrayList<Propertysquare> playerProperty, boolean isInJail, int playerTurnTries,int rolledDoublesResult)
		// setting the player position on jail and isInJail into false at first
    	Player testplayer = new Player(null, 1500, 5, 0, null, false, 1, 1,1);
		controller.evaluateRollAction(5, testplayer);
		
		// check whether the player position is on jail, 5, but isInJail is still set as false
        assertTrue(testplayer.getToken() == 5 && testplayer.isInJail() == false );            	
	}

}
