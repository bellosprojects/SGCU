package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.PanelAdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PanelAdminController implements ActionListener {
    private NavigationDelegate delegate;
    private PanelAdminView panelAdminView;
    private PersistenciaManager persistenciaManager;
    private Double porcentajeEstudiante;
    private Double porcentajeProfesor;
    private Double porcentajeTrabajador;

    public PanelAdminController(PanelAdminView panelAdminView, PersistenciaManager persistenciaManager,
            NavigationDelegate delegate) {
        this.panelAdminView = panelAdminView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        this.porcentajeEstudiante = 25.0;
        this.porcentajeProfesor = 85.0;
        this.porcentajeTrabajador = 100.0;
        guardarTarifasPorDefecto();
        setupListeners();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == panelAdminView.getCCBButton()) {
            goToCalcularCCB();
        } else if (e.getSource() == panelAdminView.getGuardarButton()) {
            actualizarTarifa();
        } else if (e.getSource() == panelAdminView.getVolverButton()) {
            gotoLoginView();
        } else if (e.getSource() == panelAdminView.getMenuButton()) {
            goToMenuGestion();
        }

    }

    private void actualizarTarifa() {
        try {
            Double nuevoPorcentaje = Double.parseDouble(panelAdminView.getPorcentaje());
            String Role = panelAdminView.getRolSelect();
            persistenciaManager.guardarTarifa(nuevoPorcentaje, Role);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelAdminView,
                    "Error: Asegúrate de ingresar números válidos en CCB y Porcentaje.",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    void setupListeners() {
        this.panelAdminView.getMenuButton().addActionListener(this);
        this.panelAdminView.getGuardarButton().addActionListener(this);
        this.panelAdminView.getVolverButton().addActionListener(this);
        this.panelAdminView.getCCBButton().addActionListener(this);
    }

    void gotoLoginView() {
        delegate.onBackToLoginRequested();
    }

    void goToCalcularCCB() {
        delegate.onCalcularCCBRequested();
    }

    void goToMenuGestion() {
        delegate.onGestionarMenuRequested();
    }

    void guardarTarifasPorDefecto() {
        persistenciaManager.guardarTarifa(this.porcentajeTrabajador, "Trabajador");
        persistenciaManager.guardarTarifa(this.porcentajeEstudiante, "Estudiante");
        persistenciaManager.guardarTarifa(this.porcentajeProfesor, "Profesor");
    }

}