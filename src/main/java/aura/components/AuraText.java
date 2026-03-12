package aura.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import aura.core.AuraBox;

public class AuraText extends AuraBox<AuraText> {

    private final JLabel label = new JLabel("");

    public AuraText(String text){
        label.setText(text);
        label.setOpaque(false);
        add(label);
        background(new Color(0, 0, 0, 0));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        addMouseEvents();
    }

    public AuraText text(String text){
        this.label.setText(text);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraText font(Font font){
        this.label.setFont(font);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraText textColor(Color color){
        this.label.setForeground(color);
        repaint();
        return this;
    }

    public String getText(){
        return this.label.getText();
    }

    @Override
    public Font getFont(){
        if(this.label == null) return super.getFont();
        return this.label.getFont();
    }

    public Color getTextColor(){
        return this.label.getForeground();
    }

}