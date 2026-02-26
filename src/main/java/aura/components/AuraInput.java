package aura.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import aura.core.AuraBox;

public class AuraInput extends AuraBox<AuraInput> {

    private final JTextField input;

    public AuraInput(){
        input = new JTextField();
        input.setOpaque(false);
        input.setBorder(null);
        input.setBackground(new Color(0,0,0,0));
        input.setCursor(Cursor.getDefaultCursor());
        input.setMargin(new Insets(0, 0, 0, 0));
        input.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        addMouseEvents();

        MouseAdapter ms = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                checkHover(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkClick(e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                AuraInput.this.dispatchEvent(SwingUtilities.convertMouseEvent(input, e, AuraInput.this));
            }
            
        };

        input.addMouseListener(ms);
        input.addMouseMotionListener(ms);
        
        setLayout(new BorderLayout());
        add(input, BorderLayout.CENTER);
    }

    public AuraInput font(Font font){
        this.input.setFont(font);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraInput textColor(Color color){
        this.input.setForeground(color);
        repaint();
        return this;
    }

    public String getText(){
        return this.input.getText();
    }

    public AuraInput text(String text){
        this.input.setText(text);
        repaint();
        return this;
    }

    public AuraInput carterColor(Color color){
        this.input.setCaretColor(color);
        return this;
    }

    public AuraInput block(){
        this.input.setEnabled(false);
        return this;
    }

    public AuraInput unblock(){
        this.input.setEnabled(true);
        return this;
    }

    @Override
        public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (input != null) {
            input.revalidate(); 
        }
    }
    
}
