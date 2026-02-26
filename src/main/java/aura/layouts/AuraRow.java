package aura.layouts;

import java.awt.*;
import aura.core.AuraBox;
import aura.core.Layout;

public class AuraRow extends Layout<AuraRow> {

    public static enum Alignment {
        CENTER,
        TOP,
        BOTTOM
    }

    private Alignment alignment = Alignment.CENTER;

    public AuraRow(){
        addMouseEvents();
        setLayout(null);
        background(new Color(0,0,0,0));
    }

    public AuraRow align(Alignment align){
        this.alignment = align;
        revalidate();
        return this;
    }

    @Override
    public void doLayout() {
        Insets in = getInsets();
        Component[] children = getComponents();
        int totalWidth = getWidth() - in.left - in.right;
        int availableHeight = getHeight() - in.top - in.bottom;

        int fixedWidth = 0;
        float totalWeight = 0;
        int visibleCount = 0;

        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            if (box.getWeight() > 0) {
                totalWeight += box.getWeight();
            } else {
                fixedWidth += c.getPreferredSize().width;
            }
            visibleCount++;
        }

        int gapSpace = (visibleCount > 1) ? (visibleCount - 1) * gap : 0;
        int remainingWidth = totalWidth - fixedWidth - gapSpace;

        int currentX = in.left;
        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            Dimension d = c.getPreferredSize();
            
            int finalWidth = d.width;
            if (box.getWeight() > 0 && remainingWidth > 0) {
                finalWidth = (int) ((box.getWeight() / totalWeight) * remainingWidth);
            }

            int finalHeight = (box.getHeightPorc() > 0)? (int) (box.getHeightPorc() * availableHeight) : d.height;

            Alignment finalAlign = (box.getAlignR() != null)? box.getAlignR() : alignment;

            int y = switch (finalAlign) {
                case CENTER -> in.top + (availableHeight - finalHeight) / 2;
                case BOTTOM -> in.top + (availableHeight - finalHeight);
                default -> in.top;
            };

            c.setBounds(currentX, y, finalWidth, finalHeight);
            currentX += finalWidth + gap;
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
                height = Math.max(height, d.height);
                width += d.width;
                visibleChildren ++;
            }
        }

        if(visibleChildren > 1){
            width += (visibleChildren - 1) * gap;
        }

        return new Dimension(
            width + in.left + in.right,
            height + in.top + in.bottom
        );
    }
}
