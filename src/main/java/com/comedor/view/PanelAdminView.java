package com.comedor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.comedor.view.components.GradientPanelRedondeado;
import com.comedor.view.components.SuperBoton;

public class PanelAdminView extends JFrame {
    
    private SuperBoton volverButton;
    private SuperBoton gestionarCCBButton;
    private SuperBoton gestionarMenuButton;

    private SuperBoton guardarPorcentajeButton;
    private JTextField porcentajeInput;
    private JComboBox <String> rolSelect;

    public PanelAdminView(){

        setTitle("Menu de Usuario - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100).getImage());
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

        JLabel icoLabel = new JLabel(cargarIcono("/images/logoColor.png", 130, 130));
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


        JLabel adminNombreLabel = new JLabel("Admin");
        adminNombreLabel.setFont(EstiloGral.MIDDLE_FONT);
        adminNombreLabel.setForeground(EstiloGral.DARK_COLOR);
        adminNombreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        headerPanel.add(adminNombreLabel, headerGbc);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        containerPanel.setOpaque(false);

        JPanel centerPanel = new GradientPanelRedondeado(20, 40, EstiloGral.BG_COLOR);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints centerPanelGbc = new GridBagConstraints();
        centerPanelGbc.gridx = 0;
        centerPanelGbc.gridy = 0;
        centerPanelGbc.insets = new Insets(20, 50, 20, 50);
        centerPanelGbc.anchor = GridBagConstraints.NORTH;

        JLabel gestionarPorcentajeLabel = new JLabel("Gestionar Porcentajes");
        gestionarPorcentajeLabel.setForeground(EstiloGral.DARK_COLOR);
        gestionarPorcentajeLabel.setFont(EstiloGral.MIDDLE_FONT);

        centerPanel.add(gestionarPorcentajeLabel, centerPanelGbc);
        centerPanelGbc.gridy++;

        JPanel porcentajePanel = new GradientPanelRedondeado(10, 0, EstiloGral.BLACK_TRANSP_COLOR);
        porcentajeInput = new JTextField(15);
        porcentajeInput.setFont(EstiloGral.INPUT_FONT);
        porcentajeInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        porcentajeInput.setOpaque(false);
        porcentajeInput.setForeground(EstiloGral.BG_COLOR);
        porcentajePanel.add(porcentajeInput);
        centerPanel.add(porcentajePanel, centerPanelGbc);
        centerPanelGbc.gridy++;

        JPanel selectRolPanel = new GradientPanelRedondeado(10, 0, EstiloGral.BLACK_TRANSP_COLOR);
        String[] roles = {"Estudiante","Profesor","Trabajador"};
        rolSelect = new JComboBox<>(roles);
        rolSelect.setFont(EstiloGral.INPUT_FONT);
        rolSelect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rolSelect.setOpaque(false);
        rolSelect.setFocusable(true);
        rolSelect.setBackground(EstiloGral.BG_COLOR);
        rolSelect.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        rolSelect.setForeground(EstiloGral.DARK_COLOR);
        selectRolPanel.add(rolSelect);
        centerPanel.add(selectRolPanel, centerPanelGbc);
        centerPanelGbc.gridy++;

        guardarPorcentajeButton = new SuperBoton("GUARDAR", EstiloGral.BUTTON_COLOR, 20, 10);
        guardarPorcentajeButton.setForeground(EstiloGral.BG_COLOR);
        guardarPorcentajeButton.setFont(EstiloGral.INPUT_FONT);
        centerPanel.add(guardarPorcentajeButton, centerPanelGbc);

        containerPanel.add(centerPanel);
        mainPanel.add(containerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new GridBagLayout());
        footerPanel.setOpaque(false);
        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.gridx = 0;
        footerGbc.gridy = 0;
        footerGbc.insets = new Insets(0, 40, 40, 40);
        footerGbc.anchor = GridBagConstraints.SOUTH;

        volverButton = new SuperBoton("VOLVER", EstiloGral.GREY_COLOR, 10, 20);
        volverButton.setFont(EstiloGral.INPUT_FONT);
        volverButton.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(volverButton, footerGbc);

        footerGbc.gridx++;
        gestionarCCBButton = new SuperBoton("GESTIONAR CCB", EstiloGral.DARK_COLOR, 10, 20);
        gestionarCCBButton.setFont(EstiloGral.INPUT_FONT);
        gestionarCCBButton.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(gestionarCCBButton, footerGbc);

        footerGbc.gridx++;
        gestionarMenuButton = new SuperBoton("GESTIONAR MENU", EstiloGral.BUTTON_COLOR, 10, 20);
        gestionarMenuButton.setFont(EstiloGral.INPUT_FONT);
        gestionarMenuButton.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(gestionarMenuButton, footerGbc);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

    }

    public SuperBoton getVolverButton(){
        return volverButton;
    }

    public SuperBoton getCCBButton(){
        return gestionarCCBButton;
    }

    public SuperBoton getMenuButton(){
        return gestionarMenuButton;
    }

    public SuperBoton getGuardarButton(){
        return guardarPorcentajeButton;
    }

    public String getRolSelect(){
        return rolSelect.getItemAt(rolSelect.getSelectedIndex());
    }

    public String getPorcentaje(){
        return porcentajeInput.getText();
    }

    public JTextField porcentajeComponent(){
        return porcentajeInput;
    }

    public void InvalidateInputs(Component input){

        GradientPanelRedondeado target = (input.getParent() instanceof GradientPanelRedondeado) ? (GradientPanelRedondeado) input.getParent() : null;

        if(target == null){
            return;
        }

        target.cancelTimers();

        Timer shaTimer = shakeComponent(target);
    
        Color colorError = EstiloGral.ERROR_COLOR;
        Color colorOriginal = target.getColor2();

        target.setColor1(colorError);

        Timer timer = new Timer(15, null);
        
        timer.addActionListener(new ActionListener() {
            float ratio = 0.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ratio += 0.02f; 
                
                if (ratio >= 1.0f) {
                    target.setColor1(colorOriginal);
                    timer.stop();
                } else {
                    
                    int r = (int) (colorError.getRed() + (colorOriginal.getRed() - colorError.getRed()) * ratio);
                    int g = (int) (colorError.getGreen() + (colorOriginal.getGreen() - colorError.getGreen()) * ratio);
                    int b = (int) (colorError.getBlue() + (colorOriginal.getBlue() - colorError.getBlue()) * ratio);
                    int a = (int) (colorError.getAlpha() + (colorOriginal.getAlpha() - colorError.getAlpha()) * ratio);
                    
                    target.setColor1(new Color(r, g, b, a));
                }
            }
        });

        timer.setInitialDelay(200); 
        timer.start();

        target.setTimers(timer, shaTimer);
    }

    public Timer shakeComponent(Component c) {
        Point originalLoc = c.getLocation();
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            int count = 0;
            int delta = 2; // Cuánto se mueve a los lados

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= 10) { // 10 movimientos
                    c.setLocation(originalLoc);
                    timer.stop();
                } else {
                    // Alterna entre sumar y restar a la X
                    int newX = (count % 2 == 0) ? originalLoc.x + delta : originalLoc.x - delta;
                    c.setLocation(newX, originalLoc.y);
                    count++;
                }
            }
        });
        timer.start();
        return timer;
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

