package com.parkinglot.dao;

import com.parkinglot.entities.Vehicle;

import java.util.Optional;

public interface ParkingDataManager<T extends Vehicle>
{
	int parkCar(int level, T vehicle);
	
	boolean leaveCar(int level, int slotNumber);

	Optional<T> seek(int level, int slotNumber);

	int getAvailableSlotsCount(int level);
}
