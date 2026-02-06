package com.comedor.view.components;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class ImageCircular extends JLabel {

    private int borderSize = 2;
    private Color borderColor = Color.WHITE;

    public ImageCircular() {
        setPreferredSize(new Dimension(100, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getIcon() != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int diameter = Math.min(width, height);

            int x = (width - diameter) / 2;
            int y = (height - diameter) / 2;

            g2.setColor(borderColor);
            g2.fillOval(x, y, diameter, diameter);

            int padding = borderSize;
            Shape clip = new Ellipse2D.Double(x + padding, y + padding, diameter - (padding * 2), diameter - (padding * 2));
            g2.setClip(clip);

            Image img = ((ImageIcon) getIcon()).getImage();
            g2.drawImage(img, x + padding, y + padding, diameter - (padding * 2), diameter - (padding * 2), this);

            g2.dispose();
        } else {
            super.paintComponent(g);
        }
    }

    // Setters para personalizar el borde
    public void setBorderSize(int borderSize) { this.borderSize = borderSize; repaint(); }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; repaint(); }
}