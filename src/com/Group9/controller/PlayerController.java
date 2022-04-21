package com.Group9.controller;

import com.Group9.GameConstants;
import com.Group9.model.*;
import com.Group9.view.Boardview;
import com.Group9.view.Playerview;

import java.beans.PropertyChangeSupport;
import java.sql.SQLOutput;
import java.util.*;

public class PlayerController {

    private ArrayList<Player> players;
    private ArrayList<Square> monopolyBoard; // this is the game board
    private Playerview playerview;
    private int Round;


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Square> getMonopolyBoard() {
        return monopolyBoard;
    }

    public void setMonopolyBoard(ArrayList<Square> monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }

//    public SaveGameController getSaveGameController() {
//        return saveGameController;
//    }
//
//    public void setSaveGameController(SaveGameController saveGameController) {
//        this.saveGameController = saveGameController;
//    }

    public Playerview getPlayerview() {
        return playerview;
    }

    public void setPlayerview(Playerview playerview) {
        this.playerview = playerview;
    }

    public PlayerController(ArrayList<Player> players, Playerview playerview, ArrayList<Square> board, int round) {
        this.players = players;
        this.playerview = playerview;
        this.monopolyBoard = board;
        Round = round;
       // this.saveGameController = saveGameController;
    }

    public int getRound() {
        return Round;
    }

    public void setRound(int round) {
        Round = round;
    }

    public void displayBoardView(int Round){
        Boardview boardview = new Boardview();
        boardview.displayGameBoard(Round);
    }


    public void displayUpdatedPlayerView() {
        for (int playernumber = 0; playernumber < players.size(); playernumber++) {
            playerview.displayPlayerView(
                    players.get(playernumber).getPlayerIndex(),
                    players.get(playernumber).getName(),
                    players.get(playernumber).getMoney(),
                    players.get(playernumber).getToken(),
                    players.get(playernumber).getProperty()
            );

            //System.out.printf("%s%d%s%s%n","Player number ",playernumber+1," property is : ",players.get(playernumber).getProperty());
        }

    }

