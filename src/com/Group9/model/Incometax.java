package com.Group9.model;

import com.Group9.GameConstants;

import java.io.Serializable;

public class Incometax extends Square implements Serializable {
    private double tax;

    public Incometax(String name, double tax) {
        super(name);
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    // This function will calcuate the tax amount to the nearest 10$
    public double calculateTaxAmount(double playerMoney){

        double taxRate = getTax();
        double taxAmount = playerMoney*taxRate;
        double remainder = taxAmount % 10;
        if(remainder >= 5) {
            taxAmount = taxAmount+(10-remainder);

        }else   {
            taxAmount = taxAmount - remainder;
        }
        return taxAmount;

    }

    public String getSquareType(){
        return GameConstants.INCOME_TAX_SQUARE;
    }


    @Override
    public String toString() {
        return "Incometax{" +
                "tax=" + tax +
                '}';
    }
}
