package aura.animations;

import java.awt.Point;

import aura.core.Transition;
import aura.core.TransitionPointStep;

public class AnimatePoint extends Transition<AnimatePoint> {

    public AnimatePoint(Point initial, Point end, int ms, TransitionPointStep step) {

        initialize(initial, end, ms, step);
    }

    private void initialize(Point initial, Point end, int ms, TransitionPointStep step){
        setup(0f, 1f, ms, value -> {

            Point p = new Point(
                initial.x + (int) ((end.x - initial.x) * value),
                initial.y + (int) ((end.y - initial.y) * value)
            );

            step.onUpdate(p);

        }, null, TransitionType.LINEAR);
    }
    
}
