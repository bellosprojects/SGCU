package aura.core;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import aura.animations.AnimateOpacity;
import aura.components.AuraImage;
import aura.components.AuraWindow;
import aura.core.Transition.AnimationType;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;
import aura.utils.BoxUtils;
import aura.utils.MathUtils;

/**
 * AuraBox es la clase central de Aura
 * proporciona carateristicas de renderizado avanzadas
 * @author bellosprojects
 * @version 1.0
 */
@SuppressWarnings ("unchecked")
public abstract class AuraBox<T extends AuraBox<T>> extends JPanel {
    
    protected AuraColumn.Alignment colAlign = null;
    protected AuraRow.Alignment rowAlign = null;
    
    protected float anchorX = 0.5f;
    protected float anchorY = 0.5f;

    protected Image backgroundImage = null;
    protected boolean scaleBackground = true;

    public T alignSelf(AuraColumn.Alignment align){
        this.colAlign = align;
        return (T) this;
    }

    public T alignSelf(AuraRow.Alignment align){
        this.rowAlign = align;
        return (T) this;
    }

    public AuraRow.Alignment getAlignR(){
        return this.rowAlign;
    }

    public AuraColumn.Alignment getAlignC(){
        return this.colAlign;
    }

    public T background(Image img){
        this.backgroundImage = img;
        repaint();
        return (T) this;
    }

    public T background(AuraImage img){
        background(img.getImage());
        return (T) this;
    }

    public T anchor(float x, float y){
        this.anchorX = x;
        this.anchorY = y;
        return (T) this;
    }

    public float getAnchorX(){
        return anchorX;
    }

    public float getAnchorY(){
        return anchorY;
    }

    protected float weight = 0;

    public T weight(float w){
        this.weight = w;
        if(getParent() != null) getParent().revalidate();
        return (T) this;
    }

    public float getWeight(){
        return this.weight;
    }

    protected final List<HoverAction<T>> hoverActions = new ArrayList<>();
    protected final List<ClickAction<T>> clickActions = new ArrayList<>();

