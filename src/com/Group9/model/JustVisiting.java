package com.Group9.model;

import java.io.Serializable;

public class JustVisiting extends Square implements Serializable {
    // This class of square will have no effect on the player


    public JustVisiting(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "JustVisiting{}";
    }
}
