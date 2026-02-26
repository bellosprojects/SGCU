package aura.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import aura.animations.AnimateInteger;
import aura.layouts.AuraColumn;
import aura.utils.BoxUtils;

public class AuraSelect extends AuraText {

    private final List<String> options;
    private AuraColumn popup;
    private Color textColor;

    public AuraSelect(String... options) {
        super(options[0]);

        this.options = Arrays.asList(options);
        
        this.radius(10).padding(10, 15).background(Color.white);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        onClick(self -> togglePopup());

    }


    private void togglePopup() {

        AuraWindow window = BoxUtils.getAuraWindow(this);
        
        Point location = SwingUtilities.convertPoint(this, 0, getHeight(), window.getLayeredPane());

        if (popup == null) {
            popup = new AuraColumn()
                .background(getBackgroundColor())
                .radius(10)
                .clipChildrens(true)
                .content(col -> {
                    
                    for(String opt : options){
                        
                        col.insert(
                        new AuraText(opt)
                            .textColor(textColor)
                            .fillWidth()
                            .clipChildrens(true)
                            .padding(4)
                            .font(getFont())
                            .cursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
                            .onClick(self -> {
                                text(opt);
                                togglePopup();
                            })
                            .onHover((self, hover) -> {
                                self.background(hover ? getBackgroundColor().darker() : getBackgroundColor());
                            })
                        );
                        
                    }
                    
                });

        
            popup.setBounds(location.x, location.y + 2, popup.getPreferredSize().width, 0);

            window.getLayeredPane().add(popup, JLayeredPane.POPUP_LAYER);

            int width = (int) Math.max(popup.getPreferredSize().width, this.getWidth());
            new AnimateInteger(0, popup.getPreferredSize().height, 250, value -> {
                popup.setBounds(location.x, location.y + 2, width, value);
                popup.revalidate();
            }).start();
        } else {
            int width = (int) Math.max(popup.getPreferredSize().width, this.getWidth());
            new AnimateInteger(popup.getPreferredSize().height, 0, 250, value -> {

                if(popup != null){
                    popup.setBounds(location.x, location.y + 2, width, value);
                    window.getLayeredPane().repaint();
                }
            })
            .then(() -> {
                window.getLayeredPane().remove(popup);
                popup = null;
                window.getLayeredPane().repaint();
            })
            .start();
        }
        window.getLayeredPane().repaint();
    }
    
}
