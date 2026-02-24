package aura.components;

import java.awt.Image;
import javax.swing.ImageIcon;
import aura.core.AuraBox;

public class AuraImage extends AuraBox<AuraImage> {
    
    public AuraImage(String url){

        Image img = new ImageIcon(url).getImage();
        this.background(img);
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
