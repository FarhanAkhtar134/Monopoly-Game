package com.Group9.view;

import com.Group9.GameConstants;
import com.Group9.model.Player;
import com.Group9.model.Propertysquare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playerview implements Serializable {
    private int numberofPlayers;
    private int RoundNumber;
    private Player player;

    public Playerview(){
        this.numberofPlayers = GameConstants.NUMBER_OF_PLAYERS;
    }

    public void setNumberofPlayers(int numberofPlayers) {
        this.numberofPlayers = numberofPlayers;
    }

    public void setRoundNumber(int roundNumber) {
        RoundNumber = roundNumber;
    }

    public int getNumberofPlayers() {
        return numberofPlayers;
    }

    public int getRoundNumber() {
        return RoundNumber;
    }


    public void createView(int playerNum, Player player){

    }



    public void displayPlayerView(int num, String Name, double Money, int playerPosition, ArrayList<Propertysquare> playerProperty){

        StringBuilder propertiese = new StringBuilder();
        //Get the property name and form a string.
    for (Propertysquare Property: playerProperty){
        propertiese.append(Property.getPropertyName()).append(" ");
    }



        System.out.printf("%d",num);
        System.out.printf("%30s",Name);
        System.out.printf("%25.1f",Money);
        System.out.printf("%30s",playerPosition);
        System.out.printf("%70s",propertiese);
        System.out.println();

        for (int i = 0 ; i < 160 ; i ++){
            System.out.printf("%s","-");
        }
        System.out.println();





    }
}
