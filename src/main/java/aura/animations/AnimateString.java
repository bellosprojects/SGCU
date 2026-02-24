package aura.animations;

import aura.core.Transition;
import aura.core.TransitionStringStep;

public class AnimateString extends Transition<AnimateString> {

    public AnimateString(String string, int ms, TransitionStringStep step){
        initialize(string, ms, step);
    }

    private void initialize(String string, int ms, TransitionStringStep step){

        int len = string.length();

        setup(0f, 1f, ms, value -> {

            String text = string.substring(0, (int) (len * value));
            step.onUpdate(text);

        }, null, TransitionType.LINEAR);

    }
    
}
