package aura.components;

import java.awt.Image;
import javax.swing.ImageIcon;
import aura.core.AuraBox;

public class AuraImage extends AuraBox<AuraImage> {
    
    public AuraImage(String url){
        try {
            Image img;
            if(url.startsWith("file:") || url.startsWith("jar:") || url.startsWith("http:") || url.startsWith("https:")){
                img = new ImageIcon(new java.net.URL(url)).getImage();
            } else {
                img = new ImageIcon(url).getImage();
            }
            this.background(img);
        } catch (Exception e) {
            Image img = new ImageIcon(url).getImage();
            this.background(img);
        }
        setOpaque(false);
        this.size(100, 100);
    }

    public AuraImage(Image img){

        this.background(img);
        setOpaque(false);
        this.size(100, 100);
        
    }

    public Image getImage(){
        return this.backgroundImage;
    }

}
