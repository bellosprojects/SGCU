package com.comedor.view;

import com.comedor.model.*;
import com.comedor.model.Menu;
import com.comedor.view.components.GradientPanelRedondeado;
import com.comedor.view.components.ImageCircular;
import com.comedor.view.components.Separator;
import com.comedor.view.components.SuperBoton;
import com.comedor.view.components.TextMultilinea;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UserMenuView extends JFrame {
    
    private SuperBoton reservarDesayunoButton;
    private SuperBoton reservarAlmuerzoButton;
    private SuperBoton enviarOpinionButton;
    private SuperBoton gestionarPerfilButton;
    private SuperBoton confirmarButton;
    private SuperBoton salirButton;
    private ImageCircular profileImage;

    private JLabel nombre;
    private JLabel cedula;
    private JLabel saldo;

    private JLabel menuFecha;
    private JLabel precio;
    private JLabel desayunoTitulo;
    private JLabel almuerzoTitulo;
    private TextMultilinea desayunoIngredients;
    private TextMultilinea almuerzoIngredients;

    public UserMenuView(){
        setTitle("Menu de Usuario - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100, false).getImage());
        setLayout(new BorderLayout());

        GradientPanelRedondeado mainPanel = new GradientPanelRedondeado(0, 0, EstiloGral.LIGHT_COLOR, EstiloGral.GREY_COLOR, GradientPanelRedondeado.VERTICAL);
        mainPanel.setLayout(new BorderLayout());

        GradientPanelRedondeado headerPanel = new GradientPanelRedondeado(20, 40, EstiloGral.BG_COLOR);
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerGbc.insets = new Insets(0, 0, 0, 40);

        JLabel icoLabel = new JLabel(cargarIcono("/images/logoColor.png", 130, 130, false));
        icoLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        headerPanel.add(icoLabel, headerGbc);

        headerGbc.gridx++;
        JLabel titleLabel = new JLabel("SGCU");
        titleLabel.setForeground(EstiloGral.DARK_COLOR);
        titleLabel.setFont(EstiloGral.TITLE_FONT);
        headerPanel.add(titleLabel, headerGbc);

        headerGbc.gridx++;
        headerGbc.weightx = 1.0;
        headerPanel.add(Box.createHorizontalGlue(), headerGbc);
        
        headerGbc.gridx++;
        headerGbc.weightx = 0;

        JPanel userDataPanel = new JPanel(new BorderLayout());
        userDataPanel.setOpaque(false);

        nombre = new JLabel("nombre apellido");
        nombre.setFont(EstiloGral.SMALL_BOLD_FONT);
        nombre.setForeground(EstiloGral.DARK_COLOR);
        nombre.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        cedula = new JLabel("V-00000000");
        cedula.setFont(EstiloGral.SMALL_FONT);
        cedula.setForeground(EstiloGral.DARK_COLOR);

        userDataPanel.add(nombre, BorderLayout.NORTH);
        userDataPanel.add(cedula, BorderLayout.SOUTH);

        headerPanel.add(userDataPanel, headerGbc);

        headerGbc.gridx++;

        profileImage = new ImageCircular();
        profileImage.setIcon(cargarIcono("/images/logoColor.png", 200, 200, false));
        profileImage.setBorderSize(10);
        profileImage.setBorderColor(EstiloGral.LIGHT_COLOR);

        headerPanel.add(profileImage, headerGbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JPanel menusPanel = new JPanel(new BorderLayout());
        menusPanel.setOpaque(false);

        JPanel generalInfoPanel = new JPanel(new GridLayout(2, 1));
        generalInfoPanel.setOpaque(false);

        menuFecha = new JLabel("Menu del dia: Jueves 5 de Febrero");
        menuFecha.setFont(EstiloGral.MIDDLE_FONT);
        menuFecha.setForeground(EstiloGral.DARK_COLOR);
        menuFecha.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 0));

        precio = new JLabel("Precio: 0.0Bs.");
        precio.setFont(EstiloGral.INPUT_FONT);
        precio.setForeground(EstiloGral.DARK_COLOR);
        precio.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 0));

        generalInfoPanel.add(menuFecha);
        generalInfoPanel.add(precio);
        menusPanel.add(generalInfoPanel, BorderLayout.NORTH);

        JPanel menusPanel2 = new JPanel(new GridBagLayout());
        menusPanel2.setOpaque(false);
        GridBagConstraints menusPanel2Gbc = new GridBagConstraints();
        menusPanel2Gbc.gridx = 0;
        menusPanel2Gbc.gridy = 0;
        menusPanel2Gbc.anchor = GridBagConstraints.NORTH;

        GradientPanelRedondeado panelDesayuno = new GradientPanelRedondeado(20, 40, EstiloGral.BG_COLOR);
        panelDesayuno.setLayout(new GridBagLayout());
        GridBagConstraints panelDesayunoGbc = new GridBagConstraints();
        panelDesayunoGbc.gridx = 0;
        panelDesayunoGbc.gridy = 0;
        panelDesayunoGbc.weighty = 1;
        panelDesayunoGbc.anchor = GridBagConstraints.NORTH;
        panelDesayunoGbc.insets = new Insets(40, 0, 0, 0);

        ImageCircular solImage = new ImageCircular();
        solImage.setIcon(cargarIcono("/images/sol.png", 100, 100, false));
        solImage.setBorderColor(EstiloGral.LIGHT_COLOR);

        panelDesayuno.add(solImage, panelDesayunoGbc);

        panelDesayunoGbc.gridy++;
        desayunoTitulo = new JLabel("Desayuno");
        desayunoTitulo.setFont(EstiloGral.MIDDLE_FONT);
        desayunoTitulo.setForeground(EstiloGral.DARK_COLOR);
        desayunoTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelDesayuno.add(desayunoTitulo, panelDesayunoGbc);

        panelDesayunoGbc.gridy++;
        desayunoIngredients = new TextMultilinea("\n\n\n", 500, EstiloGral.INPUT_FONT);

        desayunoIngredients.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        desayunoIngredients.setForeground(EstiloGral.DARK_COLOR);
        panelDesayuno.add(desayunoIngredients, panelDesayunoGbc);

        panelDesayunoGbc.gridy++;
        reservarDesayunoButton = new SuperBoton("Reservar", EstiloGral.BUTTON_COLOR, 20, 20);
        reservarDesayunoButton.setFont(EstiloGral.INPUT_FONT);
        panelDesayuno.add(reservarDesayunoButton, panelDesayunoGbc);

        panelDesayunoGbc.gridy++;
        panelDesayuno.add(new Separator(0, 35), panelDesayunoGbc);

        menusPanel2.add(panelDesayuno, menusPanel2Gbc);
        menusPanel2Gbc.gridx++;

        GradientPanelRedondeado panelAlmuerzo = new GradientPanelRedondeado(20, 40, EstiloGral.BG_COLOR);
        panelAlmuerzo.setLayout(new GridBagLayout());
        GridBagConstraints panelAlmuerzoGbc = new GridBagConstraints();
        panelAlmuerzoGbc.gridx = 0;
        panelAlmuerzoGbc.gridy = 0;
        panelAlmuerzoGbc.weighty = 1;
        panelAlmuerzoGbc.anchor = GridBagConstraints.NORTH;
        panelAlmuerzoGbc.insets = new Insets(40, 0, 0, 0);

        ImageCircular cubiertosImage = new ImageCircular();
        cubiertosImage.setIcon(cargarIcono("/images/cubiertos.png", 100, 100, false));
        cubiertosImage.setBorderColor(EstiloGral.LIGHT_COLOR);

        panelAlmuerzo.add(cubiertosImage, panelAlmuerzoGbc);

        panelAlmuerzoGbc.gridy++;
        almuerzoTitulo = new JLabel("Almuerzo");
        almuerzoTitulo.setFont(EstiloGral.MIDDLE_FONT);
        almuerzoTitulo.setForeground(EstiloGral.DARK_COLOR);
        almuerzoTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelAlmuerzo.add(almuerzoTitulo, panelAlmuerzoGbc);

        panelAlmuerzoGbc.gridy++;
        almuerzoIngredients = new TextMultilinea("\n\n\n", 500, EstiloGral.INPUT_FONT);

        almuerzoIngredients.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        almuerzoIngredients.setForeground(EstiloGral.DARK_COLOR);
        panelAlmuerzo.add(almuerzoIngredients, panelAlmuerzoGbc);

        panelAlmuerzoGbc.gridy++;
        reservarAlmuerzoButton = new SuperBoton("Reservar", EstiloGral.BUTTON_COLOR, 20, 20);
        reservarAlmuerzoButton.setFont(EstiloGral.INPUT_FONT);
        panelAlmuerzo.add(reservarAlmuerzoButton, panelAlmuerzoGbc);

        panelAlmuerzoGbc.gridy++;
        panelAlmuerzo.add(new Separator(0, 35), panelAlmuerzoGbc);

        menusPanel2.add(panelAlmuerzo, menusPanel2Gbc);

        menusPanel.add(menusPanel2, BorderLayout.CENTER);

        centerPanel.add(menusPanel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setOpaque(false);

        GridBagConstraints optionsPanelGbc = new GridBagConstraints();
        optionsPanelGbc.gridx = 0;
        optionsPanelGbc.gridy = 0;
        optionsPanelGbc.anchor = GridBagConstraints.CENTER;
        optionsPanelGbc.insets = new Insets(0, 20, 0, 80);

        enviarOpinionButton = new SuperBoton("Enviar opinion", EstiloGral.BUTTON_COLOR, 15, 10);
        enviarOpinionButton.setFont(EstiloGral.INPUT_FONT);
        enviarOpinionButton.setForeground(EstiloGral.BG_COLOR);

        optionsPanel.add(enviarOpinionButton, optionsPanelGbc);

        optionsPanelGbc.gridy++;
        GradientPanelRedondeado extraPanel = new GradientPanelRedondeado(20, 20, EstiloGral.BG_COLOR);
        JPanel saldoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        saldoPanel.setOpaque(false);

        JLabel saldoDisponibleLabel = new JLabel("Saldo disponible: ");
        saldoDisponibleLabel.setFont(EstiloGral.LABEL_FONT);
        saldoDisponibleLabel.setForeground(EstiloGral.LABEL_COLOR);

        saldo = new JLabel("0.0Bs.");
        saldo.setFont(EstiloGral.MIDDLE_FONT);
        saldo.setForeground(EstiloGral.DARK_COLOR);

        saldoPanel.add(saldoDisponibleLabel);
        saldoPanel.add(saldo);

        extraPanel.add(saldoPanel);

        optionsPanel.add(extraPanel, optionsPanelGbc);

        optionsPanelGbc.gridy++;
        salirButton = new SuperBoton("VOLVER", EstiloGral.BUTTON_COLOR, 20, 10);
        salirButton.setFont(EstiloGral.INPUT_FONT);
        salirButton.setForeground(EstiloGral.BG_COLOR);

        optionsPanel.add(salirButton, optionsPanelGbc);

        centerPanel.add(optionsPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    public void setDesayuno(Menu menu){

        this.menuFecha.setText(menu.getFecha());
        this.desayunoTitulo.setText(menu.getPlato());
        this.desayunoIngredients.setText(menu.getIngredientes());

    }

    public void setAlmuerzo(Menu menu){

        this.menuFecha.setText(menu.getFecha());
        this.almuerzoTitulo.setText(menu.getPlato());
        this.almuerzoIngredients.setText(menu.getIngredientes());

    }

    public void setUser(User user){

        this.nombre.setText(user.getNombres());
        this.cedula.setText("V-" + user.getCedula());
        this.saldo.setText(user.getSaldo() + "Bs.");

        this.profileImage.setIcon(cargarIcono("C:/SGCU/imagenes/" + user.getCedula() + ".jpg", 100, 100, true));
        this.profileImage.revalidate();
        this.profileImage.repaint();
    }

    public SuperBoton getReservarDesayunoButton(){
        return this.reservarDesayunoButton;
    }

    public SuperBoton getReservarAlmuerzoButton(){
        return this.reservarAlmuerzoButton;
    }

    public SuperBoton getEnviarOpinionButton(){
        return this.enviarOpinionButton;
    }

    public SuperBoton getGestionarPerfilButton(){
        return this.gestionarPerfilButton;
    }

    public SuperBoton getConfirmarButton(){
        return this.confirmarButton;
    }

    public SuperBoton getSalirButton(){
        return this.salirButton;
    }

    public void setPrecio(double precio){
        this.precio.setText("Precio: " + precio + "Bs.");
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto, boolean isAbsolute) {
        if(!isAbsolute){
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
        else{
            File file = new File(ruta);
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
    }

}