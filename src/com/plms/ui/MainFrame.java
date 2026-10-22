package com.plms.ui;

import com.plms.model.*;
import com.plms.service.ParkingManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application window for the Parking Lot Management System.
 */
public class MainFrame extends JFrame {
    private final ParkingManager parkingManager;
    private final List<SlotPanel> slotPanels = new ArrayList<>();

    // Dashboard metrics
    private JLabel totalSlotsLabel;
    private JLabel availableSlotsLabel;
    private JLabel occupiedSlotsLabel;

    // Check-in controls
    private JTextField plateField;
    private JRadioButton carRadio;
    private JRadioButton bikeRadio;

    // Check-out controls
    private JTextField checkOutField;

    // Transaction History Table
    private DefaultTableModel tableModel;

    public MainFrame(ParkingManager parkingManager) {
        super("Parking Lot Management System (PLMS) - Desktop Control Center");
        this.parkingManager = parkingManager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);

        initComponents();
        loadHistoricalRecords();
        refreshDashboard();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(241, 245, 249));

        // 1. Top Header Panel with KPIs
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Center Content Split: Left (Operations) & Center (Floor Plan Grid)
        JPanel mainContent = new JPanel(new BorderLayout(12, 12));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(0, 15, 15, 15));

        mainContent.add(createOperationsPanel(), BorderLayout.WEST);
        mainContent.add(createFloorPlanPanel(), BorderLayout.CENTER);
        mainContent.add(createLogTablePanel(), BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(15, 15));
        header.setBackground(new Color(15, 23, 42)); // Dark Slate 900
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 2, 2));
        titleBox.setOpaque(false);
        JLabel title = new JLabel("PARKING LOT MANAGEMENT SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Automated Allocation & Fee Calculation Engine | OOP Demonstration");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(148, 163, 184));
        titleBox.add(title);
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);

        // KPI Badges
        JPanel kpiPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        kpiPanel.setOpaque(false);

        totalSlotsLabel = createKpiBadge("TOTAL SLOTS", "12", new Color(59, 130, 246));
        availableSlotsLabel = createKpiBadge("AVAILABLE", "12", new Color(34, 197, 94));
        occupiedSlotsLabel = createKpiBadge("OCCUPIED", "0", new Color(239, 68, 68));

        kpiPanel.add(totalSlotsLabel.getParent());
        kpiPanel.add(availableSlotsLabel.getParent());
        kpiPanel.add(occupiedSlotsLabel.getParent());

        header.add(kpiPanel, BorderLayout.EAST);
        return header;
    }

    private JLabel createKpiBadge(String title, String val, Color valColor) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 0));
        card.setOpaque(false);
        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLbl.setForeground(new Color(203, 213, 225));

        JLabel valLbl = new JLabel(val, SwingConstants.CENTER);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valLbl.setForeground(valColor);

        card.add(titleLbl);
        card.add(valLbl);
        return valLbl;
    }

    private JPanel createOperationsPanel() {
        JPanel opsPanel = new JPanel();
        opsPanel.setLayout(new BoxLayout(opsPanel, BoxLayout.Y_AXIS));
        opsPanel.setPreferredSize(new Dimension(280, 400));
        opsPanel.setOpaque(false);

        // --- Check-in Box ---
        JPanel checkInBox = new JPanel();
        checkInBox.setLayout(new BoxLayout(checkInBox, BoxLayout.Y_AXIS));
        checkInBox.setBackground(Color.WHITE);
        checkInBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel inTitle = new JLabel("Vehicle Entry (Check-In)");
        inTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inTitle.setForeground(new Color(30, 41, 59));
        checkInBox.add(inTitle);
        checkInBox.add(Box.createVerticalStrut(10));

        checkInBox.add(new JLabel("License Plate Number:"));
        checkInBox.add(Box.createVerticalStrut(4));
        plateField = new JTextField();
        plateField.setFont(new Font("Segoe UI", Font.BOLD, 13));
        plateField.setMaximumSize(new Dimension(260, 30));
        plateField.addActionListener(e -> handleCheckIn());
        checkInBox.add(plateField);
        checkInBox.add(Box.createVerticalStrut(8));

        checkInBox.add(new JLabel("Vehicle Category:"));
        carRadio = new JRadioButton("Car (Rate: 20/hr)", true);
        bikeRadio = new JRadioButton("Bike (Rate: 10/hr)");
        carRadio.setOpaque(false);
        bikeRadio.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        group.add(carRadio);
        group.add(bikeRadio);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        radioPanel.setOpaque(false);
        radioPanel.add(carRadio);
        radioPanel.add(bikeRadio);
        checkInBox.add(radioPanel);
        checkInBox.add(Box.createVerticalStrut(10));

        ModernButton checkInBtn = new ModernButton("Assign Slot & Check-In",
                new Color(37, 99, 235),
                new Color(29, 78, 216));
        checkInBtn.setPreferredSize(new Dimension(260, 36));
        checkInBtn.setMaximumSize(new Dimension(260, 36));
        checkInBtn.addActionListener(e -> handleCheckIn());
        checkInBox.add(checkInBtn);

        opsPanel.add(checkInBox);
        opsPanel.add(Box.createVerticalStrut(12));

        // --- Check-out Box ---
        JPanel checkOutBox = new JPanel();
        checkOutBox.setLayout(new BoxLayout(checkOutBox, BoxLayout.Y_AXIS));
        checkOutBox.setBackground(Color.WHITE);
        checkOutBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel outTitle = new JLabel("Vehicle Exit (Check-Out)");
        outTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        outTitle.setForeground(new Color(30, 41, 59));
        checkOutBox.add(outTitle);
        checkOutBox.add(Box.createVerticalStrut(10));

        checkOutBox.add(new JLabel("Plate or Slot ID:"));
        checkOutBox.add(Box.createVerticalStrut(4));
        checkOutField = new JTextField();
        checkOutField.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkOutField.setMaximumSize(new Dimension(260, 30));
        checkOutField.addActionListener(e -> handleCheckOut(checkOutField.getText()));
        checkOutBox.add(checkOutField);
        checkOutBox.add(Box.createVerticalStrut(10));

        ModernButton checkOutBtn = new ModernButton("Calculate Fee & Release",
                new Color(225, 29, 72),
                new Color(190, 18, 60));
        checkOutBtn.setPreferredSize(new Dimension(260, 36));
        checkOutBtn.setMaximumSize(new Dimension(260, 36));
        checkOutBtn.addActionListener(e -> handleCheckOut(checkOutField.getText()));
        checkOutBox.add(checkOutBtn);

        opsPanel.add(checkOutBox);
        return opsPanel;
    }

    private JTabbedPane createFloorPlanPanel() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));

        tabs.addTab("Floor 1 (Ground)", createFloorGrid(1));
        tabs.addTab("Floor 2 (Upper)", createFloorGrid(2));

        return tabs;
    }

    private JPanel createFloorGrid(int floorNumber) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(248, 250, 252));
        container.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15));
        grid.setOpaque(false);

        for (ParkingSlot slot : parkingManager.getAllSlots()) {
            if (slot.getFloorNumber() == floorNumber) {
                SlotPanel sp = new SlotPanel(slot, this::onSlotClicked);
                slotPanels.add(sp);
                grid.add(sp);
            }
        }

        container.add(grid, BorderLayout.CENTER);
        return container;
    }

    private JPanel createLogTablePanel() {
        JPanel logPanel = new JPanel(new BorderLayout(5, 5));
        logPanel.setPreferredSize(new Dimension(1000, 160));
        logPanel.setBackground(Color.WHITE);
        logPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(6, 10, 6, 10)));

        JLabel title = new JLabel("Audit & Transaction Activity Feed");
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));
        title.setForeground(new Color(71, 85, 105));
        logPanel.add(title, BorderLayout.NORTH);

        String[] cols = {"Ticket ID", "License Plate", "Type", "Slot ID", "Entry Time", "Exit Time", "Fee (INR)", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        JScrollPane sp = new JScrollPane(table);
        logPanel.add(sp, BorderLayout.CENTER);
        return logPanel;
    }

    private void onSlotClicked(ParkingSlot slot) {
        if (!slot.isAvailable()) {
            checkOutField.setText(slot.getSlotId());
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Slot " + slot.getSlotId() + " is occupied by " + slot.getCurrentVehicle().getLicensePlate() +
                            ".\nDo you want to check out this vehicle now?",
                    "Slot Selected",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                handleCheckOut(slot.getSlotId());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Slot " + slot.getSlotId() + " is currently AVAILABLE for " + slot.getAllowedType() + "s.");
        }
    }

    private void handleCheckIn() {
        String plate = plateField.getText().trim().toUpperCase();
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid license plate number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Vehicle vehicle = carRadio.isSelected() ? new Car(plate) : new Bike(plate);

        try {
            Booking booking = parkingManager.checkInVehicle(vehicle, null);
            plateField.setText("");
            refreshDashboard();

            // Add row to table
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            tableModel.insertRow(0, new Object[]{
                    booking.getTicketId(),
                    booking.getLicensePlate(),
                    booking.getVehicleType(),
                    booking.getSlotId(),
                    booking.getEntryTime().format(fmt),
                    "-",
                    "-",
                    booking.getPaymentStatus()
            });

            JOptionPane.showMessageDialog(this,
                    String.format("Vehicle checked in successfully!\nTicket: %s\nAssigned Slot: %s", booking.getTicketId(), booking.getSlotId()),
                    "Check-In Complete",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Check-In Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCheckOut(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a License Plate or Slot ID to check out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Booking booking = parkingManager.checkOutVehicle(identifier.trim());
            checkOutField.setText("");
            refreshDashboard();

            // Update row in table
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(booking.getTicketId())) {
                    tableModel.setValueAt(booking.getExitTime().format(fmt), i, 5);
                    tableModel.setValueAt(String.format("%.2f", booking.getFeeAmount()), i, 6);
                    tableModel.setValueAt(booking.getPaymentStatus(), i, 7);
                    break;
                }
            }

            // Show Receipt Dialog
            ReceiptDialog receipt = new ReceiptDialog(this, booking);
            receipt.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Check-Out Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHistoricalRecords() {
        if (parkingManager.getDatabaseManager() == null) return;
        List<Booking> past = parkingManager.getDatabaseManager().loadAllBookings();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Booking b : past) {
            tableModel.insertRow(0, new Object[]{
                    b.getTicketId(),
                    b.getLicensePlate(),
                    b.getVehicleType(),
                    b.getSlotId(),
                    b.getEntryTime().format(fmt),
                    b.getExitTime() != null ? b.getExitTime().format(fmt) : "-",
                    b.getExitTime() != null ? String.format("%.2f", b.getFeeAmount()) : "-",
                    b.getPaymentStatus()
            });
        }
    }

    private void refreshDashboard() {
        totalSlotsLabel.setText(String.valueOf(parkingManager.getTotalSlots()));
        availableSlotsLabel.setText(String.valueOf(parkingManager.getAvailableCount()));
        occupiedSlotsLabel.setText(String.valueOf(parkingManager.getOccupiedCount()));

        for (SlotPanel sp : slotPanels) {
            sp.refresh();
        }
    }
}
