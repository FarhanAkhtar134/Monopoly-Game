package com.Group9.model;

import com.Group9.GameConstants;

import java.io.Serializable;

public class Propertysquare extends Square implements Serializable {
    private String propertyName;
    private int propertyPrice;
    private int propertyRent;
    private boolean isOwned;
    private Player ownedBy;

    public Propertysquare(String propertyName, int propertyPrice, int propertyRent, boolean isOwned) {
        super(GameConstants.PROPERTY_SQUARE);
        this.propertyName = propertyName;
        this.propertyPrice = propertyPrice;
        this.propertyRent = propertyRent;
        this.isOwned = isOwned;
    }

    public Player getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Player ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public int getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(int propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public int getPropertyRent() {
        return propertyRent;
    }

    public void setPropertyRent(int propertyRent) {
        this.propertyRent = propertyRent;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public String getSquareType(){
        return propertyName;
    }

    @Override
    public String toString() {
        return "Propertysquare{" +
                "propertyName='" + propertyName + '\'' +
                ", propertyPrice=" + propertyPrice +
                ", propertyRent=" + propertyRent +
                ", isOwned=" + isOwned +
                ", ownedBy=" + ownedBy +
                '}';
    }
}
