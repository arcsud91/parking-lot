package com.parkinglot.entities;

public abstract class Vehicle {
    private String plateNumber;
    private Color color;

    public Vehicle(String plateNumber, Color color) {
        this.color = color;
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
