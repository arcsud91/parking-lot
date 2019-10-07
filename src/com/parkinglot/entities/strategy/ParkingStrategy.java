package com.parkinglot.entities.strategy;

public interface ParkingStrategy {
    void add(int i);

    int getSlot();

    void removeSlot(int slot);
}