    protected void addMouseEvents(){

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e){
                checkHover(e.getPoint());
            }
            @Override
            public void mouseReleased(MouseEvent e){
                checkClick(e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {

                Point p = e.getPoint();

                Point adjustedPoint = new Point(
                    (int)(p.x - offsetX),
                    (int)(p.y - offsetY)
                );

                if(shapePath == null || !shapePath.contains(adjustedPoint)){
                    updateHover(false);
                    playHovers(false);
                }
            }
            
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void playClicks(){

        AuraBox<T> self = this;

        for(ClickAction<T> action : clickActions){
            action.onClick((T) self);
        }
    }

    public void playHovers(boolean hover){

        AuraBox<T> self = this;

        for(HoverAction<T> action : hoverActions){
            action.onHover((T) self , hover);
        }
    }

    public T onClick(ClickAction<T> action){
        this.clickActions.add(action);
        return (T) this;
    }

    public T onHover(HoverAction<T> action){
        this.hoverActions.add(action);
        return (T) this;
    }

    private boolean bgType = false; //false = lineal, true = radial

    //Esquinas planas o redondeadas
    private float[] radios = new float[4];
    private boolean[] flatCorners = new boolean[4];

    //Espaciado interno y externo
    private int[] padding = new int[4];
    private int[] margin = new int[4];

    //Fondo degradado, angulo y desplazamiento
    private final List<Color> backgroundColors = new ArrayList<>();
    private final List<Float> backgroundFractions = new ArrayList<>();
    private float backgroundAngle = 0.0f;
    private float backgroundOffsetX = 0f;
    private float backgroundOffsetY = 0f;

    //Figura de fondo y opacidad
    private Path2D shapePath;
    private float opacity = 1f;

    //offset general;
    private float offsetX = 0;
    private float offsetY = 0;

    //rotacion
    protected  float angle = 0;

    //Para SGrid
    protected int colSpan = 1;
    protected int rowSpan = 1;

    //scala
    protected float scale = 1.0f;

    protected boolean isHovered = false;

    //shadow
    protected Color shadowColor = new Color(0, 0, 0, 100);
    protected float shadowSize = 0;
    protected float shadowOffsetX = 0;
    protected float shadowOffsetY = 0;

    //Bordes
    protected Color StrokeColor = new Color(200, 200, 200);
    protected float strokeWidth = 0f;
    protected boolean[] paintedStrokes = new boolean[]{true, true, true, true};

    //Comportamiento con hijos
    protected boolean clipChildrens = false;

    //Animaciones
    protected final List<Transition<?>> timers = new ArrayList<>();
    protected final List<AnimationType> timersTypes = new ArrayList<>();

    protected final Map<Component, Point> absoluteComponents = new HashMap<>();

    public static enum Backgrounds {

        RADIAL,
        LINEAR

    };


    public T colSpan(int s){
        this.colSpan = s;
        return (T) this;
    }

    public T rowSpan(int s){
        this.rowSpan = s;
        return (T) this;
    }

    public int getRowSpan(){
        return this.rowSpan;
    }

    public int getColSpan(){
        return this.colSpan;
    }

    public T setBgType(boolean ty){
        this.bgType = ty;
        return (T) this;
    }

    public T rotate(float angle){
        this.angle = angle;
        revalidate();
        repaint();
        return (T) this;
    }

    public float getRotation(){
        return this.angle;
    }

    @Override
    public Dimension getPreferredSize(){
        Dimension d = super.getPreferredSize();

        if(angle == 0) return d;

        double rad = Math.toRadians(Math.abs(angle));
        int w = (int) (d.width * Math.cos(rad) + d.height * Math.sin(rad));
        int h = (int) (d.width * Math.sin(rad) + d.height * Math.cos(rad));

        return new Dimension(w, h);
    }

    public T clipChildrens(boolean clipChildrens){
        this.clipChildrens = clipChildrens;
        repaint();
        return (T) this;
    }

    public boolean getClipChild(){
        return this.clipChildrens;
    }

    public T scale(float scale){
        this.scale = scale;
        repaint();
        return (T) this;
    }

    public float getScale(){
        return this.scale;
    }

    @Override
    public boolean contains(Point p) {
        return (shapePath != null && shapePath.contains(p));
    }

    protected void checkClick(Point p){

        Point adjustedPoint = new Point(
            (int)(p.x - offsetX),
            (int)(p.y - offsetY)
        );

        if(shapePath != null && shapePath.contains(adjustedPoint)){
            playClicks();
        }
    }

    protected void checkHover(Point p){

        Point adjustedPoint = new Point(
            (int)(p.x - offsetX),
            (int)(p.y - offsetY)
        );

        boolean contains = (shapePath != null && shapePath.contains(adjustedPoint));
        if(contains != isHovered){
            updateHover(contains);
            playHovers(contains);
        }
    }

    protected void updateHover(boolean hover){
        this.isHovered = hover;
        repaint();
    }

    public T stroke(Color color, float width){
        this.StrokeColor = color;
        this.strokeWidth = width;
        repaint();
        return (T) this;
    }

    public T offset(float x, float y){
        this.offsetX = x;
        this.offsetY = y;
        repaint();
        return (T) this;
    }

    protected float[] getOffset(){
        return new float[]{offsetX, offsetY};
    }

    public T radius(float all){
        return radius(all, all, all, all);
    }

    public T radius(float top, float bottom){
        return radius(top, top, bottom, bottom);
    }

    public T radius(float tl, float tr, float bl, float br){
        this.radios = new float[]{tl, tr, bl, br};
        repaint();
        return (T) this;
    }

    public Insets getRadius(){
        return new Insets((int) this.radios[0], (int) this.radios[1], (int) this.radios[2], (int) this.radios[3]);
    }

    public Insets getPadding(){
        return new Insets((int) this.padding[0], (int) this.padding[1], (int) this.padding[2], (int) this.padding[3]);
    }

    public T flatCorners(boolean all){
        return flatCorners(all, all, all, all);
    }

    public T flatCorners(boolean top, boolean bottom){
        return flatCorners(top, top, bottom, bottom);
    }

    public T flatCorners(boolean tl, boolean tr, boolean bl, boolean br){
        this.flatCorners = new boolean[]{tl, tr, bl, br};
        repaint();
        return (T) this;
    }

    public boolean[] getFlatCorners(){
        return this.flatCorners;
    }

    public T paintStrokes(boolean all){
        return paintStrokes(all, all, all, all);
    }

    public T paintStrokes(boolean top_bottom, boolean left_right){
        return paintStrokes(top_bottom, left_right, top_bottom, left_right);
    }

    public T paintStrokes(boolean top, boolean left, boolean bottom, boolean right){
        this.paintedStrokes = new boolean[]{top, left, bottom, right};
        repaint();
        return (T) this;
    }

    public boolean[] getPaintStrokes(){
        return this.paintedStrokes;
    }

    public Insets getMargin(){
        return new Insets((int) this.margin[0], (int) this.margin[1], (int) this.margin[2], (int) this.margin[3]);
    }

    public T background(Color color){
        this.backgroundColors.clear();
        this.backgroundFractions.clear();
        this.backgroundColors.add(color);
        this.backgroundFractions.add(0.0f);
        repaint();
        return (T) this;
    }

    public T addBg(Color color, float position){
        this.backgroundColors.add(color);
        this.backgroundFractions.add(position);
        repaint();
        return (T) this;
    }

    protected AuraBox(){
        setOpaque(false);
        this.backgroundColors.add(new Color(255, 255, 255));
        this.backgroundFractions.add(0.0f);
    }

    public Color getBackgroundColor(){
        return this.backgroundColors.get(0);
    }

    public T backgroundColorAtIndex(Color color, int index){
        if(index < 0 || index >= this.backgroundColors.size()) return (T) this;
        this.backgroundColors.set(index, color);
        repaint();
        return (T) this;
    }

    public T backgroundFractionAtIndex(float fraction, int index){
        if(index < 0 || index >= this.backgroundColors.size()) return (T) this;
        this.backgroundFractions.set(index, fraction);
        repaint();
        return (T) this;
    }

    public T backgroundAtIndex(Color color, float fraction, int index){
        if(index < 0 || index >= this.backgroundColors.size()) return (T) this;
        this.backgroundFractions.set(index, fraction);
        this.backgroundColors.set(index, color);
        repaint();
        return (T) this;
    }

    public T opacity(float alpha){
        if(alpha > 1f || alpha < 0f) return (T) this;
        this.opacity = alpha;
        repaint();
        return (T) this;
    }

    public float getOpacity(){
        return this.opacity;
    }

    public T backgroundAngle(float angle){
        this.backgroundAngle = angle;
        repaint();
        return (T) this;
    }

    public float getBackgroundAngle(){
        return this.backgroundAngle;
    }

    public T backgroundOffset(float offsetX, float offetY){
        this.backgroundOffsetX = offsetX;
        this.backgroundOffsetY = offsetY;
        repaint();
        return (T) this;
    }

    public T shadow(Color color, float size){
        this.shadowColor = color;
        this.shadowSize = size;
        repaint();
        return (T) this;
    }

    public T shadowOffset(float offsetX, float offsetY){
        this.shadowOffsetX = offsetX;
        this.shadowOffsetY = offsetY;
        repaint();
        return (T) this;
    }

    protected void drawShadow(Graphics2D g2, Shape shape) {
        if (shadowSize <= 0) return;

        Graphics2D gShadow = (Graphics2D) g2.create();
        
        BoxUtils.setHighQuality(gShadow);
        gShadow.translate(shadowOffsetX, shadowOffsetY);

        float change = shadowColor.getAlpha() / (shadowSize);

        for (float i = shadowSize; i >= 1; i--) {
            float opacity_ = (float) (change * (shadowSize - i + 1));
            gShadow.setPaint(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), (int) MathUtils.clamp(opacity_, 0, 255)));
            
            gShadow.setStroke(new BasicStroke(i));
            gShadow.draw(shape);
        }
        
        gShadow.dispose();
    }

