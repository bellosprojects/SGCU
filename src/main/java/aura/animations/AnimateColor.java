package aura.animations;

import java.awt.Color;

import aura.core.Transition;
import aura.core.TransitionColorStep;
import aura.utils.MathUtils;

public class AnimateColor extends Transition<AnimateColor> {

    public AnimateColor(Color initial, Color end, int ms, TransitionColorStep step) {

        setup(0f, 1f, ms, 
            
            value -> {

                Color c = new Color(
                    (int) MathUtils.clamp(initial.getRed() + value * (end.getRed() - initial.getRed()), 0, 255),
                    (int) MathUtils.clamp(initial.getGreen() + value * (end.getGreen() - initial.getGreen()), 0, 255),
                    (int) MathUtils.clamp(initial.getBlue() + value * (end.getBlue() - initial.getBlue()), 0, 255),
                    (int) MathUtils.clamp(initial.getAlpha() + value * (end.getAlpha() - initial.getAlpha()), 0, 255)
                );

                step.onUpdate(c);

            }
            
            , null, TransitionType.LINEAR);

    }

}