    public Map<Integer, Boolean> startRound(int RoundNumber, int startPlayerNumber){
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(RoundNumber,false);
        int RoundNum = RoundNumber;
        boolean IsRoundQuit = false; // this will check when all players have taken turn so that round will end
        boolean hasPlayerEndedRound = false;  // this will check when a player enters x during a round to end the round
        int playerTurnNumber = 0;
        Scanner input = new Scanner(System.in);
        String choice = " ";
        boolean choiceaction= true;
        int rollDoublesResult = 0;  // this is for the case when user is in jail

       // System.out.println("At the start of each round the monopoly board sqaures look like this : ");
       // System.out.println(monopolyBoard);
       // System.out.println("At the start of each round the roll doubles result for the player is: " + rollDoublesResult);


        for (int playerNumber = startPlayerNumber; playerNumber< players.size(); playerNumber++)
        {
            // check if player is in jail then they can only roll dice to get out of jail
            if(players.get(playerNumber).isInJail()) {

                int currentPosition = players.get(playerNumber).getToken(); // current position of player which is the jail square
                int finalPosition;
                double currentPlayersMoney = players.get(playerNumber).getMoney();// get the current Money the current player has
                double updatedMoney;// for subtracting jail fine from the current players money

                if(players.get(playerNumber).getPlayerTurnTries() <=2 ){

                    if(players.get(playerNumber).getPlayerTurnTries() == 1 || players.get(playerNumber).getPlayerTurnTries()==2){
                        String jailchoice;
                        boolean jailChoiceAction = true;
                        //check if player wants to pay jail fine before 2nd or 3rd roll of dice.
                        System.out.printf("%s%d%s%s%s%s%d%s%n",
                                "Player",
                                players.get(playerNumber).getPlayerIndex(),
                                " ",
                                players.get(playerNumber).getName(),
                                "Press Y to pay jail fine of amount: ",
                                "$",GameConstants.JailFine,
                                " or Press N to continue rolling dice");
                        jailchoice = input.nextLine().trim();

                        while(jailChoiceAction){
                            switch (jailchoice){
                                case "Y":
                                case "y":
                                    //boolean hasPaidJailFine = payJailFine();
                                    //if(hasPaidJailFine) {
                                        rollDoublesResult = players.get(playerNumber).getRolledDoublesResult(); // set the rolled double result to the value from previous turn rolled double result
                                        updatedMoney = currentPlayersMoney - GameConstants.JailFine;
                                        players.get(playerNumber).setMoney(updatedMoney);                     // update current players money after subtracting jail fine
                                        finalPosition = currentPosition + rollDoublesResult;                  // update final position of the player
                                        players.get(playerNumber).setToken(finalPosition);                    // update current players token with the roll result
                                        players.get(playerNumber).setInJail(false);
                                        players.get(playerNumber).setPlayerTurnTries(0);                      // when player is out of jail, reset turn number and roll doubles to 0
                                        players.get(playerNumber).setRolledDoublesResult(0);
                                        System.out.println("You are out of jail.");
                                    System.out.println("Your Previous roll result is: "+players.get(playerNumber).getRolledDoublesResult());
                                        evaluateSquareType(finalPosition); // see what kind of sqaure we landed on
                                        evaluateRollAction(finalPosition, players.get(playerNumber));          // if player paid fine then move player
                                        // forward by roll doubles and evaluate which square the player landed on after getting out of jail

                                  //  }
                                    jailChoiceAction = false;
                                    break;
                                case "N":
                                case"n":
                                    //player did not pay fine before 2nd or 3rd throw
                                    //player will roll dice on 2nd and 3rd

                                    System.out.printf("%s%d%s%s%n",
                                            "Player",
                                            players.get(playerNumber).getPlayerIndex(),
                                            players.get(playerNumber).getName(),
                                            " Press R to roll Dice to get out of jail S to save game or X to quit the round: ");
                                    choice = input.nextLine().trim();
                                    while(choiceaction){
                                        switch (choice){
                                            case"R":
                                            case"r":
                                                int currentPlayerRollTurns = players.get(playerNumber).getPlayerTurnTries();
                                                rollDoublesResult = rollDice();
                                                System.out.printf("%s%d%s%s%s%d%n", "Player", players.get(playerNumber).getPlayerIndex(), " ", players.get(playerNumber).getName(), " Rolled: ", rollDoublesResult);
                                                if(rollDoubles(rollDoublesResult)){
                                                    System.out.println("You have Rolled doubles. Getting out of Jail");
                                                    players.get(playerNumber).setInJail(false); // set the inJail status of player as false
                                                    finalPosition = currentPosition + rollDoublesResult;
                                                    players.get(playerNumber).setToken(finalPosition); // move the player by the result of rolling doubles
                                                    evaluateSquareType(finalPosition); // see what kind of sqaure we landed on
                                                    evaluateRollAction(finalPosition,players.get(playerNumber)); // evaluate which square player lands after
                                                    // getting out of jail
                                                    choiceaction = false;
                                                    jailChoiceAction = false;
                                                }
                                                else{
                                                    System.out.println("You did not roll doubles try on next turn or pay fine to get out of jail");
                                                    currentPlayerRollTurns++;
                                                    players.get(playerNumber).setPlayerTurnTries(currentPlayerRollTurns); // updated turn after each successive roll
                                                    players.get(playerNumber).setRolledDoublesResult(rollDoublesResult);  // update the players rolled doubled results
                                                    choiceaction = false;
                                                    jailChoiceAction = false;
                                                }
                                                break;

                                            case"X":
                                            case"x":
                                                // to quit the round for the jail turn
                                                choiceaction = false;
                                                hasPlayerEndedRound = true; // make this true when player ends a round
                                                break;

                                            case "S":
                                            case "s":
                                                System.out.println("Game Saved Successfully");
                                                // pass player states to save game function
                                                SaveGameController.saveGame(players,monopolyBoard,playerview, getRound(),playerNumber);
                                                // saveGameController.loadGameControllerInitialization(saveGameController.getSaveFiles());
                                                System.out.printf("%s%d%s%s%s%n",
                                                        "Player",
                                                        players.get(playerNumber).getPlayerIndex(),
                                                        " ",
                                                        players.get(playerNumber).getName(),
                                                        " Press R to roll Dice to get out of jail or X to quit the round: ");
                                                choice = input.nextLine().trim();
                                                break;

                                            default:
                                                System.out.println("Invalid choice. Press R to roll Dice to get out of jail or X to quit the round: ");
                                                choice = input.nextLine().trim();
                                                break;

                                        }
                                    }

                                    break;

                                default:
                                    System.out.println("invalid choice. Press Y to pay jail fine or N to continue rolling the dice");
                                    jailchoice = input.nextLine().trim();
                                    break;


                            }

                        }


                    }

                    else{
                        System.out.printf("%s%d%s%s%s",
                                "Player",
                                players.get(playerNumber).getPlayerIndex(),
                                " ",
                                players.get(playerNumber).getName(),
                                " Press R to roll Dice to get out of jail S to save game or X to quit the round: ");
                        choice = input.nextLine().trim();
                        while(choiceaction){
                            switch (choice){
                                case"R":
                                case"r":
                                    int currentPlayerRollTurns = players.get(playerNumber).getPlayerTurnTries();
                                    rollDoublesResult = rollDice();
                                    System.out.printf("%s%d%s%s%s%d%n", "Player", players.get(playerNumber).getPlayerIndex(), " ", players.get(playerNumber).getName(), " Rolled: ", rollDoublesResult);
                                    if(rollDoubles(rollDoublesResult)){
                                        System.out.println("You have Rolled doubles. Getting out of Jail");
                                        players.get(playerNumber).setInJail(false); // set the inJail status of player as false
                                        finalPosition = currentPosition + rollDoublesResult; // add the roll doubles result to the current position
                                        players.get(playerNumber).setToken(finalPosition); // move the player by the result of rolling doubles
                                        players.get(playerNumber).setPlayerTurnTries(0);   // reset turns taken for rolling doubles to 0
                                        evaluateSquareType(finalPosition); // check what kind of sqaure player has landed on after rolling doubles to get out of jail
                                        evaluateRollAction(finalPosition,players.get(playerNumber)); // move the player by the roll doubles and evaluate the action
                                        // on the sqaure the player lands after getting out of jail
                                        choiceaction = false;
                                    }
                                    else{
                                        System.out.println("You did not roll doubles try on next turn or pay fine on next turn to get out of jail");
                                        currentPlayerRollTurns++;
                                        players.get(playerNumber).setPlayerTurnTries(currentPlayerRollTurns);
                                        players.get(playerNumber).setRolledDoublesResult(rollDoublesResult); // update players rolled double results
                                        choiceaction = false;
                                        // check choice action again
                                    }
                                    break;


                                case"X":
                                case"x":
                                    // to quit the round for the jail turn
                                    choiceaction = false;
                                    hasPlayerEndedRound = true; // make this true when player ends a round
                                    break;

                                case "S":
                                case "s":
                                    System.out.println("Game Saved Successfully");
                                    // pass player states to save game function
                                    SaveGameController.saveGame(players,monopolyBoard,playerview, getRound(),playerNumber);
                                    // saveGameController.loadGameControllerInitialization(saveGameController.getSaveFiles());
                                    System.out.printf("%s%d%s%s%s",
                                            "Player",
                                            players.get(playerNumber).getPlayerIndex(),
                                            " ",
                                            players.get(playerNumber).getName(),
                                            " Press R to roll Dice to get out of jail or X to quit the round: ");
                                    choice = input.nextLine().trim();
                                    break;



                            }

                        }

                    }



                }
                // if player exceed three turns to roll doubles
                else{

                    System.out.printf("%s%d%s%s%s%n",
                            "Player",
                            players.get(playerNumber).getPlayerIndex(),
                            " ",
                            players.get(playerNumber).getName(),
                            " has failed to roll doubles on three rolls. Deducting jail fine " );

                    rollDoublesResult = players.get(playerNumber).getRolledDoublesResult(); // update roll result to previous turns
                    currentPlayersMoney = players.get(playerNumber).getMoney();  // get the current Money the current player has
                    updatedMoney = currentPlayersMoney-GameConstants.JailFine;   // subtract jail fine from the current players money
                    finalPosition = currentPosition + rollDoublesResult;
                    players.get(playerNumber).setMoney(updatedMoney);                   // update current players money after subtracting jail fine
                    players.get(playerNumber).setToken(finalPosition);              // update current players token with the roll result
                    players.get(playerNumber).setInJail(false);
                    players.get(playerNumber).setRolledDoublesResult(0);  // reset the rolled doubled result for this player
                    players.get(playerNumber).setPlayerTurnTries(0);      // reset the turn number to 0
                    evaluateSquareType(finalPosition);
                    evaluateRollAction(finalPosition,players.get(playerNumber));

                }


                if (choice.equals("R") || choice.equals("r")) {
                    choiceaction = true;
                }

                if (choice.equals("X") || choice.equals("x")) {
                    System.out.println("The player has ended the round. Going back to main menu");
                    break;
                }


            }

            // if player is not in jail then they can roll dice to continue the game
            else {
                System.out.printf("%s%d%s%s%s", "Player", players.get(playerNumber).getPlayerIndex()," ", players.get(playerNumber).getName(), " Press R to roll Dice S to save Game or X to quit the round: ");
                choice = input.nextLine().trim();
               // System.out.println(choiceaction);

                while (choiceaction) {
                    switch (choice) {
                        case "R":
                        case "r":
                            //start rolling
                            int rollresult = rollDice();
                            System.out.printf("%s%d%s%s%s%d%n", "Player", players.get(playerNumber).getPlayerIndex(), " ", players.get(playerNumber).getName(), " Rolled: ", rollresult);
                            int currentposition = players.get(playerNumber).getToken(); // get current position of the player on the board
                            int finalposition = evaluatePosition(rollresult, players.get(playerNumber)); // get final position of the player on the board

                            // check if during roll player passed through GO square.
                            // If passed through GO square then add 1500$ to players existing Money
                            System.out.println("Current position is " + currentposition + " final position is " + finalposition);
                            boolean hasPlayerPassedGoSquare = checkIfPassedGoSquare(currentposition, finalposition);

                           // System.out.println("player passed through go square: " + hasPlayerPassedGoSquare);

                            //see if the player passed through GO square
                            if (hasPlayerPassedGoSquare) {
                                System.out.printf("%s%d%s%s%s%d%n",
                                        "Player ",
                                        players.get(playerNumber).getPlayerIndex(),
                                        " ",
                                        players.get(playerNumber).getName(),
                                        " passed through GO square. Gaining Salary Amount of $",
                                        GameConstants.STARTING_SALARY);

                                double currentMoney = players.get(playerNumber).getMoney(); // get current players current money
                                double updatedMoney = currentMoney + 1500;
                                players.get(playerNumber).setMoney(updatedMoney);//set players money to new money

                            }

                            players.get(playerNumber).setToken(finalposition); // update player token after each roll
                            Square squaretype = evaluateSquareType(finalposition);
                            evaluateRollAction(finalposition, players.get(playerNumber)); // to evaluate the roll action impacts on players money and property
                            choiceaction = false;

                            break;

                        case "S":
                        case"s":
                            System.out.println("Game Saved Successfully");
                            // pass player states to save game function
                            SaveGameController.saveGame(players,monopolyBoard,playerview, getRound(),playerNumber);
                           // saveGameController.loadGameControllerInitialization(saveGameController.getSaveFiles());
                            System.out.printf("%s%d%s%s%s%n", "Player", players.get(playerNumber).getPlayerIndex()," ", players.get(playerNumber).getName(), " Press R to roll Dice or X to quit the round: ");
                            choice = input.nextLine().trim();

                           // choiceaction = false;
                            break;

                        case "X":
                        case "x":
                            //Quit the round
                            choiceaction = false;
                            hasPlayerEndedRound = true; // make this true when player ends a round
                            break;
                        default:
                            System.out.println("Invalid Choice. Press R to roll Dice or X to Quit the round");
                            //input.nextLine();
                            choice = input.nextLine().trim();
                    }
                    //System.out.println(choiceaction);

                }

                // condition for breaking the for loop which runs the turns of each round.
                if (choice.equals("X") || choice.equals("x")) {
                    System.out.println("The player has ended the round. Going back to main menu");
                    break;
                }

                // if user chose R or S then reset the choiceaction to true to allow the next user to continue rolling.
                if (choice.equals("R") || choice.equals("r") || choice.equals("S") || choice.equals("s")) {
                    choiceaction = true;
                }
            }


            playerTurnNumber++; // Update the turn number when each player successfully takes a turn to roll the dice
          //  System.out.println("Playerturn number is : " + playerTurnNumber);


            // logic to see if the player has negative money
            // if player has negative money then remove player from game
            // make all the propertiese owned by the player unowned

            if(players.get(playerNumber).getMoney()<0){
                System.out.printf("%s%d%s%s%s%n","Player",players.get(playerNumber).getPlayerIndex()," ",players.get(playerNumber).getName()," has negative money.");
                System.out.printf("%s%d%s%s%s%n","Removing player ",players.get(playerNumber).getPlayerIndex()," ", players.get(playerNumber).getName()," from the game");
                resetPropertyStatus(players.get(playerNumber).getProperty());
              //  System.out.println("Player Properties now are : " + players.get(playerNumber).getProperty()); // check how property looks now

                //players.set(playerNumber,null);
                players.remove(players.get(playerNumber)); // remove the player from the game
               // System.out.println("Players are now : " + players);
                System.out.println();

            }


            if (players.size() == 1){
                System.out.println("Game has ended");
                displayBoardView(Round); //
                displayUpdatedPlayerView(); // show the player view when players are removed
                System.out.println("Winner is");
                System.out.println(players.get(0));
                IsRoundQuit = true;
//                map.clear();
//                map.put(RoundNum,IsRoundQuit);
                break;

            }


        }

//        System.out.println("Playerturn number after loop is : " + playerTurnNumber);
//        System.out.println("player arraylist size is : " + players.size());
        //System.out.println("Players status : "+ players);

        if(playerTurnNumber == players.size()){
            RoundNum++;
            setRound(RoundNum);
            //IsRoundQuit = true;
            map.clear();
            map.put(RoundNum,IsRoundQuit);
           // System.out.println(map);
            return map;
        }
        // This else is for when the player has ended the round by entering X during the round
        else{
            if(playerTurnNumber != players.size() && players.size() ==1){
                RoundNum++;
                setRound(RoundNum);
                map.clear();
                map.put(RoundNum,IsRoundQuit);
            }
            else{

                map.clear();
                map.put(RoundNum,hasPlayerEndedRound);
            }



        }

        return map;

    }

