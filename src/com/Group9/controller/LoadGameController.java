package com.Group9.controller;

import com.Group9.model.Player;
import com.Group9.model.Square;
import com.Group9.view.GameMenu;
import com.Group9.view.LoadGameView;
import com.Group9.view.Playerview;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.*;

public class LoadGameController {

    private ArrayList<File> saveFile;

    private final static String GAME_ROOT = "MonopolyMaster";
    private final static String SAVED_GAMES = "Saves";

    public LoadGameController(ArrayList<File> saveFile) {


        this.saveFile = saveFile;
    }

    public void listAllSavedGameFiles(){
        String userHomePath = System.getProperty("user.home");
        File savePath = new File (userHomePath + File.separator + GAME_ROOT + File.separator + SAVED_GAMES);
//        System.out.println("Path is "+ savePath);
        if (savePath.exists()){

            for(File saveFiles : savePath.listFiles()){
                saveFile.add(saveFiles);
            }


        }

//        System.out.println("Save files are : " + saveFile);
    }



    public void displayLoadGameFiles(){
        LoadGameView loadGameView = new LoadGameView();
        // update the view when new files are added
        // show the view
        loadGameView.displayLoadFilesMenu(saveFile);

    }



    public void loadGameFile(){
        Scanner input = new Scanner(System.in);
        Integer fileNumber;
        System.out.println("Type the number of file and press enter to load it:");
        fileNumber = Integer.valueOf(input.nextLine().trim()); // ask the user to input the file number they want to load


        ArrayList<Player> gamePlayer = new ArrayList<>();
        ArrayList<Square> monopolyBoard = new ArrayList<>();
        Playerview playerview = new Playerview();
        int RoundNumber = 0;
        int playerTurn = 0;
        try {
            FileInputStream fis = new FileInputStream(saveFile.get(fileNumber));
            ObjectInputStream ois = new ObjectInputStream(fis);
           // sb = (StringBuffer) ois.readObject();
           // while(ois.available() != 0) {
                gamePlayer = (ArrayList<Player>) ois.readObject();
                monopolyBoard = (ArrayList<Square>) ois.readObject();
                playerview = (Playerview) ois.readObject();
                RoundNumber = (int) ois.readObject();
                playerTurn = (int) ois.readObject();
           // }
            ois.close();
        } catch (IOException ioException) {
            System.out.println(ioException);
        } catch (ClassNotFoundException classNotFoundException ) {
            System.out.println(classNotFoundException );
        }

//        System.out.println("Players in the loaded game are : " + gamePlayer);
//        System.out.println("Monopoly Board in the loaded game is : " + monopolyBoard);
//        System.out.println("Player View in the loaded game is : " + playerview);
//        System.out.println("Round num in the loaded game is : " + RoundNumber);

        startLoad(gamePlayer,playerview,monopolyBoard,RoundNumber, playerTurn);




    }

    /**
     * This function will start the loaded file
     * parameters are arraylist of players, player view, monopolyBoard, round number and Player turn
     * players is the players of the game who are saved in the save file
     * The save file will contain the saved info of the players such as money, token etc.
     * monopoly board is the saved game board that contains the info of all the square
     * any updates made to the squares such as the property squares of the board will be saved in the save file
     * via the monopolyBoard object
     * roundNumber is the saved round number. When the game was saved the round number at that instance was saved as well
     * playerTurn will tell us which player was taking their turn when the game was saved i.e. the player who saved the game and then
     * exit the game that player index is given by playerTurn
     * in this function the player turn number is used to see from which players turn the round will start
     * **/

    public void startLoad(ArrayList<Player> players, Playerview playerview, ArrayList<Square> monopolyBoard, int roundNumber, int playerTurn) {

        PlayerController playerController = new PlayerController(players,playerview,monopolyBoard,roundNumber);

        boolean hasRoundEnded = false;


        while ( roundNumber <= 100) {


            playerController.displayBoardView(roundNumber);
            playerController.displayUpdatedPlayerView();

            Map<Integer, Boolean> map = new HashMap<>();
            map = playerController.startRound(roundNumber,playerTurn);
            Map.Entry<Integer, Boolean> entry = map.entrySet().iterator().next();
            System.out.printf("%s%d%s%n","round number ", roundNumber," has ended");
            roundNumber = entry.getKey();
            playerTurn = 0; // make player turn equal to zero again before the next round after loading a game
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


        if (hasRoundEnded || roundNumber == 100){
            System.out.println();
            GameMenu.runGame();
        }


    }








    public ArrayList<File> getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(ArrayList<File> saveFile) {
        this.saveFile = saveFile;
    }




}
