# CHAPTER 5: MODULE-WISE DESCRIPTION & OOP DEMONSTRATION EXHIBITS

*(Formatted for academic submission. Team members can use these exact code snippets and explanatory paragraphs for report screenshots and viva preparation.)*

---

## 5.1 Object-Oriented Principles Implementation

The Parking Lot Management System (PLMS) was deliberately architected around the four fundamental paradigms of Object-Oriented Programming (OOP) to achieve high cohesion, loose coupling, and modular maintainability.

---

### Exhibit 5.1: Abstraction (`Vehicle.java`)
**Concept:** Abstraction hides complex internal implementation details and exposes only the essential interface contract to the caller.

```java
package com.plms.model;

import java.time.LocalDateTime;

/**
 * Demonstrates ABSTRACTION.
 * Vehicle acts as an abstract base template. It cannot be instantiated directly,
 * enforcing that any concrete vehicle entering the lot must define its type and fee model.
 */
public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType vehicleType;
    private final LocalDateTime entryTime;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty");
        }
        this.licensePlate = licensePlate.toUpperCase().trim();
        this.vehicleType = vehicleType;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public LocalDateTime getEntryTime() { return entryTime; }

    // Abstract method: Forces each concrete vehicle subclass to define its tariff computation
    public abstract double calculateBaseHourlyRate();
}
```

---

### Exhibit 5.2: Inheritance (`Car.java` & `Bike.java`)
**Concept:** Inheritance allows specialized classes (`Car`, `Bike`) to inherit common state and behaviors from the generalized parent class (`Vehicle`), avoiding redundant code.

```java
package com.plms.model;

/**
 * Demonstrates INHERITANCE:
 * Car extends Vehicle, inheriting licensePlate, vehicleType, and entryTime.
 */
public class Car extends Vehicle {
    private final int numberOfDoors;

    public Car(String licensePlate, int numberOfDoors) {
        super(licensePlate, VehicleType.CAR);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() { return numberOfDoors; }

    @Override
    public double calculateBaseHourlyRate() {
        return 20.0; // ₹20 or $20 per hour for Cars
    }
}
```

```java
package com.plms.model;

/**
 * Demonstrates INHERITANCE:
 * Bike extends Vehicle, reusing base attributes and adding motorcycle-specific state.
 */
public class Bike extends Vehicle {
    private final boolean hasHelmetStorage;

    public Bike(String licensePlate, boolean hasHelmetStorage) {
        super(licensePlate, VehicleType.BIKE);
        this.hasHelmetStorage = hasHelmetStorage;
    }

    public boolean hasHelmetStorage() { return hasHelmetStorage; }

    @Override
    public double calculateBaseHourlyRate() {
        return 10.0; // ₹10 or $10 per hour for Two-Wheelers
    }
}
```

---

### Exhibit 5.3: Polymorphism (Dynamic Method Dispatch)
**Concept:** Polymorphism allows treating different subclass objects uniformly as the base type `Vehicle`, with the runtime executing the overridden method specific to the instantiated subclass.

```java
// Demonstration of Polymorphic Behavior in FeeCalculator:
public double calculateFee(Vehicle vehicle, Duration duration) {
    long hours = Math.max(1, (long) Math.ceil(duration.toMinutes() / 60.0));
    // Dynamic Method Dispatch: polymorphically calls Car.calculateBaseHourlyRate() or Bike.calculateBaseHourlyRate()
    double baseRate = vehicle.calculateBaseHourlyRate();
    double total = hours * baseRate;
    return Math.max(total, minimumFee);
}
```

---

### Exhibit 5.4: Encapsulation (`ParkingSlot.java`)
**Concept:** Encapsulation bundles fields and methods into a single protective unit, keeping fields `private` and exposing carefully validated accessors and mutators to prevent unauthorized state corruption.

```java
package com.plms.model;

/**
 * Demonstrates ENCAPSULATION:
 * Fields are private; slot state transitions (assign/vacate) are strictly controlled.
 */
public class ParkingSlot {
    private final String slotId;
    private final VehicleType allowedType;
    private final int floorNumber;
    private SlotStatus status;
    private Vehicle currentVehicle;

    public ParkingSlot(String slotId, VehicleType allowedType, int floorNumber) {
        this.slotId = slotId;
        this.allowedType = allowedType;
        this.floorNumber = floorNumber;
        this.status = SlotStatus.AVAILABLE;
        this.currentVehicle = null;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (this.status != SlotStatus.AVAILABLE) return false;
        if (vehicle.getVehicleType() != this.allowedType) return false;

        this.currentVehicle = vehicle;
        this.status = SlotStatus.OCCUPIED;
        return true;
    }

    public synchronized Vehicle vacate() {
        if (this.status != SlotStatus.OCCUPIED) return null;
        Vehicle released = this.currentVehicle;
        this.currentVehicle = null;
        this.status = SlotStatus.AVAILABLE;
        return released;
    }

    public String getSlotId() { return slotId; }
    public VehicleType getAllowedType() { return allowedType; }
    public int getFloorNumber() { return floorNumber; }
    public SlotStatus getStatus() { return status; }
    public Vehicle getCurrentVehicle() { return currentVehicle; }
    public boolean isAvailable() { return status == SlotStatus.AVAILABLE; }
}
```

---

### Exhibit 5.5: Composition (`ParkingManager.java`)
**Concept:** Rather than inheriting, `ParkingManager` *has-a* list of `ParkingSlot` objects, an active bookings map, a `FeeCalculator`, and a `DatabaseManager`.

```java
public class ParkingManager {
    // COMPOSITION: ParkingManager owns and coordinates its components
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final Map<String, Booking> activeBookings = new HashMap<>();
    private final FeeCalculator feeCalculator;
    private final DatabaseManager databaseManager;

    public ParkingManager(FeeCalculator feeCalculator, DatabaseManager databaseManager) {
        this.feeCalculator = feeCalculator;
        this.databaseManager = databaseManager;
    }
}
```

---

### Exhibit 5.6: Separation of Concerns (`FeeCalculator.java`)
Fee calculation logic is completely isolated from parking allocation logic and UI code. Tariff changes (e.g. holiday surcharges or discounts) require modifying only this dedicated class.
