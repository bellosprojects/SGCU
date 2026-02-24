package aura.animations;

import aura.core.AuraBox;
import aura.core.Transition;

public class AnimateWobble extends Transition<AnimateWobble> {

    public AnimateWobble(AuraBox<?> component, int max, int repeats, int ms){
        initialize(component, component.getAnchorX(), component.getAnchorY(), max, repeats,  ms);
    }

    private void initialize(AuraBox<?> component, float initialX, float initialY, int intensity, int repeats, int ms){

        setup(0, 0, 0, value -> {}, component, TransitionType.LINEAR);

        component.anchor(1f, 1f);

        Transition<?> current = this;

        for(int i = 1; i <= repeats; i ++){

            int aX = (i + 1) % 2;
            float angle = (float) Math.pow(-1, i + 1) * intensity / ( i / 1.2f);
            angle = Math.min(angle, intensity);

            AnimateRotation t = new AnimateRotation(component, angle, ms / (i + 1)).then(() -> {
                component.anchor(aX, 1f);
            }).pingPong();

            current.serie(t);
            current = t;

        }

        current.then(() -> {
            component.anchor(initialX, initialY);
        });

    }
    
}
