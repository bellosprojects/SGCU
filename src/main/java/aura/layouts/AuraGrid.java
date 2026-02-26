package aura.layouts;

import java.awt.*;
import aura.core.AuraBox;
import aura.core.Layout;

public class AuraGrid extends Layout<AuraGrid> {
    
    private int rows;
    private int cols;
    private int hGap = 0;
    private int vGap = 0;

    public AuraGrid(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        setLayout(null);
        addMouseEvents();
        background(new Color(0,0,0,0));
    }

    public AuraGrid rows(int r){
        this.rows = r;
        revalidate();
        return this;
    }

    public AuraGrid cols(int c){
        this.cols = c;
        revalidate();
        return this;
    }

    @Override
    public AuraGrid gap(int g){
        this.hGap = g;
        this.vGap = g;
        revalidate();
        return this;
    }

    public AuraGrid gap(int h, int v){
        this.vGap = v;
        this.hGap = h;
        revalidate();
        return this;
    }

    @Override
    public void doLayout(){
        
        Insets in = getInsets();
        int totalW = getWidth() - in.left - in.right;
        int totalH = getHeight() - in.top - in.bottom;
        int cellW = (totalW - (cols - 1) * hGap) / cols;
        int cellH = (totalH - (rows - 1) * vGap) / rows;

        boolean[][] occupied = new boolean[rows][cols];

        for (Component child : getComponents()) {
            if (!child.isVisible() || !(child instanceof AuraBox)) continue;
            AuraBox<?> box = (AuraBox<?>) child;

            int cs = box.getColSpan();
            int rs = box.getRowSpan();

            int foundR = 0, foundC = 0;
            search:
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (!occupied[r][c]) {
                        foundR = r;
                        foundC = c;
                        break search;
                    }
                }
            }

            for (int r = foundR; r < foundR + rs && r < rows; r++) {
                for (int c = foundC; c < foundC + cs && c < cols; c++) {
                    occupied[r][c] = true;
                }
            }

            int width = (cs * cellW) + ((cs - 1) * hGap);
            int height = (rs * cellH) + ((rs - 1) * vGap);
            int x = in.left + (foundC * (cellW + hGap));
            int y = in.top + (foundR * (cellH + vGap));

            child.setBounds(x, y, width, height);
        }
    }

    @Override
    public Dimension getPreferredSize(){
        Insets in = getInsets();

        int maxW = 0;
        int maxH = 0;

        for(Component child : getComponents()){

            Dimension d = child.getPreferredSize();
            maxW = Math.max(maxW, d.width);
            maxH = Math.max(maxH, d.height);

        }

        int totalW = (maxW * cols) + (hGap * (cols - 1)) + in.left + in.right;
        int totalH = (maxH * rows) + (vGap * (rows - 1)) + in.top + in.bottom;

        return new Dimension(totalW, totalH);
    }

}
