package com.Group9.view;

import com.Group9.GameConstants;
import com.Group9.controller.LoadGameController;
import com.Group9.controller.PlayerController;
import com.Group9.controller.SaveGameController;
import com.Group9.model.Gameboard;
import com.Group9.model.Player;
import com.Group9.model.Propertysquare;

import java.io.File;
import java.util.*;

public class NewgameView {

    //Number of players in the game
    private static int numberOfPlayers;


    public static void runNewgameView() {

        boolean menurun=true;
        boolean hasRoundEnded = false;
        int roundNumber=1;
        int startPlayerNumber = 0; // when round starts the turn of this player will run first
        Scanner input = new Scanner(System.in);
        showNewgameView();
        int players;
        while(menurun){
            try {
                players = input.nextInt();
                if(validatenumberofPlayers(players)){
                    numberOfPlayers = players;
                    GameConstants.NUMBER_OF_PLAYERS = numberOfPlayers;
                    ArrayList<Player> newGamePlayers = createPlayers(numberOfPlayers);
                    Playerview playerview = new Playerview(); // create game view
                    Gameboard gameboard = new Gameboard(); // create game board
                    gameboard.createBoard();
                    // create Save game controller
                    PlayerController playerController = new PlayerController(newGamePlayers, playerview, gameboard.getMonopolyBoard(),roundNumber);
                    // need to initiate game board once only. this way updated game board will exists during the start of each round
                    while ( roundNumber <= 100) {


                        playerController.displayBoardView(roundNumber);
                        playerController.displayUpdatedPlayerView();


                            Map<Integer, Boolean> map = new HashMap<>();
                            map = playerController.startRound(roundNumber,0);
                            Map.Entry<Integer, Boolean> entry = map.entrySet().iterator().next();
                            System.out.printf("%s%d%s%n","round number ", roundNumber," has ended");
                            roundNumber = entry.getKey();
                            hasRoundEnded = entry.getValue();
                            // if game exceed 5 rounds then the next round , round 6 has ended
                            if(roundNumber > 100){
                                hasRoundEnded = true;
                            }
                            if(hasRoundEnded){
                                System.out.println("round number " + roundNumber + " has ended");
                            }else{
                                System.out.println("round number " + roundNumber + " has started ");
                            }

                        System.out.println();

                        //if round is ended by player then show the Game menu again.
                        if(hasRoundEnded){
                            menurun = false;
                            break;
                        }


                    }

                    // logic for ending the game if 100 rounds are complete
                    // after the 100th round the 101 round is ended and then we check the wining player
                    if(roundNumber == 101){
                        System.out.println();
                        System.out.println("100 rounds reached");
                        System.out.println("Game Ended");
                        System.out.println("Players view in last round is : ");
                        playerController.displayBoardView(roundNumber);
                        playerController.displayUpdatedPlayerView();
                        System.out.println();
                        System.out.println("Winners are:");
                        ArrayList<Player> gamePlayers = playerController.getPlayers();
                        Player winner = Collections.max(gamePlayers); // get the player with the max money
                        double maxMoney = winner.getMoney();

                        gamePlayers.remove(winner);
                        ArrayList<Player> winingPlayers = new ArrayList<>();
                        winingPlayers.add(winner);
                        for(Player player : gamePlayers){
                            if(player.getMoney() == winner.getMoney()){
                                winingPlayers.add(player);
                                gamePlayers.remove(player);
                            }
                        }
                        System.out.println(winingPlayers);

                    }



                    menurun = false; // terminating condition
                }
                else {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 6");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid Input. Please enter a integer between 2 and 6");
            }
            input.nextLine(); // clean scanner buffer incase of wrong input type

        }


        // if the round has ended either by player wining or player ending the round by pressing x then show the menu again
        if (hasRoundEnded || roundNumber == 100){
            System.out.println();
            GameMenu.runGame();
        }

    }



    public static void LoadGame (){
//        System.out.println("Save files are as follows");
//        System.out.println(loadGameController.getSaveFile());
        LoadGameController loadGameController = new LoadGameController(new ArrayList<File>());
        loadGameController.listAllSavedGameFiles();
        if (loadGameController.getSaveFile().size()==0){
            System.out.println("No Save files present");
            System.out.println("Going back to main Menu");
            System.out.println();
            GameMenu.runGame();
        }else{
            loadGameController.displayLoadGameFiles();
            System.out.println();
            loadGameController.loadGameFile();
        }

    }



    public static void showNewgameView(){
        System.out.println("Enter the number of players (Between 2 and 6)");
    }

    // to check the right number of players have been added in the game
    public static boolean validatenumberofPlayers(int numofPlayers) {
        if(numofPlayers < 2 || numofPlayers > 6)
        {
            return false;
        }
        return true;

    }

    //Function to create players at the start. The user is asked to input player names.
    // the initial money that the players will have is 1500 $ same as the starting salary
    public static ArrayList<Player> createPlayers(int numofPlayers){
        ArrayList<Player>gamePlayer = new ArrayList<Player>(numofPlayers); // wrong create different lists for different players
        ArrayList< ArrayList<Propertysquare> > playerProperty = new ArrayList<>(); // create a array list that contains list of propertise
        // of each player. Each element in playerProperty is a list of propertiese the player has
        Scanner input = new Scanner(System.in);

        // this loop is for creating list of propertise for each player
        for (int j = 0 ; j < numofPlayers ; j++){
            ArrayList<Propertysquare> playerPropertyList = new ArrayList<>();
            playerProperty.add(playerPropertyList);
        }


        for (int i = 0 ; i < numofPlayers; i++){
            int playerIndex = i + 1;
            String playerName = "";
            System.out.printf("%s%d%s%n","Enter player",i+1," name:");
            playerName = input.nextLine().trim();
            boolean enteredCorrectName = false;
            while(true){
                if(playerName.equals("") || playerName.equals(" ")){
                    System.out.println("Invalid Player Name. Enter again");
                    playerName = input.nextLine().trim();
                }
                else{
                    playerName = validatePlayerName(playerName);
                    break;
                }
            }

            gamePlayer.add(new Player(playerName, 1500,0,1500, playerProperty.get(i),false,0,0,playerIndex));


        }
        return gamePlayer;
    }


    /**
     * This function will validate entered player Name. If player didnt type any name or simply pressed
     * spaces then ask player to enter a valid name again
     * if player entered a long name greater than 6 character length than truncate the name to only six characters*/

    public static String validatePlayerName (String playerName){
        String name = "";
        if (playerName.length() > 6){
            name = playerName.substring(0,6);
        }
        else{
            name = playerName;
        }
        return name;

    }


}
