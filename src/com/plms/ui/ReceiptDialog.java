package com.plms.ui;

import com.plms.model.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Dialog displaying the generated billing receipt upon vehicle exit.
 */
public class ReceiptDialog extends JDialog {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReceiptDialog(Frame parent, Booking booking) {
        super(parent, "Parking Exit Billing Receipt", true);
        setLayout(new BorderLayout());
        setSize(380, 480);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 25, 20, 25));
        content.setBackground(new Color(248, 250, 252));

        JLabel titleLabel = new JLabel("PARKING LOT MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Official Exit Receipt & Invoice");
        subLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        subLabel.setForeground(Color.GRAY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(340, 2));

        content.add(titleLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(subLabel);
        content.add(Box.createVerticalStrut(15));
        content.add(sep1);
        content.add(Box.createVerticalStrut(15));

        content.add(createRow("Ticket ID:", booking.getTicketId()));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("License Plate:", booking.getLicensePlate()));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("Vehicle Type:", booking.getVehicleType().getDisplayName()));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("Assigned Slot:", booking.getSlotId()));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("Entry Timestamp:", booking.getEntryTime().format(FORMATTER)));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("Exit Timestamp:", booking.getExitTime() != null ? booking.getExitTime().format(FORMATTER) : "N/A"));
        content.add(Box.createVerticalStrut(8));
        content.add(createRow("Duration (mins):", String.valueOf(booking.getDurationMinutes())));
        content.add(Box.createVerticalStrut(15));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(340, 2));
        content.add(sep2);
        content.add(Box.createVerticalStrut(15));

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setOpaque(false);
        totalPanel.setMaximumSize(new Dimension(320, 35));
        JLabel totalText = new JLabel("TOTAL DUE:");
        totalText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel totalAmount = new JLabel(String.format("Rs. %.2f", booking.getFeeAmount()));
        totalAmount.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalAmount.setForeground(new Color(22, 163, 74));
        totalPanel.add(totalText, BorderLayout.WEST);
        totalPanel.add(totalAmount, BorderLayout.EAST);
        content.add(totalPanel);

        content.add(Box.createVerticalStrut(15));
        JLabel paidLabel = new JLabel("STATUS: PAID & CLEARED", SwingConstants.CENTER);
        paidLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        paidLabel.setForeground(new Color(22, 101, 52));
        paidLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(paidLabel);

        add(content, BorderLayout.CENTER);

        ModernButton closeBtn = new ModernButton("Close Receipt",
                new Color(30, 41, 59),
                new Color(15, 23, 42));
        closeBtn.setPreferredSize(new Dimension(150, 36));
        closeBtn.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(241, 245, 249));
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel createRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(320, 22));
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(new Color(71, 85, 105));
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 12));
        v.setForeground(new Color(15, 23, 42));
        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        return row;
    }
}
