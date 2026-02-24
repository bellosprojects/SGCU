package aura.animations;

import aura.core.Transition;
import aura.core.AuraBox;

public class AnimateRotation extends Transition<AnimateRotation> {

    private final AuraBox<?> component;
    private int ms = 0;
    private float to = 0;

    public AnimateRotation(AuraBox<?> component, float to, int ms){
        this.component = component;
        this.ms = ms;
        this.to = to;
        initialize(component, component.getRotation(), to, ms);
    }

    private void initialize(AuraBox<?> component, float initialAngle, float toAngle, int ms){

        setup(initialAngle, toAngle, ms, value -> {

            component.rotate(value);
            if(component.getParent() != null){
                component.getParent().repaint();
            }

        }, component, TransitionType.EASE_IN);


        animationType(AnimationType.ROTATE);
    }

    public AnimateRotation anchor(float x, float y){
        component.anchor(x, y);
        initialize(component, component.getRotation(), to, ms);
        return this;
    }
    
}
