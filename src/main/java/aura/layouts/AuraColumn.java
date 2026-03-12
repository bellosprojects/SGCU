package aura.layouts;

import java.awt.*;
import aura.core.AuraBox;
import aura.core.Layout;

public class AuraColumn extends Layout<AuraColumn> {

    public static enum Alignment {
        CENTER,
        LEFT,
        RIGHT
    }

    private Alignment alignment = Alignment.CENTER;

    public AuraColumn(){
        setLayout(null);
        addMouseEvents();
        background(new Color(0,0,0,0));
    }

    public AuraColumn align(Alignment align){
        this.alignment = align;
        revalidate();
        return this;
    }

    @Override
    public void doLayout() {
        Insets in = getInsets();
        Component[] children = getComponents();
        int totalHeight = getHeight() - in.top - in.bottom;
        int availableWidth = getWidth() - in.left - in.right;

        int fixedHeight = 0;
        float totalWeight = 0;
        int visibleCount = 0;

        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            if (box.getWeight() > 0) {
                totalWeight += box.getWeight();
            } else {
                fixedHeight += c.getPreferredSize().height;
            }
            visibleCount++;
        }

        int gapSpace = (visibleCount > 1) ? (visibleCount - 1) * gap : 0;
        int remainingHeight = totalHeight - fixedHeight - gapSpace;

        int currentY = in.top;
        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            Dimension d = c.getPreferredSize();
            
            int finalHeight = d.height;
            if (box.getWeight() > 0 && remainingHeight > 0) {
                finalHeight = (int) ((box.getWeight() / totalWeight) * remainingHeight);
            }

            Alignment finalAlign = (box.getAlignC() != null)? box.getAlignC() : alignment;

            int finalWidth = (box.getWidthPorc() > 0)? (int) (box.getWidthPorc() * availableWidth) : d.width;

            int x = switch (finalAlign) {
                case CENTER -> in.left + (availableWidth - finalWidth) / 2;
                case RIGHT -> in.left + (availableWidth - finalWidth);
                default -> in.left;
            };


            c.setBounds(x, currentY, finalWidth, finalHeight);
            currentY += finalHeight + gap;
        }
    }

    @Override
    public Dimension getPreferredSize(){

        Insets in = getInsets();
        int width = 0;
        int height = 0;
        int visibleChildren = 0;

        for(Component child : getComponents()){
            if(child.isVisible()){
                Dimension d = child.getPreferredSize();
                width = Math.max(width, d.width);
                height += d.height;
                visibleChildren ++;
            }
        }

        if(visibleChildren > 1){
            height += (visibleChildren - 1) * gap;
        }

        return new Dimension(
            width + in.left + in.right,
            height + in.top + in.bottom
        );
    }

}
