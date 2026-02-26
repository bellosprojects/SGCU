package aura.animations;

import aura.core.AuraBox;
import aura.core.Transition;

public class AnimateRipple extends Transition<AnimateRipple> {
    
    public AnimateRipple(AuraBox<?> component, float to, int ms){

        float initialScale = component.getScale();

        initialize(component, initialScale, to, ms);
    }

    private void initialize(AuraBox<?> component, float initialScale, float maximus, int ms){
        setup(initialScale, maximus, ms / 3, value -> {

            component.scale(value);

        }, component, TransitionType.LINEAR);

        pingPong();

        animationType(AnimationType.SCALE);

        type(TransitionType.EASE_IN);

        serie(
            
            new AnimateScale(component, initialScale + ((maximus - initialScale) / 2f), ms / 4)
            .pingPong()
            .cancelPrev(true)
            .animationType(AnimationType.SCALE)
            .serie(
                new AnimateScale(component, initialScale + ((maximus - initialScale) / 4f), ms / 6)
                    .pingPong()
                    .animationType(AnimationType.SCALE)
                    .cancelPrev(true)
                    .serie(
                        new AnimateScale(component, initialScale + ((maximus - initialScale) / 8f), ms /  8)
                            .pingPong()
                            .animationType(AnimationType.SCALE)
                            .cancelPrev(true)
                            .then(() -> {
                                component.scale(initialScale);
                            })
                    )
            )
        );
    }

}
