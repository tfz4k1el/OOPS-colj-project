package com.plms.model;

/**
 * Demonstrates ENCAPSULATION:
 * All internal fields are private; mutations occur exclusively through
 * validated methods (assignVehicle, vacate).
 */
public class ParkingSlot {
    private final String slotId;
    private final VehicleType allowedType;
    private final int floorNumber;
    private SlotStatus status;
    private Vehicle currentVehicle;

    public ParkingSlot(String slotId, VehicleType allowedType, int floorNumber) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new IllegalArgumentException("Slot ID cannot be empty");
        }
        this.slotId = slotId.trim();
        this.allowedType = allowedType;
        this.floorNumber = floorNumber;
        this.status = SlotStatus.AVAILABLE;
        this.currentVehicle = null;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (this.status != SlotStatus.AVAILABLE) {
            return false;
        }
        if (vehicle.getVehicleType() != this.allowedType) {
            return false; // Type mismatch (e.g. Car in Bike slot)
        }
        this.currentVehicle = vehicle;
        this.status = SlotStatus.OCCUPIED;
        return true;
    }

    public synchronized Vehicle vacate() {
        if (this.status != SlotStatus.OCCUPIED) {
            return null;
        }
        Vehicle released = this.currentVehicle;
        this.currentVehicle = null;
        this.status = SlotStatus.AVAILABLE;
        return released;
    }

    public synchronized void setStatus(SlotStatus status) {
        if (status == SlotStatus.OCCUPIED && this.currentVehicle == null) {
            throw new IllegalStateException("Cannot set OCCUPIED without an assigned vehicle");
        }
        this.status = status;
    }

    public String getSlotId() {
        return slotId;
    }

    public VehicleType getAllowedType() {
        return allowedType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public synchronized SlotStatus getStatus() {
        return status;
    }

    public synchronized Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public synchronized boolean isAvailable() {
        return status == SlotStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("Slot %s (Floor %d, %s) - %s", slotId, floorNumber, allowedType, status);
    }
}
