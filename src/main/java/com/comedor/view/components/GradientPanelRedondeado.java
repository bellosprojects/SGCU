package com.comedor.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

public class GradientPanelRedondeado extends JPanel {
    
    private int radio;
    private int padding;
    private int orientacion;
    private boolean conDegradado;
    private Color color1;
    private Color color2;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DIAGONAL = 2;

    public GradientPanelRedondeado(int radio, int padding, Color color) {
        this.radio = radio;
        this.padding = padding;
        this.color1 = color;
        this.color2 = color;
        this.orientacion = DIAGONAL;
        this.conDegradado = false;

        setOpaque(false);
    }

    public GradientPanelRedondeado(int radio, int padding, Color color1, Color color2, int orientacion) {
        this.radio = radio;
        this.padding = padding;
        this.color1 = color1;
        this.color2 = color2;
        this.orientacion = orientacion;
        this.conDegradado = true;

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth() - padding * 2;
        int alto = getHeight() - padding * 2;

        RoundRectangle2D forma = new RoundRectangle2D.Double(
            padding, padding, ancho, alto, radio, radio
        );

        if(conDegradado && !color1.equals(color2)){
            GradientPaint gp = crearDegradado(ancho, alto);
            g2d.setPaint(gp);
        } else {
            g2d.setColor(color1);
        }
        g2d.fill(forma);
        g2d.dispose();
    }

    private GradientPaint crearDegradado(int ancho, int alto){
        Point puntoInicio = new Point(0, 0);
        Point puntoFin = new Point(ancho, alto);

        switch (orientacion) {
            case HORIZONTAL:
                puntoFin = new Point(ancho, 0);
                break;
            case VERTICAL:
                puntoFin = new Point(0, alto);
                break;
            case DIAGONAL:
                puntoFin = new Point(ancho, alto);
                break;
            default:
                puntoFin = new Point(ancho, alto);
                break;
        }

        return new GradientPaint(puntoInicio, color1, puntoFin, color2);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }
    
    @Override
    public Insets getInsets() {
        return new Insets(padding, padding, padding, padding);
    }
    
    // Getters y Setters
    public int getRadio() {
        return radio;
    }
    
    public void setRadio(int radio) {
        this.radio = radio;
        repaint();
    }
    
    public int getPadding() {
        return padding;
    }
    
    public void setPadding(int padding) {
        this.padding = padding;
        repaint();
    }
    
    public Color getColor1() {
        return color1;
    }
    
    public void setColor1(Color color1) {
        this.color1 = color1;
        repaint();
    }
    
    public Color getColor2() {
        return color2;
    }
    
    public void setColor2(Color color2) {
        this.color2 = color2;
        repaint();
    }
    
    public int getOrientacion() {
        return orientacion;
    }
    
    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
        repaint();
    }
    
    public boolean isConDegradado() {
        return conDegradado;
    }
    
    public void setConDegradado(boolean conDegradado) {
        this.conDegradado = conDegradado;
        repaint();
    }

}
