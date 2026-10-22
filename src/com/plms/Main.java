package com.plms;

import com.plms.db.DatabaseManager;
import com.plms.service.FeeCalculator;
import com.plms.service.ParkingManager;
import com.plms.ui.MainFrame;

import javax.swing.*;

/**
 * Main application launcher for the Parking Lot Management System.
 */
public class Main {
    public static void main(String[] args) {
        // Use system look and feel for native Windows appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            FeeCalculator feeCalculator = new FeeCalculator(10.0);
            DatabaseManager databaseManager = new DatabaseManager();
            ParkingManager parkingManager = new ParkingManager(feeCalculator, databaseManager);

            MainFrame frame = new MainFrame(parkingManager);
            frame.setVisible(true);
        });
    }
}
