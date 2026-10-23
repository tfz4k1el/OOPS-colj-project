package com.plms.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Modern custom button with antialiased gradients, smooth hover transitions,
 * crisp contrast, and optional outline or pill styling.
 */
public class ModernButton extends JButton {
    private Color normalBg;
    private Color hoverBg;
    private Color pressedBg;
    private Color gradientEnd = null;
    private Color hoverGradientEnd = null;
    private int cornerRadius = 8;
    private boolean isOutlined = false;
    private Color borderColor = null;

    public ModernButton(String text, Color normalBg, Color hoverBg) {
        this(text, normalBg, hoverBg, 8);
    }

    public ModernButton(String text, Color normalBg, Color hoverBg, int cornerRadius) {
        super(text);
        this.normalBg = normalBg;
        this.hoverBg = hoverBg;
        this.pressedBg = hoverBg.darker();
        this.cornerRadius = cornerRadius;
        setupDefaults();
    }

    public ModernButton(String text, Color startGradient, Color endGradient, Color hoverStart, Color hoverEnd) {
        super(text);
        this.normalBg = startGradient;
        this.gradientEnd = endGradient;
        this.hoverBg = hoverStart;
        this.hoverGradientEnd = hoverEnd;
        this.pressedBg = hoverStart.darker();
        this.cornerRadius = 8;
        setupDefaults();
    }

    private void setupDefaults() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    public void setOutlined(boolean outlined, Color border, Color textColor) {
        this.isOutlined = outlined;
        this.borderColor = border;
        setForeground(textColor);
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (isOutlined) {
            if (getModel().isRollover()) {
                g2.setColor(new Color(241, 245, 249, 180));
                g2.fillRoundRect(0, 0, w - 1, h - 1, cornerRadius, cornerRadius);
            }
            g2.setColor(borderColor != null ? borderColor : new Color(203, 213, 225));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, cornerRadius, cornerRadius);
        } else {
            if (gradientEnd != null) {
                Color start = getModel().isPressed() ? pressedBg : (getModel().isRollover() ? hoverBg : normalBg);
                Color end = getModel().isPressed() ? hoverGradientEnd.darker() : (getModel().isRollover() ? hoverGradientEnd : gradientEnd);
                GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
                g2.setPaint(gp);
            } else {
                Color c = getModel().isPressed() ? pressedBg : (getModel().isRollover() ? hoverBg : normalBg);
                g2.setColor(c);
            }
            g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
