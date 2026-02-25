package aura.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import aura.animations.AnimateOpacity;
import aura.core.AuraBox;

public class AuraWindow extends JFrame {

    private final AuraContainer main;

    public AuraWindow(String title){

        main = new AuraContainer();

        setTitle(title);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(main);
    }

    public AuraWindow center(){
        setLocationRelativeTo(null);
        return this;
    }

    public AuraWindow size(int width, int height){
        setSize(width, height);
        return this;
    }

    public AuraWindow display(){
        setVisible(true);
        return this;
    }

    public AuraWindow fullScreen(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        return this;
    }

    public AuraWindow noResizable(){
        setResizable(false);
        return this;
    }

    public AuraWindow background(Color color){
        main.background(color);
        return this;
    }

    public AuraWindow background(Image img){
        main.background(img);
        return this;
    }

    public AuraWindow background(AuraImage img){
        main.background(img);
        return this;
    }

    public AuraWindow padding(int all){
        main.padding(all);
        return this;
    }

    public AuraWindow padding(int top_bottom, int left_right){
        main.padding(top_bottom, left_right);
        return this;
    }

    public AuraWindow padding(int top, int left, int bottom, int right){
        main.padding(top, left, bottom, right);
        return this;
    }

    public AuraWindow content(Consumer<AuraWindow> content){
        content.accept(this);
        return this;
    }

    public AuraWindow icon(Image ico){
        this.setIconImage(ico);
        return this;
    }

    public AuraWindow icon(AuraImage ico){
        this.setIconImage(ico.getImage());
        return this;
    }

    public void insert(Component c){
        this.main.insert(c, 0, 0);
    }

    public void insert(Component c, int x, int y){
        this.main.insert(c, x, y);
    }

    public void showToast(String message, Color bg, int duration) {
        AuraToast toast = new AuraToast(message, bg);
        
        int x = (this.getWidth() - toast.getPreferredSize().width) / 2;
        int y = this.getHeight() - 120; 
        
        toast.setBounds(x, y, toast.getPreferredSize().width, toast.getPreferredSize().height);
        
        this.getLayeredPane().add(toast, JLayeredPane.POPUP_LAYER);
        
        new AnimateOpacity(toast, 1, 200)
            .then(() -> {
                toast.animateOut(duration + 100);
            })
            .start();
    }

    public AuraBox<?> find(String id){

        return main.find(id);

    }

    public List<AuraBox<?>> findAll(String id) {
        return main.findAll(id);
    }
}
