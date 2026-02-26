package aura.animations;

import java.awt.Color;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.utils.MathUtils;

public class AnimateShadow extends Transition<AnimateShadow> {
    
    public AnimateShadow(AuraBox<?> component, Color toColor, int toSize, int ms){

        Color initial = component.getShadowColor();
        float initialSize = component.getShadowSize();

        initialize(component, initial, initialSize, toColor, toSize, ms);
    }

    private void initialize(AuraBox<?> component, Color initialColor, float initialSize, Color toColor, int toSize, int ms){
        setup(0f, 1f, ms, value -> {

            Color c = new Color(
                (int) MathUtils.clamp((initialColor.getRed() + (toColor.getRed() - initialColor.getRed()) * value), 0, 255),
                (int) MathUtils.clamp((initialColor.getGreen() + (toColor.getGreen() - initialColor.getGreen()) * value), 0, 255),
                (int) MathUtils.clamp((initialColor.getBlue() + (toColor.getBlue() - initialColor.getBlue()) * value), 0, 255),
                (int) MathUtils.clamp((initialColor.getAlpha() + (toColor.getAlpha() - initialColor.getAlpha()) * value), 0, 255)
            );

            float newSize = initialSize + (toSize - initialSize) * value;

            component.shadow(c, newSize);

        }, component, TransitionType.EASE_OUT);

        animationType(AnimationType.SHADOW);
    }

}
