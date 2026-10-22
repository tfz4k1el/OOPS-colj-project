# Parking Lot Management System (PLMS)

A complete, object-oriented desktop application for automated vehicle entry, real-time slot allocation, dynamic occupancy visualization, and automated billing.

**Team Members:** Sonu, Vibik, Hafiz, Hiba, Ansiya, Devanandha, Arsha

---

## 🏛️ Project Architecture & OOP Mapping

Built strictly according to the team requirements and OOP pillars defined in [PLMS-Project-Rules.md](file:///d:/Parking%20lot%20mgmt/PLMS-Project-Rules.md):

| Class / Module | Role | OOP Concept Demonstrated |
|---|---|---|
| `Vehicle` *(abstract)* | Base entity defining common attributes (`licensePlate`, `vehicleType`, `entryTime`) and abstract tariff contract | **Abstraction** |
| `Car` & `Bike` | Concrete specializations extending `Vehicle` with custom attributes and polymorphic rate overrides | **Inheritance & Polymorphism** |
| `ParkingSlot` | Manages slot allocation state, guarding fields with validated `assignVehicle()` / `vacate()` mutators | **Encapsulation** |
| `FeeCalculator` | Dedicated pricing engine computing billing from stay duration and category rates | **Separation of Concerns** |
| `ParkingManager` | Orchestrates slot inventory, active tickets, and transaction logging | **Composition** |
| `DatabaseManager` | Relational persistence with SQLite / MySQL JDBC support and persistent log fallback | **Data Integrity** |
| `MainFrame` | Visual desktop dashboard with multi-floor interactive slot grid and audit feed | **User Interface (Swing)** |

---

## 🚀 Quick Start Guide

### 1. Run the Application
Double-click `run.bat` or execute in PowerShell:
```powershell
.\run.bat
```

### 2. Run Automated Tests
Double-click `test.bat` or run:
```powershell
.\test.bat
```

### 3. Rebuild from Source
```powershell
.\build.bat
```

---

## 📑 Report & Documentation Alignment Kit

All documentation files are pre-formatted to follow Section 1 & 2 of [PLMS-Project-Rules.md](file:///d:/Parking%20lot%20mgmt/PLMS-Project-Rules.md) (A4 margins, Times New Roman guidelines, IEEE references, locked terminology):

- **[docs/glossary.md](file:///d:/Parking%20lot%20mgmt/docs/glossary.md)**: Locked terminology across all chapters (`ParkingSlot`, `Vehicle`, `Booking`, `FeeCalculator`).
- **[docs/chapter_2_plms_goals.md](file:///d:/Parking%20lot%20mgmt/docs/chapter_2_plms_goals.md)**: Ready-to-use draft of Chapter 2 (purged of the leftover "MDPS" error, establishes genuine PLMS objectives and scope).
- **[docs/chapter_5_oop_exhibits.md](file:///d:/Parking%20lot%20mgmt/docs/chapter_5_oop_exhibits.md)**: Curated code snippets with explanations ready for screenshots into Chapter 5 and viva defense.
- **[docs/diagrams/dfd_and_er.md](file:///d:/Parking%20lot%20mgmt/docs/diagrams/dfd_and_er.md)**: Specification and Mermaid code for DFD Level 0, DFD Level 1, ER Diagram, and UML Class Diagram.
- **[database/schema.sql](file:///d:/Parking%20lot%20mgmt/database/schema.sql)**: DDL schema script for `parking_slots`, `vehicles`, and `bookings` with initial seed data.

---

## 📂 Project Directory Structure

```
d:\Parking lot mgmt\
├── build.bat                     # One-click compile script
├── run.bat                       # One-click launch script
├── test.bat                      # Automated unit test runner
├── database\
│   └── schema.sql                # SQL DDL script for database creation
├── docs\
│   ├── glossary.md               # Terminology lock guide
│   ├── chapter_2_plms_goals.md   # Corrected Chapter 2 draft
│   ├── chapter_5_oop_exhibits.md # Code exhibits for report screenshots
│   └── diagrams\
│       └── dfd_and_er.md         # DFD, ER & UML specifications
├── src\
│   └── com\plms\
│       ├── Main.java             # Application launcher
│       ├── model\                # Vehicle, Car, Bike, ParkingSlot, Booking
│       ├── service\              # ParkingManager, FeeCalculator
│       ├── db\                   # DatabaseManager
│       └── ui\                   # MainFrame, SlotPanel, ReceiptDialog
└── test\
    └── com\plms\test\
        └── PLMSTest.java         # Test suite
```

# OOPS-colj-project
