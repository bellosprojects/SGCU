package aura.core;

import java.awt.Color;

@FunctionalInterface
public interface TransitionColorStep {
    void onUpdate(Color color);
}
