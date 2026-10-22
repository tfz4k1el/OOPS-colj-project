package com.plms.model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents a parking transaction / ticket record.
 */
public class Booking {
    private final String ticketId;
    private final String licensePlate;
    private final VehicleType vehicleType;
    private final String slotId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private long durationMinutes;
    private double feeAmount;
    private String paymentStatus; // PENDING, PAID

    public Booking(String ticketId, String licensePlate, VehicleType vehicleType, String slotId, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.paymentStatus = "PENDING";
    }

    public void completeBooking(LocalDateTime exitTime, double feeAmount) {
        this.exitTime = exitTime;
        this.durationMinutes = Math.max(1, Duration.between(entryTime, exitTime).toMinutes());
        this.feeAmount = feeAmount;
        this.paymentStatus = "PAID";
    }

    public String getTicketId() { return ticketId; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public String getSlotId() { return slotId; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public long getDurationMinutes() { return durationMinutes; }
    public double getFeeAmount() { return feeAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
