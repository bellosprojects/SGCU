package aura.components;

import java.awt.*;
import aura.animations.AnimateOpacity;

public class AuraToast extends AuraText {

    public AuraToast(String message, Color bg) {
        
        super(message);
        background(bg)
        .radius(15)
        .padding(10, 20)
        .font(new Font("SansSerif", Font.BOLD, 30))
        .textColor(Color.white)
        .opacity(0f);

    }

    public void animateOut(int delay) {

        new AnimateOpacity(this, 0, 500).then(() -> {
            Container parent = getParent();
            if (parent != null) {
                parent.remove(this);
                parent.repaint();
            }
        })
        .delay(delay)
        .start();
    }
}