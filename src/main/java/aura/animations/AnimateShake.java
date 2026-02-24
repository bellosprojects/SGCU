package aura.animations;

import java.awt.Point;

import aura.core.AuraBox;
import aura.core.Transition;

public class AnimateShake extends Transition<AnimateShake> {

    public AnimateShake(AuraBox<?> component, int intensity, int ms){

        initialize(component, intensity, ms);
    }

    private void initialize(AuraBox<?> component, int intensity, int ms){

        Point originalLoc = component.getLocation();

        setup(1f, 10f, ms, value -> {
            
            int newX = originalLoc.x + (int) (Math.sin(value * ms / 100) * intensity);

            component.setLocation(newX, originalLoc.y);


         },component, TransitionType.LINEAR);

        animationType(AnimationType.MOVE);

        then(() -> component.setLocation(originalLoc));
    }
    
}
