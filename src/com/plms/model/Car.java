package com.plms.model;

/**
 * Demonstrates INHERITANCE and POLYMORPHISM:
 * Inherits state from Vehicle and provides specific behavior for four-wheelers.
 */
public class Car extends Vehicle {
    private final int numberOfDoors;

    public Car(String licensePlate, int numberOfDoors) {
        super(licensePlate, VehicleType.CAR);
        this.numberOfDoors = numberOfDoors;
    }

    public Car(String licensePlate) {
        this(licensePlate, 4);
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    @Override
    public double calculateBaseHourlyRate() {
        // Car parking rate: 20.0 per hour
        return VehicleType.CAR.getDefaultHourlyRate();
    }
}
