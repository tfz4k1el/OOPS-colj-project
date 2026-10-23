package com.plms.ui;

import com.plms.model.Booking;
import com.plms.model.ParkingSlot;
import com.plms.model.Vehicle;
import com.plms.model.VehicleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * Revamped visual card representation of a single ParkingSlot.
 * Features realistic Indian license plates for occupied slots, live duration tags,
 * smooth hover effects, and crisp status badges.
 */
public class SlotPanel extends JPanel {
    private final ParkingSlot slot;
    private final Consumer<ParkingSlot> onClickListener;
    private boolean isHovered = false;

    // Theme Colors
    private static final Color BG_VACANT = new Color(240, 253, 244);     // Emerald 50
    private static final Color BORDER_VACANT = new Color(134, 239, 172); // Emerald 300
    private static final Color BG_OCCUPIED = new Color(255, 241, 242);   // Rose 50
    private static final Color BORDER_OCCUPIED = new Color(253, 164, 175);// Rose 300
    private static final Color HOVER_BORDER = new Color(59, 130, 246);   // Blue 500

    private static final Color BADGE_VACANT = new Color(16, 185, 129);   // Emerald 500
    private static final Color BADGE_OCCUPIED = new Color(225, 29, 72);  // Rose 600

    private JLabel idLabel;
    private JLabel typeBadge;
    private JLabel statusPill;
    private JLabel plateBadge;
    private JLabel metaLabel;
    private JLabel actionHint;

    public SlotPanel(ParkingSlot slot, Consumer<ParkingSlot> onClickListener) {
        this.slot = slot;
        this.onClickListener = onClickListener;

        setLayout(new BorderLayout(4, 6));
        setPreferredSize(new Dimension(175, 135));
        setMinimumSize(new Dimension(160, 125));
        setBorder(new EmptyBorder(10, 10, 8, 10));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        buildComponents();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClickListener != null) {
                    onClickListener.accept(slot);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });

        refresh();
    }

    private void buildComponents() {
        // --- Top Bar: Slot ID & Allowed Vehicle Type ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        idLabel = new JLabel(slot.getSlotId());
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idLabel.setForeground(new Color(15, 23, 42));

        typeBadge = new JLabel(slot.getAllowedType() == VehicleType.CAR ? "🚗 CAR" : "🏍️ BIKE");
        typeBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        typeBadge.setForeground(new Color(71, 85, 105));

        topBar.add(idLabel, BorderLayout.WEST);
        topBar.add(typeBadge, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // --- Center Content: Status & Vehicle Details ---
        JPanel centerBox = new JPanel();
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
        centerBox.setOpaque(false);

        // Status Pill
        JPanel pillWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pillWrapper.setOpaque(false);
        statusPill = new JLabel("● VACANT");
        statusPill.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusPill.setForeground(Color.WHITE);
        statusPill.setBorder(new EmptyBorder(2, 10, 2, 10));
        statusPill.setOpaque(false);
        pillWrapper.add(statusPill);
        centerBox.add(pillWrapper);
        centerBox.add(Box.createVerticalStrut(6));

        // License Plate Badge (Indian Yellow HSRP Style)
        plateBadge = new JLabel("---", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!slot.isAvailable()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Yellow plate background
                    g2.setColor(new Color(254, 240, 138)); // Amber 200
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                    // Thin black border
                    g2.setColor(new Color(15, 23, 42));
                    g2.setStroke(new BasicStroke(1.2f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        plateBadge.setFont(new Font("Consolas", Font.BOLD, 12));
        plateBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
        plateBadge.setBorder(new EmptyBorder(3, 8, 3, 8));
        centerBox.add(plateBadge);
        centerBox.add(Box.createVerticalStrut(4));

        // Meta details (Tariff or Elapsed Time)
        metaLabel = new JLabel("Rate: ₹20/hr", SwingConstants.CENTER);
        metaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        metaLabel.setForeground(new Color(100, 116, 139));
        metaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerBox.add(metaLabel);

        add(centerBox, BorderLayout.CENTER);

        // --- Bottom Action Hint ---
        actionHint = new JLabel("Click to Park", SwingConstants.CENTER);
        actionHint.setFont(new Font("Segoe UI", Font.BOLD, 10));
        actionHint.setForeground(new Color(59, 130, 246));
        add(actionHint, BorderLayout.SOUTH);
    }

    public void refresh() {
        if (slot.isAvailable()) {
            statusPill.setText("● VACANT");
            plateBadge.setText("BAY READY");
            plateBadge.setForeground(new Color(100, 116, 139));
            double rate = slot.getAllowedType() == VehicleType.CAR ? 20.0 : 10.0;
            metaLabel.setText(String.format("Standard Tariff: ₹%.0f/hr", rate));
            metaLabel.setForeground(new Color(71, 85, 105));
            actionHint.setText("👉 Click to Check-In");
            actionHint.setForeground(new Color(16, 185, 129));
            setToolTipText("Slot " + slot.getSlotId() + " is vacant and ready for " + slot.getAllowedType());
        } else {
            statusPill.setText("● OCCUPIED");
            Vehicle v = slot.getCurrentVehicle();
            if (v != null) {
                plateBadge.setText(v.getLicensePlate());
                plateBadge.setForeground(new Color(15, 23, 42));

                Duration stay = Duration.between(v.getEntryTime(), LocalDateTime.now());
                long minutes = Math.max(1, stay.toMinutes());
                String durationStr = minutes < 60 ? (minutes + " mins") : (stay.toHours() + "h " + (minutes % 60) + "m");

                metaLabel.setText("⏱️ Stay: " + durationStr);
                metaLabel.setForeground(new Color(185, 28, 28));
                setToolTipText(String.format("Occupied by %s since %s (%s)",
                        v.getLicensePlate(), v.getEntryTime().toLocalTime().withNano(0), durationStr));
            } else {
                plateBadge.setText("PARKED");
                plateBadge.setForeground(new Color(15, 23, 42));
                metaLabel.setText("Active Ticket");
            }
            actionHint.setText("⚡ Click to Check-Out");
            actionHint.setForeground(new Color(225, 29, 72));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Background
        Color bg = slot.isAvailable() ? BG_VACANT : BG_OCCUPIED;
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, w - 1, h - 1, 12, 12);

        // Border
        Color border = isHovered ? HOVER_BORDER : (slot.isAvailable() ? BORDER_VACANT : BORDER_OCCUPIED);
        g2.setColor(border);
        g2.setStroke(new BasicStroke(isHovered ? 2.0f : 1.2f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, 12, 12);

        // Status pill background
        Point pillPos = SwingUtilities.convertPoint(statusPill, 0, 0, this);
        g2.setColor(slot.isAvailable() ? BADGE_VACANT : BADGE_OCCUPIED);
        g2.fillRoundRect(pillPos.x, pillPos.y, statusPill.getWidth(), statusPill.getHeight(), 12, 12);

        g2.dispose();
        super.paintComponent(g);
    }

    public ParkingSlot getSlot() {
        return slot;
    }
}