    protected void drawStrokes(Graphics2D g2, Shape shape, float x, float y, float width, float height) {
        if (strokeWidth <= 0) return;

        Graphics2D gStroke = (Graphics2D) g2.create();
        BoxUtils.setHighQuality(gStroke);
        gStroke.setPaint(StrokeColor);

        x -= margin[1];
        y -= margin[0];
        width += margin[3] + margin[1];
        height += margin[2] + margin[0];
        float cX = x + (width) / 2f;
        float cY = y + (height) / 2f;

        Area clipArea = new Area(new Rectangle2D.Float(x, y, width + 1f, height + 1f));

        if (!this.paintedStrokes[0]) clipArea.subtract(new Area(BoxUtils.createTriangle(x, y, x+width, y, cX, cY))); // Top
        if (!this.paintedStrokes[1]) clipArea.subtract(new Area(BoxUtils.createTriangle(x, y, x, y+height, cX, cY))); // Bottom
        if (!this.paintedStrokes[2]) clipArea.subtract(new Area(BoxUtils.createTriangle(x, y + height + 1, x + width + 1, y+height + 1, cX, cY))); // Left
        if (!this.paintedStrokes[3]) clipArea.subtract(new Area(BoxUtils.createTriangle(x+width + 1, y, x+width + 1, y+height + 1, cX, cY))); // Right

        gStroke.setClip(clipArea);
        gStroke.setStroke(new BasicStroke(strokeWidth));
        
        gStroke.draw(shape);

        gStroke.dispose();
    }

    

