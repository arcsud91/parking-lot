package com.parkinglot.dao;

import com.parkinglot.entities.Vehicle;
import java.util.Optional;

public interface ParkingLevelDataManager<T extends Vehicle> {
    int parkCar(T vehicle);

    boolean leaveCar(int slotNumber);

    Optional<T> seek(int slotNumber);

    int getAvailableSlotsCount();
}
