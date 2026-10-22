package com.plms.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Modern custom button with antialiased rounded background and crisp high-contrast text.
 * Prevents Windows Look & Feel from overriding custom button background colors.
 */
public class ModernButton extends JButton {
    private final Color normalBg;
    private final Color hoverBg;
    private final Color pressedBg;
    private final int cornerRadius;

    public ModernButton(String text, Color normalBg, Color hoverBg) {
        this(text, normalBg, hoverBg, 8);
    }

    public ModernButton(String text, Color normalBg, Color hoverBg, int cornerRadius) {
        super(text);
        this.normalBg = normalBg;
        this.hoverBg = hoverBg;
        this.pressedBg = hoverBg.darker();
        this.cornerRadius = cornerRadius;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(pressedBg);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverBg);
        } else {
            g2.setColor(normalBg);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();

        super.paintComponent(g);
    }
}
