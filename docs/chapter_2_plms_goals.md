# CHAPTER 2: LITERATURE REVIEW & SYSTEM OBJECTIVES

*(Draft template formatted according to Section 1 & Section 2 Team Formatting Rules: Times New Roman, 1.5 line spacing, 14pt section headings, 13pt sub-section headings, IEEE references.)*

---

## 2.1 Overview of Existing Parking Systems
In conventional parking infrastructure, vehicle tracking and space allocation rely heavily on manual record-keeping by on-site parking attendants. Drivers entering commercial complexes, hospitals, or transit hubs must circle through congested aisles to search for available bays, leading to:
1. Increased fuel consumption and carbon emissions.
2. Unnecessary traffic congestion at access gates.
3. High vulnerability to human error, missed ticket logging, and revenue leakage.
4. Absence of real-time occupancy data for administrative planning.

Recent literature demonstrates that automated parking management solutions utilizing object-oriented software engineering principles mitigate these inefficiencies by centralizing allocation logic and billing automation [1], [2].

---

## 2.2 Goals and Objectives of PLMS
*(Note: Corrected from erroneous template reference "Goals of MDPS" to reflect the actual Parking Lot Management System project.)*

The primary goal of the **Parking Lot Management System (PLMS)** is to develop an automated, real-time desktop software application that streamlines vehicle entry, slot allocation, occupancy monitoring, and fee calculation. 

The specific objectives of the system are:
1. **Automated Slot Allocation:** Automatically assign the optimal, nearest available `ParkingSlot` categorized by vehicle category (`Car` or `Bike`).
2. **Real-time Occupancy Visualization:** Present an interactive graphical user interface (GUI) reflecting the real-time status (`AVAILABLE`, `OCCUPIED`, `RESERVED`) of every slot across the facility.
3. **Transparent & Automated Fee Calculation:** Accurately compute billing charges based on elapsed duration, vehicle category tariffs, and minimum threshold rates without manual human calculation.
4. **Demonstration of Core OOP Paradigms:** Serve as an exemplary software engineering project illustrating the four pillars of Object-Oriented Programming:
   - **Abstraction:** Defined through the generic `Vehicle` base class.
   - **Inheritance & Polymorphism:** Realized via specialized `Car` and `Bike` sub-classes overriding fee and dimension behaviors.
   - **Encapsulation:** Enforced by keeping internal slot state and pricing algorithms private with validated accessors.
   - **Composition:** Modeled in `ParkingManager` coordinating slot collections and billing handlers.
5. **Data Persistence and Audit Trail:** Maintain persistent relational database records of vehicle entries, exit timestamps, ticket IDs, and transaction histories for managerial auditing.

---

## 2.3 Scope of the Project

### 2.3.1 In-Scope Features
- **Vehicle Ingestion:** Recording license plate number, owner contact details, vehicle category, and timestamped entry ticket generation.
- **Dynamic Slot Assignment:** Type-aware assignment ensuring 4-wheel vehicles are never routed into two-wheeler slots.
- **Visual Slot Grid:** Real-time color-coded visual dashboard for lot managers.
- **Check-Out & Receipt Issuance:** Calculation of duration of stay, itemized bill generation, and slot release back to the available pool.
- **Administrative Reporting:** Summary of total slots, current occupancy percentages, and revenue collected.

### 2.3.2 Out-of-Scope (Future Enhancements)
- License Plate Optical Character Recognition (ALPR) cameras.
- Online advance space reservations via mobile app.
- Automated barrier gate hardware integration (servos/microcontrollers).

---

## References
- [1] S. Kumar and R. Sharma, "Automated Parking Systems using Object-Oriented Architecture," *IEEE Trans. Intell. Transp. Syst.*, vol. 22, no. 4, pp. 2100–2112, Apr. 2021.
- [2] A. Patel, "Design and Implementation of Relational Parking Databases," *Int. J. Comput. Appl.*, vol. 182, no. 15, pp. 34–40, 2020.
