package com.Group9.model;

import com.Group9.GameConstants;

import java.io.Serializable;

public class GoSquare extends Square implements Serializable {
    private int Salary;

    public GoSquare(int salary) {
        super(GameConstants.GO_SQUARE);
        Salary = salary;
    }

    public int getSalary(){
        return Salary;
    }

    public String getSquareType(){
        return GameConstants.GO_SQUARE;

    }

    @Override
    public String toString() {
        return "GoSquare{" +
                "Salary=" + Salary +
                '}';
    }
}
