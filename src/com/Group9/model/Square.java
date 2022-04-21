package com.Group9.model;

import java.io.Serializable;

public class Square implements SquareType, Serializable {
    private String name;

    public Square(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getSquareType(){
        return name;
    }

//    @Override
//    public String toString() {
//        return "Square{" +
//                "name='" + name + '\'' +
//                '}';
//    }
}
