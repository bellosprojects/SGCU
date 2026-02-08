package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.GestionarCCBView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CCBCalculoController implements ActionListener {
    private NavigationDelegate delegate;
    private GestionarCCBView calcularCCBView;
    private PersistenciaManager persistenciaManager;
    private double precioCCB;

    public CCBCalculoController(GestionarCCBView calcularCCBView, PersistenciaManager persistenciaManager,
            NavigationDelegate delegate) {
        this.calcularCCBView = calcularCCBView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        this.precioCCB = persistenciaManager.getCCB();
        calcularCCBView.setCCB(precioCCB);
        setupListeners();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == calcularCCBView.getGuardarButton()) {
            calcularCCB();
        } else if (e.getSource() == calcularCCBView.getBackButton()) {
            gotoPanelAdminView();
        }
    }

    void setupListeners() {
        this.calcularCCBView.getGuardarButton().addActionListener(this);
        this.calcularCCBView.getBackButton().addActionListener(this);
    }

    void calcularCCB() {
        Double CF = Double.parseDouble(calcularCCBView.getCostosFijos());
        Double CV = Double.parseDouble(calcularCCBView.getCostosVariables());
        Integer NB = Integer.parseInt(calcularCCBView.getCantidadBandejas());
        Double porcMerma = Double.parseDouble(calcularCCBView.getPorcentajeMerma());
        this.precioCCB = procesarCalculo(CF, CV, NB, porcMerma);
        persistenciaManager.guardarCCB(this.precioCCB);
        gotoPanelAdminView();
    }

    void gotoPanelAdminView() {
        delegate.onAdminPanelRequested();
    }

    public double procesarCalculo(double CF, double CV, int NB, double porcMerma) {
        porcMerma = porcMerma / 100.0;

        if (NB <= 0) {
            System.out.println("Error: El número de bandejas (NB) debe ser mayor a 0");
            return 0.0;
        }
        double costosTotales = CF + CV;
        double costoPorBandeja = costosTotales / NB;
        double CCB = costoPorBandeja * (1 + porcMerma);
        return CCB;
    }

}
