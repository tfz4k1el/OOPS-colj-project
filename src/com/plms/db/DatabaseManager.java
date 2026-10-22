package com.plms.db;

import com.plms.model.Booking;
import com.plms.model.VehicleType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Handles database persistence for PLMS.
 * Supports SQLite / MySQL via JDBC when available, and includes a persistent
 * local file fallback to ensure zero-dependency instant execution.
 */
public class DatabaseManager {
    private static final String CSV_FILE = "parking_records.csv";
    private Connection connection = null;
    private boolean isJdbcAvailable = false;

    public DatabaseManager() {
        initStorage();
    }

    private void initStorage() {
        // Attempt JDBC connection (SQLite / MySQL)
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:plms.db");
            createTables();
            isJdbcAvailable = true;
            System.out.println("[DatabaseManager] Connected to SQLite database.");
        } catch (Throwable t) {
            initFileStorage();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS bookings (" +
                    "ticket_id TEXT PRIMARY KEY, " +
                    "license_plate TEXT NOT NULL, " +
                    "vehicle_type TEXT NOT NULL, " +
                    "slot_id TEXT NOT NULL, " +
                    "entry_time TEXT NOT NULL, " +
                    "exit_time TEXT, " +
                    "duration_minutes INTEGER, " +
                    "fee_amount REAL, " +
                    "payment_status TEXT);");
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Table creation error: " + e.getMessage());
        }
    }

    private void initFileStorage() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.println("TicketId,LicensePlate,VehicleType,SlotId,EntryTime,ExitTime,DurationMinutes,FeeAmount,PaymentStatus");
            } catch (IOException e) {
                System.err.println("[DatabaseManager] Failed to create log file: " + e.getMessage());
            }
        }
    }

    public synchronized void recordCheckIn(Booking booking) {
        if (isJdbcAvailable && connection != null) {
            String sql = "INSERT INTO bookings (ticket_id, license_plate, vehicle_type, slot_id, entry_time, payment_status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, booking.getTicketId());
                pstmt.setString(2, booking.getLicensePlate());
                pstmt.setString(3, booking.getVehicleType().name());
                pstmt.setString(4, booking.getSlotId());
                pstmt.setString(5, booking.getEntryTime().toString());
                pstmt.setString(6, booking.getPaymentStatus());
                pstmt.executeUpdate();
                return;
            } catch (SQLException e) {
                System.err.println("[DatabaseManager] JDBC check-in error: " + e.getMessage());
            }
        }

        // File log fallback
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(CSV_FILE, true), StandardCharsets.UTF_8))) {
            out.printf("%s,%s,%s,%s,%s,,,0.0,%s%n",
                    booking.getTicketId(),
                    booking.getLicensePlate(),
                    booking.getVehicleType().name(),
                    booking.getSlotId(),
                    booking.getEntryTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    booking.getPaymentStatus());
        } catch (IOException e) {
            System.err.println("[DatabaseManager] File write error: " + e.getMessage());
        }
    }

    public synchronized void recordCheckOut(Booking booking) {
        if (isJdbcAvailable && connection != null) {
            String sql = "UPDATE bookings SET exit_time = ?, duration_minutes = ?, fee_amount = ?, payment_status = ? WHERE ticket_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, booking.getExitTime() != null ? booking.getExitTime().toString() : "");
                pstmt.setLong(2, booking.getDurationMinutes());
                pstmt.setDouble(3, booking.getFeeAmount());
                pstmt.setString(4, booking.getPaymentStatus());
                pstmt.setString(5, booking.getTicketId());
                pstmt.executeUpdate();
                return;
            } catch (SQLException e) {
                System.err.println("[DatabaseManager] JDBC check-out error: " + e.getMessage());
            }
        }

        // In-place CSV row update to prevent duplicate ticket lines
        File file = new File(CSV_FILE);
        List<String> lines = new ArrayList<>();
        boolean updated = false;
        String updatedRow = String.format("%s,%s,%s,%s,%s,%s,%d,%.2f,%s",
                booking.getTicketId(),
                booking.getLicensePlate(),
                booking.getVehicleType().name(),
                booking.getSlotId(),
                booking.getEntryTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                booking.getExitTime() != null ? booking.getExitTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "",
                booking.getDurationMinutes(),
                booking.getFeeAmount(),
                booking.getPaymentStatus());

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(booking.getTicketId() + ",")) {
                        lines.add(updatedRow);
                        updated = true;
                    } else {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("[DatabaseManager] Error reading CSV: " + e.getMessage());
            }
        }

        if (!updated) {
            lines.add(updatedRow);
        }

        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                out.println(line);
            }
        } catch (IOException e) {
            System.err.println("[DatabaseManager] File write error: " + e.getMessage());
        }
    }

    public List<Booking> loadAllBookings() {
        List<Booking> list = new ArrayList<>();
        File file = new File(CSV_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    try {
                        String ticketId = parts[0];
                        String plate = parts[1];
                        VehicleType type = VehicleType.valueOf(parts[2]);
                        String slotId = parts[3];
                        LocalDateTime entryTime = LocalDateTime.parse(parts[4]);
                        Booking b = new Booking(ticketId, plate, type, slotId, entryTime);
                        if (parts.length >= 9 && !parts[5].isEmpty()) {
                            LocalDateTime exitTime = LocalDateTime.parse(parts[5]);
                            double fee = Double.parseDouble(parts[7]);
                            b.completeBooking(exitTime, fee);
                            b.setPaymentStatus(parts[8]);
                        }
                        list.add(b);
                    } catch (Exception ex) {
                        // ignore malformed line
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[DatabaseManager] Error reading records: " + e.getMessage());
        }
        return list;
    }
}
