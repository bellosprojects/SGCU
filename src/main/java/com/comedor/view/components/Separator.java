package com.comedor.view.components;

import javax.swing.*;

import com.comedor.view.EstiloGral;;

public class Separator extends JPanel {
    
    public Separator (int width, int height){
        setSize(width, height);
        setBackground(EstiloGral.TRANSPARENT_COLOR);
        setOpaque(false); 
    }

}
