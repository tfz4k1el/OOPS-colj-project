package com.plms.ui;

import com.plms.model.*;
import com.plms.service.ParkingManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Revamped Desktop Control Center for the Parking Lot Management System.
 * Uses pure Java2D vector icons via UiIcon to guarantee flawless rendering
 * on any platform without missing font glyphs or broken emojis.
 */
public class MainFrame extends JFrame {
    private final ParkingManager parkingManager;
    private final List<SlotPanel> slotPanels = new ArrayList<>();

    // KPI Badges
    private JLabel totalSlotsVal;
    private JLabel availableSlotsVal;
    private JLabel availableSubVal;
    private JLabel occupiedSlotsVal;
    private JLabel occupiedSubVal;
    private JLabel revenueVal;
    private JLabel revenueSubVal;

    // Floor Occupancy Meters
    private JProgressBar floor1Progress;
    private JLabel floor1ProgressLabel;
    private JProgressBar floor2Progress;
    private JLabel floor2ProgressLabel;

    // Check-in controls
    private JTextField plateField;
    private JRadioButton carRadio;
    private JRadioButton bikeRadio;
    private JComboBox<String> preferredSlotCombo;

    // Check-out controls
    private JTextField checkOutField;

    // Activity Feed & Table
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JLabel liveClockLabel;

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("hh:mm:ss a • EEE, dd MMM yyyy");

