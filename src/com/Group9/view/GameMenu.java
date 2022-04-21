package com.Group9.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameMenu {
    //Input choice of the user
    private static String menuChoice;
    // to continue showing the menu
    private static boolean menuRun;

    public static void runGame()
    {
        try {
            Scanner input = new Scanner(System.in);
            showStartingMenu();
            menuRun = true;
            menuChoice = input.nextLine().trim();
            while (menuRun) {
                switch (menuChoice) {
                    case "1":
                        System.out.println("Game started");
                        NewgameView.runNewgameView();
                        menuRun = false;
                        break;
                    case "2":
                        System.out.println("Loading game");
                        NewgameView.LoadGame();
                        menuRun = false;
                        break;
                    case "3":
                        menuRun = false;
                        break;
                    default:
                        System.out.println("Invalid input. Press 1,2 or 3 to navigate the menu");
                        menuChoice = input.nextLine();

                }
            }
        }
        catch (InputMismatchException e){
            System.out.println("Invalid Input. Please enter 1,2 or 3 to navigate the menu");
        }
    }

    public static void showStartingMenu(){
        System.out.println("Welcome to Monopoly Master Game");
        System.out.println("1.Start the game");
        System.out.println("2.Load Game");
        System.out.println("3.Quit");
        System.out.println("Press 1,2 or 3 to navigate the menu");
    }

}
