package com.comedor.view;

import java.awt.*;
import javax.swing.*;

import com.comedor.view.components.*;

import java.awt.event.*;

public class LoginView extends JFrame{

    private JTextField cedulaInput;
    private JPasswordField passInput;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPassButton;
    
    public LoginView() {
        setTitle("Login - SGCU");
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100).getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new ImagePanel(cargarIcono("/images/comedor.png", 1920, 1080).getImage());
        mainPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.anchor = GridBagConstraints.NORTH;
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("SGCU - Iniciar sesión"); 
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
        JPanel inputCedulaPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
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
        JPanel inputPassPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        passInput = new JPasswordField(22);
        passInput.setFont(EstiloGral.INPUT_FONT);
        passInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passInput.setOpaque(false);
        inputPassPanel.add(passInput);
        centerPanel.add(inputPassPanel, centerGbc);

        centerGbc.gridy++;

        forgotPassButton = new JButton("¿Olvidaste tu contraseña?");
        forgotPassButton.setFont(EstiloGral.SMALL_FONT);
        forgotPassButton.setForeground(EstiloGral.BG_COLOR);
        forgotPassButton.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        forgotPassButton.setBackground(new Color(0,0,0,0));
        forgotPassButton.setBorder(null);
        forgotPassButton.setContentAreaFilled(false);
        centerPanel.add(forgotPassButton, centerGbc);

        centerGbc.anchor = GridBagConstraints.EAST;

        registerButton = new JButton("¿No tienes cuenta? Regístrate");
        registerButton.setFont(EstiloGral.SMALL_FONT);
        registerButton.setForeground(EstiloGral.BG_COLOR);
        registerButton.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        registerButton.setBackground(new Color(0,0,0,0));
        registerButton.setBorder(null);
        registerButton.setContentAreaFilled(false);
        centerPanel.add(registerButton, centerGbc);


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

        GradientPanelRedondeado loginButtonPanel = new GradientPanelRedondeado(10, 40, EstiloGral.DARK_COLOR, EstiloGral.DARK_COLOR, GradientPanelRedondeado.HORIZONTAL);
        loginButtonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton = new JButton("INICIAR SESIÓN");
        loginButton.setFont(EstiloGral.MIDDLE_FONT);
        loginButton.setForeground(EstiloGral.BG_COLOR);
        loginButton.setBorder(null);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButtonPanel.setColor1(EstiloGral.BG_COLOR);
                loginButtonPanel.setColor2(EstiloGral.GREY_COLOR);
                loginButton.setForeground(EstiloGral.DARK_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButtonPanel.setColor1(EstiloGral.DARK_COLOR);
                loginButtonPanel.setColor2(EstiloGral.DARK_COLOR);
                loginButton.setForeground(EstiloGral.BG_COLOR);
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

    public JPasswordField getPassInput() {
        return this.passInput;
    }

    public JButton getLoginButton() {
        return this.loginButton;
    }

    public JButton getRegisterButton() {
        return this.registerButton;
    }

    public JButton getForgotPassButton() {
        return this.forgotPassButton;
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