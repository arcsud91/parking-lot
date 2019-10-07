package com.parkinglot.dao.impl;

import com.parkinglot.constants.Constants;
import com.parkinglot.dao.ParkingLevelDataManager;
import com.parkinglot.entities.Vehicle;
import com.parkinglot.entities.strategy.NearestFirstParkingStrategy;
import com.parkinglot.entities.strategy.ParkingStrategy;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLevelManagerImpl<T extends Vehicle> implements ParkingLevelDataManager<T> {
    // For Multilevel Parking lot - 0 -> Ground floor 1 -> First Floor etc
    private AtomicInteger level = new AtomicInteger(0);
    private AtomicInteger capacity = new AtomicInteger();
    private AtomicInteger availability = new AtomicInteger();
    // Allocation Strategy for parking
    private ParkingStrategy parkingStrategy;
    // this is per level - slot - vehicle
    private Map<Integer, Optional<T>> slotVehicleMap;

    private static ParkingLevelManagerImpl instance = null;

    public static <T extends Vehicle> ParkingLevelManagerImpl<T> getInstance(int level, int capacity,
																			 ParkingStrategy parkingStrategy) {
        if (instance == null) {
            synchronized (ParkingLevelManagerImpl.class) {
                if (instance == null) {
                    instance = new ParkingLevelManagerImpl<T>(level, capacity, parkingStrategy);
                }
            }
        }
        return instance;
    }

    private ParkingLevelManagerImpl(int level, int capacity, ParkingStrategy parkingStrategy) {
        this.level.set(level);
        this.capacity.set(capacity);
        this.availability.set(capacity);
        this.parkingStrategy = parkingStrategy;
        if (parkingStrategy == null)
            parkingStrategy = new NearestFirstParkingStrategy();
        slotVehicleMap = new ConcurrentHashMap<>();
        for (int i = 1; i <= capacity; i++) {
            slotVehicleMap.put(i, Optional.empty());
            parkingStrategy.add(i);
        }
    }

    @Override
    public int parkCar(T vehicle) {
        int availableSlot;
        if (availability.get() == 0) {
            return Constants.NOT_AVAILABLE;
        } else {
            availableSlot = parkingStrategy.getSlot();
            if (slotVehicleMap.containsValue(Optional.of(vehicle)))
                return Constants.VEHICLE_ALREADY_EXIST;

            slotVehicleMap.put(availableSlot, Optional.of(vehicle));
            availability.decrementAndGet();
            parkingStrategy.removeSlot(availableSlot);
        }
        return availableSlot;
    }

    @Override
    public boolean leaveCar(int slotNumber) {
        if (!slotVehicleMap.get(slotNumber).isPresent()) // Slot already empty
            return false;
        availability.incrementAndGet();
        parkingStrategy.add(slotNumber);
        slotVehicleMap.put(slotNumber, Optional.empty());
        return true;
    }

    @Override
    public Optional<T> seek(int slotNumber) {
        Optional<T> vehicle = slotVehicleMap.get(slotNumber);
        return vehicle;
    }

    @Override
    public int getAvailableSlotsCount() {
        return availability.get();
    }

}
