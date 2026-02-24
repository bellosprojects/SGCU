package aura.components;

import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;
import aura.core.AuraBox;

public class AuraContainer extends AuraBox<AuraContainer> {

    public AuraContainer(){
        setLayout(null);
        addMouseEvents();
    }

    public void insert(Component comp, int x, int y) {
        absoluteComponents.put(comp, new Point(x, y));
        super.add(comp); 
        revalidate();
        repaint();
    }

    public void insert(Component comp) {
        absoluteComponents.put(comp, new Point(0, 0));
        super.add(comp); 
        revalidate();
        repaint();
    }

    @Override
    public void doLayout(){

        super.doLayout();

        for (Map.Entry<Component, Point> entry : absoluteComponents.entrySet()) {
            Component c = entry.getKey();
            Point pos = entry.getValue();

            Insets insets = getInsets();
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            Dimension d = box.getPreferredSize();
            
            int finalWidth = (box.getWidthPorc() > 0)? (int) (box.getWidthPorc() * getWidth()) : d.width;
            int finalHeight = (box.getHeightPorc() > 0)? (int) (box.getHeightPorc() * getHeight()) : d.height;

            c.setBounds(
                insets.left + pos.x, 
                insets.top + pos.y, 
                finalWidth, 
                finalHeight
            );
        }

    }

    public AuraContainer content(Consumer<AuraContainer> content){
        content.accept(this);
        return this;
    }

}
