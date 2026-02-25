package aura.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JLabel;

import aura.animations.AnimateBackground;
import aura.animations.AnimateRipple;
import aura.core.AuraBox;
import aura.core.Transition;

public class AuraButton extends AuraBox<AuraButton> {

    private final JLabel label;

    public AuraButton(String text){
        label = new JLabel(text);
        label.setOpaque(false);
        label.setForeground(Color.black);
        radius(10, 10, 10, 10);
        setLayout(new BorderLayout());
        addMouseEvents();
        add(label, BorderLayout.CENTER);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        padding(10, 20);

        onClick(self -> {

            cancelAnimations(Transition.AnimationType.BACKGROUND);

            new AnimateRipple(self, 0.95f, 200)
                .parallel(
                    new AnimateBackground(self, getBackgroundColor().brighter(), 100).pingPong()
                )
                .start();

        });
    }

    public AuraButton text(String text){
        this.label.setText(text);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraButton font(Font font){
        this.label.setFont(font);
        repaint();
        if(getParent() != null) getParent().revalidate();
        return this;
    }

    public AuraButton textColor(Color color){
        this.label.setForeground(color);
        repaint();
        return this;
    }

    public String getText(){
        return this.label.getText();
    }
    
}
