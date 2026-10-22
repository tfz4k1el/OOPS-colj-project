package com.plms.model;

/**
 * Demonstrates INHERITANCE and POLYMORPHISM:
 * Inherits state from Vehicle and provides specific behavior for two-wheelers.
 */
public class Bike extends Vehicle {
    private final boolean hasHelmetStorage;

    public Bike(String licensePlate, boolean hasHelmetStorage) {
        super(licensePlate, VehicleType.BIKE);
        this.hasHelmetStorage = hasHelmetStorage;
    }

    public Bike(String licensePlate) {
        this(licensePlate, true);
    }

    public boolean hasHelmetStorage() {
        return hasHelmetStorage;
    }

    @Override
    public double calculateBaseHourlyRate() {
        // Two-wheeler rate: 10.0 per hour
        return VehicleType.BIKE.getDefaultHourlyRate();
    }
}
