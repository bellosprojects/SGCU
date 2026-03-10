package com.comedor.view;

import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;
import aura.layouts.AuraColumn.Alignment;

import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraModal;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;

import aura.animations.AnimateBackground;
import aura.animations.AnimateOpacity;
import aura.animations.AnimateShake;

import aura.core.AuraBox;
import aura.core.Transition;

public class GestionarBecariosView extends AuraContainer {
    
    private AuraModal modal;

    public GestionarBecariosView(){
        
        insert(
            new AuraColumn()
                .fillParent()
                .background(EstiloGral.DARK_BG__COLOR2)
                .addBg(EstiloGral.DARK_BG__COLOR2.darker(), 1f)
                .backgroundAngle(90)
                .align(Alignment.LEFT)
                .content(mainCol -> {
                    mainCol.insert(
                        new AuraRow()
                            .gap(40)
                            .padding(10, 40, 0, 0)
                            .content(row -> {
                                row.insert(
                                    new AuraImage(getResourcePath("/images/logoWhite.png"))
                                        .size(200, 200)
                                );

                                row.insert(
                                    new AuraText("SGCU - Gestionar Becarios y Exonerados")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            })
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraColumn()
                            .padding(40)
                            .background(EstiloGral.WHITE_TRANSP_COLOR2)
                            .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                            .radius(12)
                            .margin(0, 100)
                            .content(innerCol -> {

                                innerCol.insert(
                                    new AuraText("Ingrese la cedula")
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .margin(0, 20, 15, 0)
                                        .alignSelf(Alignment.LEFT)
                                );

                                innerCol.insert(
                                    new AuraInput()
                                        .fillWidth()
                                        .font(EstiloGral.MIDDLE_FONT2)
                                        .padding(15)
                                        .radius(12)
                                        .background(EstiloGral.BLACK_TRANSP_COLOR)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .carterColor(EstiloGral.BG_COLOR)
                                        .id("cedula")
                                );

                                innerCol.insert(
                                    new AuraRow()
                                        .gap(20)
                                        .margin(35,20,0,20)
                                        .content(btnsRow -> {
                                            btnsRow.insert(
                                                new AuraButton("EXONERAR")
                                                    .background(EstiloGral.BUTTON_COLOR)
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .id("exonerarBtn")
                                            );

                                            btnsRow.insert(
                                                new AuraButton("BECAR")
                                                    .background(EstiloGral.BUTTON_COLOR)
                                                    .font(EstiloGral.MIDDLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .id("becarBtn")
                                            );
                                        })
                                );

                            })
                    );

                    mainCol.insert(
                        new AuraSpacer()
                    );

                    mainCol.insert(
                        new AuraButton("Volver")
                            .background(EstiloGral.GREY_COLOR)
                            .font(EstiloGral.MIDDLE_FONT)
                            .textColor(EstiloGral.BG_COLOR)
                            .alignSelf(Alignment.RIGHT)
                            .margin(40, 80)
                            .id("backBtn")
                    );
                })
        );

    }

    public String getCedula(){
        return ((AuraInput) find("cedula")).getText();
    }

    public String getDescuento(){
        return ((AuraInput) modal.find("descuento")).getText();
    }

    private String getResourcePath(String ruta) {
        return getClass().getResource(ruta).toString();
    }

    public void InvalidateInputs(String... ids){

        for(String id : ids){

            AuraBox<?> component = find(id);
            if(component == null){
                component = modal.find(id);
            }

            component.cancelAnimations(Transition.AnimationType.BACKGROUND);

            AnimateBackground t = new AnimateBackground(component, EstiloGral.ERROR_COLOR, 200)
                                    .pingPong();

            AnimateShake t2 = new AnimateShake(component, 5, 500);

            t.parallel(t2).start();
        }

    }

    public void createModal(AuraWindow parent){

        modal = new AuraModal(parent);

        AuraColumn content = new AuraColumn()
                            .gap(20)
                            .padding(20)
                            .radius(15)
                            .background(EstiloGral.BG_COLOR)
                            .content(modalCol -> {
                                modalCol.insert(
                                    new AuraText("Becar Estudiante")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.DARK_COLOR)
                                        .margin(0, 60)
                                );

                                modalCol.insert(
                                    new AuraText("Ingrese el descuento (%)")
                                        .alignSelf(Alignment.LEFT)
                                        .font(EstiloGral.LABEL_FONT)
                                );

                                modalCol.insert(
                                    new AuraInput()
                                        .radius(15)
                                        .padding(15)
                                        .font(EstiloGral.INPUT_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .carterColor(EstiloGral.BG_COLOR)
                                        .background(EstiloGral.DARK_COLOR)
                                        .fillWidth()
                                        .id("descuento")
                                );

                                modalCol.insert(
                                    new AuraRow()
                                        .gap(40)
                                        .content(row -> {
                                            row.insert(
                                                new AuraButton("Cancelar")
                                                    .background(EstiloGral.GREY_COLOR)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .onClick(b -> {
                                                        hideModal();
                                                    })
                                            );

                                            row.insert(
                                                new AuraButton("Confirmar")
                                                    .background(EstiloGral.BUTTON_COLOR)
                                                    .font(EstiloGral.INPUT_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .id("confirmarBtn")
                                            );
                                        })
                                );
                            });

        modal.content(content);

    }

    public void showModal(){
        new AnimateOpacity(this, 0.3f, 200).start();
        modal.display();
    }

    public void hideModal(){
        new AnimateOpacity(this, 1f, 200).start();
        modal.close();
    }

    public AuraModal getModal(){
        return modal;
    }
}
