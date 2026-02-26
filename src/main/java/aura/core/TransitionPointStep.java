package aura.core;

import java.awt.Point;

@FunctionalInterface
public interface TransitionPointStep {
    void onUpdate(Point value);
}
