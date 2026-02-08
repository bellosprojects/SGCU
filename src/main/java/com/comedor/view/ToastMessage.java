
package com.comedor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ToastMessage extends JWindow {

    private float opacity = 0;
    private boolean active = false;

    public ToastMessage(String texto, Color colorFondo) {
        setAlwaysOnTop(true);
        setLayout(new GridBagLayout());
        setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorFondo);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel label = new JLabel(texto);
        label.setForeground(EstiloGral.BG_COLOR);
        label.setFont(EstiloGral.INPUT_FONT);
        panel.add(label);
        
        add(panel);
        pack();

        // Posicionamiento (Parte inferior central)
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - getWidth()) / 2;
        int y = (int) (screen.height * 0.85); // 85% hacia abajo
        setLocation(x, y);
    }

    public void fadeIn() {

        active = true;

        new Thread(() -> {
            try {
                for (opacity = 0; opacity <= 1; opacity += 0.1f) {
                    setOpacity(opacity);
                    Thread.sleep(20);
                }
                Thread.sleep(1800); // Duración del mensaje visible
                fadeOut();
            } catch (Exception e) {}
        }).start();
    }

    private void fadeOut() {
        new Thread(() -> {
            try {
                active = false;
                for (opacity = 1; opacity >= 0; opacity -= 0.075f) {
                    setOpacity(opacity);
                    Thread.sleep(20);
                }
                dispose(); // Elimina la ventana por completo
            } catch (Exception e) {}
        }).start();
    }

    public boolean getActive(){
        return this.active;
    }
}