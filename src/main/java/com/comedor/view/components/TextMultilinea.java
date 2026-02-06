package com.comedor.view.components;

import javax.swing.*;

import com.comedor.view.EstiloGral;

import java.awt.*;

public class TextMultilinea extends JTextArea {


    public TextMultilinea(String texto, int ancho, Font font) {
        super(texto);
        
        setLineWrap(true);          
        setWrapStyleWord(true);      
        setEditable(false);          
        setFocusable(false);         
        setAlignmentX(Component.CENTER_ALIGNMENT);

        setOpaque(false);
        setBackground(EstiloGral.TRANSPARENT_COLOR); 
        setForeground(EstiloGral.BG_COLOR);
        setFont(font);
        
        setSize(new Dimension(ancho, 1));
        setPreferredSize(new Dimension(ancho, getPreferredSize().height));
        
    }
    
}