    @Override
    protected void paintComponent(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g.create();
        BoxUtils.setHighQuality(g2);
        
        
        
        if (scale != 1.0f || angle != 1.0f) {
            double pivotX = getWidth() * anchorX;
            double pivotY = getHeight() * anchorY;
            g2.translate(pivotX, pivotY);
            g2.scale(scale, scale);
            g2.rotate(Math.toRadians(this.angle));
            g2.translate(-pivotX, -pivotY);
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        
        
        Insets insets = (getBorder() != null) ? getBorder().getBorderInsets(this) : new Insets(0,0,0,0);
        
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        
        int x = insets.left;
        int y = insets.top;
        
        
        shapePath = new Path2D.Float();
        shapePath.moveTo(x + radios[0], y);
        
        shapePath.lineTo(x+ width - radios[1], y); // Top
        
        if (this.flatCorners[1]){
            shapePath.lineTo(x + width, y + radios[1]);
        }else {
            shapePath.quadTo(x + width, y, x + width, y + radios[1]); // TR
        }
        
        shapePath.lineTo(x + width, y + height - radios[3]); // Right
        
        if(this.flatCorners[3]){
            shapePath.lineTo(x + width- radios[3], y + height);
        } else {
            shapePath.quadTo(x + width, y + height, x + width - radios[3], y + height); // BR
        }
        
        shapePath.lineTo(x + radios[2], y + height); // Bottom
        
        if(this.flatCorners[2]){
            shapePath.lineTo(x, y + height - radios[2]);
        } else {
            shapePath.quadTo(x, y + height, x, y + height - radios[2]); // BL
        }
        
        shapePath.lineTo(x, y + radios[0]); // Left
        
        if(this.flatCorners[0]){
            shapePath.lineTo(x + radios[0], y);
        } else {
            shapePath.quadTo(x, y, x + radios[0], y); // TL
        }
        g2.translate(offsetX, offsetY);
        
        if(getParent() instanceof AuraBox){
            AuraBox<?> parent = (AuraBox<?>) getParent();
            g2.setClip(parent.getClipChild()? g2.getClip() : null);
        } else {
            g2.setClip(null);
        }
        
        Color[] colors = new Color[this.backgroundColors.size()];
        for(int i =0; i < this.backgroundColors.size(); i ++ ){
            colors[i] = this.backgroundColors.get(i);
        }
        
        float[] fractions = new float[this.backgroundFractions.size()];
        for(int i =0; i < this.backgroundFractions.size(); i ++ ){
            fractions[i] = this.backgroundFractions.get(i);
        }

        
        if (this.backgroundColors.size() > 1){
            float angleRad = (float) Math.toRadians(this.backgroundAngle);
            float cos = (float) Math.cos(angleRad);
            float sin = (float) Math.sin(angleRad);
            
            float length = Math.abs(width * cos) + Math.abs(height * sin);
            
            float centerX = x + width / 2f;
            float centerY = y + height / 2f;
            
            if(this.bgType){
                
                Paint background = new RadialGradientPaint(centerX, centerY, (float) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)), fractions, colors);
                g2.setPaint(background);
                
                
            } else {
                
                float startX = centerX - (length / 2f) * cos + backgroundOffsetX * width;
                float startY = centerY - (length / 2f) * sin + backgroundOffsetY * height;
                float endX = centerX + (length / 2f) * cos + backgroundOffsetX * width;
                float endY = centerY + (length / 2f) * sin + backgroundOffsetY * height;
                
                Paint background = new LinearGradientPaint(
                    startX, startY,
                    endX, endY,
                    fractions,
                    colors
                );
                
                g2.setPaint(background);
            }
            
        } else {
            g2.setColor(this.backgroundColors.get(0));
        }
        
