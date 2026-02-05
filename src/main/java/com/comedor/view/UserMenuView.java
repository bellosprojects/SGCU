package com.comedor.view;

import com.comedor.model.*;
import com.comedor.model.Menu;
import com.comedor.view.components.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class UserMenuView extends JFrame {
    
    JButton reservarDesayunoButton;
    JButton reservarAlmuerzoButton;
    JButton enviarOpinionButton;
    JButton gestionarPerfilButton;
    JButton confirmarButton;
    JButton salirButton;

    public UserMenuView(){
        setTitle("Menu de Usuario - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100).getImage());
        setLayout(new BorderLayout());

        JPanel mainPanel = new ImagePanel(cargarIcono("/images/comedor.png", 1920, 1080).getImage());
        mainPanel.setLayout(new BorderLayout());


        add(mainPanel, BorderLayout.CENTER);
    }

    public void setDesayuno(Menu menu){

    }

    public void setAlmuerzo(Menu menu){

    }

    public void setUser(User user){

    }

    public JButton getReservarDesayunoButton(){
        return this.reservarDesayunoButton;
    }

    public JButton getReservarAlmuerzoButton(){
        return this.reservarAlmuerzoButton;
    }

    public JButton getEnviarOpinionButton(){
        return this.enviarOpinionButton;
    }

    public JButton getGestionarPerfilButton(){
        return this.gestionarPerfilButton;
    }

    public JButton getConfirmarButton(){
        return this.confirmarButton;
    }

    public JButton getSalirButton(){
        return this.salirButton;
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        java.net.URL imgURL = getClass().getResource(ruta);
        if(imgURL != null){
            ImageIcon icon = new ImageIcon(imgURL);

            Image img = icon.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
            return new ImageIcon(img);

        } else {
            System.err.println("No se pudo cargar el icono: " + ruta);
            return null;
        }
    }

}