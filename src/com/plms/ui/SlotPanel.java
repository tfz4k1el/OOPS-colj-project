package com.plms.ui;

import com.plms.model.ParkingSlot;
import com.plms.model.Vehicle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Visual Swing representation of a single ParkingSlot.
 * Dynamically changes colors based on status and supports interactive clicking.
 */
public class SlotPanel extends JPanel {
    private final ParkingSlot slot;
    private final Consumer<ParkingSlot> onClickListener;
    private final JLabel idLabel;
    private final JLabel typeLabel;
    private final JLabel statusLabel;
    private final JLabel plateLabel;

    private static final Color COLOR_AVAILABLE = new Color(34, 197, 94); // Modern emerald green
    private static final Color COLOR_OCCUPIED = new Color(239, 68, 68);  // Vibrant crimson red
    private static final Color BG_CARD = new Color(255, 255, 255);

    public SlotPanel(ParkingSlot slot, Consumer<ParkingSlot> onClickListener) {
        this.slot = slot;
        this.onClickListener = onClickListener;

        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(130, 110));
        setBackground(BG_CARD);
        setBorder(new EmptyBorder(8, 8, 8, 8));

        // Header (Slot ID & Type)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        idLabel = new JLabel(slot.getSlotId());
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        typeLabel = new JLabel(slot.getAllowedType() == com.plms.model.VehicleType.CAR ? "[Car]" : "[Bike]");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        typeLabel.setForeground(new Color(100, 116, 139));

        headerPanel.add(idLabel, BorderLayout.WEST);
        headerPanel.add(typeLabel, BorderLayout.EAST);

        // Center (Status & Plate)
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        centerPanel.setOpaque(false);

        statusLabel = new JLabel("AVAILABLE", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setForeground(Color.WHITE);

        plateLabel = new JLabel("Empty", SwingConstants.CENTER);
        plateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        plateLabel.setForeground(new Color(51, 65, 85));

        centerPanel.add(statusLabel);
        centerPanel.add(plateLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClickListener != null) {
                    onClickListener.accept(slot);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        refresh();
    }

    public void refresh() {
        if (slot.isAvailable()) {
            statusLabel.setText("AVAILABLE");
            statusLabel.setBackground(COLOR_AVAILABLE);
            plateLabel.setText("Empty");
            plateLabel.setForeground(new Color(100, 116, 139));
            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(187, 247, 208), 2, true),
                    new EmptyBorder(6, 6, 6, 6)));
        } else {
            statusLabel.setText("OCCUPIED");
            statusLabel.setBackground(COLOR_OCCUPIED);
            Vehicle v = slot.getCurrentVehicle();
            plateLabel.setText(v != null ? v.getLicensePlate() : "Active");
            plateLabel.setForeground(new Color(185, 28, 28));
            setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(254, 202, 202), 2, true),
                    new EmptyBorder(6, 6, 6, 6)));
        }
        revalidate();
        repaint();
    }

    public ParkingSlot getSlot() {
        return slot;
    }
}
