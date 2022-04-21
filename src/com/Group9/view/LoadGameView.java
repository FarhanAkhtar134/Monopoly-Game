package com.Group9.view;

import java.io.File;
import java.util.ArrayList;

public class LoadGameView {

//    private ArrayList<File> saveFiles;





    // Displays all the save files in a directory at the moment
    public void displayLoadFilesMenu(ArrayList<File> saveFiles){

        System.out.println("Saved Files");

        for (int i = 0 ; i < saveFiles.size(); i++){
            System.out.printf("%d%s%s%n",i,": ",saveFiles.get(i).getName());
        }


    }


}
