 # Parking Lot Management System — Team Rules & Tech Stack

**Group members:** Sonu, Vibik, Hafiz, Hiba, Ansiya, Devanandha, Arsha

---

## ⚠️ Fix This First

Your Table of Contents template still contains a leftover reference to **"2.2: Goals of MDPS"** — this appears to be copied from a previous project (likely a Medical Diagnosis Prediction System report). Before anyone starts writing Chapter 2, rename every leftover reference like this to your actual project (Parking Lot Management System / PLMS). Examiners spot copy-pasted templates immediately.

---

## 1. Document Formatting Rules

Set these up in **one shared template file** that everyone copies from — do not let each member format their own section independently.

| Element | Rule |
|---|---|
| Page size / margins | A4; Left margin 1.5" (binding side), Top/Bottom/Right 1" |
| Body font | Times New Roman, 12pt |
| Chapter titles | Times New Roman, 16pt, Bold, Centered, ALL CAPS |
| Section headings (e.g. 6.1) | 14pt, Bold, Left-aligned |
| Sub-section headings (e.g. 6.1.1) | 13pt, Bold Italic |
| Line spacing | 1.5 |
| Paragraph alignment | Justified |
| Page numbers | Roman numerals (i, ii, iii...) before Chapter 1; Arabic (1, 2, 3...) from Chapter 1 onward — bottom-center |
| Figures / tables | Numbered per chapter (Fig. 6.1, Fig. 6.2...); caption below figures, above tables; centered |
| References | IEEE numbered style — `[1]`, `[2]`, etc. |
| File format | One shared `.docx` (draft in Google Docs if convenient, export to Word at the end) |

**Team rule:** Assign one person as the **formatting owner** who merges everyone's sections at the end and fixes inconsistencies. Don't let multiple people fight over styling in a shared live doc.

---

## 2. Content Consistency Rules

- **Lock terminology early.** Decide once: "parking slot" vs. "parking space," "vehicle record" vs. "vehicle entry," etc. Put the agreed glossary at the top of the shared doc. Inconsistent terms across sections are the #1 sign a report was written by different people without coordination.
- **One diagramming tool for everyone** (recommended: draw.io or Lucidchart) with consistent notation for DFDs and ER diagrams. Don't mix hand-drawn and tool-made diagrams.
- **Class/entity names must match across chapters.** If Chapter 5 (Module-wise Description) defines `Vehicle`, `ParkingSlot`, `Booking` — Chapter 3 (Proposed System) and Chapter 6 (ER Diagram) must use the exact same names.

---

## 3. Tech Stack

### Language: **Java**
The abstract explicitly emphasizes OOP principles — encapsulation, inheritance, polymorphism, abstraction. Java maps these directly onto real language features (`private` fields, `extends`, method overriding, `abstract class` / `interface`) that you can screenshot straight into Chapter 5. This is also the expected choice for this report format.

### GUI: **Java Swing**
Simple, well-documented, standard for this kind of project. (JavaFX is a more modern-looking alternative if the team wants a nicer UI Diagram chapter, at the cost of slightly more setup.)

### Database: **MySQL** (via JDBC)
Maps naturally onto your ER diagram: `Vehicle`, `ParkingSlot`, and `Booking`/`Transaction` tables align directly with the four features named in the abstract.

### Suggested Class Structure
| Class | Purpose | OOP concept demonstrated |
|---|---|---|
| `Vehicle` (abstract) | Base type | Abstraction |
| `Car`, `Bike` (extends `Vehicle`) | Specific vehicle types | Inheritance, Polymorphism |
| `ParkingSlot` | Slot ID, status, type (fields kept private) | Encapsulation |
| `ParkingManager` | Allocation logic, ties modules together | Composition |
| `FeeCalculator` | Calculates fee by duration of stay | Separation of concerns |

### Supporting Tools
- **IDE:** Eclipse or IntelliJ IDEA
- **Diagrams:** draw.io (free; covers DFD, ER, and UML)
- **Version control:** GitHub — one repo, one designated merge-owner to avoid conflicts

### Lighter Alternative
If your course permits and the team wants faster setup: **Python + Tkinter + SQLite**. Same OOP concepts apply with less boilerplate, though Java/C++ is the more typically expected choice for this report style.

---

*Reference document for team alignment — update as decisions are finalized.*