    public MainFrame(ParkingManager parkingManager) {
        super("Parking Lot Management System (PLMS) - Desktop Control Center");
        this.parkingManager = parkingManager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1240, 800);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);

        initComponents();
        startLiveClock();
        loadHistoricalRecords();
        refreshDashboard();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(241, 245, 249));

        // 1. Top Header with Live Clock & System Status
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Main Content Container
        JPanel contentContainer = new JPanel(new BorderLayout(14, 14));
        contentContainer.setOpaque(false);
        contentContainer.setBorder(new EmptyBorder(12, 16, 16, 16));

        // KPI Banner at top of content
        contentContainer.add(createKpiBanner(), BorderLayout.NORTH);

        // Center split: Operations (Left) and Interactive Floor Plan (Center)
        JPanel centerSplit = new JPanel(new BorderLayout(14, 14));
        centerSplit.setOpaque(false);
        centerSplit.add(createOperationsPanel(), BorderLayout.WEST);
        centerSplit.add(createFloorPlanPanel(), BorderLayout.CENTER);
        contentContainer.add(centerSplit, BorderLayout.CENTER);

        // Bottom Table
        contentContainer.add(createLogTablePanel(), BorderLayout.SOUTH);

        add(contentContainer, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout(15, 10));
        header.setBackground(new Color(15, 23, 42)); // Slate 900
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        // Left Branding with Vector Parking Icon
        JPanel brandBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brandBox.setOpaque(false);

        JLabel logoBadge = new JLabel(UiIcon.parkingLogo(36));
        logoBadge.setPreferredSize(new Dimension(36, 36));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);

        JLabel title = new JLabel("PARKING LOT MANAGEMENT SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Intelligent Slot Allocation, Dynamic Tariffs & Billing Engine");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(148, 163, 184));

        titleBox.add(title);
        titleBox.add(subtitle);

        brandBox.add(logoBadge);
        brandBox.add(titleBox);
        header.add(brandBox, BorderLayout.WEST);

        // Right Status & Clock
        JPanel statusBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        statusBox.setOpaque(false);

        // Live Clock with Clock Vector Icon
        liveClockLabel = new JLabel(" " + LocalDateTime.now().format(DATE_TIME_FMT));
        liveClockLabel.setIcon(UiIcon.clock(14, new Color(148, 163, 184)));
        liveClockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        liveClockLabel.setForeground(new Color(203, 213, 225));
        statusBox.add(liveClockLabel);

        // System Online Pill with Vector Status Dot
        JLabel statusPill = new JLabel(" SYSTEM ACTIVE", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(6, 78, 59)); // Emerald 900
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(52, 211, 153));
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        statusPill.setIcon(UiIcon.availableDot(9));
        statusPill.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusPill.setForeground(new Color(167, 243, 208));
        statusPill.setBorder(new EmptyBorder(4, 10, 4, 10));
        statusBox.add(statusPill);

        header.add(statusBox, BorderLayout.EAST);
        return header;
    }

    private JPanel createKpiBanner() {
        JPanel banner = new JPanel(new GridLayout(1, 4, 14, 0));
        banner.setOpaque(false);
        banner.setPreferredSize(new Dimension(1000, 75));

        // 1. Total Slots
        JPanel totalCard = createKpiCard("TOTAL CAPACITY", "12", "12 Total Slots (Dual Deck)",
                new Color(59, 130, 246), new Color(239, 246, 255), UiIcon.building(28, new Color(59, 130, 246)));
        totalSlotsVal = (JLabel) totalCard.getClientProperty("valLabel");

        // 2. Available Slots
        JPanel availCard = createKpiCard("AVAILABLE SLOTS", "12", "100% Free Capacity",
                new Color(16, 185, 129), new Color(240, 253, 244), UiIcon.availableDot(24));
        availableSlotsVal = (JLabel) availCard.getClientProperty("valLabel");
        availableSubVal = (JLabel) availCard.getClientProperty("subLabel");

        // 3. Occupied Slots
        JPanel occCard = createKpiCard("CURRENTLY PARKED", "0", "0% Occupancy",
                new Color(239, 68, 68), new Color(254, 242, 242), UiIcon.occupiedDot(24));
        occupiedSlotsVal = (JLabel) occCard.getClientProperty("valLabel");
        occupiedSubVal = (JLabel) occCard.getClientProperty("subLabel");

        // 4. Revenue Today
        JPanel revCard = createKpiCard("TOTAL REVENUE", "Rs. 0.00", "0 Completed Bookings",
                new Color(217, 119, 6), new Color(254, 252, 232), UiIcon.revenue(28, new Color(217, 119, 6)));
        revenueVal = (JLabel) revCard.getClientProperty("valLabel");
        revenueSubVal = (JLabel) revCard.getClientProperty("subLabel");

        banner.add(totalCard);
        banner.add(availCard);
        banner.add(occCard);
        banner.add(revCard);
        return banner;
    }

    private JPanel createKpiCard(String title, String val, String sub, Color accent, Color bg, Icon icon) {
        JPanel card = new JPanel(new BorderLayout(8, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.setColor(new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Left accent strip
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(8, 14, 8, 12));

        JPanel textBox = new JPanel(new GridLayout(3, 1, 0, 1));
        textBox.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLbl.setForeground(new Color(100, 116, 139));

        JLabel valLbl = new JLabel(val);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 19));
        valLbl.setForeground(accent);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subLbl.setForeground(new Color(148, 163, 184));

        textBox.add(titleLbl);
        textBox.add(valLbl);
        textBox.add(subLbl);

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setPreferredSize(new Dimension(32, 32));

        card.add(textBox, BorderLayout.CENTER);
        card.add(iconLbl, BorderLayout.EAST);

        card.putClientProperty("valLabel", valLbl);
        card.putClientProperty("subLabel", subLbl);
        return card;
    }

    private JPanel createOperationsPanel() {
        JPanel opsPanel = new JPanel();
        opsPanel.setLayout(new BoxLayout(opsPanel, BoxLayout.Y_AXIS));
        opsPanel.setPreferredSize(new Dimension(320, 460));
        opsPanel.setOpaque(false);

        // --- Card 1: Check-in Box ---
        JPanel checkInBox = createStyledCard("Vehicle Check-In (Entry)", UiIcon.checkIn(16, new Color(37, 99, 235)));
        checkInBox.add(createFieldLabel("License Plate Number:"));
        plateField = new JTextField();
        plateField.setFont(new Font("Consolas", Font.BOLD, 14));
        plateField.setMaximumSize(new Dimension(300, 32));
        plateField.setToolTipText("Enter vehicle number plate, e.g. KL-07-CB-4220");
        plateField.addActionListener(e -> handleCheckIn());
        checkInBox.add(plateField);
        checkInBox.add(Box.createVerticalStrut(8));

        checkInBox.add(createFieldLabel("Vehicle Category & Tariff:"));
        carRadio = new JRadioButton(" Car (Rs. 20/hr)", true);
        carRadio.setIcon(UiIcon.car(16, new Color(59, 130, 246)));
        bikeRadio = new JRadioButton(" Bike (Rs. 10/hr)");
        bikeRadio.setIcon(UiIcon.bike(16, new Color(139, 92, 246)));
        carRadio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        bikeRadio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        carRadio.setOpaque(false);
        bikeRadio.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        group.add(carRadio);
        group.add(bikeRadio);

        carRadio.addActionListener(e -> updatePreferredSlotDropdown());
        bikeRadio.addActionListener(e -> updatePreferredSlotDropdown());

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        radioPanel.setOpaque(false);
        radioPanel.add(carRadio);
        radioPanel.add(bikeRadio);
        checkInBox.add(radioPanel);
        checkInBox.add(Box.createVerticalStrut(6));

        checkInBox.add(createFieldLabel("Slot Preference:"));
        preferredSlotCombo = new JComboBox<>();
        preferredSlotCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        preferredSlotCombo.setMaximumSize(new Dimension(300, 30));
        updatePreferredSlotDropdown();
        checkInBox.add(preferredSlotCombo);
        checkInBox.add(Box.createVerticalStrut(10));

        ModernButton checkInBtn = new ModernButton(" Assign Slot & Check-In",
                new Color(37, 99, 235), new Color(29, 78, 216));
        checkInBtn.setIcon(UiIcon.checkMark(14, Color.WHITE));
        checkInBtn.setPreferredSize(new Dimension(280, 36));
        checkInBtn.setMaximumSize(new Dimension(280, 36));
        checkInBtn.addActionListener(e -> handleCheckIn());
        checkInBox.add(checkInBtn);

        opsPanel.add(checkInBox);
        opsPanel.add(Box.createVerticalStrut(10));

        // --- Card 2: Check-out Box ---
        JPanel checkOutBox = createStyledCard("Vehicle Check-Out & Billing", UiIcon.checkOut(16, new Color(225, 29, 72)));
        checkOutBox.add(createFieldLabel("Plate or Slot ID:"));
        checkOutField = new JTextField();
        checkOutField.setFont(new Font("Consolas", Font.BOLD, 14));
        checkOutField.setMaximumSize(new Dimension(300, 32));
        checkOutField.setToolTipText("Enter Plate (e.g. KL-07-CB-4220) or Slot ID (e.g. F1-C01)");
        checkOutField.addActionListener(e -> handleCheckOut(checkOutField.getText()));
        checkOutBox.add(checkOutField);
        checkOutBox.add(Box.createVerticalStrut(10));

        ModernButton checkOutBtn = new ModernButton(" Compute Fee & Clear Slot",
                new Color(225, 29, 72), new Color(190, 18, 60));
        checkOutBtn.setIcon(UiIcon.checkOut(14, Color.WHITE));
        checkOutBtn.setPreferredSize(new Dimension(280, 36));
        checkOutBtn.setMaximumSize(new Dimension(280, 36));
        checkOutBtn.addActionListener(e -> handleCheckOut(checkOutField.getText()));
        checkOutBox.add(checkOutBtn);

        opsPanel.add(checkOutBox);
        opsPanel.add(Box.createVerticalStrut(10));

        // --- Card 3: Quick Simulation Tools ---
        JPanel toolsBox = createStyledCard("Quick Demonstration Tools", UiIcon.lightning(15, new Color(217, 119, 6)));
        JPanel toolBtns = new JPanel(new GridLayout(1, 2, 8, 0));
        toolBtns.setOpaque(false);
        toolBtns.setMaximumSize(new Dimension(300, 32));

        ModernButton demoCarBtn = new ModernButton(" Test Car", new Color(71, 85, 105), new Color(51, 65, 85));
        demoCarBtn.setIcon(UiIcon.car(14, Color.WHITE));
        demoCarBtn.setCornerRadius(6);
        demoCarBtn.addActionListener(e -> quickDemoCheckIn(VehicleType.CAR));

        ModernButton demoBikeBtn = new ModernButton(" Test Bike", new Color(71, 85, 105), new Color(51, 65, 85));
        demoBikeBtn.setIcon(UiIcon.bike(14, Color.WHITE));
        demoBikeBtn.setCornerRadius(6);
        demoBikeBtn.addActionListener(e -> quickDemoCheckIn(VehicleType.BIKE));

        toolBtns.add(demoCarBtn);
        toolBtns.add(demoBikeBtn);
        toolsBox.add(toolBtns);

        opsPanel.add(toolsBox);
        return opsPanel;
    }

    private JPanel createStyledCard(String title, Icon icon) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(10, 12, 10, 12)));

        JLabel t = new JLabel(" " + title);
        if (icon != null) t.setIcon(icon);
        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(new Color(30, 41, 59));
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(t);
        card.add(Box.createVerticalStrut(8));
        return card;
    }

    private JLabel createFieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(new Color(71, 85, 105));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTabbedPane createFloorPlanPanel() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabs.setBackground(Color.WHITE);

        tabs.addTab("Floor 1 (Ground Deck)", UiIcon.building(14, new Color(71, 85, 105)), createFloorView(1));
        tabs.addTab("Floor 2 (Upper Deck)", UiIcon.building(14, new Color(71, 85, 105)), createFloorView(2));

        return tabs;
    }

    private JPanel createFloorView(int floorNumber) {
        JPanel container = new JPanel(new BorderLayout(8, 8));
        container.setBackground(new Color(248, 250, 252));
        container.setBorder(new EmptyBorder(10, 12, 10, 12));

        // Floor Occupancy Bar
        JPanel meterPanel = new JPanel(new BorderLayout(8, 0));
        meterPanel.setOpaque(false);
        meterPanel.setBorder(new EmptyBorder(0, 0, 6, 0));

        JLabel meterTitle = new JLabel(String.format("Floor %d Occupancy Rate:", floorNumber));
        meterTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        meterTitle.setForeground(new Color(71, 85, 105));

        JProgressBar progress = new JProgressBar(0, 6);
        progress.setValue(0);
        progress.setPreferredSize(new Dimension(160, 16));
        progress.setForeground(new Color(37, 99, 235));
        progress.setBackground(new Color(226, 232, 240));

        JLabel progressLbl = new JLabel("0 / 6 Occupied (0%)");
        progressLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        progressLbl.setForeground(new Color(15, 23, 42));

        meterPanel.add(meterTitle, BorderLayout.WEST);
        meterPanel.add(progress, BorderLayout.CENTER);
        meterPanel.add(progressLbl, BorderLayout.EAST);

        if (floorNumber == 1) {
            floor1Progress = progress;
            floor1ProgressLabel = progressLbl;
        } else {
            floor2Progress = progress;
            floor2ProgressLabel = progressLbl;
        }

        container.add(meterPanel, BorderLayout.NORTH);

        // 2x3 Grid of Slot Cards
        JPanel grid = new JPanel(new GridLayout(2, 3, 12, 12));
        grid.setOpaque(false);

        for (ParkingSlot slot : parkingManager.getAllSlots()) {
            if (slot.getFloorNumber() == floorNumber) {
                SlotPanel sp = new SlotPanel(slot, this::onSlotClicked);
                slotPanels.add(sp);
                grid.add(sp);
            }
        }
        container.add(grid, BorderLayout.CENTER);

        // Legend bar with Vector Icons
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 4));
        legend.setOpaque(false);
        legend.add(createLegendItem("Vacant Bay", UiIcon.availableDot(10), new Color(22, 101, 52)));
        legend.add(createLegendItem("Occupied Bay", UiIcon.occupiedDot(10), new Color(185, 28, 28)));
        legend.add(createLegendItem("Car Designated (Rs. 20/hr)", UiIcon.car(13, new Color(30, 41, 59)), new Color(30, 41, 59)));
        legend.add(createLegendItem("Bike Designated (Rs. 10/hr)", UiIcon.bike(13, new Color(30, 41, 59)), new Color(30, 41, 59)));
        legend.add(createLegendItem("Click any slot to Park/Clear", UiIcon.lightning(12, new Color(37, 99, 235)), new Color(37, 99, 235)));
        container.add(legend, BorderLayout.SOUTH);

        return container;
    }

    private JLabel createLegendItem(String text, Icon icon, Color color) {
        JLabel l = new JLabel(" " + text);
        if (icon != null) l.setIcon(icon);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(color);
        return l;
    }

    private JPanel createLogTablePanel() {
        JPanel logPanel = new JPanel(new BorderLayout(8, 8));
        logPanel.setPreferredSize(new Dimension(1000, 190));
        logPanel.setBackground(Color.WHITE);
        logPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(8, 12, 8, 12)));

        // Top Toolbar: Search + Filter + Export
        JPanel toolBar = new JPanel(new BorderLayout(10, 0));
        toolBar.setOpaque(false);

        JLabel title = new JLabel(" Live Activity & Audit Transaction Log");
        title.setIcon(UiIcon.building(16, new Color(30, 41, 59)));
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(new Color(30, 41, 59));
        toolBar.add(title, BorderLayout.WEST);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controls.setOpaque(false);

        // Real-time search box with Search Vector Icon
        JLabel sLbl = new JLabel(" Filter:");
        sLbl.setIcon(UiIcon.search(13, new Color(100, 116, 139)));
        sLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchField = new JTextField(12);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyTableFilter(); }
            public void removeUpdate(DocumentEvent e) { applyTableFilter(); }
            public void changedUpdate(DocumentEvent e) { applyTableFilter(); }
        });
        controls.add(sLbl);
        controls.add(searchField);

        // Status filter
        filterCombo = new JComboBox<>(new String[]{"All Records", "Active Only", "Paid / Cleared"});
        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        filterCombo.addActionListener(e -> applyTableFilter());
        controls.add(filterCombo);

        // Export CSV button with Export Vector Icon
        ModernButton exportBtn = new ModernButton(" Export CSV", new Color(15, 23, 42), new Color(30, 41, 59), 6);
        exportBtn.setIcon(UiIcon.export(12, Color.WHITE));
        exportBtn.setPreferredSize(new Dimension(115, 26));
        exportBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        exportBtn.addActionListener(e -> exportRecordsToCsv());
        controls.add(exportBtn);

        toolBar.add(controls, BorderLayout.EAST);
        logPanel.add(toolBar, BorderLayout.NORTH);

        // Table Model
        String[] cols = {"Ticket ID", "License Plate", "Type", "Slot ID", "Entry Time", "Exit Time", "Stay (Mins)", "Fee (INR)", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tableSorter = new TableRowSorter<>(tableModel);
        JTable table = new JTable(tableModel);
        table.setRowSorter(tableSorter);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setShowGrid(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(new Color(224, 231, 255));
        table.setSelectionForeground(new Color(15, 23, 42));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setForeground(new Color(71, 85, 105));

        // Custom Cell Styling
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        // Vehicle Type Column with Vector Icon
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                String str = String.valueOf(value);
                if (str.toUpperCase().contains("CAR")) {
                    c.setIcon(UiIcon.car(14, new Color(59, 130, 246)));
                    c.setText(" Car");
                } else {
                    c.setIcon(UiIcon.bike(14, new Color(139, 92, 246)));
                    c.setText(" Bike");
                }
                return c;
            }
        });

        // Status Renderer with Vector Status Icons
        table.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setFont(new Font("Segoe UI", Font.BOLD, 11));
                String st = String.valueOf(value);
                if ("PAID".equalsIgnoreCase(st)) {
                    c.setForeground(new Color(22, 101, 52));
                    c.setIcon(UiIcon.checkMark(12, new Color(22, 101, 52)));
                    c.setText(" PAID");
                } else {
                    c.setForeground(new Color(180, 83, 9));
                    c.setIcon(UiIcon.occupiedDot(9));
                    c.setText(" ACTIVE");
                }
                return c;
            }
        });

        // Double click to view receipt
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    showSelectedRowReceipt(table.convertRowIndexToModel(table.getSelectedRow()));
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        logPanel.add(sp, BorderLayout.CENTER);

        return logPanel;
    }

    private void onSlotClicked(ParkingSlot slot) {
        if (!slot.isAvailable()) {
            checkOutField.setText(slot.getSlotId());
            Vehicle v = slot.getCurrentVehicle();
            String plate = v != null ? v.getLicensePlate() : "Unknown";

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Slot " + slot.getSlotId() + " is currently occupied by: " + plate +
                            "\nWould you like to calculate billing and check out this vehicle now?",
                    "Check-Out Vehicle",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                handleCheckOut(slot.getSlotId());
            }
        } else {
            // Pre-select slot in check-in box
            if (slot.getAllowedType() == VehicleType.CAR) {
                carRadio.setSelected(true);
            } else {
                bikeRadio.setSelected(true);
            }
            updatePreferredSlotDropdown();
            preferredSlotCombo.setSelectedItem(slot.getSlotId() + " (" + slot.getAllowedType() + ")");
            plateField.requestFocusInWindow();

            JOptionPane.showMessageDialog(this,
                    "Slot " + slot.getSlotId() + " selected! Enter vehicle license plate and click Check-In.",
                    "Slot Selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleCheckIn() {
        String plate = plateField.getText().trim().toUpperCase();
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid vehicle license plate number.", "Input Required", JOptionPane.WARNING_MESSAGE);
            plateField.requestFocusInWindow();
            return;
        }

        Vehicle vehicle = carRadio.isSelected() ? new Car(plate) : new Bike(plate);

        // Selected preferred slot
        ParkingSlot targetSlot = null;
        if (preferredSlotCombo.getSelectedIndex() > 0) {
            String selected = (String) preferredSlotCombo.getSelectedItem();
            if (selected != null) {
                String slotId = selected.split(" ")[0];
                targetSlot = parkingManager.findSlotById(slotId);
            }
        }

        try {
            Booking booking = parkingManager.checkInVehicle(vehicle, targetSlot);
            plateField.setText("");
            refreshDashboard();

            // Insert into table
            tableModel.insertRow(0, new Object[]{
                    booking.getTicketId(),
                    booking.getLicensePlate(),
                    booking.getVehicleType().getDisplayName(),
                    booking.getSlotId(),
                    booking.getEntryTime().format(TIME_FMT),
                    "-",
                    "-",
                    "-",
                    booking.getPaymentStatus()
            });

            JOptionPane.showMessageDialog(this,
                    String.format("Vehicle checked in successfully!\nTicket: %s\nAssigned Slot: %s\nVehicle: %s (%s)",
                            booking.getTicketId(), booking.getSlotId(), booking.getLicensePlate(), booking.getVehicleType()),
                    "Entry Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Check-In Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCheckOut(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a License Plate or Slot ID to check out.", "Input Required", JOptionPane.WARNING_MESSAGE);
            checkOutField.requestFocusInWindow();
            return;
        }

        try {
            Booking booking = parkingManager.checkOutVehicle(identifier.trim());
            checkOutField.setText("");
            refreshDashboard();

            // Update row in table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(booking.getTicketId())) {
                    tableModel.setValueAt(booking.getExitTime().format(TIME_FMT), i, 5);
                    tableModel.setValueAt(booking.getDurationMinutes(), i, 6);
                    tableModel.setValueAt(String.format("%.2f", booking.getFeeAmount()), i, 7);
                    tableModel.setValueAt(booking.getPaymentStatus(), i, 8);
                    break;
                }
            }

            // Show Revamped Digital Receipt
            ReceiptDialog receipt = new ReceiptDialog(this, booking);
            receipt.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Check-Out Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quickDemoCheckIn(VehicleType type) {
        Random rnd = new Random();
        String[] states = {"KL", "TN", "MH", "DL", "KA"};
        String state = states[rnd.nextInt(states.length)];
        int district = 1 + rnd.nextInt(14);
        char c1 = (char) ('A' + rnd.nextInt(26));
        char c2 = (char) ('A' + rnd.nextInt(26));
        int num = 1000 + rnd.nextInt(9000);
        String plate = String.format("%s-%02d-%c%c-%04d", state, district, c1, c2, num);

        if (type == VehicleType.CAR) {
            carRadio.setSelected(true);
        } else {
            bikeRadio.setSelected(true);
        }
        updatePreferredSlotDropdown();
        plateField.setText(plate);
        handleCheckIn();
    }

    private void updatePreferredSlotDropdown() {
        if (preferredSlotCombo == null) return;
        preferredSlotCombo.removeAllItems();
        preferredSlotCombo.addItem("Auto-Assign Best Slot");

        VehicleType allowed = carRadio.isSelected() ? VehicleType.CAR : VehicleType.BIKE;
        for (ParkingSlot s : parkingManager.getAllSlots()) {
            if (s.isAvailable() && s.getAllowedType() == allowed) {
                preferredSlotCombo.addItem(s.getSlotId() + " (" + allowed + ")");
            }
        }
    }

    private void applyTableFilter() {
        String query = searchField.getText().trim();
        String filter = (String) filterCombo.getSelectedItem();

        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        if (!query.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + query));
        }

        if ("Active Only".equalsIgnoreCase(filter)) {
            filters.add(RowFilter.regexFilter("(?i)ACTIVE", 8));
        } else if ("Paid / Cleared".equalsIgnoreCase(filter)) {
            filters.add(RowFilter.regexFilter("(?i)PAID", 8));
        }

        if (filters.isEmpty()) {
            tableSorter.setRowFilter(null);
        } else {
            tableSorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }

    private void showSelectedRowReceipt(int modelRow) {
        String ticketId = (String) tableModel.getValueAt(modelRow, 0);
        String plate = (String) tableModel.getValueAt(modelRow, 1);
        String slotId = (String) tableModel.getValueAt(modelRow, 3);
        String feeStr = String.valueOf(tableModel.getValueAt(modelRow, 7));
        String status = String.valueOf(tableModel.getValueAt(modelRow, 8));

        Booking b = new Booking(ticketId, plate, plate.contains("Bike") ? VehicleType.BIKE : VehicleType.CAR, slotId, LocalDateTime.now().minusHours(1));
        if (!"-".equals(feeStr)) {
            try {
                double fee = Double.parseDouble(feeStr);
                b.completeBooking(LocalDateTime.now(), fee);
                b.setPaymentStatus(status);
            } catch (Exception ignored) {}
        }

        ReceiptDialog receipt = new ReceiptDialog(this, b);
        receipt.setVisible(true);
    }

    private void exportRecordsToCsv() {
        File file = new File("Exported_PLMS_Records.csv");
        try (FileWriter fw = new FileWriter(file)) {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                fw.write(tableModel.getColumnName(col) + (col == tableModel.getColumnCount() - 1 ? "\n" : ","));
            }
            for (int r = 0; r < tableModel.getRowCount(); r++) {
                for (int c = 0; c < tableModel.getColumnCount(); c++) {
                    fw.write(String.valueOf(tableModel.getValueAt(r, c)) + (c == tableModel.getColumnCount() - 1 ? "\n" : ","));
                }
            }
            JOptionPane.showMessageDialog(this, "Records successfully exported to:\n" + file.getAbsolutePath(),
                    "Export Completed", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to export CSV: " + ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHistoricalRecords() {
        if (parkingManager.getDatabaseManager() == null) return;
        List<Booking> past = parkingManager.getDatabaseManager().loadAllBookings();
        for (Booking b : past) {
            tableModel.insertRow(0, new Object[]{
                    b.getTicketId(),
                    b.getLicensePlate(),
                    b.getVehicleType().getDisplayName(),
                    b.getSlotId(),
                    b.getEntryTime().format(TIME_FMT),
                    b.getExitTime() != null ? b.getExitTime().format(TIME_FMT) : "-",
                    b.getExitTime() != null ? b.getDurationMinutes() : "-",
                    b.getExitTime() != null ? String.format("%.2f", b.getFeeAmount()) : "-",
                    b.getPaymentStatus()
            });
        }
    }

    private void refreshDashboard() {
        int total = parkingManager.getTotalSlots();
        int available = parkingManager.getAvailableCount();
        int occupied = parkingManager.getOccupiedCount();

        totalSlotsVal.setText(String.valueOf(total));
        availableSlotsVal.setText(String.valueOf(available));
        occupiedSlotsVal.setText(String.valueOf(occupied));

        int occPct = (int) Math.round((occupied * 100.0) / total);
        int availPct = 100 - occPct;
        availableSubVal.setText(availPct + "% Capacity Available");
        occupiedSubVal.setText(occPct + "% Current Occupancy");

        // Calculate Revenue from Table
        double totalRev = 0.0;
        int paidCount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object feeObj = tableModel.getValueAt(i, 7);
            if (feeObj != null && !"-".equals(feeObj.toString())) {
                try {
                    totalRev += Double.parseDouble(feeObj.toString());
                    paidCount++;
                } catch (Exception ignored) {}
            }
        }
        revenueVal.setText(String.format("Rs. %.2f", totalRev));
        revenueSubVal.setText(paidCount + " Completed Bookings");

        // Floor progress
        int f1Occ = 0;
        int f2Occ = 0;
        for (ParkingSlot s : parkingManager.getAllSlots()) {
            if (!s.isAvailable()) {
                if (s.getFloorNumber() == 1) f1Occ++;
                else f2Occ++;
            }
        }

        if (floor1Progress != null) {
            floor1Progress.setValue(f1Occ);
            int f1Pct = (int) Math.round((f1Occ * 100.0) / 6);
            floor1ProgressLabel.setText(String.format("%d / 6 Occupied (%d%%)", f1Occ, f1Pct));
        }

        if (floor2Progress != null) {
            floor2Progress.setValue(f2Occ);
            int f2Pct = (int) Math.round((f2Occ * 100.0) / 6);
            floor2ProgressLabel.setText(String.format("%d / 6 Occupied (%d%%)", f2Occ, f2Pct));
        }

        for (SlotPanel sp : slotPanels) {
            sp.refresh();
        }
        updatePreferredSlotDropdown();
    }

    private void startLiveClock() {
        Timer timer = new Timer(1000, e -> {
            if (liveClockLabel != null) {
                liveClockLabel.setText(" " + LocalDateTime.now().format(DATE_TIME_FMT));
            }
        });
        timer.start();
    }
}
