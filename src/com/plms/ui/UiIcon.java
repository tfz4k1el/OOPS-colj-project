package com.plms.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * High-performance vector icon engine for PLMS.
 * Completely eliminates broken emoji squares / missing glyphs on Windows Swing
 * by rendering pure, antialiased Java2D vector paths.
 */
public class UiIcon implements Icon {
    public enum Type {
        CAR,
        BIKE,
        PARKING_LOGO,
        BUILDING,
        AVAILABLE_DOT,
        OCCUPIED_DOT,
        REVENUE,
        CHECK_IN,
        CHECK_OUT,
        SEARCH,
        LIGHTNING,
        CLOCK,
        EXPORT,
        CHECK_MARK
    }

    private final Type type;
    private final int size;
    private final Color primaryColor;
    private final Color secondaryColor;

    public UiIcon(Type type, int size, Color primaryColor) {
        this(type, size, primaryColor, null);
    }

    public UiIcon(Type type, int size, Color primaryColor, Color secondaryColor) {
        this.type = type;
        this.size = size;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    // Factory methods for convenience
    public static Icon car(int size, Color color) { return new UiIcon(Type.CAR, size, color); }
    public static Icon bike(int size, Color color) { return new UiIcon(Type.BIKE, size, color); }
    public static Icon parkingLogo(int size) { return new UiIcon(Type.PARKING_LOGO, size, new Color(37, 99, 235), new Color(79, 70, 229)); }
    public static Icon building(int size, Color color) { return new UiIcon(Type.BUILDING, size, color); }
    public static Icon availableDot(int size) { return new UiIcon(Type.AVAILABLE_DOT, size, new Color(16, 185, 129)); }
    public static Icon occupiedDot(int size) { return new UiIcon(Type.OCCUPIED_DOT, size, new Color(239, 68, 68)); }
    public static Icon revenue(int size, Color color) { return new UiIcon(Type.REVENUE, size, color); }
    public static Icon checkIn(int size, Color color) { return new UiIcon(Type.CHECK_IN, size, color); }
    public static Icon checkOut(int size, Color color) { return new UiIcon(Type.CHECK_OUT, size, color); }
    public static Icon search(int size, Color color) { return new UiIcon(Type.SEARCH, size, color); }
    public static Icon lightning(int size, Color color) { return new UiIcon(Type.LIGHTNING, size, color); }
    public static Icon clock(int size, Color color) { return new UiIcon(Type.CLOCK, size, color); }
    public static Icon export(int size, Color color) { return new UiIcon(Type.EXPORT, size, color); }
    public static Icon checkMark(int size, Color color) { return new UiIcon(Type.CHECK_MARK, size, color); }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2.translate(x, y);

        switch (type) {
            case CAR:
                drawCar(g2, size, primaryColor);
                break;
            case BIKE:
                drawBike(g2, size, primaryColor);
                break;
            case PARKING_LOGO:
                drawParkingLogo(g2, size, primaryColor, secondaryColor);
                break;
            case BUILDING:
                drawBuilding(g2, size, primaryColor);
                break;
            case AVAILABLE_DOT:
                drawStatusDot(g2, size, primaryColor, new Color(167, 243, 208));
                break;
            case OCCUPIED_DOT:
                drawStatusDot(g2, size, primaryColor, new Color(254, 202, 202));
                break;
            case REVENUE:
                drawRevenue(g2, size, primaryColor);
                break;
            case CHECK_IN:
                drawCheckIn(g2, size, primaryColor);
                break;
            case CHECK_OUT:
                drawCheckOut(g2, size, primaryColor);
                break;
            case SEARCH:
                drawSearch(g2, size, primaryColor);
                break;
            case LIGHTNING:
                drawLightning(g2, size, primaryColor);
                break;
            case CLOCK:
                drawClock(g2, size, primaryColor);
                break;
            case EXPORT:
                drawExport(g2, size, primaryColor);
                break;
            case CHECK_MARK:
                drawCheckMark(g2, size, primaryColor);
                break;
        }

        g2.dispose();
    }

    // --- Vector Drawing Implementations ---

    private void drawCar(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        // Car Body Upper (Cabin)
        Path2D cabin = new Path2D.Double();
        cabin.moveTo(5, 12);
        cabin.lineTo(8, 6);
        cabin.lineTo(16, 6);
        cabin.lineTo(19, 12);
        cabin.closePath();
        g2.fill(cabin);

        // Car Body Lower
        RoundRectangle2D body = new RoundRectangle2D.Double(2, 11, 20, 6, 3, 3);
        g2.fill(body);

        // Windows (Cutout / contrast)
        g2.setColor(Color.WHITE);
        Path2D w1 = new Path2D.Double();
        w1.moveTo(8.5, 7.5);
        w1.lineTo(11.5, 7.5);
        w1.lineTo(11.5, 11);
        w1.lineTo(6.5, 11);
        w1.closePath();
        g2.fill(w1);

        Path2D w2 = new Path2D.Double();
        w2.moveTo(12.5, 7.5);
        w2.lineTo(15.5, 7.5);
        w2.lineTo(17.5, 11);
        w2.lineTo(12.5, 11);
        w2.closePath();
        g2.fill(w2);

        // Wheels
        g2.setColor(new Color(15, 23, 42));
        g2.fill(new Ellipse2D.Double(5, 15, 4, 4));
        g2.fill(new Ellipse2D.Double(15, 15, 4, 4));

        // Hubcaps
        g2.setColor(new Color(203, 213, 225));
        g2.fill(new Ellipse2D.Double(6.5, 16.5, 1.2, 1.2));
        g2.fill(new Ellipse2D.Double(16.5, 16.5, 1.2, 1.2));
    }

