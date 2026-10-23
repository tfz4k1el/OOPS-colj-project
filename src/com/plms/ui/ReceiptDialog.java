package com.plms.ui;

import com.plms.model.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Revamped Digital Parking Exit Invoice Dialog.
 * Features clean thermal receipt design, itemized tariff breakdown,
 * and one-click receipt export.
 */
public class ReceiptDialog extends JDialog {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReceiptDialog(Frame parent, Booking booking) {
        super(parent, "Parking Exit Billing Receipt", true);
        setLayout(new BorderLayout());
        setSize(420, 560);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(241, 245, 249));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(22, 26, 20, 26));
        card.setBackground(Color.WHITE);

        // --- Facility Header ---
        JLabel brandLabel = new JLabel("🅿️ PLMS SMART PARKING SOLUTIONS", SwingConstants.CENTER);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        brandLabel.setForeground(new Color(15, 23, 42));
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Automated Vehicle Clearance & Exit Invoice", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(new Color(100, 116, 139));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel locLabel = new JLabel("MGM Tech Campus • 24x7 Automated Facility", SwingConstants.CENTER);
        locLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        locLabel.setForeground(new Color(148, 163, 184));
        locLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(brandLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(subLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(locLabel);
        card.add(Box.createVerticalStrut(14));

        // Dotted divider
        card.add(createDottedSeparator());
        card.add(Box.createVerticalStrut(14));

        // Receipt Details
        card.add(createRow("Ticket Number:", booking.getTicketId(), true));
        card.add(Box.createVerticalStrut(7));
        card.add(createRow("License Plate:", booking.getLicensePlate(), true));
        card.add(Box.createVerticalStrut(7));
        card.add(createRow("Vehicle Type:", booking.getVehicleType().getDisplayName(), false));
        card.add(Box.createVerticalStrut(7));
        card.add(createRow("Assigned Slot ID:", booking.getSlotId(), false));
        card.add(Box.createVerticalStrut(7));
        card.add(createRow("Check-In Timestamp:", booking.getEntryTime().format(FORMATTER), false));
        card.add(Box.createVerticalStrut(7));
        card.add(createRow("Check-Out Timestamp:", booking.getExitTime() != null ? booking.getExitTime().format(FORMATTER) : "N/A", false));
        card.add(Box.createVerticalStrut(7));

        long mins = booking.getDurationMinutes();
        String durationText = mins < 60 ? (mins + " mins") : ((mins / 60) + "h " + (mins % 60) + "m (" + mins + " mins)");
        card.add(createRow("Elapsed Duration:", durationText, false));
        card.add(Box.createVerticalStrut(7));

        long billableHours = (long) Math.ceil(Math.max(1, mins) / 60.0);
        double rate = booking.getVehicleType() == com.plms.model.VehicleType.CAR ? 20.0 : 10.0;
        card.add(createRow("Applied Tariff:", String.format("%d hr(s) @ ₹%.2f/hr", billableHours, rate), false));
        card.add(Box.createVerticalStrut(14));

        card.add(createDottedSeparator());
        card.add(Box.createVerticalStrut(14));

        // --- Total Amount Banner ---
        JPanel totalBox = new JPanel(new BorderLayout());
        totalBox.setBackground(new Color(240, 253, 244)); // Emerald 50
        totalBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(187, 247, 208), 1, true),
                new EmptyBorder(10, 15, 10, 15)));
        totalBox.setMaximumSize(new Dimension(380, 55));

        JLabel totalText = new JLabel("TOTAL DUE & COLLECTED:");
        totalText.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalText.setForeground(new Color(22, 101, 52));

        JLabel totalAmount = new JLabel(String.format("₹ %.2f", booking.getFeeAmount()));
        totalAmount.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalAmount.setForeground(new Color(21, 128, 61));

        totalBox.add(totalText, BorderLayout.WEST);
        totalBox.add(totalAmount, BorderLayout.EAST);
        card.add(totalBox);

        card.add(Box.createVerticalStrut(12));

        // Status Stamp
        JLabel stamp = new JLabel("✓ CLEARED & PAID VIA CASH/ONLINE", SwingConstants.CENTER);
        stamp.setFont(new Font("Segoe UI", Font.BOLD, 11));
        stamp.setForeground(new Color(22, 101, 52));
        stamp.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(stamp);

        card.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // --- Bottom Action Bar ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        actionPanel.setBackground(new Color(241, 245, 249));

        ModernButton saveBtn = new ModernButton("💾 Export Receipt Text",
                new Color(15, 23, 42),
                new Color(30, 41, 59));
        saveBtn.setPreferredSize(new Dimension(170, 36));
        saveBtn.addActionListener(e -> exportReceipt(booking));

        ModernButton closeBtn = new ModernButton("Done / Close",
                new Color(37, 99, 235),
                new Color(29, 78, 216));
        closeBtn.setPreferredSize(new Dimension(140, 36));
        closeBtn.addActionListener(e -> dispose());

        actionPanel.add(saveBtn);
        actionPanel.add(closeBtn);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void exportReceipt(Booking booking) {
        String fileName = "Receipt_" + booking.getTicketId() + ".txt";
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("====================================================\n");
            writer.write("        PLMS SMART PARKING OFFICIAL RECEIPT         \n");
            writer.write("====================================================\n");
            writer.write("Ticket ID    : " + booking.getTicketId() + "\n");
            writer.write("License Plate: " + booking.getLicensePlate() + "\n");
            writer.write("Vehicle Type : " + booking.getVehicleType().getDisplayName() + "\n");
            writer.write("Slot Assigned: " + booking.getSlotId() + "\n");
            writer.write("Entry Time   : " + booking.getEntryTime().format(FORMATTER) + "\n");
            writer.write("Exit Time    : " + (booking.getExitTime() != null ? booking.getExitTime().format(FORMATTER) : "N/A") + "\n");
            writer.write("Stay Duration: " + booking.getDurationMinutes() + " minute(s)\n");
            writer.write("Total Paid   : INR " + String.format("%.2f", booking.getFeeAmount()) + "\n");
            writer.write("Status       : PAID & CLEARED\n");
            writer.write("====================================================\n");
            writer.write("             THANK YOU FOR VISITING!                \n");
            writer.write("====================================================\n");

            JOptionPane.showMessageDialog(this,
                    "Receipt successfully saved to:\n" + file.getAbsolutePath(),
                    "Receipt Exported",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to export receipt: " + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createDottedSeparator() {
        JPanel sep = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(203, 213, 225));
                float[] dash = {4f, 4f};
                g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f));
                g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
                g2.dispose();
            }
        };
        sep.setMaximumSize(new Dimension(360, 4));
        sep.setPreferredSize(new Dimension(360, 4));
        sep.setOpaque(false);
        return sep;
    }

    private JPanel createRow(String label, String value, boolean isBoldVal) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(360, 22));

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(new Color(100, 116, 139));

        JLabel v = new JLabel(value);
        v.setFont(new Font(isBoldVal ? "Segoe UI" : "Segoe UI", isBoldVal ? Font.BOLD : Font.PLAIN, 12));
        v.setForeground(new Color(15, 23, 42));

        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        return row;
    }
}
