package com.Group9.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GoToJail extends Square implements Serializable {

    private int jailFine;
    private ArrayList<Player> players;

    public GoToJail(String name, int jailFine, ArrayList<Player> players) {
        super(name);
        this.jailFine = jailFine;
        this.players = players;
    }

    @Override
    public String toString() {
        return "GoToJail{" +
                "jailFine=" + jailFine +
                ", players=" + players +
                '}';
    }
}
