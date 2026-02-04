package com.comedor.view;

import java.awt.*;
import javax.swing.*;

import com.comedor.view.components.*;

public class LoginView extends JFrame{

    private JTextField cedulaInput;
    private JTextField passField;
    
    public LoginView() {
        setTitle("Login - SGCU");
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100).getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new ImagePanel(cargarIcono("/images/background.jpeg", 1920, 1080).getImage());
        mainPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.anchor = GridBagConstraints.NORTH;
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("SGCU"); 
        titleLabel.setFont(EstiloGral.TITLE_FONT);
        titleLabel.setForeground(EstiloGral.BG_COLOR);

        JLabel logoIcon = new JLabel(cargarIcono("/images/logoWhite.png", 200, 200));

        header.add(logoIcon, headerGbc);
        headerGbc.gridy++;
        header.add(titleLabel, headerGbc);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.WEST;
        centerGbc.insets.top = 10;
        centerGbc.insets.bottom = 10;

        JLabel cedulaLabel = new JLabel("CEDULA");
        cedulaLabel.setFont(EstiloGral.LABEL_FONT);
        cedulaLabel.setForeground(EstiloGral.BG_COLOR);
        cedulaLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        centerPanel.add(cedulaLabel, centerGbc);

        centerGbc.gridy++;
        JPanel inputCedulaPanel = new GradientPanelRedondeado(10, 0, new Color(255, 255, 255, 200));
        cedulaInput = new JTextField(22);
        cedulaInput.setFont(EstiloGral.INPUT_FONT);
        cedulaInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cedulaInput.setOpaque(false);
        inputCedulaPanel.add(cedulaInput);
        centerPanel.add(inputCedulaPanel, centerGbc);

        centerGbc.gridy++;
        JLabel passLabel = new JLabel("CONTRASEÑA");
        passLabel.setFont(EstiloGral.LABEL_FONT);
        passLabel.setForeground(EstiloGral.BG_COLOR);
        passLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 0));
        centerPanel.add(passLabel, centerGbc);

        centerGbc.gridy++;
        JPanel inputPassPanel = new GradientPanelRedondeado(10, 0, new Color(255, 255, 255, 200));
        passField = new JTextField(22);
        passField.setFont(EstiloGral.INPUT_FONT);
        passField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passField.setOpaque(false);
        inputPassPanel.add(passField);
        centerPanel.add(inputPassPanel, centerGbc);

        centerGbc.gridy++;

        JLabel forgotPassLabel = new JLabel("¿Olvidaste tu contraseña?");
        forgotPassLabel.setFont(EstiloGral.SMALL_FONT);
        forgotPassLabel.setForeground(EstiloGral.BG_COLOR);
        forgotPassLabel.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        centerPanel.add(forgotPassLabel, centerGbc);

        centerGbc.anchor = GridBagConstraints.EAST;

        JLabel registerLabel = new JLabel("¿No tienes cuenta? Regístrate");
        registerLabel.setFont(EstiloGral.SMALL_FONT);
        registerLabel.setForeground(EstiloGral.BG_COLOR);
        registerLabel.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        centerPanel.add(registerLabel, centerGbc);


        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new GridBagLayout());
        footer.setOpaque(false);

        GridBagConstraints footerGbc = new GridBagConstraints();
        footerGbc.gridx = 0;
        footerGbc.gridy = 0;
        footerGbc.weightx = 0;
        footerGbc.anchor = GridBagConstraints.SOUTH;

        JLabel copyRightLabel = new JLabel("© 2026 SGCU. Todos los derechos reservados.");
        copyRightLabel.setFont(EstiloGral.SMALL_FONT);
        copyRightLabel.setForeground(EstiloGral.BG_COLOR);
        copyRightLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 15, 0));
        footer.add(copyRightLabel, footerGbc);

        footerGbc.gridx++;
        footerGbc.weightx = 1;
        footer.add(new JLabel(""), footerGbc); // Espaciador
        footerGbc.gridx++;
        footerGbc.weightx = 0;

        GradientPanelRedondeado loginButtonPanel = new GradientPanelRedondeado(10, 40, new Color(255, 255, 255, 200));
        loginButtonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton loginButton = new JButton("INICIAR SESIÓN");
        loginButton.setFont(EstiloGral.MIDDLE_FONT);
        loginButton.setForeground(EstiloGral.DARK_COLOR);
        loginButton.setBorder(null);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButtonPanel.setColor1(new Color(50, 50, 50, 220));
                loginButton.setForeground(EstiloGral.BG_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButtonPanel.setColor1(new Color(255, 255, 255, 200));
                loginButton.setForeground(EstiloGral.DARK_COLOR);
            }
        });
        loginButtonPanel.add(loginButton);
        footer.add(loginButtonPanel, footerGbc);

        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public JTextField getCedulaInput() {
        return this.cedulaInput;
    }

    public JTextField getPassField() {
        return this.passField;
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