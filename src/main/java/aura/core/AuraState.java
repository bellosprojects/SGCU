package aura.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AuraState<T> {
    private T value;
    private final List<Consumer<T>> listeners = new ArrayList<>();

    public AuraState(T initialValue) {
        this.value = initialValue;
    }

    public void set(T newValue){
        this.value = newValue;
        listeners.forEach(l -> l.accept(newValue));
    }

    public T get(){
        return value;
    }

    public void onChange(Consumer<T> listener){
        listeners.add(listener);
        listener.accept(value);
    }
}