        drawShadow(g2, shapePath);
        
        if (backgroundImage != null) {
            
            Graphics2D gImg = (Graphics2D) g2.create();
            BoxUtils.setHighQuality(gImg);
            
            gImg.setClip(shapePath);
            
            if(scaleBackground){
                gImg.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                gImg.drawImage(backgroundImage, 0, 0, this);
            }
            
        } else {
            g2.fill(shapePath);
        }
        
        drawStrokes(g2, shapePath, x, y, width, height);
        
        g2.dispose();
    }
    
    public T padding(int all){
        return padding(all, all, all, all);
    }
    
    public T padding(int top_bottom, int left_right){
        return padding(top_bottom, left_right, top_bottom, left_right);
    }

    public T padding(int top, int left, int bottom, int right){
        this.padding = new int[]{top, left, bottom, right};
        revalidate();
        repaint();
        return (T) this;
    }

    @Override
    public Insets getInsets() {
        Insets borderInsets = super.getInsets();
        return new Insets(
            borderInsets.top + padding[0],
            borderInsets.left + padding[1],
            borderInsets.bottom + padding[2],
            borderInsets.right + padding[3]
        );
    }

    public T margin(int all){
        return margin(all, all, all, all);
    }

    public T margin(int top_bottom, int left_right){
        return margin(top_bottom, left_right, top_bottom, left_right);
    }

    public T margin(int top, int left, int bottom, int right){
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        this.margin = new int[]{top, left, bottom, right};
        revalidate();
        repaint();
        return (T) this;
    }

    @Override
    protected void paintChildren(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();
        BoxUtils.setHighQuality(g2);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.translate(offsetX, offsetY);

        if(angle != 0.0f){
            double pivotX = getWidth() * anchorX;
            double pivotY = getHeight() * anchorY;
            g2.translate(pivotX, pivotY);
            g2.rotate(Math.toRadians(this.angle));
            g2.translate(-pivotX, -pivotY);
        }
        
        if (scale != 1.0f) {
            double pivotX = getWidth() / 2;
            double pivotY = getHeight() / 2;
            g2.translate(pivotX, pivotY);
            g2.scale(scale, scale);
            g2.translate(-pivotX, -pivotY);
        }

        
        if(shapePath != null && clipChildrens){
            g2.setClip(shapePath);
        }

        super.paintChildren(g2);
        
        g2.dispose();

    }

    public T animate(Transition<?> transition, boolean cancel){

        if(cancel){
            for(int i = 0; i < timers.size(); i ++){
                if(timersTypes.get(i) == transition.getAnimationType()){
                    timers.get(i).stop(true);
                    timers.remove(i);
                    timersTypes.remove(i);
                }
            }
        }

        this.timers.add(transition);
        this.timersTypes.add(transition.getAnimationType());
        return (T) this;
    }

    public T cancelAnimations(AnimationType type){

        for(int i = 0; i < timers.size(); i ++){
            if(type == null || timersTypes.get(i) == type){
                timers.get(i).stop(true);
                timers.remove(i);
                timersTypes.remove(i);
            }
        }

        return (T) this;
        
    }

    public float getShadowSize(){
        return this.shadowSize;
    }

    public Color getShadowColor(){
        return this.shadowColor;
    }

    public Shape getShape(){
        return this.shapePath;
    }

    public float getShadowOffsetX(){
        return this.shadowOffsetX;
    }

    public float getShadowOffsetY(){
        return this.shadowOffsetY;
    }

    public T cursor(Cursor cursor){
        this.setCursor(cursor);
        return (T) this;
    }

    public T size(int width, int height){

        int w = width + getMargin().left + getMargin().right;
        int h = height + getMargin().top + getMargin().bottom;

        this.setPreferredSize(new Dimension(w, h));
        this.setMinimumSize(new Dimension(w, h));
        this.setMaximumSize(new Dimension(w, h));

        repaint();
        return (T) this;
    }

    public T width(int width){
        return size(width, getHeight());
    }

    public T height(int height){
        return size(getWidth(), height);
    }

    private float widthPorc = -1;
    private float heightPorc = -1;

    public T widthPorc(float porc){
        this.widthPorc = porc;
        if (getParent() != null) getParent().revalidate();
        return (T) this;
    }

    public T heightPorc(float porc){
        this.heightPorc = porc;
        if (getParent() != null) getParent().revalidate();
        return (T) this;
    }

    public T fillParent(){
        return widthPorc(1).heightPorc(1);
    }

    public T fillWidth(){
        return widthPorc(1);
    }

    public T fillHeight(){
        return heightPorc(1);
    }

    public float getWidthPorc(){
        return this.widthPorc;
    }

    public float getHeightPorc(){
        return this.heightPorc;
    }

    private String id;

    public T id(String id){
        this.id = id;
        return (T) this;
    }

    public String getId(){
        return this.id;
    }

    public AuraBox<?> find(String id) {

        if (id.equals(this.id)) {
            return (AuraBox<?>) this;
        }

        for (Component child : getComponents()) {
            if (child instanceof AuraBox) {
                AuraBox<?> found = ((AuraBox<?>) child).find(id);
                if (found != null) return found;
            }
        }

        return null;
    }

    private AuraBox<?> info;
    private float[] infoIn;

    public T info(AuraBox<?> boxInfo, float sx, float sy){
        return info(boxInfo, sx, sy, 0, 0);
    }

    public T info(AuraBox<?> boxInfo){
        return info(boxInfo, 0, 1, 0, 0);
    }

    public T info(AuraBox<?> boxInfo, float sx, float sy, float tx, float ty){
        this.info = boxInfo;
        this.infoIn = new float[]{sx, sy, tx, ty};


        info.opacity(0);
        onHover((self, shover) -> {

            manageInfo(shover);

        });

        return (T) this;
    }

    private void manageInfo(boolean hover){

        AuraWindow window = BoxUtils.getAuraWindow(this);
                
        Point location = SwingUtilities.convertPoint(this, (int) (infoIn[0] * (this.getWidth() - getMargin().left - getMargin().right)), (int) (infoIn[1] * (this.getHeight() - getMargin().bottom - getMargin().top)), window.getLayeredPane());

        location.x += this.getMargin().left;
        location.y += this.getMargin().top;

        if(hover){

            info.setBounds(location.x - (int) (infoIn[2] * (info.getPreferredSize().width)), location.y - (int) (infoIn[3] * (info.getPreferredSize().height)), info.getPreferredSize().width, info.getPreferredSize().height);
            window.getLayeredPane().add(info, JLayeredPane.POPUP_LAYER);
            new AnimateOpacity(info, 1, 300).start();


        } else {
            
                new AnimateOpacity(info, 0, 300).then(() -> {
                    window.getLayeredPane().remove(info);
                    window.getLayeredPane().repaint();
                    info.opacity(0);
                }).start();

        }

        window.getLayeredPane().repaint();

    }

    public AuraBox<?> getInfo(){
        return this.info;
    }

}