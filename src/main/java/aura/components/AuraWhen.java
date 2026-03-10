package aura.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import aura.animations.AnimateOpacity;
import aura.core.AuraBox;
import aura.core.AuraState;

public class AuraWhen<T> extends AuraBox<AuraWhen<T>> {

    private T currentState;
    private final Map<T, AuraBox<?>> states = new HashMap<>();
    private final AuraState<T> stateController;
    private int animationDuration = 200;

    public AuraWhen(AuraState<T> state){
        addMouseEvents();
        this.stateController = state;
        this.setLayout(new BorderLayout());
        this.background(new Color(0,0,0,0));

        stateController.onChange(v -> updateView(v));
    }

    public AuraWhen<T> addCase(T value, AuraBox<?> view){
        states.put(value, view);
        if(value.equals(stateController.get())){
            updateView(value);
        }
        return this;
    }

    public AuraWhen<T> animationDuration(int duration){
        this.animationDuration = duration;
        return this;
    }
    
    private void updateView(T newValue){

        AuraBox<?> currentView = states.get(currentState);
        this.currentState = newValue;

        if(currentView == null){
            removeAll();
            deployNew(true);
        } else {
            new AnimateOpacity(currentView, 0f, animationDuration / 2)
                .then(() -> {
                    removeAll();
                    deployNew(false);
                })
                .start();
        }
        
    }

    private void deployNew(boolean unique){
        AuraBox<?> next = states.get(currentState);
        if(next != null){ 
            next.opacity(0f);
            this.add(next, BorderLayout.CENTER);
            new AnimateOpacity(next, 1f, animationDuration / (unique? 1 : 2))
                .then(() -> {    
                    this.revalidate();
                    this.repaint();
                })
                .start();
        }
    }
}
