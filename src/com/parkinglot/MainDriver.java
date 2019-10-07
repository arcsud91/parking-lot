package com.parkinglot;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.Color;
import com.parkinglot.entities.Vehicle;
import com.parkinglot.service.ParkingService;
import com.parkinglot.service.impl.ParkingServiceImpl;
import com.parkinglot.exceptions.ParkingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainDriver {

    private static ParkingService parkingService = new ParkingServiceImpl();

    /**
     * Main driver method which will initiate the service calls
     * */
    public static void main(String[] args) {
        int level = 1;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("===============Enter Capacity of the Parking lot=================");
            int capacity = sc.nextInt();
            parkingService.createParkingLot(level, capacity);
            String c;
            do {
                System.out.println("===============Select operation=================");
                System.out.println("1. Parking");
                System.out.println("2. Seek");
                int input = sc.nextInt();
                switch (input) {
                    case 1: {
                        System.out.println("===============Enter Car Plate Number=================");
                        String number = sc.next();
                        System.out.println("===============Enter color of car=================");
                        String color = sc.next();
                        parkingService.park(level, new Car(number, getColor(color)));
                        break;
                    }
                    case 2: {
                        System.out.println("===============Enter slot number to seek =================");
                        int number = sc.nextInt();
                        Optional<Vehicle> vehicle = parkingService.seek(level, number);
                        if (vehicle.isEmpty()) {
                            System.out.println("Slot is vacant.");
                        } else {
                            System.out.print("Vehicle Number: " + vehicle.get().getPlateNumber());
                            System.out.print(" Vehicle Color: " + vehicle.get().getColor());
                            System.out.println("");
                        }
                        break;
                    }
                    default: {
                        System.exit(1);
                    }
                }
                System.out.println("Continue ?(y/n)");
                c = sc.next();
            } while ("yes".equalsIgnoreCase(c) || "y".equalsIgnoreCase(c));

        } catch (ParkingException e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }

    private static Color getColor(String name) {
        List<Color> colors = Arrays.asList(Color.values());
        for (Color c: colors) {
            if (c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return Color.DEFAULT;
    }
}