    // roll the dice
    public int rollDice(){
        Dice Dice1 = new Dice();
        Dice Dice2 = new Dice();
        Dice1.rollDice(); // roll dice value is random 1 to 4
        Dice2.rollDice(); // roll dice value is random 1 to 4
        int diceValue = Dice1.getValue() + Dice2.getValue();  // sum the dice value to get the resultant dice value
        return diceValue;

    }


    public void resetPropertyStatus(ArrayList<Propertysquare> playerProperty){

        for(Propertysquare propertysquare : playerProperty){
            propertysquare.setOwned(false);
            propertysquare.setOwnedBy(null);
        }


    }




    // this method to check if doubles are rolled by player
    public boolean rollDoubles(int rollResult){
        // the variable roll result is the result of rolling two dice ( sum of values of two dice)
        // rolling doubles means the sum of the two dice should be 2 or 4 or 6 or 8
        // since each die is rolling 1 or 2 or 3 or 4
        if(rollResult == 2 || rollResult == 4 || rollResult == 6 || rollResult == 8){
            return true;
        }
        else {
            return false;
        }

    }

    public boolean payJailFine(){
        Scanner input = new Scanner(System.in);
        boolean choiceaction = true;
        boolean paidJailFine = false;
        String choice;
        choice = input.nextLine().trim();
        System.out.printf("%s%s%d%s%n","Press Y to pay Jail Fine of ","$",GameConstants.JailFine, " or N to continue turn ");

        while (choiceaction){
            switch (choice){
                case "Y":
                case "y":
                    paidJailFine = true;
                    choiceaction = false;
                    break;
                case "N":
                case "n":
                    System.out.printf("%s%n","Player is continuing with their turn");
                    choiceaction = false;
                    break;

                default:
                    System.out.printf("%s%s%s%d%s%n","Invalid Choice ","Press Y to pay Jail Fine of ","$",GameConstants.JailFine, " or N to continue turn ");
                    choice = input.nextLine().trim();
                    break;


            }
        }

        return paidJailFine;
    }