    private void drawBike(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Wheels
        g2.draw(new Ellipse2D.Double(2, 12, 6.5, 6.5));
        g2.draw(new Ellipse2D.Double(15.5, 12, 6.5, 6.5));

        // Frame
        Path2D frame = new Path2D.Double();
        frame.moveTo(5.2, 15.2);
        frame.lineTo(9.5, 15.2);
        frame.lineTo(14, 9.5);
        frame.lineTo(8.5, 9.5);
        frame.lineTo(5.2, 15.2);
        g2.draw(frame);

        // Fork to front wheel
        g2.draw(new Line2D.Double(14, 9.5, 18.7, 15.2));

        // Handlebars
        g2.draw(new Line2D.Double(14, 9.5, 13.5, 6.5));
        g2.draw(new Line2D.Double(12, 6.5, 15.5, 6.5));

        // Seat
        g2.draw(new Line2D.Double(7.5, 8.5, 10.5, 8.5));
    }

    private void drawParkingLogo(Graphics2D g2, int s, Color c1, Color c2) {
        // Gradient rounded background
        GradientPaint gp = new GradientPaint(0, 0, c1 != null ? c1 : new Color(37, 99, 235),
                s, s, c2 != null ? c2 : new Color(79, 70, 229));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, s, s, s / 3, s / 3);

        // Bold letter "P"
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, (int) (s * 0.65)));
        FontMetrics fm = g2.getFontMetrics();
        int strWidth = fm.stringWidth("P");
        int strHeight = fm.getAscent();
        int px = (s - strWidth) / 2;
        int py = (s + strHeight) / 2 - (int) (s * 0.08);
        g2.drawString("P", px, py);
    }

    private void drawBuilding(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        // Main Tower
        g2.fillRoundRect(4, 3, 16, 18, 2, 2);

        // Windows (White cutouts)
        g2.setColor(Color.WHITE);
        for (int y = 5; y <= 15; y += 4) {
            for (int x = 6; x <= 14; x += 4) {
                g2.fillRect(x, y, 2, 2);
            }
        }

        // Door
        g2.setColor(new Color(15, 23, 42));
        g2.fillRect(10, 17, 4, 4);
    }

    private void drawStatusDot(Graphics2D g2, int s, Color dotColor, Color ringColor) {
        // Outer soft glowing ring
        g2.setColor(ringColor);
        g2.fillOval(0, 0, s, s);

        // Inner solid dot
        int inner = Math.max(4, (int) (s * 0.55));
        int offset = (s - inner) / 2;
        g2.setColor(dotColor);
        g2.fillOval(offset, offset, inner, inner);
    }

    private void drawRevenue(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        // Coin Circle
        g2.setStroke(new BasicStroke(1.8f));
        g2.drawOval(2, 2, 20, 20);

        // Inner currency symbol or star
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth("₹");
        g2.drawString("₹", 12 - w / 2, 16);
    }

    private void drawCheckIn(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        // Downward arrow into a tray
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Arrow stem & head
        g2.drawLine(12, 3, 12, 14);
        g2.drawLine(8, 10, 12, 14);
        g2.drawLine(16, 10, 12, 14);

        // Bottom tray
        Path2D tray = new Path2D.Double();
        tray.moveTo(4, 13);
        tray.lineTo(4, 19);
        tray.lineTo(20, 19);
        tray.lineTo(20, 13);
        g2.draw(tray);
    }

    private void drawCheckOut(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        // Upward arrow out of a tray
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Arrow stem & head
        g2.drawLine(12, 15, 12, 4);
        g2.drawLine(8, 8, 12, 4);
        g2.drawLine(16, 8, 12, 4);

        // Bottom tray
        Path2D tray = new Path2D.Double();
        tray.moveTo(4, 13);
        tray.lineTo(4, 19);
        tray.lineTo(20, 19);
        tray.lineTo(20, 13);
        g2.draw(tray);
    }

    private void drawSearch(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Glass lens
        g2.drawOval(4, 4, 11, 11);
        // Handle
        g2.drawLine(12, 12, 19, 19);
    }

    private void drawLightning(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        Path2D bolt = new Path2D.Double();
        bolt.moveTo(13, 2);
        bolt.lineTo(6, 12);
        bolt.lineTo(12, 12);
        bolt.lineTo(10, 22);
        bolt.lineTo(18, 10);
        bolt.lineTo(12, 10);
        bolt.closePath();
        g2.fill(bolt);
    }

    private void drawClock(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        g2.setStroke(new BasicStroke(1.8f));
        g2.drawOval(2, 2, 20, 20);

        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Hour hand
        g2.drawLine(12, 12, 12, 6);
        // Minute hand
        g2.drawLine(12, 12, 16, 12);
    }

    private void drawExport(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Disk body
        g2.drawRoundRect(3, 3, 18, 18, 2, 2);
        // Upper label
        g2.fillRect(7, 3, 10, 6);
        // Lower shutter
        g2.drawRect(6, 13, 12, 8);
    }

    private void drawCheckMark(Graphics2D g2, int s, Color color) {
        g2.setColor(color);
        double scale = s / 24.0;
        g2.scale(scale, scale);

        g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(4, 12, 9, 17);
        g2.drawLine(9, 17, 20, 6);
    }
}
