package com.comedor;

import javax.swing.SwingUtilities;

import com.comedor.control.AppCoordinator;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AppCoordinator app = new AppCoordinator();
            app.start();
        });

    }
}
