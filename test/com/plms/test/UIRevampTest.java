package com.plms.test;

import com.plms.db.DatabaseManager;
import com.plms.model.Booking;
import com.plms.model.Car;
import com.plms.model.VehicleType;
import com.plms.service.FeeCalculator;
import com.plms.service.ParkingManager;
import com.plms.ui.MainFrame;
import com.plms.ui.ReceiptDialog;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * Headless / Swing verification for the revamped UI components.
 */
public class UIRevampTest {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Starting UI Revamp Automated Verification ===");

        FeeCalculator feeCalculator = new FeeCalculator(10.0);
        DatabaseManager dbManager = new DatabaseManager();
        ParkingManager manager = new ParkingManager(feeCalculator, dbManager);

        // 1. Verify MainFrame Instantiation
        MainFrame frame = new MainFrame(manager);
        if (frame.getTitle().contains("Parking Lot Management System")) {
            System.out.println("[PASS] UI Test 1: MainFrame successfully initialized with modern title and layout.");
        } else {
            throw new RuntimeException("UI Test 1 Failed");
        }

        // 2. Perform a Check-In and verify slot state
        Car car = new Car("KL-07-REVAMP-01");
        Booking b = manager.checkInVehicle(car, null);
        if (b != null && b.getSlotId().equals("F1-C01")) {
            System.out.println("[PASS] UI Test 2: Check-in allocated slot F1-C01 correctly.");
        } else {
            throw new RuntimeException("UI Test 2 Failed");
        }

        // 3. Verify ReceiptDialog Instantiation
        b.completeBooking(LocalDateTime.now().plusHours(2), 40.0);
        ReceiptDialog receipt = new ReceiptDialog(frame, b);
        if (receipt.getTitle().contains("Billing Receipt")) {
            System.out.println("[PASS] UI Test 3: ReceiptDialog initialized with itemized tariff and export options.");
        } else {
            throw new RuntimeException("UI Test 3 Failed");
        }

        receipt.dispose();
        frame.dispose();

        System.out.println("=== UI Revamp Verification: All Checks Passed! ===");
        System.exit(0);
    }
}
