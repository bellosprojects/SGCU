package aura.layouts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import aura.core.AuraBox;
import aura.core.Layout;
import aura.utils.MathUtils;

public class AuraRow extends Layout<AuraRow> {

    public static enum Alignment { CENTER, TOP, BOTTOM }
    private Alignment alignment = Alignment.CENTER;

    public AuraRow() {
        addMouseEvents();
        setLayout(null);
        background(new Color(0,0,0,0));
    }

    public AuraRow align(Alignment align) {
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
            } else if (box.getWidthPorc() > 0) {
                fixedWidth += MathUtils.clamp(box.getWidthPorc() * totalWidth, box.getMinimalSize().width, box.getMaximalSize().width);
            } else {
                fixedWidth += getClampedSize(box).width;
            }
            visibleCount++;
        }

        int gapTotal = (visibleCount > 1) ? (visibleCount - 1) * gap : 0;
        int remainingWidth = Math.max(0, totalWidth - fixedWidth - gapTotal);
        int currentX = in.left;

        for (Component c : children) {
            if (!c.isVisible()) continue;
            AuraBox<?> box = (AuraBox<?>) c;
            Dimension d = getClampedSize(box);

            int finalWidth = d.width;
            if (box.getWeight() > 0 && totalWeight > 0) {
                finalWidth = (int) ((box.getWeight() / totalWeight) * remainingWidth);
                finalWidth = (int) MathUtils.clamp(finalWidth, 
                    box.getMinimalSize().width != -1 ? box.getMinimalSize().width : 0, 
                    box.getMaximalSize().width != -1 ? box.getMaximalSize().width : Integer.MAX_VALUE);
            } else if (box.getWidthPorc() > 0) {
                finalWidth = (int) MathUtils.clamp(box.getWidthPorc() * totalWidth, box.getMinimalSize().width, box.getMaximalSize().width);
            }

            int finalHeight = (box.getHeightPorc() > 0) ? (int)(box.getHeightPorc() * availableHeight) : d.height;

            if (box.getRatio() > 0 ){
                finalHeight = (int) (finalWidth * box.getRatio());
            }

            finalHeight = (int) MathUtils.clamp(finalHeight, 
                box.getMinimalSize().height != -1 ? box.getMinimalSize().height : 0, 
                box.getMaximalSize().height != -1 ? box.getMaximalSize().height : Integer.MAX_VALUE);

            Alignment finalAlign = (box.getAlignR() != null) ? box.getAlignR() : alignment;
            int y = switch (finalAlign) {
                case CENTER -> in.top + (availableHeight - finalHeight) / 2;
                case BOTTOM -> in.top + (availableHeight - finalHeight);
                default -> in.top;
            };

            c.setBounds(currentX, y, finalWidth, finalHeight);
            currentX += finalWidth + gap;
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
