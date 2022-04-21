package com.Group9.model;

import java.io.Serializable;

public class FreeParking extends Square implements Serializable {

    public FreeParking(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "FreeParking{}";
    }
}
