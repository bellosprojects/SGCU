package aura.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

import aura.core.AuraBox;

public class AuraMultiText extends AuraBox<AuraMultiText> {

    private final JTextArea textArea;

    public AuraMultiText(String text) {
        this.textArea = new JTextArea(text);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setOpaque(false);
        this.textArea.setBackground(new Color(0,0,0,0));
        
        this.setLayout(new BorderLayout());
        this.padding(10).add(textArea, BorderLayout.CENTER);
    }

    public AuraMultiText text(String text){
        this.textArea.setText(text);
        repaint();
        if(this.getParent() != null) this.getParent().revalidate();
        return this;
    }

    public String getText(){
        return textArea.getText();
    }

    public AuraMultiText font(Font font){
        this.textArea.setFont(font);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraMultiText textColor(Color color){
        this.textArea.setForeground(color);
        repaint();
        return this;
    }

    public AuraMultiText rows(int rows){
        this.textArea.setRows(rows);
        return this;
    }

    public AuraMultiText block(){
        this.textArea.setEditable(false);
        this.textArea.setFocusable(false);
        return this;
    }

    @Override
    public Font getFont(){
        if(this.textArea == null) return super.getFont();
        return this.textArea.getFont();
    }

    public Color getTextColor(){
        return this.textArea.getForeground();
    }


}