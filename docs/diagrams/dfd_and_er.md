# CHAPTER 6 DIAGRAM SPECIFICATIONS: DFD, ER & UML

> **Team Rule:** Consistent notation across all diagrams. Use these diagrams in draw.io or embed the Mermaid diagrams directly into markdown viewers.

---

## 1. Entity-Relationship (ER) Diagram (Chapter 6)

```mermaid
erDiagram
    VEHICLE ||--o{ BOOKING : "generates"
    PARKING_SLOT ||--o{ BOOKING : "assigned_to"
    
    VEHICLE {
        string license_plate PK
        string vehicle_type "CAR | BIKE"
        string owner_name
        string contact_number
        datetime registered_at
    }

    PARKING_SLOT {
        string slot_id PK
        string slot_type "CAR | BIKE"
        string status "AVAILABLE | OCCUPIED | RESERVED"
        int floor_number
    }

    BOOKING {
        string ticket_id PK
        string license_plate FK
        string slot_id FK
        datetime entry_time
        datetime exit_time
        int duration_minutes
        decimal fee_amount
        string payment_status "PENDING | PAID"
    }
```

---

## 2. UML Class Diagram (Chapter 5 / 6)

```mermaid
classDiagram
    class Vehicle {
        <<abstract>>
        #String licensePlate
        #VehicleType vehicleType
        #LocalDateTime entryTime
        +getLicensePlate() String
        +getVehicleType() VehicleType
        +getEntryTime() LocalDateTime
        +calculateBaseHourlyRate()* double
    }

    class Car {
        -int numberOfDoors
        +getNumberOfDoors() int
        +calculateBaseHourlyRate() double
    }

    class Bike {
        -boolean hasHelmetStorage
        +hasHelmetStorage() boolean
        +calculateBaseHourlyRate() double
    }

    class ParkingSlot {
        -String slotId
        -VehicleType allowedType
        -int floorNumber
        -SlotStatus status
        -Vehicle currentVehicle
        +assignVehicle(Vehicle vehicle) boolean
        +vacate() Vehicle
        +getSlotId() String
        +getAllowedType() VehicleType
        +getFloorNumber() int
        +getStatus() SlotStatus
        +getCurrentVehicle() Vehicle
        +isAvailable() boolean
    }

    class Booking {
        -String ticketId
        -String licensePlate
        -VehicleType vehicleType
        -String slotId
        -LocalDateTime entryTime
        -LocalDateTime exitTime
        -long durationMinutes
        -double feeAmount
        -String paymentStatus
        +completeBooking(LocalDateTime exitTime, double feeAmount) void
        +getTicketId() String
        +getLicensePlate() String
        +getVehicleType() VehicleType
        +getSlotId() String
        +getEntryTime() LocalDateTime
        +getExitTime() LocalDateTime
        +getDurationMinutes() long
        +getFeeAmount() double
        +getPaymentStatus() String
        +setPaymentStatus(String paymentStatus) void
    }

    class FeeCalculator {
        -double minimumFee
        +calculateFee(Vehicle vehicle, Duration duration) double
        +getMinimumFee() double
    }

    class ParkingManager {
        -List~ParkingSlot~ slots
        -Map~String, Booking~ activeBookings
        -FeeCalculator feeCalculator
        -DatabaseManager databaseManager
        +checkInVehicle(Vehicle vehicle, ParkingSlot targetSlot) Booking
        +checkOutVehicle(String slotIdOrPlate) Booking
        +findAvailableSlot(VehicleType type) ParkingSlot
        +getAllSlots() List~ParkingSlot~
        +getActiveBooking(String slotId) Booking
        +getTotalSlots() int
        +getOccupiedCount() int
        +getAvailableCount() int
    }

    Vehicle <|-- Car : Inheritance
    Vehicle <|-- Bike : Inheritance
    ParkingSlot o-- Vehicle : Holds
    ParkingManager *-- ParkingSlot : Composition
    ParkingManager --> FeeCalculator : Delegates Fee
    ParkingManager ..> Booking : Creates / Closes
```

---

## 3. Data Flow Diagrams (DFD)

### 3.1 DFD Level 0 (Context Level Diagram)

```mermaid
flowchart LR
    Driver([Driver / Customer])
    Attendant([Parking Attendant / Admin])
    PLMS[((Parking Lot Management System))]
    DB[(PLMS Database)]

    Driver -->|1. Vehicle Arrival & Plate| PLMS
    PLMS -->|2. Entry Ticket & Slot Number| Driver

    Driver -->|3. Ticket at Exit| PLMS
    PLMS -->|4. Billing Receipt & Gate Clearance| Driver

    Attendant -->|Check Slot Status / Config| PLMS
    PLMS -->|Occupancy & Revenue Reports| Attendant

    PLMS <-->|Read / Write Records| DB
```

### 3.2 DFD Level 1 (Detailed Subsystem Flow)

```mermaid
flowchart TD
    Driver([Driver])
    P1[1.0 Vehicle Ingestion & Validation]
    P2[2.0 Slot Allocation Engine]
    P3[3.0 Real-Time Display Update]
    P4[4.0 Duration & Fee Calculator]
    P5[5.0 Payment & Slot Release]
    
    D1[(Slots Store)]
    D2[(Bookings Store)]
    D3[(Tariff Store)]

    Driver -->|Vehicle Plate & Type| P1
    P1 -->|Validated Vehicle| P2
    P2 <-->|Check Available Slots| D1
    P2 -->|Save Active Ticket| D2
    P2 -->|Assigned Slot Notification| Driver
    P2 -->|Update Slot State| P3

    Driver -->|Ticket ID at Exit| P4
    D2 -->|Fetch Entry Timestamp| P4
    D3 -->|Tariff Rates| P4
    P4 -->|Calculated Fee Due| Driver

    Driver -->|Payment Received| P5
    P5 -->|Mark Ticket Paid| D2
    P5 -->|Vacate Slot (Status=AVAILABLE)| D1
    P5 -->|Receipt Printout| Driver
    P5 -->|Slot Vacated Signal| P3
```
