package com.comedor.view;

import aura.layouts.AuraColumn;
import aura.layouts.AuraGrid;
import aura.layouts.AuraRow;

import aura.components.AuraButton;
import aura.components.AuraImage;
import aura.components.AuraWindow;

import aura.animations.AnimateBackground;
import aura.animations.AnimateFloat;
import aura.animations.AnimateShake;
import aura.components.AuraInput;
import aura.components.AuraSpacer;
import aura.components.AuraText;

import aura.core.AuraBox;
import aura.core.Transition;

public class GestionarCCBView extends AuraWindow {
    
    public GestionarCCBView(){
        super("Gestionar CCB - SGCU");

        fullScreen()
        .noResizable()
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

        insert(
            new AuraColumn()
                .fillParent()
                .background(EstiloGral.DARK_BG__COLOR2)
                .addBg(EstiloGral.DARK_BG__COLOR2.darker(), 1f)
                .backgroundAngle(90)
                .content(mainCol -> {
                    mainCol.insert(
                        new AuraRow()
                            .gap(40)
                            .alignSelf(AuraColumn.Alignment.LEFT)
                            .padding(10, 40, 0, 0)
                            .content(row -> {
                                row.insert(
                                    new AuraImage(getResourcePath("/images/logoWhite.png"))
                                        .size(200, 200)
                                );

                                row.insert(
                                    new AuraText("SGCU - Gestionar CCB")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            })
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraText("CCB Actual: 0,00")
                            .alignSelf(AuraColumn.Alignment.LEFT)
                            .margin(0,80,0,0)
                            .font(EstiloGral.MIDDLE_FONT2)
                            .textColor(EstiloGral.BG_COLOR)
                            .id("ccbActual")
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraGrid(2, 2)
                            .gap(100)
                            .fillWidth()
                            .margin(20,80,0,80)
                            .content(grid -> {
                                grid.insert(

                                    new AuraColumn()
                                        .gap(10)
                                        .padding(0,20)
                                        .align(AuraColumn.Alignment.LEFT)
                                        .content(cfCol -> {
                                            cfCol.insert(
                                                new AuraText("Costos Fijos (CF)")
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0,50,0,0)
                                            );

                                            cfCol.insert(
                                                new AuraInput()
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .background(EstiloGral.DARK_BG__COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .carterColor(EstiloGral.BG_COLOR)
                                                    .padding(15)
                                                    .radius(15)
                                                    .fillWidth()
                                                    .id("costosFijos")
                                            );
                                        })
                                );

                                grid.insert(
                                    new AuraColumn()
                                        .gap(10)
                                        .padding(0,20)
                                        .align(AuraColumn.Alignment.LEFT)
                                        .content(cvCol -> {
                                            cvCol.insert(
                                                new AuraText("Costos Variables (CV)")
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0,50,0,0)
                                            );

                                            cvCol.insert(
                                                new AuraInput()
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .background(EstiloGral.DARK_BG__COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .carterColor(EstiloGral.BG_COLOR)
                                                    .padding(15)
                                                    .radius(15)
                                                    .fillWidth()
                                                    .id("costosVariables")
                                            );
                                        })
                                );

                                grid.insert(
                                    new AuraColumn()
                                        .gap(10)
                                        .padding(0,20)
                                        .align(AuraColumn.Alignment.LEFT)
                                        .content(nbCol -> {
                                            nbCol.insert(
                                                new AuraText("Cantidad de Bandejas (NB)")
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0,50,0,0)
                                            );

                                            nbCol.insert(
                                                new AuraInput()
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .background(EstiloGral.DARK_BG__COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .carterColor(EstiloGral.BG_COLOR)
                                                    .padding(15)
                                                    .radius(15)
                                                    .fillWidth()
                                                    .id("cantidadBandejas")
                                            );
                                        })
                                );

                                grid.insert(
                                    new AuraColumn()
                                        .gap(10)
                                        .padding(0,20)
                                        .align(AuraColumn.Alignment.LEFT)
                                        .content(mermaCol -> {
                                            mermaCol.insert(
                                                new AuraText("Porcentaje de Merma (%)")
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0,50,0,0)
                                            );

                                            mermaCol.insert(
                                                new AuraInput()
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .background(EstiloGral.DARK_BG__COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .carterColor(EstiloGral.BG_COLOR)
                                                    .padding(15)
                                                    .radius(15)
                                                    .fillWidth()
                                                    .id("porcentajeMerma")
                                            );
                                        })
                                );
                            })
                    );

                    mainCol.insert(
                        new AuraSpacer()
                            .weight(2f)
                    );

                    mainCol.insert(
                        new AuraRow()
                            .gap(40)
                            .margin(40,0)
                            .content(footer -> {
                                footer.insert(
                                    new AuraButton("Volver")
                                        .background(EstiloGral.GREY_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("backBtn")
                                    
                                );

                                footer.insert(
                                    new AuraButton("Limpiar")
                                        .background(EstiloGral.BUTTON_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("clearBtn")
                                    
                                );

                                footer.insert(
                                    new AuraButton("Calcular y Guardar")
                                        .background(EstiloGral.GREEN_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("calcularBtn")
                                    
                                );
                            })
                    );
                })
        );
        
    }

    public void setCCB(double CCB){
        AuraText ccbText = (AuraText) find("ccbActual");

        new AnimateFloat(0f, (float) CCB, 1800, value -> {
            ccbText.text(String.format("CCB Actual: %.2f", value));
        })
        .delay(1500)
        .start();
    }

    public void limpiarFormulario(){
        ((AuraInput) find("costosFijos")).text("");
        ((AuraInput) find("costosVariables")).text("");
        ((AuraInput) find("cantidadBandejas")).text("");
        ((AuraInput) find("porcentajeMerma")).text("");
    }

    private String getResourcePath(String ruta) {
        return getClass().getResource(ruta).toString();
    }

    public void InvalidateInputs(String... ids){

        for(String id : ids){

            AuraBox<?> component = find(id);

            component.cancelAnimations(Transition.AnimationType.BACKGROUND);

            AnimateBackground t = new AnimateBackground(component, EstiloGral.ERROR_COLOR, 200)
                                    .pingPong();

            AnimateShake t2 = new AnimateShake(component, 5, 500);

            t.parallel(t2).start();
        }

    }

    public String getCostosFijos(){
        return ((AuraInput) find("costosFijos")).getText();
    }

    public String getCostosVariables(){
        return ((AuraInput) find("costosVariables")).getText();
    }

    public String getCantidadBandejas(){
        return ((AuraInput) find("cantidadBandejas")).getText();
    }

    public String getPorcentajeMerma(){
        return ((AuraInput) find("porcentajeMerma")).getText();
    }

}
