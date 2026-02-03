package com.comedor.view.components;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BordeRedondeado implements Border {
    private int radio;
    private Color color;
    private int grosor;
    private int padding;

    public BordeRedondeado(int radio, Color color, int grosor, int padding) {
        this.radio = radio;
        this.color = color;
        this.grosor = grosor;
        this.padding = padding;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(grosor));
        
        // Dibujamos el contorno redondeado
        // Restamos el grosor para que el borde no se corte en los límites del componente
        g2.draw(new RoundRectangle2D.Float(x + grosor/2f, y + grosor/2f, 
                width - grosor, height - grosor, radio, radio));
        
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Esto le da "padding" interno para que el texto no toque el borde
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}