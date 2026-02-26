package com.comedor.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class EstiloGral {
    
    public static final Color BG_COLOR = new Color(248, 248, 248);
    public static final Color LIGHT_COLOR = new Color(200, 200, 200);
    public static final Color TITLE_COLOR = new Color(100, 100, 100);
    public static final Color LABEL_COLOR = new Color(160, 160, 160);
    public static final Color ACCENT_COLOR = new Color(0, 120, 215);
    public static final Color DARK_COLOR = new Color(50, 50, 50);
    public static final Color ACCENT_COLOR2 = new Color(210, 10, 150);
    public static final Color SKY_BLUE_COLOR = new Color(142, 163, 192);
    public static final Color GREY_COLOR = new Color(100, 100, 100);
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    public static final Color WHITE_TRANSP_COLOR = new Color(255, 255, 255, 100);
    public static final Color WHITE_TRANSP_COLOR2 = new Color(255, 255, 255, 20);
    public static final Color BLACK_TRANSP_COLOR = new Color(0, 0, 0, 150);
    public static final Color ERROR_COLOR = new Color(248, 10, 3);
    public static final Color BUTTON_COLOR = new Color(158, 10, 34);

    public static final Color GREEN_COLOR = new Color(40, 167, 69);

    public static final Color DARK_BG__COLOR = new Color(33,33,29);
    public static final Color DARK_BG__COLOR2 = new Color(71,71,58);

    public static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 25);
    public static final Font LABEL_BOLD_FONT = new Font("SansSerif", Font.BOLD, 25);
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 60);
    public static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 28);
    public static final Font MIDDLE_FONT = new Font("SansSerif", Font.BOLD, 32);
    public static final Font MIDDLE_FONT2 = new Font("SansSerif", Font.BOLD, 40);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 17);
    public static final Font SMALL_BOLD_FONT = new Font("SansSerif", Font.BOLD, 17);
    public static final Font CLOCK_FONT = new Font("Consolas", Font.BOLD, 45);

    public static final Cursor HOVER_CURSOR = new Cursor(Cursor.HAND_CURSOR);

    public static final int INFO_MESSAGE = 0;
    public static final int ERROR_MESSAGE = 1;
    public static final int SUCCESS_MESSAGE = 2;

    private static ToastMessage toastMessaje = null;

    public static void ShowMessage(String texto, int tipo) {

        if(toastMessaje != null && toastMessaje.getActive()){
            return;
        }

        Color color;
        switch (tipo) {
            case ERROR_MESSAGE -> color = new Color(220, 53, 69, 200); 
            case SUCCESS_MESSAGE -> color = new Color(40, 167, 69, 200); 
            default -> color = new Color(50, 50, 50, 200);    
        }

        toastMessaje = new ToastMessage(texto, color);
        toastMessaje.setVisible(true);
        toastMessaje.fadeIn();
    }

    public static String getImgPath(String cedula){
        Path path = Paths.get("src/main/java/com/comedor/database/images", cedula + ".jpg");
        return path.toString();
    }
}