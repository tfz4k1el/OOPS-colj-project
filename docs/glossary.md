# Parking Lot Management System (PLMS) — Terminology & Entity Glossary

> **Team Alignment Rule:** All team members (Sonu, Vibik, Hafiz, Hiba, Ansiya, Devanandha, Arsha) must use the exact terms defined below across all report chapters (Chapter 3, 4, 5, 6) and source code.

---

## 1. Locked Terms

| Approved Term | Disallowed Variants (Do Not Use) | Description |
|---|---|---|
| **Parking Slot** | parking space, bay, lot spot, car stall | A designated, demarcated physical space reserved for parking one vehicle. Identified by a unique `slotId` (e.g., `S-C01`, `S-B01`). |
| **Vehicle** | automobile, machine, motor | The generic entity representing any transport unit entering the lot. Concrete specializations are `Car` and `Bike`. |
| **Booking** | ticket record, transaction entry, pass | The record created when a vehicle enters a parking slot and closed when the vehicle departs upon fee payment. |
| **Parking Ticket** | token, receipt pass, coupon | The physical or digital proof of entry issued to the driver containing ticket ID, vehicle plate, assigned slot, and entry timestamp. |
| **Billing Receipt** | invoice, bill copy, payment voucher | The document issued at exit stating duration of stay, calculated charges, payment method, and exit timestamp. |
| **Parking Manager** | lot controller, system admin module | The central coordinator class managing slot availability, vehicle assignment, and business rules. |
| **Fee Calculator** | pricing engine, tariff module | Dedicated service responsible for determining parking fees based on duration and vehicle type tariffs. |

---

## 2. Slot Status Lifecycle

All diagrams and code must adhere to these 4 statuses:
1. `AVAILABLE` — Slot is empty and ready for assignment.
2. `OCCUPIED` — A vehicle is currently parked in the slot.
3. `RESERVED` — Slot is held for emergency, VIP, or maintenance.
4. `OUT_OF_SERVICE` — Slot is physically damaged or blocked.

---

## 3. Class & Entity Naming Rules

| Layer | Class / Entity Name | Key Attributes | OOP Pillar |
|---|---|---|---|
| Domain | `Vehicle` *(abstract)* | `licensePlate`, `type`, `entryTime` | **Abstraction** |
| Domain | `Car` | `carType`, `doors` | **Inheritance & Polymorphism** |
| Domain | `Bike` | `engineCc`, `hasHelmetStorage` | **Inheritance & Polymorphism** |
| Domain | `ParkingSlot` | `slotId`, `slotType`, `status`, `assignedVehicle` | **Encapsulation** |
| Service | `FeeCalculator` | `hourlyRateCar`, `hourlyRateBike`, `minFee` | **Separation of Concerns** |
| Service | `ParkingManager` | `slotsList`, `feeCalculator`, `dbManager` | **Composition** |
| Data | `Booking` | `ticketId`, `licensePlate`, `slotId`, `entryTime`, `exitTime`, `totalFee`, `paymentStatus` | **Data Integrity** |