    // evaluate the current position of the player on the board and update it with the dice roll result.
    public int evaluatePosition(int rollresult, Player currentPlayer){
        int currentTokenPosition = currentPlayer.getToken();
        int newTokenPosition = rollresult + currentTokenPosition;
        if (newTokenPosition > 19)
        {
            newTokenPosition = newTokenPosition - 20;
            return newTokenPosition;
        }
        else {
            return newTokenPosition;
        }
    }

    //This method to see what type of Square the player has landed on
    public Square evaluateSquareType(int position){
        ArrayList<Square> monopolyBoard = getMonopolyBoard();
        Square squareType = monopolyBoard.get(position);
       // System.out.println("Square Name is : " + squareType.getName());
        System.out.println("Square Type landed on is : " + squareType.getSquareType());
        return squareType;

    }

    //this method will evaluate the action when player lands on a square after rolling the dice
    public void evaluateRollAction(int position, Player currentPlayer){
    //here we write the logic to perform actions for each square.

        switch (position){
            // for landing on GO square which is at index 0, player will recieve 1500$
            case 0:
                double currentPlayerMoney = currentPlayer.getMoney();
                double updatedMoney= currentPlayerMoney + 1500; // here player recieved a salary of 1500 if player lands on GO square
                currentPlayer.setMoney(updatedMoney); // update the players money with the new money.
                System.out.printf("%s%d%s%s%s%d%n","Player",currentPlayer.getPlayerIndex()," ", currentPlayer.getName(), " received Salary amount of : ", GameConstants.STARTING_SALARY);
                break;
                // for central property square
            case 1:
                Propertysquare central = (Propertysquare)monopolyBoard.get(1); // downcast to property square
                boolean owningStatusCentral = ((Propertysquare) monopolyBoard.get(1)).isOwned();

                if (owningStatusCentral){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(central, getPlayers()); // here the index of the player in the players list
                    // check if the current player lands on the property square it has already bought
                    if(players.get(owningPlayer).equals(currentPlayer) ){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    // who owns the property of central is return in the owning player index
                    // check which player owns this property
                    // pay rent to the player who owns this property
                    else{
                        payrent(owningPlayer,currentPlayer,central);
                    }

                }
                else
                {
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(1),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%d%s%s%s%s%n","Player", currentPlayer.getPlayerIndex(), " ",  currentPlayer.getName()," bought the property ", central.getPropertyName() );

                    }else{
                        System.out.printf("%s%d%s%s%s%s%n","Player", currentPlayer.getPlayerIndex(), " ",  currentPlayer.getName()," did not buy the property ", central.getPropertyName() );

                    }

                }
                break;
            case 2:
                Propertysquare wanchai = (Propertysquare)monopolyBoard.get(2);

                boolean owningStatusWanchia = ((Propertysquare) monopolyBoard.get(2)).isOwned();

                if(owningStatusWanchia){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(wanchai,getPlayers());
                    // check if current player lands on a property the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,wanchai);
                    }


                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(2),currentPlayer);
                  //  System.out.println("has Player Bought Property: " + hasPlayerBoughtProperty);
                   // System.out.println("Property now is : " + monopolyBoard.get(2));
                    if(hasPlayerBoughtProperty){
                        //((Propertysquare) monopolyBoard.get(2)).setOwned(true);
                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," bought the property ", wanchai.getPropertyName() );

                       // System.out.println("Property now is : " + monopolyBoard.get(2));
                    }else{
                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," did not buy the property ", wanchai.getPropertyName() );
                    }

                }
                break;
            case 3:
                Incometax incomeTax = new Incometax(GameConstants.INCOME_TAX_SQUARE,GameConstants.INCOME_TAX_RATE);
                double PlayerMoney = currentPlayer.getMoney(); // get the current money of the player
                double taxamount = incomeTax.calculateTaxAmount(PlayerMoney); // calculate income tax on current money
                currentPlayer.setMoney(PlayerMoney-taxamount); // set the new money for the player after deducting income tax
                System.out.printf("%s%d%s%s%s%f%n","Player", currentPlayer.getPlayerIndex() ," ", currentPlayer.getName() ," paid income tax amount of $",taxamount);
                break;

            case 4:
                Propertysquare stanley = (Propertysquare) monopolyBoard.get(4);
                boolean owningStatusStanley = ((Propertysquare) monopolyBoard.get(4)).isOwned();

                if(owningStatusStanley){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(stanley,getPlayers());
                    // check if current player lands on a property that current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,stanley);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(4),currentPlayer);

                    if(hasPlayerBoughtProperty){
                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," bought the property ", stanley.getPropertyName() );
                    }else{
                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," did not buy the property ", stanley.getPropertyName() );
                    }
                }
                break;
            case 5:
                System.out.printf("%s%d%s%s%s%n","Player ",currentPlayer.getPlayerIndex() , " ", currentPlayer.getName()," Just Visiting.");
                break;
            case 6:
                Propertysquare shekO = (Propertysquare)monopolyBoard.get(6);
               // System.out.println(shekO);
                boolean owningStatusSheko = ((Propertysquare) monopolyBoard.get(6)).isOwned();
                if(owningStatusSheko){
                    System.out.println("This Property is already owned");
                    int owningPlayer = checkOwningPlayer(shekO,getPlayers());
                    //check if current player lands on a property that current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,shekO);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(6),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," bought the property ", shekO.getPropertyName() );
                    }else{
                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," did not buy the property ", shekO.getPropertyName() );
                    }
                }
                break;
            case 7:
                Propertysquare mongkok = (Propertysquare)monopolyBoard.get(7);

                boolean owningStatusMongKok = ((Propertysquare) monopolyBoard.get(7)).isOwned();

                if (owningStatusMongKok){
                    System.out.println("This Property is already owned");
                    int owningPlayer = checkOwningPlayer(mongkok,getPlayers());
                    // check if current player lands on a property that current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,mongkok);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(7),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%d%s%s%s%s%n","Player",currentPlayer.getPlayerIndex() , " ",currentPlayer.getName()," bought the property ", mongkok.getPropertyName() );
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", mongkok.getPropertyName() );
                    }
                }
                break;
            case 8:
            case 12:
            case 18:
                ChanceSquare chanceSquare = new ChanceSquare(GameConstants.CHANCE_SQUARE,0,0,0,0);
                chanceSquare.createChance();
                chanceSquare.calculateAmount();
                int amount = chanceSquare.getAmount(); // this is the amount player will either gain or lose
                if(amount > 0)
                {
                    System.out.printf("%s%d%n","Player has gained an amount of : $", amount);
                }else{
                    System.out.printf("%s%d%n","Player has lost an amount of : $", -1 * amount);
                }
                double money = currentPlayer.getMoney(); // get the current money that the player has
                double newMoney= money + amount; // calculate the new player money after adding the chance amount
                currentPlayer.setMoney(newMoney);  // update player money with the new Money
                break;

            case 9:
                Propertysquare tsingYi = (Propertysquare)monopolyBoard.get(9);
                boolean owningStatusTsingYi = ((Propertysquare) monopolyBoard.get(9)).isOwned();

                if(owningStatusTsingYi){
                    System.out.println("This Property is already owned");
                    int owningPlayer = checkOwningPlayer(tsingYi,getPlayers());
                    // check if current player lands on a property that current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,tsingYi);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(9),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", tsingYi.getPropertyName() );
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", tsingYi.getPropertyName() );
                    }
                }
                break;
            case 11:
                Propertysquare shatin = (Propertysquare)monopolyBoard.get(11);

                boolean owningStatusShatin = ((Propertysquare) monopolyBoard.get(11)).isOwned();

                if(owningStatusShatin){
                    System.out.println("This Property is already owned");
                    int owningPlayer = checkOwningPlayer(shatin,getPlayers());
                    // check if current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,shatin);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(11),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", shatin.getPropertyName() );
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", shatin.getPropertyName() );
                    }
                }
                break;
            case 13:
                Propertysquare tuenmun = (Propertysquare)monopolyBoard.get(13);

                boolean owningStatustuenmun = ((Propertysquare) monopolyBoard.get(13)).isOwned();

                if(owningStatustuenmun){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(tuenmun,getPlayers());
                    // check if current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,tuenmun);
                    }

                }
                else
                {
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(13),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", tuenmun.getPropertyName() );
                    }else {
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", tuenmun.getPropertyName() );
                    }
                }
                break;
            case 14:
                Propertysquare taiPo = (Propertysquare)monopolyBoard.get(14);

                boolean owningStatusTaiPo = ((Propertysquare) monopolyBoard.get(14)).isOwned();

                if(owningStatusTaiPo){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(taiPo,getPlayers());
                    // check if current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,taiPo);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(14),currentPlayer);

                    if(hasPlayerBoughtProperty){
                       // ((Propertysquare) monopolyBoard.get(14)).setOwned(true);
                       // System.out.println("Property now is : " + monopolyBoard.get(14));
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", taiPo.getPropertyName());
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", taiPo.getPropertyName());
                    }
                }
                break;

            case 15:
                // go to jail logic
                int presentPosition = currentPlayer.getToken(); // get the current position of the player rolling the dice
                currentPlayer.setToken(5); // move the player to the just visiting square
                currentPlayer.setInJail(true);//set the jail status of current player as true.
                // This means the player is now in Jail
                System.out.printf("%s%s%s%n","Player ",currentPlayer.getName(), " moved to Jail" );
                // moving the player to just visiting square from the go to jail square means
                // the player has entered the jail.

                break;
            case 16:
                Propertysquare saiKung = (Propertysquare)monopolyBoard.get(16);
                //System.out.println(saiKung);
                boolean owningStatusSaiKung = ((Propertysquare) monopolyBoard.get(16)).isOwned();

                if(owningStatusSaiKung){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(saiKung,getPlayers());
                    // check if current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,saiKung);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(16),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", saiKung.getPropertyName() );
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", saiKung.getPropertyName() );
                    }
                }
                break;
            case 17:
                Propertysquare yuenLong = (Propertysquare)monopolyBoard.get(17);

                boolean owningStatusYuenLong = ((Propertysquare) monopolyBoard.get(17)).isOwned();

                if(owningStatusYuenLong){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(yuenLong,getPlayers());
                    // check if the current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,yuenLong);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(17),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", yuenLong.getPropertyName() );
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", yuenLong.getPropertyName() );
                    }
                }
                break;
            case 19:
                Propertysquare taiO = (Propertysquare)monopolyBoard.get(19);

                boolean owningStatusTaiO = ((Propertysquare) monopolyBoard.get(19)).isOwned();

                if(owningStatusTaiO){
                    System.out.println("This property is already owned");
                    int owningPlayer = checkOwningPlayer(taiO,getPlayers());
                    // check if the current player lands on a property that the current player already owns
                    if(players.get(owningPlayer).equals(currentPlayer)){
                        System.out.println("You already own this property. Continue with next players turn");
                    }
                    else{
                        payrent(owningPlayer,currentPlayer,taiO);
                    }

                }
                else{
                    boolean hasPlayerBoughtProperty = propertyAction((Propertysquare)monopolyBoard.get(19),currentPlayer);

                    if(hasPlayerBoughtProperty){

                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," bought the property ", taiO.getPropertyName());
                    }else{
                        System.out.printf("%s%s%s%s%n","Player ",currentPlayer.getName()," did not buy the property ", taiO.getPropertyName());
                    }
                }
                break;


        }
    }



    public int checkOwningPlayer(Propertysquare property, ArrayList<Player> gamePlayers){
        int owningPlayerIndex = -1; // this is the default value which means no player owns this property

        for (int i = 0 ; i < gamePlayers.size(); i++){
            if(gamePlayers.get(i).getProperty().contains(property)){
                System.out.printf("%s%d%s%s%s%s%n","Player ", i+1," ", gamePlayers.get(i).getName()  , " owns the property ",property.getPropertyName());
               owningPlayerIndex = i;
               break;
                // return the index of the player who owns this property for rent paying purposes
            }
        }


        return owningPlayerIndex;

    }

    public void payrent(int owningPlayerindex,Player currentPlayer,  Propertysquare ownedProperty){
        double owningPlayerMoney = getPlayers().get(owningPlayerindex).getMoney(); // this is the money the Property Owning player currently has
        int ownedPropertyRent = ownedProperty.getPropertyRent(); // this will give the owned property rent;
        double updatedMoney = owningPlayerMoney + ownedPropertyRent;
        getPlayers().get(owningPlayerindex).setMoney(updatedMoney);
        // update the money of the player who owns the property by adding
        // the rent amount to its money

        double currentPlayerMoney = currentPlayer.getMoney(); // get the current money of the player who is currently rolling the dice
        // this is the player who landed on the property dice and is going to pay the rent
        double updatedCurrentPlayerMoney = currentPlayerMoney - ownedPropertyRent; // update money by subtracting the rent amount
        currentPlayer.setMoney(updatedCurrentPlayerMoney); // set the money of the current player with updated money
        // the updated money for the current player consists of subtracting rent amount from the money the current player
        // rolling the dice already has.

        System.out.printf("%s%s%s%s%d%s%s%n",
                "Player ",
                currentPlayer.getName(),
                " paid rent amount of ",
                "$",
                ownedPropertyRent,
                " to Player ",
                getPlayers().get(owningPlayerindex).getName()
                );

    }


    // the method will take property square and the current player who rolled the dice and is taking the turn as parameters
    //
    public boolean propertyAction(Propertysquare propertysquare, Player player){
        System.out.println("Property Information is : " + propertysquare);
        boolean hasPlayerBoughtProperty = false;
        Scanner input = new Scanner(System.in);
        boolean choiceAction = true;
        String choice;
        System.out.println("Press Y to buy the property or N to continue");
        choice = input.nextLine().trim();
        while (choiceAction){
            switch (choice){
                case "Y":
                case"y":
                    //buy property
                    double playerMoney = player.getMoney(); // get the money player currently has
                    int propertyPrice = propertysquare.getPropertyPrice(); // get the price of the property player is buying
                    double updatePlayerMoney = playerMoney - propertyPrice; // subtract property price from player money
                    player.setMoney(updatePlayerMoney); // update the money player money with new player money
                    player.getProperty().add(propertysquare); // add the property to the player list of owned property
                    propertysquare.setOwnedBy(player); // set the player who owns this property
                    propertysquare.setOwned(true);
                    // update isOwned status of this property
                    hasPlayerBoughtProperty = true;
                    choiceAction = false;
                    break;

                case "N":
                case "n":
                    // continue with the game. Player turn ends and next player should take their turn
                  //  System.out.println("Next players turn");
                    choiceAction = false;
                    break;

                default:
                    System.out.println("Wrong input");
                    System.out.println("Press Y to buy the property or N to continue to next players turn ");
                    choice = input.nextLine();
                    break;

            }
        }
        return hasPlayerBoughtProperty;
    }




    // This method will check if during each roll the player passes through the GO square or not
    public boolean checkIfPassedGoSquare(int currentPosition, int finalPosition) {
       // System.out.println("in the function values are : "+ currentPosition + " final position : " + finalPosition);
        Gameboard gameboard = new Gameboard();
        gameboard.createBoard();
        ArrayList<Square> monopolyBoard = gameboard.getMonopolyBoard();
        ArrayList<Square> temp = new ArrayList<>(); // contains the sqaures between current and final position
        GoSquare goSquare = new GoSquare(GameConstants.STARTING_SALARY);

        boolean hasPassedGoSquare = false;

        if(currentPosition < finalPosition){
            for (int i = currentPosition+1; i < finalPosition; i++){
                temp.add(monopolyBoard.get(i));
            }
        }
        else{
            if (currentPosition > finalPosition){

                for (int i = currentPosition + 1; i < 20 ; i++){
                    temp.add(monopolyBoard.get(i));
                }

                for (int i = 0; i < finalPosition; i++){
                    temp.add(monopolyBoard.get(i));
                }
            }
        }

      //  System.out.println("The list is as follows: " );

        //Use iterator to see what the square type is
        Iterator<Square> loop = temp.iterator();

        while (loop.hasNext()){
            String value = loop.next().getSquareType();
           // System.out.print(value+ " ");
            if(value.equals(GameConstants.GO_SQUARE)){
                hasPassedGoSquare = true;
                break;
            }
        }

        return hasPassedGoSquare;

    }

}
