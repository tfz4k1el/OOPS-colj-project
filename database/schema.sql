-- =========================================================
-- Parking Lot Management System (PLMS) Database Schema
-- Compatible with MySQL and SQLite
-- =========================================================

-- 1. Parking Slots Table
CREATE TABLE IF NOT EXISTS parking_slots (
    slot_id VARCHAR(20) PRIMARY KEY,
    slot_type VARCHAR(10) NOT NULL CHECK (slot_type IN ('CAR', 'BIKE')),
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'OCCUPIED', 'RESERVED', 'OUT_OF_SERVICE')),
    floor_number INT NOT NULL DEFAULT 1
);

-- 2. Vehicles Table
CREATE TABLE IF NOT EXISTS vehicles (
    license_plate VARCHAR(30) PRIMARY KEY,
    vehicle_type VARCHAR(10) NOT NULL CHECK (vehicle_type IN ('CAR', 'BIKE')),
    owner_name VARCHAR(100),
    contact_number VARCHAR(20),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Bookings / Transactions Table
CREATE TABLE IF NOT EXISTS bookings (
    ticket_id VARCHAR(50) PRIMARY KEY,
    license_plate VARCHAR(30) NOT NULL,
    slot_id VARCHAR(20) NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    duration_minutes INT,
    fee_amount DECIMAL(10, 2) DEFAULT 0.00,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (payment_status IN ('PENDING', 'PAID', 'CANCELLED')),
    FOREIGN KEY (license_plate) REFERENCES vehicles(license_plate),
    FOREIGN KEY (slot_id) REFERENCES parking_slots(slot_id)
);

-- =========================================================
-- Initial Seed Data: 12 Default Slots (6 Car, 6 Bike across 2 Floors)
-- =========================================================
INSERT OR IGNORE INTO parking_slots (slot_id, slot_type, status, floor_number) VALUES
-- Floor 1
('F1-C01', 'CAR', 'AVAILABLE', 1),
('F1-C02', 'CAR', 'AVAILABLE', 1),
('F1-C03', 'CAR', 'AVAILABLE', 1),
('F1-B01', 'BIKE', 'AVAILABLE', 1),
('F1-B02', 'BIKE', 'AVAILABLE', 1),
('F1-B03', 'BIKE', 'AVAILABLE', 1),
-- Floor 2
('F2-C01', 'CAR', 'AVAILABLE', 2),
('F2-C02', 'CAR', 'AVAILABLE', 2),
('F2-C03', 'CAR', 'AVAILABLE', 2),
('F2-B01', 'BIKE', 'AVAILABLE', 2),
('F2-B02', 'BIKE', 'AVAILABLE', 2),
('F2-B03', 'BIKE', 'AVAILABLE', 2);
