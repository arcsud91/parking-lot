package com.parkinglot.service.impl;

import com.parkinglot.exceptions.ErrorCode;
import com.parkinglot.constants.Constants;
import com.parkinglot.dao.ParkingDataManager;
import com.parkinglot.dao.impl.ParkingDataManagerImpl;
import com.parkinglot.entities.Vehicle;
import com.parkinglot.entities.strategy.NearestFirstParkingStrategy;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.service.ParkingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingServiceImpl implements ParkingService {

    private ParkingDataManager<Vehicle> dataManager = null;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(int level, int capacity) {
        List<Integer> parkingLevels = new ArrayList<>();
        List<Integer> capacityList = new ArrayList<>();
        parkingLevels.add(level);
        capacityList.add(capacity);
        // We can change parking strategy from here if required in future.
        this.dataManager = ParkingDataManagerImpl.getInstance(parkingLevels, capacityList, new NearestFirstParkingStrategy());
        System.out.println("Created parking lot with " + capacity + " slots");
    }

    @Override
    public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException {
        Optional<Integer> value = Optional.empty();
        lock.writeLock().lock();
        validateParkingLot();
        try {
            value = Optional.of(dataManager.parkCar(level, vehicle));
            if (value.get() == Constants.NOT_AVAILABLE)
                throw new ParkingException(ErrorCode.PARKING_FULL.getMessage(), ErrorCode.PARKING_FULL.getCode());
            else if (value.get() == Constants.VEHICLE_ALREADY_EXIST)
                throw new ParkingException(ErrorCode.VEHICLE_ALREADY_EXIST.getMessage(), ErrorCode.VEHICLE_ALREADY_EXIST.getCode());
            else
                System.out.println("Allocated slot number: " + value.get());
        } catch (Exception e) {
            throw new ParkingException(e.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    private void validateParkingLot() throws ParkingException {
        if (dataManager == null) {
            throw new ParkingException(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }

    @Override
    public void unPark(int level, int slotNumber) throws ParkingException {
        lock.writeLock().lock();
        validateParkingLot();
        try {

            if (dataManager.leaveCar(level, slotNumber))
                System.out.println("Slot number " + slotNumber + " is free");
            else
                System.out.println("Slot number is Empty Already.");
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.SLOT_ALREADY_VACANT.getMessage().replace("{slotNumber}", String.valueOf(slotNumber)), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Vehicle> seek(int level, int slotNumber) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        Optional<Vehicle> value;
        try {
            value = dataManager.seek(level, slotNumber);
            if (value.isEmpty()) {
                throw new ParkingException(ErrorCode.VEHICLE_NOT_EXIST.getMessage(), ErrorCode.VEHICLE_NOT_EXIST.getCode());
            }
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException {
        lock.readLock().lock();
        Optional<Integer> value = Optional.empty();
        validateParkingLot();
        try {
            value = Optional.of(dataManager.getAvailableSlotsCount(level));
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }
}
