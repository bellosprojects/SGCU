package aura.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLayeredPane;

import aura.animations.AnimateOpacity;
import aura.core.AuraBox;

public class AuraModal extends AuraContainer {
    private final AuraWindow parent;
    private AuraBox<?> cont;

    public AuraModal(AuraWindow parent) {
        this.parent = parent;
        
        this.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        this.setLayout(null);
        this.background(new Color(0,0,0,0));
        
    }

    public void content(AuraBox<?> content){
        for(Component c : getComponents()){
            this.remove(c);
        }

        int x = (this.getWidth() - content.getPreferredSize().width) / 2;
        int y = (this.getHeight() - content.getPreferredSize().height) / 2;
        
        this.cont = content.opacity(1f);
        this.insert(content, x, y);
    }

    public void display(){
        this.cont.opacity(1f);
        parent.getLayeredPane().add(this, JLayeredPane.MODAL_LAYER);
        parent.getLayeredPane().revalidate();
    }

    public void close() {
        new AnimateOpacity(cont, 0f, 100).then(() -> {
            parent.getLayeredPane().remove(this);
            parent.getLayeredPane().repaint();
        }).start();
    }
}