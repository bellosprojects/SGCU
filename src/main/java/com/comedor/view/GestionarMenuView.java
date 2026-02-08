package com.comedor.view;

import javax.swing.*;

import com.comedor.view.components.GradientPanelRedondeado;
import com.comedor.view.components.ImagePanel;
import com.comedor.view.components.SuperBoton;

import java.awt.*;
import java.awt.event.*;

public class GestionarMenuView extends JFrame {
    
    private JTextField fechaInput;
    private JTextField platoInput;
    private JTextArea ingredientesInput;
    private JComboBox <String> tipoComida;
    private SuperBoton btnPublicar;
    private SuperBoton btnLimpiarFormulario;
    private SuperBoton btnVolver;

    public GestionarMenuView(){
        setTitle("Gestionar Menu - SGCU");
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

        JLabel titleLabel = new JLabel("SGCU - Gestionar Menu"); 
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

        JLabel fechaLabel = new JLabel("FECHA");
        fechaLabel.setFont(EstiloGral.LABEL_FONT);
        fechaLabel.setForeground(EstiloGral.BG_COLOR);
        fechaLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        formPanel.add(fechaLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel fechaInputPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        fechaInput = new JTextField(30);
        fechaInput.setFont(EstiloGral.INPUT_FONT);
        fechaInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fechaInput.setOpaque(false);
        fechaInputPanel.add(fechaInput);
        formPanel.add(fechaInputPanel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JLabel platoLabel = new JLabel("PLATO");
        platoLabel.setFont(EstiloGral.LABEL_FONT);
        platoLabel.setForeground(EstiloGral.BG_COLOR);
        platoLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(platoLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel platoInputPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        platoInput = new JTextField(30);
        platoInput.setFont(EstiloGral.INPUT_FONT);
        platoInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        platoInput.setOpaque(false);
        platoInputPanel.add(platoInput);
        formPanel.add(platoInputPanel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JLabel ingredientesLabel = new JLabel("INGREDIENTES");
        ingredientesLabel.setFont(EstiloGral.LABEL_FONT);
        ingredientesLabel.setForeground(EstiloGral.BG_COLOR);
        ingredientesLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(ingredientesLabel, formPanelGbc);

        formPanelGbc.gridy++;
        JPanel ingredientesInputPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        ingredientesInput = new JTextArea();
        ingredientesInput.setBackground(EstiloGral.TRANSPARENT_COLOR);
        ingredientesInput.setFont(EstiloGral.INPUT_FONT);
        ingredientesInput.setOpaque(false);
        ingredientesInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ingredientesInput.setColumns(30);
        ingredientesInput.setRows(3);
        ingredientesInput.setPreferredSize(new Dimension(3, 30));
        ingredientesInput.setLineWrap(true);
        ingredientesInput.setWrapStyleWord(true);
        ingredientesInputPanel.add(ingredientesInput);
        formPanel.add(ingredientesInputPanel, formPanelGbc);

        formPanelGbc.gridy = 0;
        formPanelGbc.gridx++;
        JLabel tipoLabel = new JLabel("TIPO");
        tipoLabel.setFont(EstiloGral.LABEL_FONT);
        tipoLabel.setForeground(EstiloGral.BG_COLOR);
        tipoLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(tipoLabel, formPanelGbc);
        
        formPanelGbc.gridy++;
        JPanel tipoInputPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        tipoComida = new JComboBox<String>(new String[] {"Almuerzo", "Desayuno"});
        tipoComida.setFont(EstiloGral.INPUT_FONT);
        tipoComida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tipoComida.setOpaque(false);
        tipoComida.setFocusable(true);
        tipoComida.setBackground(EstiloGral.BG_COLOR);
        tipoComida.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tipoComida.setForeground(EstiloGral.DARK_COLOR);
        tipoInputPanel.add(tipoComida);
        formPanel.add(tipoInputPanel, formPanelGbc);

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
        btnPublicar = new SuperBoton("PUBLICAR", EstiloGral.BUTTON_COLOR, 10, 20);
        btnPublicar.setFont(EstiloGral.INPUT_FONT);
        btnPublicar.setForeground(EstiloGral.BG_COLOR);
        footerPanel.add(btnPublicar, footerGbc);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public SuperBoton getBackButton() {
        return btnVolver;
    }

    public SuperBoton getBtnLimpiarFormulario() {
        return btnLimpiarFormulario;
    }

    public SuperBoton getPublicarButton() {
        return btnPublicar;
    }

    public String getFechaText() {
        return fechaInput.getText();
    }

    public String getPlatoText() {
        return platoInput.getText();
    }

    public String getingredientesText() {
        return ingredientesInput.getText();
    }

    public String getTipo() {
        return tipoComida.getItemAt(tipoComida.getSelectedIndex());
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

    public void limpiarFormulario(){
        tipoComida.setSelectedIndex(0);
        fechaInput.setText("");
        platoInput.setText("");
        ingredientesInput.setText("");
    }



}
