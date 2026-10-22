package com.plms.model;

/**
 * Enum defining the supported vehicle categories.
 */
public enum VehicleType {
    CAR("Car (4-Wheeler)", 20.0),
    BIKE("Bike (2-Wheeler)", 10.0);

    private final String displayName;
    private final double defaultHourlyRate;

    VehicleType(String displayName, double defaultHourlyRate) {
        this.displayName = displayName;
        this.defaultHourlyRate = defaultHourlyRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDefaultHourlyRate() {
        return defaultHourlyRate;
    }
}
