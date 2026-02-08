package com.comedor.view;

import javax.swing.*;

import com.comedor.view.components.GradientPanelRedondeado;
import com.comedor.view.components.ImagePanel;
import com.comedor.view.components.SuperBoton;

import java.awt.*;
import java.awt.event.*;

public class GestionarCCBView extends JFrame {
    
    private JTextField costosFijosInput;
    private JTextField costosVariablesInput;
    private JTextField cantidadBandejas;
    private JTextField procentajeMerma;
    private SuperBoton btnGuardar;
    private SuperBoton btnLimpiarFormulario;
    private SuperBoton btnVolver;

    private JLabel CCBActual;

    public GestionarCCBView(){
        setTitle("Gestionar CCB - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100).getImage());
        setLayout(new BorderLayout());

        JPanel mainPanel = new ImagePanel(cargarIcono("/images/comedor.png", 1920, 1080).getImage());
        mainPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerGbc.insets = new Insets(0, 40, 20, 20);
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("SGCU - Gestionar CCB"); 
        titleLabel.setFont(EstiloGral.TITLE_FONT);
        titleLabel.setForeground(EstiloGral.BG_COLOR);

        JLabel logoIcon = new JLabel(cargarIcono("/images/logoWhite.png", 200, 200));

        header.add(logoIcon, headerGbc);
        headerGbc.gridx++;
        header.add(titleLabel, headerGbc);
        
        headerGbc.gridx++;
        headerGbc.weightx = 1.0;
        header.add(Box.createHorizontalGlue(), headerGbc);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints formPanelGbc = new GridBagConstraints();
        formPanelGbc.gridx = 0;
        formPanelGbc.gridy = 0;
        formPanelGbc.insets = new Insets(5, 40, 0, 10);
        formPanelGbc.anchor = GridBagConstraints.WEST;

        JLabel costosFijosLabel = new JLabel("COSTOS FIJOS TOTALES (Bs)");
        costosFijosLabel.setFont(EstiloGral.LABEL_FONT);
        costosFijosLabel.setForeground(EstiloGral.BG_COLOR);
        costosFijosLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        formPanel.add(costosFijosLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel costosFijosPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        costosFijosInput = new JTextField(22);
        costosFijosInput.setFont(EstiloGral.INPUT_FONT);
        costosFijosInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        costosFijosInput.setOpaque(false);
        costosFijosPanel.add(costosFijosInput);
        formPanel.add(costosFijosPanel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JLabel costosVariablesLabel = new JLabel("COSTOS VARIABLES (BS)");
        costosVariablesLabel.setFont(EstiloGral.LABEL_FONT);
        costosVariablesLabel.setForeground(EstiloGral.BG_COLOR);
        costosVariablesLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(costosVariablesLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel costosVariablesPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        costosVariablesInput = new JTextField(22);
        costosVariablesInput.setFont(EstiloGral.INPUT_FONT);
        costosVariablesInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        costosVariablesInput.setOpaque(false);
        costosVariablesPanel.add(costosVariablesInput);
        formPanel.add(costosVariablesPanel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JLabel cantidadBandejasLabel = new JLabel("CANTIDAD DE BANDEJAS");
        cantidadBandejasLabel.setFont(EstiloGral.LABEL_FONT);
        cantidadBandejasLabel.setForeground(EstiloGral.BG_COLOR);
        cantidadBandejasLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(cantidadBandejasLabel, formPanelGbc);

        formPanelGbc.gridy++;
        JPanel cantidadBandejasPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        cantidadBandejas = new JTextField(22);
        cantidadBandejas.setFont(EstiloGral.INPUT_FONT);
        cantidadBandejas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cantidadBandejas.setOpaque(false);
        cantidadBandejasPanel.add(cantidadBandejas);
        formPanel.add(cantidadBandejasPanel, formPanelGbc);

        formPanelGbc.gridy++;
        JLabel mermaLabel = new JLabel("PORCENTAJE MERMA (%)");
        mermaLabel.setFont(EstiloGral.LABEL_FONT);
        mermaLabel.setForeground(EstiloGral.BG_COLOR);
        mermaLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(mermaLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel mermaPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        procentajeMerma = new JTextField(22);
        procentajeMerma.setFont(EstiloGral.INPUT_FONT);
        procentajeMerma.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        procentajeMerma.setOpaque(false);
        mermaPanel.add(procentajeMerma);
        formPanel.add(mermaPanel, formPanelGbc);

        formPanelGbc.gridy = 0;
        formPanelGbc.gridx++;
        CCBActual = new JLabel("CCB Actual: 0.0Bs.");
        CCBActual.setFont(EstiloGral.MIDDLE_FONT);
        CCBActual.setForeground(EstiloGral.BG_COLOR);
        CCBActual.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        formPanel.add(CCBActual, formPanelGbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel(new GridBagLayout());
        footerPanel.setOpaque(false);
        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.gridx = 0;
        footerGbc.gridy = 0;
        footerGbc.insets = new Insets(0, 40, 40, 40);
        footerGbc.anchor = GridBagConstraints.SOUTH;

        btnVolver = new SuperBoton("VOLVER", EstiloGral.GREY_COLOR, 10, 20);
        btnVolver.setFont(EstiloGral.INPUT_FONT);
        btnVolver.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(btnVolver, footerGbc);

        footerGbc.gridx++;
        btnLimpiarFormulario = new SuperBoton("LIMPIAR", EstiloGral.DARK_COLOR, 10, 20);
        btnLimpiarFormulario.setFont(EstiloGral.INPUT_FONT);
        btnLimpiarFormulario.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(btnLimpiarFormulario, footerGbc);

        footerGbc.gridx++;
        btnGuardar = new SuperBoton("GUARDAR", EstiloGral.BUTTON_COLOR, 10, 20);
        btnGuardar.setFont(EstiloGral.INPUT_FONT);
        btnGuardar.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(btnGuardar, footerGbc);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public SuperBoton getBackButton() {
        return btnVolver;
    }

    public SuperBoton getBtnLimpiarFormulario() {
        return btnLimpiarFormulario;
    }

    public SuperBoton getGuardarButton() {
        return btnGuardar;
    }

    public String getCostosFijos() {
        return costosFijosInput.getText();
    }

    public String getCostosVariables() {
        return costosVariablesInput.getText();
    }

    public String getCantidadBandejas() {
        return cantidadBandejas.getText();
    }

    public String getPorcentajeMerma() {
        return procentajeMerma.getText();
    }

    public JTextField getCostosFijosComponent(){
        return costosFijosInput;
    }

    public JTextField getCostosVariablesComponent(){
        return costosVariablesInput;
    }

    public JTextField getCantidadBAndejasComponent(){
        return cantidadBandejas;
    }

    public JTextField getPorcentajeMermaComponent(){
        return procentajeMerma;
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

    public void setCCB(double CCB){
        CCBActual.setText("CCB Actual: " + CCB + "Bs.");
    }

    public void limpiarFormulario(){
        costosFijosInput.setText("");
        costosVariablesInput.setText("");
        procentajeMerma.setText("");
        cantidadBandejas.setText("");
    }

}
