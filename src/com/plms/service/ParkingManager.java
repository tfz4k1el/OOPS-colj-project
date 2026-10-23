package com.plms.service;

import com.plms.db.DatabaseManager;
import com.plms.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Demonstrates COMPOSITION:
 * ParkingManager has a collection of ParkingSlots, a FeeCalculator,
 * and a DatabaseManager to coordinate the entire parking lifecycle.
 */
public class ParkingManager {
    private final List<ParkingSlot> slots;
    private final FeeCalculator feeCalculator;
    private final DatabaseManager databaseManager;
    private final Map<String, Booking> activeBookings; // key: slotId

    public ParkingManager(FeeCalculator feeCalculator, DatabaseManager databaseManager) {
        this.slots = new ArrayList<>();
        this.feeCalculator = feeCalculator;
        this.databaseManager = databaseManager;
        this.activeBookings = new HashMap<>();
        initializeDefaultSlots();
    }

    private void initializeDefaultSlots() {
        // Floor 1 (Ground)
        slots.add(new ParkingSlot("F1-C01", VehicleType.CAR, 1));
        slots.add(new ParkingSlot("F1-C02", VehicleType.CAR, 1));
        slots.add(new ParkingSlot("F1-C03", VehicleType.CAR, 1));
        slots.add(new ParkingSlot("F1-B01", VehicleType.BIKE, 1));
        slots.add(new ParkingSlot("F1-B02", VehicleType.BIKE, 1));
        slots.add(new ParkingSlot("F1-B03", VehicleType.BIKE, 1));

        // Floor 2
        slots.add(new ParkingSlot("F2-C01", VehicleType.CAR, 2));
        slots.add(new ParkingSlot("F2-C02", VehicleType.CAR, 2));
        slots.add(new ParkingSlot("F2-C03", VehicleType.CAR, 2));
        slots.add(new ParkingSlot("F2-B01", VehicleType.BIKE, 2));
        slots.add(new ParkingSlot("F2-B02", VehicleType.BIKE, 2));
        slots.add(new ParkingSlot("F2-B03", VehicleType.BIKE, 2));
    }

    /**
     * Finds the first available slot that matches the vehicle type.
     */
    public synchronized ParkingSlot findAvailableSlot(VehicleType type) {
        for (ParkingSlot slot : slots) {
            if (slot.isAvailable() && slot.getAllowedType() == type) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Finds a slot by its unique identifier (e.g. F1-C01).
     */
    public synchronized ParkingSlot findSlotById(String slotId) {
        if (slotId == null) return null;
        for (ParkingSlot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(slotId.trim())) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Parks a vehicle into an assigned slot and creates a Booking ticket.
     */
    public synchronized Booking checkInVehicle(Vehicle vehicle, ParkingSlot targetSlot) {
        if (targetSlot == null) {
            targetSlot = findAvailableSlot(vehicle.getVehicleType());
        }
        if (targetSlot == null) {
            throw new IllegalStateException("No available slot found for " + vehicle.getVehicleType());
        }

        // Prevent duplicate check-in with the same license plate
        for (Booking active : activeBookings.values()) {
            if (active.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate())) {
                throw new IllegalStateException("Vehicle " + vehicle.getLicensePlate() + " is already parked in slot " + active.getSlotId());
            }
        }

        boolean assigned = targetSlot.assignVehicle(vehicle);
        if (!assigned) {
            throw new IllegalStateException("Slot " + targetSlot.getSlotId() + " could not be assigned to vehicle.");
        }

        String ticketId = "TKT-" + System.currentTimeMillis() % 100000;
        Booking booking = new Booking(ticketId, vehicle.getLicensePlate(), vehicle.getVehicleType(), targetSlot.getSlotId(), vehicle.getEntryTime());
        activeBookings.put(targetSlot.getSlotId(), booking);
        databaseManager.recordCheckIn(booking);
        return booking;
    }

    /**
     * Checks out a vehicle, calculates the fee, vacates the slot, and completes the Booking.
     */
    public synchronized Booking checkOutVehicle(String slotIdOrPlate) {
        ParkingSlot targetSlot = null;
        Booking booking = null;

        // Try lookup by slotId first
        for (ParkingSlot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(slotIdOrPlate)) {
                targetSlot = slot;
                booking = activeBookings.get(slot.getSlotId());
                break;
            }
        }

        // Try lookup by license plate
        if (targetSlot == null) {
            for (ParkingSlot slot : slots) {
                Vehicle v = slot.getCurrentVehicle();
                if (v != null && v.getLicensePlate().equalsIgnoreCase(slotIdOrPlate.trim())) {
                    targetSlot = slot;
                    booking = activeBookings.get(slot.getSlotId());
                    break;
                }
            }
        }

        if (targetSlot == null || booking == null || targetSlot.getCurrentVehicle() == null) {
            throw new IllegalArgumentException("No occupied slot or active ticket found for: " + slotIdOrPlate);
        }

        Vehicle vehicle = targetSlot.getCurrentVehicle();
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(booking.getEntryTime(), exitTime);
        
        // Ensure at least 1 minute duration for test demo
        if (duration.isZero() || duration.isNegative()) {
            duration = Duration.ofMinutes(1);
        }

        double fee = feeCalculator.calculateFee(vehicle, duration);
        booking.completeBooking(exitTime, fee);

        targetSlot.vacate();
        activeBookings.remove(targetSlot.getSlotId());
        databaseManager.recordCheckOut(booking);

        return booking;
    }

    public synchronized List<ParkingSlot> getAllSlots() {
        return Collections.unmodifiableList(slots);
    }

    public synchronized Booking getActiveBooking(String slotId) {
        return activeBookings.get(slotId);
    }

    public synchronized int getTotalSlots() {
        return slots.size();
    }

    public synchronized int getOccupiedCount() {
        return activeBookings.size();
    }

    public synchronized int getAvailableCount() {
        return slots.size() - activeBookings.size();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
