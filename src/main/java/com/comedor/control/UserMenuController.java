package com.comedor.control;
import com.comedor.view.UserMenuView;
import com.comedor.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenuController implements ActionListener {
    private PersistenciaManager persistenciaManager;       //modelo
    private UserMenuView menuView;                             //vista
    private String cedula;              //controlador

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView){ 
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
        this.menuView.getSalirButton().addActionListener(this);   //le digo al menuView que añada el getsalirbutton a los listeners
        sendMenu();
        sendUser();
    }
    public void sendMenu(){
        Menu Desayuno= persistenciaManager.getMenu(true);
        Menu Almuerzo= persistenciaManager.getMenu(false);
        if(Desayuno.isValidMenu()){
            menuView.setDesayuno(Desayuno);
        }
        if(Almuerzo.isValidMenu()){
            menuView.setAlmuerzo(Almuerzo);
        }
    }
    public void sendUser(){
        User user = persistenciaManager.getUserFromCedula(this.cedula);
        menuView.setUser(user);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == menuView.getSalirButton()){ 
        System.exit(0);
        }
    }
}