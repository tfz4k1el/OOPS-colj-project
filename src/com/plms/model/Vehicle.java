package com.plms.model;

import java.time.LocalDateTime;

/**
 * Demonstrates ABSTRACTION:
 * Defines the contract and common properties for all vehicles.
 * An abstract class cannot be instantiated directly.
 */
public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType vehicleType;
    private final LocalDateTime entryTime;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty");
        }
        this.licensePlate = licensePlate.toUpperCase().trim();
        this.vehicleType = vehicleType;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    /**
     * Abstract method enforcing polymorphic fee rate calculation in subclasses.
     */
    public abstract double calculateBaseHourlyRate();

    @Override
    public String toString() {
        return String.format("%s [%s] (Entry: %s)", licensePlate, vehicleType, entryTime);
    }
}
