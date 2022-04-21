package com.Group9.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player>, Serializable {
    private String name;
    private double money;
    private int token;
    private double startingSalary;
    private ArrayList<Propertysquare> property;
    private boolean isInJail;
    private int playerTurnTries;
    private int rolledDoublesResult;
    private int playerIndex;


    public Player(String name, double money, int token, double startingSalary, ArrayList<Propertysquare> playerProperty, boolean isInJail, int playerTurnTries,int rolledDoublesResult, int playerIndex) {
        this.name = name;
        this.money = money;
        this.token = token;
        this.startingSalary = startingSalary;
        this.property = playerProperty;
        this.isInJail = isInJail;
        this.playerTurnTries = playerTurnTries;
        this.rolledDoublesResult = rolledDoublesResult;
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public ArrayList<Propertysquare> getProperty() {
        return property;
    }

    public void setProperty(ArrayList<Propertysquare> property) {
        this.property = property;
    }

    public int getRolledDoublesResult() {
        return rolledDoublesResult;
    }

    public void setRolledDoublesResult(int rolledDoublesResult) {
        this.rolledDoublesResult = rolledDoublesResult;
    }

    public int getPlayerTurnTries() {
        return playerTurnTries;
    }

    public void setPlayerTurnTries(int playerTurnTries) {
        this.playerTurnTries = playerTurnTries;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public double getStartingSalary() {
        return startingSalary;
    }

    public void setStartingSalary(double startingSalary) {
        this.startingSalary = startingSalary;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", token=" + token +
                ", playerNumber=" + playerIndex +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        if(this.getMoney() > o.getMoney()){
            return 1;
        }
        else{
            if(this.getMoney() < o.getMoney()){
                return -1;
            }
        }

        return 0;
    }
}
