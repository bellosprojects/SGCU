package aura.animations;

import aura.core.AuraBox;
import aura.core.Transition;

public class AnimateOpacity extends Transition<AnimateOpacity> {
    
    public AnimateOpacity(AuraBox<?> component, float to, int ms){

        float actualOp = component.getOpacity();

        initialize(component, actualOp, to, ms);
    }

    private void initialize(AuraBox<?> component, float actualOp, float to, int ms){
        setup(actualOp, to, ms, value -> {

            component.opacity(value);

        }, component, TransitionType.LINEAR);

        animationType(AnimationType.OPACITY);
    }

}
