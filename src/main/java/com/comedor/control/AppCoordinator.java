package com.comedor.control;

import java.util.HashMap;
import java.util.Map;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.CajeroView;
import com.comedor.view.EstiloGral;
import com.comedor.view.GestionarBecariosView;
import com.comedor.view.GestionarCCBView;
import com.comedor.view.GestionarMenuView;
import com.comedor.view.LoginView;
import com.comedor.view.PanelAdminView;
import com.comedor.view.RegisterView;
import com.comedor.view.UserMenuView;

import aura.components.AuraImage;
import aura.components.AuraWhen;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.AuraState;

public class AppCoordinator implements NavigationDelegate {
    
    private final PersistenciaManager model;
    private AuraWindow mainFrame;
    private AuraState<String> viewStateController;
    private final Map<String, AuraBox<?>> views = new HashMap<>();

    // Controllers are created once and reused to avoid re-attaching listeners.
    private LoginController loginController;
    private RegisterController registerController;
    private UserMenuController userMenuController;
    private PanelAdminController panelAdminController;
    private GestionarMenuController gestionarMenuController;
    private CCBCalculoController ccbCalculoController;
    private GestionarBecariosController gestionarBecariosController;
    private CajeroController cajeroController;

    public AppCoordinator() {
        this.model = new PersistenciaManager();
        initView();
    }

    private String getResourcePath(String relativePath) {
        return getClass().getResource(relativePath).toString();
    }

    private void initView(){
        mainFrame = new AuraWindow("SGCU")
            .fullScreen()
            .background(EstiloGral.DARK_COLOR)
            .noResizable()
            .icon(new AuraImage(getResourcePath("/images/logoColor.png")))
            .display();

        viewStateController = new AuraState<>("Loading");

        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        UserMenuView userMenuView = new UserMenuView();
        PanelAdminView panelAdminView = new PanelAdminView();
        GestionarMenuView gestionarMenuView = new GestionarMenuView();
        GestionarCCBView gestionarCCBView = new GestionarCCBView();
        GestionarBecariosView gestionarBecariosView = new GestionarBecariosView();
        CajeroView cajeroView = new CajeroView();

        views.put("Login", loginView);
        views.put("Register", registerView);
        views.put("UserMenu", userMenuView);
        views.put("AdminDashboard", panelAdminView);
        views.put("GestionarMenu", gestionarMenuView);
        views.put("CalcularCCB", gestionarCCBView);
        views.put("GestionarBecarios", gestionarBecariosView);
        views.put("Cajero", cajeroView);

        AuraWhen<String> screen = new AuraWhen<>(viewStateController)
            .animationDuration(250)
            .addCase("Login", loginView)
            .addCase("Register", registerView)
            .addCase("UserMenu", userMenuView)
            .addCase("AdminDashboard", panelAdminView)
            .addCase("GestionarMenu", gestionarMenuView)
            .addCase("CalcularCCB", gestionarCCBView)
            .addCase("GestionarBecarios", gestionarBecariosView)
            .addCase("Cajero", cajeroView);

        userMenuView.createModalRecharge(mainFrame);
        userMenuView.createModalSaldoPana(mainFrame);
        gestionarBecariosView.createModal(mainFrame);
        cajeroView.createModal(mainFrame);

        mainFrame.insert(screen.fillParent());

    }

    public void start() {
        showLogin();
    }

    private void showLogin() {
        viewStateController.set("Login");
        if (loginController == null) {
            loginController = new LoginController((LoginView) views.get("Login"), model, this);
        }
    }

    private void showRegister() {
        viewStateController.set("Register");
        if (registerController == null) {
            registerController = new RegisterController((RegisterView) views.get("Register"), model, this);
        }
    }

    private void showUserMenu(String cedula) {
        viewStateController.set("UserMenu");
        if (userMenuController == null) {
            userMenuController = new UserMenuController(model, cedula, (UserMenuView) views.get("UserMenu"), this);
        } else {
            userMenuController.setCedula(cedula);
        }
    }

    private void showAdminDashboard() {
        viewStateController.set("AdminDashboard");
        if (panelAdminController == null) {
            panelAdminController = new PanelAdminController((PanelAdminView) views.get("AdminDashboard"), model, this);
        }
    }

    private void showGestionarMenuView() {
        viewStateController.set("GestionarMenu");
        if (gestionarMenuController == null) {
            gestionarMenuController = new GestionarMenuController((GestionarMenuView) views.get("GestionarMenu"), model, this);
        }
    }

    private void showCalcularCCBView() {
        viewStateController.set("CalcularCCB");
        if (ccbCalculoController == null) {
            ccbCalculoController = new CCBCalculoController((GestionarCCBView) views.get("CalcularCCB"), model, this);
        }
    }

    public void showGestionarBecarios() {
        viewStateController.set("GestionarBecarios");
        if(gestionarBecariosController == null){
            gestionarBecariosController = new GestionarBecariosController((GestionarBecariosView) views.get("GestionarBecarios"), model, this);
        }
    }

    public void showCajeroView(){
        viewStateController.set("Cajero");
        if(cajeroController == null){
            cajeroController = new CajeroController((CajeroView) views.get("Cajero"), model, this);
        }
    }

    @Override
    public void onRegisterSuccess() {
        showLogin();
    }

    @Override
    public void onBackToLoginRequested() {
        showLogin();
    }

    @Override
    public void onRegisterRequested() {
        showRegister();
    }

    @Override
    public void onLoginSuccess(String cedula) {
        if (model.getRoleFromCedula(cedula).toString().equals("ADMIN")) {
            showAdminDashboard();
        } else {
            showUserMenu(cedula);
        }
    }

    @Override
    public void onAdminPanelRequested() {
        showAdminDashboard();
    }

    @Override
    public void onGestionarMenuRequested() {
        showGestionarMenuView();
    }

    @Override
    public void onCalcularCCBRequested() {
        showCalcularCCBView();
    }

    @Override
    public void onGestionarBecariosRequested() {
        showGestionarBecarios();
    }

    @Override
    public void onCajeroRequested(){
        showCajeroView();
    }
}
