package com.comedor.view;

import java.awt.Component;

import com.comedor.model.ComensalesPorServicio;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.Prices;
import com.comedor.model.User.Role;

import aura.animations.AnimateBackground;
import aura.animations.AnimateFloat;
import aura.animations.AnimateShake;
import aura.animations.AnimateString;
import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraInput;
import aura.components.AuraSelect;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWhen;
import aura.core.AuraBox;
import aura.core.AuraState;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraColumn.Alignment;
import aura.layouts.AuraRow;

public class PanelAdminView extends AuraContainer {
    
    AuraState<String> rightPanelStateController;

    AuraColumn listadoDesayunoColumn;
    AuraColumn listadoAlmuerzoColumn;

    public PanelAdminView(){

        rightPanelStateController = new AuraState<>("listadoDesayuno");

        listadoDesayunoColumn = new AuraColumn()
                            .id("listadoDesayuno")
                            .content(reservasCol -> {
                                reservasCol.insert(
                                    new AuraText("Desayuno - Comensales")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            });

        listadoAlmuerzoColumn = new AuraColumn()
                            .id("listadoAlmuerzo")
                            .content(reservasCol -> {
                                reservasCol.insert(
                                    new AuraText("Almuerzo - Comensales")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            });

        AuraButton menuBtn2 = new AuraButton("Gestionar Menus")
                                .background(EstiloGral.BUTTON_COLOR)
                                .textColor(EstiloGral.BG_COLOR)
                                .fillWidth()
                                .font(EstiloGral.LABEL_FONT)
                                .alignSelf(aura.layouts.AuraRow.Alignment.BOTTOM)
                                .id("menuBtn2");

        menuBtn2.setVisible(false);

        AuraColumn tarifas = new AuraColumn()
                                    .padding(20)
                                    .radius(15)
                                    .gap(10)
                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                    .align(AuraColumn.Alignment.LEFT)
                                    .weight(0.8f)
                                    .fillHeight()
                                    .content(updateCol -> {

                                        updateCol.insert(
                                            new AuraText("Actualizar Tarifas")
                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .font(EstiloGral.MIDDLE_FONT)
                                                .margin(20,50)
                                        );

                                        updateCol.insert(
                                            new AuraText("Porcentaje")
                                                .font(EstiloGral.LABEL_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .margin(0,50,0,0)

                                        );

                                        updateCol.insert(
                                            new AuraInput()
                                                .padding(15)
                                                .radius(15)
                                                .background(EstiloGral.DARK_COLOR)
                                                .carterColor(EstiloGral.BG_COLOR)
                                                .font(EstiloGral.INPUT_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .fillWidth()
                                                .id("porcentaje")
                                        );

                                        updateCol.insert(
                                            new AuraText("Rol")
                                                .font(EstiloGral.LABEL_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .margin(20,50,0,0)

                                        );

                                        updateCol.insert(
                                            new AuraSelect("ESTUDIANTE", "PROFESOR", "TRABAJADOR")
                                                .background(EstiloGral.DARK_COLOR)
                                                .font(EstiloGral.LABEL_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .fillWidth()
                                                .id("rol")
                                        );

                                        updateCol.insert(
                                            new AuraButton("Actualizar")
                                                    .background(EstiloGral.BUTTON_COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .id("update")
                                                    .alignSelf(AuraColumn.Alignment.CENTER)
                                                    .margin(20,0,10,0)
                                        );
                                    });

        tarifas.setVisible(true);

        AuraRow tarifas2 = new AuraRow()
                                .padding(20)
                                .radius(15)
                                .gap(10)
                                .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                .weight(1f)
                                .content(row -> {
                                    row.insert(
                                        new AuraSelect("ESTUDIANTE", "PROFESOR", "TRABAJADOR")
                                                .background(EstiloGral.DARK_COLOR)
                                                .font(EstiloGral.LABEL_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .id("rol2")
                                    );

                                    row.insert(
                                        new AuraInput()
                                                .padding(15)
                                                .radius(15)
                                                .background(EstiloGral.DARK_COLOR)
                                                .carterColor(EstiloGral.BG_COLOR)
                                                .font(EstiloGral.INPUT_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .weight(1f)
                                                .id("porcentaje2")
                                    );

                                    row.insert(new AuraButton("Actualizar")
                                                    .background(EstiloGral.BUTTON_COLOR)
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .id("update2")
                                                    .fillHeight()
                                                    .alignSelf(AuraColumn.Alignment.CENTER)
                                                    .margin(20,0,10,0));
                                });

        onSize((w, h) -> {

            if(w < 1300){
                find("tarifasColumn").setVisible(false);
            } else {
                find("tarifasColumn").setVisible(true);
            }

            if(h < 1030 || w < 1300){
                find("menusColumn").setVisible(false);
                menuBtn2.setVisible(true);
            } else {
                find("menusColumn").setVisible(true);
                menuBtn2.setVisible(false);
            }

            if( h < 1000){
                tarifas.setVisible(false);
                tarifas2.setVisible(true);
            } else {
                tarifas.setVisible(true);
                tarifas2.setVisible(false);
            }

        });

        insert(
            new AuraRow()
                .fillParent()
                .content(mainRow -> {
                    mainRow.insert(
                        new AuraColumn()
                            .weight(1f)
                            .background(EstiloGral.DARK_BG__COLOR2)
                            .addBg(EstiloGral.DARK_BG__COLOR2.darker(), 1f)
                            .backgroundAngle(90)
                            .fillHeight()
                            .padding(40,60)
                            .content(mainCol -> {
                                mainCol.insert(
                                    new AuraText("Panel del Administrador")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                );

                                mainCol.insert(
                                    new AuraRow()
                                        .fillWidth()
                                        .align(AuraRow.Alignment.BOTTOM)
                                        .content(auxRow -> {
                                            auxRow.insert(
                                                new AuraText("Fecha del CCB: 01/01/01")
                                                    .textColor(EstiloGral.LIGHT_COLOR)
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .id("ccbFecha")
                                            );

                                            auxRow.insert(
                                                new AuraSpacer()
                                            );

                                            auxRow.insert(
                                                new AuraButton("Cerrar sesión")
                                                    .id("backBtn")
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .radius(25)
                                            );
                                        })
                                );

                                mainCol.insert(
                                    new AuraRow()
                                        .alignSelf(Alignment.LEFT)
                                        .margin(60,0)
                                        .fillWidth()
                                        .gap(40)
                                        .content(pricesRow -> {
                                            pricesRow.insert(
                                                new AuraColumn()
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .padding(30, 40, 30, 100)
                                                    .gap(10)
                                                    .id("tarifasColumn")
                                                    .fillHeight()
                                                    .weight(1f)
                                                    .maximalSize(350, -1)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .content(tarifasCol -> {
                                                        tarifasCol.insert(
                                                            new AuraText("Tarifas Actuales")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .margin(0,0,10,0)
                                                        );

                                                        tarifasCol.insert(
                                                            new AuraText("Estudiante: 0,00")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .id("student")
                                                        );

                                                        tarifasCol.insert(
                                                            new AuraText("Profesor: 0,00")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .id("teacher")
                                                        );

                                                        tarifasCol.insert(
                                                            new AuraText("Trabajador: 0,00")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .id("worker")
                                                        );

                                                    })
                                            );

                                            pricesRow.insert(
                                                new AuraColumn()
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .padding(30, 40, 30, 0)
                                                    .gap(10)
                                                    .fillHeight()
                                                    .weight(1f)
                                                    .maximalSize(250, -1)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .content(ccbRow -> {
                                                        ccbRow.insert(
                                                            new AuraText("CCB Actual")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                        );

                                                        ccbRow.insert(
                                                            new AuraText("0,00")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT)
                                                                .id("ccbValue")
                                                                .margin(20,0)
                                                        );

                                                        ccbRow.insert(
                                                            new AuraButton("Actualizar")
                                                                .background(EstiloGral.BUTTON_COLOR)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .fillWidth()
                                                                .maximalSize(160, -1)
                                                                .id("ccbBtn")
                                                        );

                                                    })
                                            );

                                            pricesRow.insert(

                                                new AuraColumn()
                                                        .weight(1f)
                                                        .gap(40)
                                                        .alignSelf(AuraRow.Alignment.BOTTOM)
                                                        .maximalSize(260, -1)
                                                        .content(auxCol -> {
                                                            auxCol.insert(
                                                                new AuraButton("Gestionar Becarios")
                                                                    .background(EstiloGral.BUTTON_COLOR)
                                                                    .textColor(EstiloGral.BG_COLOR)
                                                                    .fillWidth()
                                                                    .font(EstiloGral.LABEL_FONT)
                                                                    .alignSelf(aura.layouts.AuraRow.Alignment.BOTTOM)
                                                                    .id("becariosBtn")
                                                            );

                                                            auxCol.insert(
                                                                menuBtn2
                                                            );
                                                        })
                                                
                                            );
                                        })
                                );

                                mainCol.insert(
                                    
                                    new AuraRow()
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .align(AuraRow.Alignment.TOP)
                                        .fillWidth()
                                        .weight(1f)
                                        .gap(40)
                                        .content(footer -> {
                                            footer.insert(
                                                tarifas
                                            );

                                            footer.insert(
                                                tarifas2
                                            );

                                            footer.insert(
                                                new AuraColumn()
                                                    .weight(1f)
                                                    .fillHeight()
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .radius(15)
                                                    .padding(15)
                                                    .gap(10)
                                                    .id("menusColumn")
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .content(menusCol -> {
                                                        menusCol.insert(
                                                            new AuraText("Menus Actuales")
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT)
                                                                .margin(20,50)
                                                        );

                                                        menusCol.insert(
                                                            new AuraText("Desayuno")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .margin(0,50,0,0)

                                                        );

                                                        menusCol.insert(
                                                            new AuraText(" ")
                                                                .padding(15)
                                                                .radius(15)
                                                                .background(EstiloGral.DARK_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .fillWidth()
                                                                .id("desayuno")
                                                        );

                                                        menusCol.insert(
                                                            new AuraText("Almuerzo")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .margin(20,50,0,0)

                                                        );

                                                        menusCol.insert(
                                                            new AuraText(" ")
                                                                .padding(15)
                                                                .radius(15)
                                                                .background(EstiloGral.DARK_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .fillWidth()
                                                                .id("almuerzo")
                                                        );

                                                        menusCol.insert(
                                                            new AuraButton("Gestionar")
                                                                    .background(EstiloGral.BUTTON_COLOR)
                                                                    .textColor(EstiloGral.BG_COLOR)
                                                                    .font(EstiloGral.LABEL_FONT)
                                                                    .id("menuBtn")
                                                                    .alignSelf(AuraColumn.Alignment.CENTER)
                                                                    .margin(20,0,10,0)
                                                        );
                                                    })
                                            );
                                        })
                                );
                            })
                    );

                    mainRow.insert(
                        new AuraColumn()
                            .background(EstiloGral.DARK_BG__COLOR)
                            .addBg(EstiloGral.DARK_BG__COLOR.darker(), 1f)
                            .backgroundAngle(90)
                            .fillHeight()
                            .weight(0.4f)
                            .padding(40,0)
                            .content(rightCol -> {

                                rightCol.insert(

                                    new AuraWhen<>(rightPanelStateController)
                                        .fillWidth()
                                        .weight(1f)
                                        .background(EstiloGral.TRANSPARENT_COLOR)
                                        .animationDuration(250)
                                        .addCase("listadoDesayuno", listadoDesayunoColumn)
                                        .addCase("listadoAlmuerzo", listadoAlmuerzoColumn)
                                );

                                rightCol.insert(
                                    new AuraWhen<>(rightPanelStateController)
                                        .animationDuration(250)
                                        .addCase("listadoDesayuno", 
                                            new AuraButton("Ver Listado Almuerzo")
                                                .background(EstiloGral.BUTTON_COLOR)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .font(EstiloGral.LABEL_FONT)
                                                .onClick(b -> {
                                                    rightPanelStateController.set("listadoAlmuerzo");
                                                })
                                        )
                                        .addCase("listadoAlmuerzo", 
                                            new AuraButton("Ver Listado Desayuno")
                                                .background(EstiloGral.BUTTON_COLOR)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .font(EstiloGral.LABEL_FONT)
                                                .onClick(b -> {
                                                    rightPanelStateController.set("listadoDesayuno");
                                                })
                                        )
                                );
                                
                            })
                    );
                })
        );

    }

    public void setFechaCCB(String fecha){
        ((AuraText) find("ccbFecha")).text("Fecha del CCB: " + fecha);
    }

    public void setListado(ComensalesPorServicio listado, TipoMenu tipo){

        AuraColumn listadoColumn = (tipo == TipoMenu.ALMUERZO)? listadoAlmuerzoColumn : listadoDesayunoColumn;

        listadoColumn.removeAll();

        listadoColumn.insert(
            listadoItem(Role.ESTUDIANTE, listado.getCantidadEstudiante())
        );

        listadoColumn.insert(
            listadoItem(Role.BECARIO, listado.getCantidadBecario())
        );

        listadoColumn.insert(
            listadoItem(Role.EXONERADO, listado.getCantidadExonerado())
        );

        listadoColumn.insert(
            listadoItem(Role.PROFESOR, listado.getCantidadProfesor())
        );

        listadoColumn.insert(
            listadoItem(Role.TRABAJADOR, listado.getCantidadTrabajador())
        );
    }

    public AuraColumn listadoItem(Role rol, int cantidad){
        return new AuraColumn()
                    .padding(12, 25)
                    .radius(15)
                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                    .align(Alignment.LEFT)
                    .fillWidth()
                    .margin(10, 40)
                    .content(item -> {
                        item.insert(
                            new AuraText(rol.toString())
                                .textColor(EstiloGral.DARK_COLOR)
                                .font(EstiloGral.LABEL_BOLD_FONT)
                        );

                        item.insert(
                            new AuraText(String.valueOf(cantidad))               
                                .textColor(EstiloGral.DARK_COLOR)
                                .font(EstiloGral.SMALL_FONT)
                                .margin(5,10)
                        );
                    });
    }

    public void reset(){
        
        for(Component c : listadoAlmuerzoColumn.getComponents()){
            if(c instanceof AuraColumn){
                listadoAlmuerzoColumn.remove(c);
            }
        }

        for(Component c : listadoDesayunoColumn.getComponents()){
            if(c instanceof AuraColumn){
                listadoDesayunoColumn.remove(c);
            }
        }
    }

    public void setMenus(String desayuno, String almuerzo){

        AuraText desayunoText = (AuraText) find("desayuno");
        AuraText almuerzoText = (AuraText) find("almuerzo");

        if(desayuno != null){
            new AnimateString(desayuno, 1500, value -> {

                desayunoText.text(value);

            }).delay(1500)
            .start();
        }

        if(almuerzo != null){
            new AnimateString(almuerzo, 1500, value -> {

                almuerzoText.text(value);

            }).delay(1500)
            .start();
        }

    }

    public void setPrices(Prices prices){

        AuraText ccbValue = (AuraText) find("ccbValue");

        new AnimateFloat(0f, (float) (double) prices.getCCB(), 1800, value -> {
            ccbValue.text(String.format("%.2f", value));
        })
        .delay(1500)
        .start();

        AuraText student = (AuraText) find("student");

        new AnimateFloat(0f, (float) (double) prices.getEstudiante(), 1800, value -> {
            student.text(String.format("Estudiante: %.2f", value));
        })
        .delay(1500)
        .start();

        AuraText teacher = (AuraText) find("teacher");

        new AnimateFloat(0f, (float) (double) prices.getProfesor(), 1800, value -> {
            teacher.text(String.format("Profesor: %.2f", value));
        })
        .delay(1500)
        .start();

        AuraText worker = (AuraText) find("worker");

        new AnimateFloat(0f, (float) (double) prices.getTrabajador(), 1800, value -> {
            worker.text(String.format("Trabajador: %.2f", value));
        })
        .delay(1500)
        .start();

    }

    public String getPorcentaje(){
        return  ((AuraInput) find(getHeight() < 1000? "porcentaje2" : "porcentaje")).getText();
    }

    public String getRole(){
        return ((AuraSelect) find(getHeight() < 1000? "rol2" : "rol")).getText();
    }

    public void InvalidateInputs(String... ids){

        for(String id : ids){

            
            if(id.equals("porcentaje") && getHeight() < 1000){
                id = "porcentaje2";
            }
            AuraBox<?> component = find(id);

            component.cancelAnimations(Transition.AnimationType.BACKGROUND);

            AnimateBackground t = new AnimateBackground(component, EstiloGral.ERROR_COLOR, 200)
                                    .pingPong();

            AnimateShake t2 = new AnimateShake(component, 5, 500);

            t.parallel(t2).start();
        }

    }

}

