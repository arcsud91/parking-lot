package com.parkinglot.dao.impl;

import com.parkinglot.dao.ParkingLevelDataManager;
import com.parkinglot.entities.Vehicle;
import com.parkinglot.entities.strategy.ParkingStrategy;
import com.parkinglot.dao.ParkingDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParkingDataManagerImpl<T extends Vehicle> implements ParkingDataManager<T> {
    private Map<Integer, ParkingLevelDataManager<T>> levelParkingMap;

    private static ParkingDataManagerImpl instance = null;

    public static <T extends Vehicle> ParkingDataManagerImpl<T> getInstance(List<Integer> parkingLevels,
																			List<Integer> capacityList, ParkingStrategy parkingStrategy) {
        // Make sure the each of the lists are of equal size
        if (instance == null) {
            synchronized (ParkingDataManagerImpl.class) {
                if (instance == null) {
                    instance = new ParkingDataManagerImpl<T>(parkingLevels, capacityList, parkingStrategy);
                }
            }
        }
        return instance;
    }

    private ParkingDataManagerImpl(List<Integer> parkingLevels, List<Integer> capacityList, ParkingStrategy parkingStrategy) {
        // creating levels with the given strategy
    	if (levelParkingMap == null)
            levelParkingMap = new HashMap<>();
        for (int i = 0; i < parkingLevels.size(); i++) {
            levelParkingMap.put(parkingLevels.get(i), ParkingLevelManagerImpl.getInstance(parkingLevels.get(i),
                    capacityList.get(i), parkingStrategy));

        }
    }

    @Override
    public int parkCar(int level, T vehicle) {
        return levelParkingMap.get(level).parkCar(vehicle);
    }

    @Override
    public boolean leaveCar(int level, int slotNumber) {
        return levelParkingMap.get(level).leaveCar(slotNumber);
    }

    @Override
    public Optional<T> seek(int level, int slotNumber) {
        return levelParkingMap.get(level).seek(slotNumber);
    }

    @Override
    public int getAvailableSlotsCount(int level) {
        return levelParkingMap.get(level).getAvailableSlotsCount();
    }

}
