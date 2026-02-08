package com.comedor.view;

import java.awt.*;

public class EstiloGral {
    
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
    public static final Color BLACK_TRANSP_COLOR = new Color(0, 0, 0, 150);
    public static final Color ERROR_COLOR = new Color(248, 10, 3);
    public static final Color BUTTON_COLOR = new Color(158, 10, 34);

    public static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 25);
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 60);
    public static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 28);
    public static final Font MIDDLE_FONT = new Font("SansSerif", Font.BOLD, 32);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 17);
    public static final Font SMALL_BOLD_FONT = new Font("SansSerif", Font.BOLD, 17);

    public static final Cursor HOVER_CURSOR = new Cursor(Cursor.HAND_CURSOR);

    public static final int INFO_MESSAGE = 0;
    public static final int ERROR_MESSAGE = 1;
    public static final int SUCCESS_MESSAGE = 2;

    public static void ShowMessage(String texto, int tipo) {
        Color color;
        switch (tipo) {
            case ERROR_MESSAGE -> color = new Color(220, 53, 69, 230); 
            case SUCCESS_MESSAGE -> color = new Color(40, 167, 69, 230); 
            default -> color = new Color(50, 50, 50, 230);    
        }

        ToastMessage toast = new ToastMessage(texto, color);
        toast.setVisible(true);
        toast.fadeIn();
    }
}