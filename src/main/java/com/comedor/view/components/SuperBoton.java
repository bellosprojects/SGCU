package com.comedor.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SuperBoton extends JButton {
    private Color backgroundColor;
    private int radio;
    private Color originalBackground;

    // Interfaz para la sobrecarga de funciones (Callback)
    public interface HoverAction {
        void onHover(boolean entered);
    }

    private HoverAction hoverAction;

    public SuperBoton(String texto, Color bg, int radio, int padding) {
        super(texto);
        this.backgroundColor = bg;
        this.originalBackground = bg;
        this.radio = radio;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(padding, padding, padding, padding));
        setBackground(bg);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 14));

        // Listener para el efecto Hover visual
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                
                // Si NO hay una acción manual, usamos el brillo por defecto
                if (hoverAction == null) {
                    setBackground(backgroundColor.brighter());
                } else {
                    // Si HAY una acción manual, dejamos que el programador decida
                    hoverAction.onHover(true);
                }
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackground);
                if (hoverAction != null) hoverAction.onHover(false);
                repaint();
            }
        });
    }

    public void setOnHoverAction(HoverAction action) {
        this.hoverAction = action;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

        FontMetrics fm = g2.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
        
        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}