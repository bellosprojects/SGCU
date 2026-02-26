package aura.animations;

import aura.core.AuraBox;
import aura.core.Transition;

public class AnimateScale extends Transition<AnimateScale> {
    
    public AnimateScale(AuraBox<?> component, float to, int ms){
        
        initialize(component, component.getScale(), to, ms);
    }

    private void initialize(AuraBox<?> component,float initialScale, float to, int ms){
        setup(initialScale, to, ms, value -> { component.scale(value); } ,component, TransitionType.LINEAR);

        animationType(AnimationType.SCALE);
    }

}
