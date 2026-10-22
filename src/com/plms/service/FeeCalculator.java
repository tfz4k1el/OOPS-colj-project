package com.plms.service;

import com.plms.model.Vehicle;
import java.time.Duration;

/**
 * Demonstrates SEPARATION OF CONCERNS:
 * All tariff computations, hourly rounding rules, and penalty logic
 * are encapsulated solely in this class, independent of storage or UI.
 */
public class FeeCalculator {
    private final double minimumFee;

    public FeeCalculator(double minimumFee) {
        this.minimumFee = minimumFee;
    }

    public FeeCalculator() {
        this(10.0); // Default minimum parking charge
    }

    /**
     * Calculates fee using polymorphic base hourly rate from the Vehicle subclass.
     * Rounds up partial hours to the nearest whole hour.
     */
    public double calculateFee(Vehicle vehicle, Duration duration) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        long minutes = Math.max(1, duration.toMinutes());
        // Ceil to next full hour (e.g. 61 mins -> 2 hours)
        long billableHours = (long) Math.ceil(minutes / 60.0);
        
        // Polymorphic call: vehicle.calculateBaseHourlyRate()
        double rate = vehicle.calculateBaseHourlyRate();
        double total = billableHours * rate;
        
        return Math.max(total, minimumFee);
    }

    public double getMinimumFee() {
        return minimumFee;
    }
}
