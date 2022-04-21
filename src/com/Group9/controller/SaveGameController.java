package com.Group9.controller;

import com.Group9.model.Player;
import com.Group9.model.Square;
import com.Group9.view.Playerview;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaveGameController {
    private final static String GAME_ROOT = "MonopolyMaster";
    private final static String SAVED_GAMES = "Saves";
  //  private ArrayList<File> saveFiles;
  //  private ArrayList<String> fileNames;
    //private LoadGameController loadGameController;



//    public ArrayList<File> getSaveFiles() {
//        return saveFiles;
//    }

//    public void setSaveFiles(ArrayList<File> saveFiles) {
//        this.saveFiles = saveFiles;
//    }

//    public ArrayList<String> getFileNames() {
//        return fileNames;
//    }

//    public void setFileNames(ArrayList<String> fileNames) {
//        this.fileNames = fileNames;
//    }



//    public SaveGameController(ArrayList<File> saveFiles, ArrayList<String> fileNames) {
//        this.saveFiles = saveFiles;
//        this.fileNames = fileNames;
//
//    }

    public static void saveGame(ArrayList<Player> players, ArrayList<Square> monopolyBoard, Playerview playerview, int RoundNumber,int playerTurn) {
        File gameDirectory = createGameDirectory();
        saveGameStateTo(gameDirectory, players, monopolyBoard,playerview, RoundNumber, playerTurn);
    }

    public static File createGameDirectory() {
        String userHome = System.getProperty("user.home");
//        System.out.println("User name is : " + userHome);
        File dir = new File(userHome);
        dir = new File(dir, GAME_ROOT);
        if ( ! dir.exists() ) {
            dir.mkdir();
        }
        dir = new File(dir, SAVED_GAMES);
        if ( ! dir.exists() ) {
            dir.mkdir();
        }


//        System.out.println("directory is " +dir);

        return dir;
    }
    public static void saveGameStateTo(File gameDirectory,ArrayList<Player> players, ArrayList<Square> monopolyBoard, Playerview playerview, int RoundNumber, int playerTurn ) {
        // serialize and store all game state objects to specified dir.
        String fileName = new SimpleDateFormat("EEE--yyyy-dd-M--HH-mm-ss").format(new Date());
       // fileNames.add(fileName);
        File newSaveFile = new File(gameDirectory + File.separator + fileName);

       // StringBuffer sb = new StringBuffer("Beam me up!");
        try {
           // FileOutputStream fos = new FileOutputStream("C:\\Users\\Farhan Akhtar\\MonopolyMaster\\Saves\\"+fileSuffix);
            FileOutputStream fos = new FileOutputStream(newSaveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write game states to a file
            oos.writeObject(players);
            oos.writeObject(monopolyBoard);
            oos.writeObject(playerview);
            oos.writeObject(RoundNumber);
            oos.writeObject(playerTurn);
            oos.close();
           // saveFiles.add(newSaveFile);
           // System.out.println("Files are: " + saveFiles);
        } catch (IOException ioException) {
            System.out.println(ioException);
        }


       // System.out.println("File Names are : " + fileNames);

    }


    //initialize the load game controller from here
//    public void loadGameControllerInitialization(ArrayList<File> saveGameFiles){
//        loadGameController.setSaveFile(saveGameFiles);
//    }




}
