package com.comedor.view;

import aura.animations.AnimateBackground;
import aura.animations.AnimateShake;
import aura.components.AuraButton;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraMultiText;
import aura.components.AuraSelect;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;

public class GestionarMenuView extends AuraWindow {

    public GestionarMenuView(){
        super("Gestionar Menu - SGCU");

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
                                    new AuraText("SGCU - Gestionar Menu")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            })
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraRow()
                            .margin(0,150)
                            .gap(150)
                            .fillWidth()
                            .content(dataRow -> {
                                dataRow.insert(
                                    new AuraColumn()
                                        .weight(1f)
                                        .content(col1 -> {
                                            col1.insert(
                                                new AuraText("Plato")
                                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0, 50, 10, 0)
                                            );

                                            col1.insert(
                                                new AuraInput()
                                                    .fillWidth()
                                                    .radius(15)
                                                    .padding(15)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                    .id("plato")
                                            );

                                            col1.insert(
                                                new AuraText("Fecha")
                                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(50, 50, 10, 0)
                                            );

                                            col1.insert(
                                                new AuraInput()
                                                    .fillWidth()
                                                    .radius(15)
                                                    .padding(15)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                    .id("fecha")
                                            );
                                        })
                                );

                                dataRow.insert(
                                    new AuraColumn()
                                        .weight(0.5f)
                                        .content(col2 -> {
                                            col2.insert(
                                                new AuraText("Cupos")
                                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(0, 50, 10, 0)
                                            );

                                            col2.insert(
                                                new AuraInput()
                                                    .fillWidth()
                                                    .radius(15)
                                                    .padding(15)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                    .id("cupos")
                                            );

                                            col2.insert(
                                                new AuraText("Tipo")
                                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .margin(50, 50, 10, 0)
                                            );

                                            col2.insert(
                                                new AuraSelect("Almuerzo", "Desayuno")
                                                    .fillWidth()
                                                    .radius(15)
                                                    .padding(15)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                    .id("tipo")
                                            );
                                        })
                                );
                            })
                    );  

                    mainCol.insert(
                        new AuraText("Ingredientes")
                                .alignSelf(AuraColumn.Alignment.LEFT)
                                .font(EstiloGral.LABEL_FONT)
                                .textColor(EstiloGral.BG_COLOR)
                                .margin(50, 200, 10, 0)
                    );

                    mainCol.insert(
                        new AuraMultiText(" ")
                            .alignSelf(AuraColumn.Alignment.LEFT)
                            .fillWidth()
                            .height(160)
                            .radius(15)
                            .padding(15)
                            .font(EstiloGral.INPUT_FONT)
                            .background(EstiloGral.WHITE_TRANSP_COLOR)
                            .margin(0,150)
                            .id("ingredientes")
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraRow()
                            .margin(40,0)
                            .gap(40)
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
                                        .id("clear")
                                );

                                footer.insert(
                                    new AuraButton("Guardar")
                                        .background(EstiloGral.GREEN_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("save")
                                );
                            })
                    );
                })
        );
        
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

    public String getFecha(){
        return ((AuraInput) find("fecha")).getText();
    }

    public String getPlato(){
        return ((AuraInput) find("plato")).getText();
    }

    public String getIngredientes(){
        return ((AuraMultiText) find("ingredientes")).getText();
    }

    public String getCupos(){
        return ((AuraInput) find("cupos")).getText();
    }

    public String getTipo(){
        return ((AuraSelect) find("tipo")).getText();
    }

    public void limpiarFormulario(){
        ((AuraInput) find("fecha")).text("");
        ((AuraInput) find("plato")).text("");
        ((AuraMultiText) find("ingredientes")).text("");
        ((AuraInput) find("cupos")).text("");
    }
}
