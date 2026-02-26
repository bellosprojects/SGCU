package aura.animations;

import aura.core.Transition;
import aura.core.TransitionFloatStep;

public class AnimateFloat extends Transition<AnimateFloat> {

    public AnimateFloat(float initial, float end, int ms, TransitionFloatStep step) {

        this.setup(initial, end, ms, value -> {step.onUpdate(value);}, null, TransitionType.LINEAR);

    }

}
