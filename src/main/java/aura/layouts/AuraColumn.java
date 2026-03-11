package aura.layouts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import aura.core.AuraBox;
import aura.core.Layout;
import aura.utils.MathUtils;

public class AuraColumn extends Layout<AuraColumn> {

    public static enum Alignment { CENTER, LEFT, RIGHT }
    private Alignment alignment = Alignment.CENTER;

    public AuraColumn() {
        setLayout(null);
        addMouseEvents();
        background(new Color(0,0,0,0));
    }

    public AuraColumn align(Alignment align) {
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
            } 
            else if (box.getHeightPorc() > 0){
                fixedHeight += MathUtils.clamp(box.getHeightPorc() * totalHeight, box.getMinimalSize().height, box.getMaximalSize().height);
            }else {
                fixedHeight += getClampedSize(box).height;
            }
            visibleCount++;
        }

        int gapTotal = (visibleCount > 1) ? (visibleCount - 1) * gap : 0;
        int remainingHeight = Math.max(0, totalHeight - fixedHeight - gapTotal);
        int currentY = in.top;

        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            Dimension d = getClampedSize(box);

            int finalHeight = d.height;
            if (box.getWeight() > 0 && totalWeight > 0) {
                finalHeight = (int) ((box.getWeight() / totalWeight) * remainingHeight);
                finalHeight = (int) MathUtils.clamp(finalHeight, 
                    box.getMinimalSize().height != -1 ? box.getMinimalSize().height : 0, 
                    box.getMaximalSize().height != -1 ? box.getMaximalSize().height : Integer.MAX_VALUE);
            } else if (box.getHeightPorc() > 0){
                finalHeight = (int) MathUtils.clamp(box.getHeightPorc() * totalHeight, box.getMinimalSize().height, box.getMaximalSize().height);
            }

            int finalWidth = (box.getWidthPorc() > 0) ? (int) (box.getWidthPorc() * availableWidth) : d.width;

            if (box.getRatio() > 0){
                finalWidth = (int) (finalHeight * box.getRatio());
            }
            finalWidth = (int) MathUtils.clamp(finalWidth, 
                box.getMinimalSize().width != -1 ? box.getMinimalSize().width : 0, 
                box.getMaximalSize().width != -1 ? box.getMaximalSize().width : Integer.MAX_VALUE);


            Alignment finalAlign = (box.getAlignC() != null) ? box.getAlignC() : alignment;
            int x = switch (finalAlign) {
                case CENTER -> in.left + (availableWidth - finalWidth) / 2;
                case RIGHT -> in.left + (availableWidth - finalWidth);
                default -> in.left;
            };

            c.setBounds(x, currentY, finalWidth, finalHeight);
            currentY += finalHeight + gap;
        }
    }

    private Dimension getClampedSize(AuraBox<?> box) {
        Dimension d = box.getPreferredSize();
        int w = (int) MathUtils.clamp(d.width, 
            box.getMinimalSize().width != -1 ? box.getMinimalSize().width : 0, 
            box.getMaximalSize().width != -1 ? box.getMaximalSize().width : Integer.MAX_VALUE);
        int h = (int) MathUtils.clamp(d.height, 
            box.getMinimalSize().height != -1 ? box.getMinimalSize().height : 0, 
            box.getMaximalSize().height != -1 ? box.getMaximalSize().height : Integer.MAX_VALUE);
        
        return new Dimension(w, h);
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
