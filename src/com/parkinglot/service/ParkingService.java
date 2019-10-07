package com.parkinglot.service;

import com.parkinglot.entities.Vehicle;
import com.parkinglot.exceptions.ParkingException;
import java.util.Optional;

public interface ParkingService {

    /**
     * Creates a parking lot with 1 level.
     * Multiple levels can added in the parking lot by again calling the
     * createParkingLot method with different level number
     * @param level
     * @param capacity
     * @throws ParkingException
     */
    void createParkingLot(int level, int capacity) throws ParkingException;

    /**
     * Parks a vehicle at a given level and returns parking slot number.
     * @param level
     * @param vehicle
     * @throws ParkingException
     */
    Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;

    /**
     * Un-parks a vehicle from a given level and parking slot number.
     * @param level
     * @param slotNumber
     * @throws ParkingException
     */
    void unPark(int level, int slotNumber) throws ParkingException;

    /**
     * Fetches the details of a car parked at given level and slot number.
     * @param level
     * @param slotNumber
     * @throws ParkingException
     */
    Optional<Vehicle> seek(int level, int slotNumber) throws ParkingException;

    Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException;
}
