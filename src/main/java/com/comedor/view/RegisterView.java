package com.comedor.view;

import javax.swing.*;

import com.comedor.view.components.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class RegisterView extends JFrame {

    private JTextField usernameInput;
    private JTextField emailInput;
    private JTextField cedulaInput;
    private JComboBox <String>facultadSelect;
    private JPasswordField passInput;
    private JPasswordField confirmPassInput;
    private JButton registerButton;
    private JButton backButton;
    private JButton profileImagFileChooser;
    private String profileImagePath;
    private JLabel imageContainer;
    private JComboBox <String> rolSelect;

    public RegisterView() {
        
        setTitle("Registro - SGCU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(cargarIcono("/images/logoColor.png", 100, 100, false).getImage());
        setLayout(new BorderLayout());

        JPanel mainPanel = new ImagePanel(cargarIcono("/images/comedor.png", 1920, 1080, false).getImage());
        mainPanel.setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridBagLayout());
        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.gridx = 0;
        headerGbc.gridy = 0;
        headerGbc.anchor = GridBagConstraints.WEST;
        headerGbc.insets = new Insets(0, 40, 20, 20);
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("SGCU - Registro"); 
        titleLabel.setFont(EstiloGral.TITLE_FONT);
        titleLabel.setForeground(EstiloGral.BG_COLOR);

        JLabel logoIcon = new JLabel(cargarIcono("/images/logoWhite.png", 200, 200, false));

        header.add(logoIcon, headerGbc);
        headerGbc.gridx++;
        header.add(titleLabel, headerGbc);
        mainPanel.add(header, BorderLayout.NORTH);

        headerGbc.gridx++;
        headerGbc.weightx = 1.0;
        header.add(Box.createHorizontalGlue(), headerGbc);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.SOUTHWEST;
        centerGbc.insets = new Insets(5, 40, 0, 10);

        JLabel cedulaLabel = new JLabel("CEDULA");
        cedulaLabel.setFont(EstiloGral.LABEL_FONT);
        cedulaLabel.setForeground(EstiloGral.BG_COLOR);
        cedulaLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        formPanel.add(cedulaLabel, centerGbc);
        centerGbc.gridy++;

        JPanel inputCedulaPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        cedulaInput = new JTextField(22);
        cedulaInput.setFont(EstiloGral.INPUT_FONT);
        cedulaInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cedulaInput.setOpaque(false);
        inputCedulaPanel.add(cedulaInput);
        formPanel.add(inputCedulaPanel, centerGbc);
        
        centerGbc.gridy++;
        JLabel usernameLabel = new JLabel("NOMBRE Y APELLIDO");
        usernameLabel.setFont(EstiloGral.LABEL_FONT);
        usernameLabel.setForeground(EstiloGral.BG_COLOR);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(usernameLabel, centerGbc);

        centerGbc.gridy++;
        JPanel inputUsernamePanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        usernameInput = new JTextField(22);
        usernameInput.setFont(EstiloGral.INPUT_FONT);
        usernameInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameInput.setOpaque(false);
        inputUsernamePanel.add(usernameInput);
        formPanel.add(inputUsernamePanel, centerGbc);

        centerGbc.gridy++;
        JLabel passLabel = new JLabel("CONTRASEÑA");
        passLabel.setFont(EstiloGral.LABEL_FONT);
        passLabel.setForeground(EstiloGral.BG_COLOR);
        passLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(passLabel, centerGbc);

        centerGbc.gridy++;
        JPanel inputPassPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        passInput = new JPasswordField(22);
        passInput.setFont(EstiloGral.INPUT_FONT);
        passInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passInput.setOpaque(false);
        inputPassPanel.add(passInput);
        formPanel.add(inputPassPanel, centerGbc);

        centerGbc.gridy++;
        JLabel rolLabel = new JLabel("ROL");
        rolLabel.setFont(EstiloGral.LABEL_FONT);
        rolLabel.setForeground(EstiloGral.BG_COLOR);
        rolLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(rolLabel, centerGbc);

        centerGbc.gridy++;
        JPanel rolPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        String[] roles = {"Estudiante","Profesor","Trabajador","Admin"};
        rolSelect = new JComboBox<String>(roles);
        rolSelect.setFont(EstiloGral.INPUT_FONT);
        rolSelect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rolSelect.setOpaque(false);
        rolSelect.setFocusable(true);
        rolSelect.setBackground(EstiloGral.BG_COLOR);
        rolSelect.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        rolSelect.setForeground(EstiloGral.DARK_COLOR);
        rolPanel.add(rolSelect);
        formPanel.add(rolPanel, centerGbc);

        centerGbc.gridy=0;
        centerGbc.gridx++;

        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setFont(EstiloGral.LABEL_FONT);
        emailLabel.setForeground(EstiloGral.BG_COLOR);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        formPanel.add(emailLabel, centerGbc);

        centerGbc.gridy++;
        JPanel inputEmailPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        emailInput = new JTextField(22);
        emailInput.setFont(EstiloGral.INPUT_FONT);
        emailInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        emailInput.setOpaque(false);
        inputEmailPanel.add(emailInput);
        formPanel.add(inputEmailPanel, centerGbc);

        centerGbc.gridy++;
        JLabel facultadLabel = new JLabel("FACULTAD");
        facultadLabel.setFont(EstiloGral.LABEL_FONT);
        facultadLabel.setForeground(EstiloGral.BG_COLOR);
        facultadLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(facultadLabel, centerGbc);

        centerGbc.gridy++;
        JPanel selectFacultadPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        String[] facultades = {"Ingeniería", "Ciencias", "Artes", "Medicina", "Derecho"};
        facultadSelect = new JComboBox<>(facultades);
        facultadSelect.setFont(EstiloGral.INPUT_FONT);
        facultadSelect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        facultadSelect.setOpaque(false);
        facultadSelect.setFocusable(true);
        facultadSelect.setBackground(EstiloGral.BG_COLOR);
        facultadSelect.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        facultadSelect.setForeground(EstiloGral.DARK_COLOR);
        selectFacultadPanel.add(facultadSelect);
        formPanel.add(selectFacultadPanel, centerGbc);

        centerGbc.gridy++;
        JLabel confirmPassLabel = new JLabel("CONFIRMAR CONTRASEÑA");
        confirmPassLabel.setFont(EstiloGral.LABEL_FONT);
        confirmPassLabel.setForeground(EstiloGral.BG_COLOR);
        confirmPassLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        formPanel.add(confirmPassLabel, centerGbc);

        centerGbc.gridy++;
        GradientPanelRedondeado inputConfirmPassPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        confirmPassInput = new JPasswordField(22);
        confirmPassInput.setFont(EstiloGral.INPUT_FONT);
        confirmPassInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        confirmPassInput.setOpaque(false);
        inputConfirmPassPanel.add(confirmPassInput);
        formPanel.add(inputConfirmPassPanel, centerGbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        JPanel imgPanel = new JPanel(new GridBagLayout());
        imgPanel.setOpaque(false);
        GridBagConstraints imgGbc = new GridBagConstraints();
        imgGbc.gridx = 0;
        imgGbc.gridy = 0;
        imgGbc.anchor = GridBagConstraints.CENTER;
        imgGbc.insets = new Insets(0, 0, 0, 80);

        GradientPanelRedondeado imgIconRectangle = new GradientPanelRedondeado(20, 0, EstiloGral.WHITE_TRANSP_COLOR);

        imageContainer = new JLabel(cargarIcono("/images/logoColor.png", 300, 300, false));
        imageContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        imgIconRectangle.add(imageContainer);

        imgPanel.add(imgIconRectangle, imgGbc);

        imgGbc.gridy++;
        imgGbc.weightx = 1.0;

        profileImagFileChooser = new JButton("SELECCIONAR");
        profileImagFileChooser.setBackground(EstiloGral.TRANSPARENT_COLOR);
        profileImagFileChooser.setContentAreaFilled(false);
        profileImagFileChooser.setForeground(EstiloGral.BG_COLOR);
        profileImagFileChooser.setFont(EstiloGral.SMALL_FONT);
        profileImagFileChooser.setCursor(EstiloGral.HOVER_CURSOR);
        profileImagFileChooser.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        imgPanel.add(profileImagFileChooser, imgGbc);

        mainPanel.add(imgPanel, BorderLayout.EAST);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);

        JPanel backButtonPanel = new GradientPanelRedondeado(10, 0, EstiloGral.WHITE_TRANSP_COLOR);
        backButton = new JButton("CANCELAR");
        backButton.setFont(EstiloGral.MIDDLE_FONT);
        backButton.setForeground(EstiloGral.BG_COLOR);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setCursor(EstiloGral.HOVER_CURSOR);
        backButtonPanel.add(backButton);
        footerPanel.add(backButtonPanel);

        GradientPanelRedondeado registerButtonPanel = new GradientPanelRedondeado(10, 20, EstiloGral.DARK_COLOR, EstiloGral.DARK_COLOR, GradientPanelRedondeado.HORIZONTAL);
        registerButton = new JButton("REGISTRARSE");
        registerButton.setFont(EstiloGral.MIDDLE_FONT);
        registerButton.setForeground(EstiloGral.BG_COLOR);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setCursor(EstiloGral.HOVER_CURSOR);

        registerButton.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseEntered(MouseEvent env){
                registerButtonPanel.setColor1(EstiloGral.BG_COLOR);
                registerButtonPanel.setColor2(EstiloGral.GREY_COLOR);
                registerButton.setForeground(EstiloGral.DARK_COLOR);
           }

           @Override
           public void mouseExited(MouseEvent env){
                registerButtonPanel.setColor1(EstiloGral.DARK_COLOR);
                registerButtonPanel.setColor2(EstiloGral.DARK_COLOR);
                registerButton.setForeground(EstiloGral.BG_COLOR);
           }
        });

        registerButtonPanel.add(registerButton);
        footerPanel.add(registerButtonPanel);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public JTextField getCedulaInput() {
        return cedulaInput;
    }

    public JPasswordField getPassInput() {
        return passInput;
    }

    public JPasswordField getConfirmPassInput() {
        return confirmPassInput;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getUsernameInput() {
        return usernameInput;
    }

    public JTextField getEmailInput() {
        return emailInput;
    }

    public String getFacultadSelect() {
        return facultadSelect.getItemAt(facultadSelect.getSelectedIndex());
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public JButton getProfileImagFileChooser() {
        return profileImagFileChooser;
    }

    public String getRoleSelect(){
        return rolSelect.getItemAt(rolSelect.getSelectedIndex());
    }

    public void setImagePath(String path) {

        this.imageContainer.setIcon(cargarIcono(path, 300, 300, true));
        this.profileImagePath = path;
        this.imageContainer.revalidate();
        this.imageContainer.repaint();
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
    public String getUsernameText() {
        return usernameInput.getText();
    }

    public String getCedulaText() {
        return cedulaInput.getText();
    }

    public String getPassText() {
        return new String(passInput.getPassword());
    }

    public String getConfirmPassText() {
        return new String(confirmPassInput.getPassword());
    }

    public String getEmailText() {
        return emailInput.getText();
    }

}