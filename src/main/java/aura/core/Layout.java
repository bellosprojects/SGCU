package aura.core;

import java.awt.Component;
import java.util.function.Consumer;

@SuppressWarnings ("unchecked")
public class Layout<T extends Layout<T>> extends AuraBox<T> {
    
    protected  int gap = 0;

    public T gap(int gap){
        this.gap = gap;
        this.revalidate();
        this.repaint();
        this.validate();

        return (T) this;
    }

    public int getGap(){
        return this.gap;
    }

    public T content(Consumer<Layout<T>> content){
        content.accept(this);
        return (T) this;
    }

    public void insert(Component c){
        this.add(c);
    }

}
