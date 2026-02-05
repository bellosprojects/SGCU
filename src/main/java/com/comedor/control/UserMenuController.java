package com.comedor.control;
import com.comedor.view.UserMenuView;
import com.comedor.model.*;

public class UserMenuController{
    private PersistenciaManager persistenciaManager;       //modelo
    private UserMenuView menuView;                             //vista
    private String cedula;              //controlador

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView){ 
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
    }

    public void sendData(){
        User user = persistenciaManager.getUserFromCedula(this.cedula);
        String menuDiario= persistenciaManager.getMenu();
        menuView.setMenu(menuDiario);
        menuView.setUser(user);
    }                               
}