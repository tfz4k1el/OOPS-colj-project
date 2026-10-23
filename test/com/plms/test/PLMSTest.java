package com.plms.test;

import com.plms.db.DatabaseManager;
import com.plms.model.*;
import com.plms.service.FeeCalculator;
import com.plms.service.ParkingManager;

import java.time.Duration;

/**
 * Automated test suite verifying the core domain and business logic.
 */
public class PLMSTest {
    public static void main(String[] args) {
        System.out.println("=== Starting PLMS Automated Test Suite ===");
        int passed = 0;
        int total = 0;

        FeeCalculator feeCalculator = new FeeCalculator(10.0);
        String testCsv = "parking_records_test.csv";
        new java.io.File(testCsv).delete();
        DatabaseManager dbManager = new DatabaseManager(testCsv);
        ParkingManager manager = new ParkingManager(feeCalculator, dbManager);

        // Test 1: Verify Initial Capacity
        total++;
        if (manager.getTotalSlots() == 12 && manager.getAvailableCount() == 12 && manager.getOccupiedCount() == 0) {
            System.out.println("[PASS] Test 1: Initial slot inventory initialized correctly (12 slots, 0 occupied).");
            passed++;
        } else {
            System.err.println("[FAIL] Test 1: Initial slot inventory incorrect.");
        }

        // Test 2: Check-In a Car and Verify Polymorphism / Type Allocation
        total++;
        Car car = new Car("KL-07-AZ-1001", 4);
        Booking carBooking = manager.checkInVehicle(car, null);
        if (carBooking != null && carBooking.getSlotId().equals("F1-C01") && manager.getOccupiedCount() == 1) {
            System.out.println("[PASS] Test 2: Car check-in assigned to first CAR slot F1-C01 successfully.");
            passed++;
        } else {
            System.err.println("[FAIL] Test 2: Car check-in failed.");
        }

        // Test 3: Check-In a Bike and Verify It routes to BIKE slot
        total++;
        Bike bike = new Bike("KL-07-BK-9999", true);
        Booking bikeBooking = manager.checkInVehicle(bike, null);
        if (bikeBooking != null && bikeBooking.getSlotId().equals("F1-B01") && manager.getOccupiedCount() == 2) {
            System.out.println("[PASS] Test 3: Bike check-in assigned to first BIKE slot F1-B01 successfully.");
            passed++;
        } else {
            System.err.println("[FAIL] Test 3: Bike check-in failed.");
        }

        // Test 4: Prevent Duplicate Vehicle Ingestion
        total++;
        try {
            manager.checkInVehicle(new Car("KL-07-AZ-1001"), null);
            System.err.println("[FAIL] Test 4: Duplicate plate check-in was allowed!");
        } catch (IllegalStateException e) {
            System.out.println("[PASS] Test 4: Duplicate check-in correctly rejected: " + e.getMessage());
            passed++;
        }

        // Test 5: Verify Fee Calculation & Polymorphic Rates
        total++;
        double car1HrFee = feeCalculator.calculateFee(car, Duration.ofHours(1));
        double bike1HrFee = feeCalculator.calculateFee(bike, Duration.ofHours(1));
        double car2Hr10MinFee = feeCalculator.calculateFee(car, Duration.ofMinutes(130)); // 3 hours * 20 = 60

        if (car1HrFee == 20.0 && bike1HrFee == 10.0 && car2Hr10MinFee == 60.0) {
            System.out.println("[PASS] Test 5: Polymorphic fee calculation correct (Car: 20/hr, Bike: 10/hr, Partial ceiling: 60 for 130m).");
            passed++;
        } else {
            System.err.printf("[FAIL] Test 5: Fee calculation mismatch: car1h=%.2f, bike1h=%.2f, car130m=%.2f%n",
                    car1HrFee, bike1HrFee, car2Hr10MinFee);
        }

        // Test 6: Check-Out Vehicle and Vacate Slot
        total++;
        Booking exitBooking = manager.checkOutVehicle("KL-07-AZ-1001");
        if (exitBooking != null && exitBooking.getPaymentStatus().equals("PAID") && manager.getOccupiedCount() == 1) {
            System.out.println("[PASS] Test 6: Check-out successful, slot vacated, payment marked PAID.");
            passed++;
        } else {
            System.err.println("[FAIL] Test 6: Check-out failed.");
        }

        System.out.printf("=== Test Results: %d / %d Tests Passed ===%n", passed, total);
        new java.io.File(testCsv).delete();
        if (passed != total) {
            System.exit(1);
        }
    }
}
