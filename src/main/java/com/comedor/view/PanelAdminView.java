package com.comedor.view;

import java.util.LinkedList;
import java.util.Queue;

import com.comedor.model.Menu;
import com.comedor.model.Prices;
import com.comedor.model.Reserva;

import aura.animations.AnimateBackground;
import aura.animations.AnimateFloat;
import aura.animations.AnimateShake;
import aura.animations.AnimateString;
import aura.components.AuraButton;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraSelect;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;

public class PanelAdminView extends AuraWindow {
    
    public PanelAdminView(){

        super("Panel de Admin - SGCU");
        
        fullScreen()
        .noResizable()
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

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
                                                new AuraText("Gestiona los precios y menus desde aqui")
                                                    .textColor(EstiloGral.LIGHT_COLOR)
                                                    .font(EstiloGral.LABEL_FONT)
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
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .margin(60,0)
                                        .gap(40)
                                        .content(pricesRow -> {
                                            pricesRow.insert(
                                                new AuraColumn()
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .padding(30, 40, 30, 100)
                                                    .gap(10)
                                                    .fillHeight()
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .content(tarifasCol -> {
                                                        tarifasCol.insert(
                                                            new AuraText("Tarifas Actuales")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT)
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
                                                    .padding(30, 40, 30, 100)
                                                    .gap(10)
                                                    .fillHeight()
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .content(ccbRow -> {
                                                        ccbRow.insert(
                                                            new AuraText("CCB Actual")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT)
                                                        );

                                                        ccbRow.insert(
                                                            new AuraText("0,00")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT2)
                                                                .id("ccbValue")
                                                                .margin(20,0)
                                                        );

                                                        ccbRow.insert(
                                                            new AuraButton("Actualizar")
                                                                .background(EstiloGral.BUTTON_COLOR)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .id("ccbBtn")
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
                                        .gap(40)
                                        .content(footer -> {
                                            footer.insert(
                                                new AuraColumn()
                                                    .padding(20)
                                                    .radius(15)
                                                    .gap(10)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .align(AuraColumn.Alignment.LEFT)
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
                                                            new AuraSelect("Estudiante", "Profesor", "Trabajador")
                                                                .background(EstiloGral.DARK_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .fillWidth()
                                                                .id("role")
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
                                                    })
                                            );

                                            footer.insert(
                                                new AuraColumn()
                                                    .weight(1f)
                                                    .fillHeight()
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .radius(15)
                                                    .padding(15)
                                                    .gap(10)
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
                            .id("reservas")
                            .padding(40,60)
                            .content(reservasCol -> {
                                reservasCol.insert(
                                    new AuraText("Reservaciones Pendientes")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );

                                reservasCol.insert(
                                    new AuraText("No hay reservaciones pendientes")
                                        .font(EstiloGral.LABEL_FONT)
                                        .textColor(EstiloGral.LIGHT_COLOR)
                                        .margin(20)
                                        .id("isNotR")
                                );
                            })
                    );
                })
        );

    }

    public void setReservasDesayuno(Queue<Reserva> reservas){

        AuraColumn reservasColumn = (AuraColumn) find("reservas");

        Queue<Reserva> listaLimpia = new LinkedList<>();
        for(Reserva r : reservas){
            if(r.getEstadoReserva() == Reserva.EstadoReserva.EN_ESPERA){
                listaLimpia.add(r);
            }
        }

        if(listaLimpia.isEmpty()){
            ((AuraText) find("isNotR")).setVisible(true);       
        }else{
            ((AuraText) find("isNotR")).setVisible(false);
            for(Reserva r : listaLimpia){

                if(reservasColumn.find(r.getCedula()) == null){

                    AuraColumn reservaCol = createReserva(r, Menu.TipoMenu.DESAYUNO);

                    reservasColumn.insert(
                        reservaCol
                    );
                }
            }
        }
    }

    public void setReservasAlmuerzo(Queue<Reserva> reservas){

        AuraColumn reservasColumn = (AuraColumn) find("reservas");

        Queue<Reserva> listaLimpia = new LinkedList<>();
        for(Reserva r : reservas){
            if(r.getEstadoReserva() == Reserva.EstadoReserva.EN_ESPERA){
                listaLimpia.add(r);
            }
        }

        if(listaLimpia.isEmpty()){
            ((AuraText) find("isNotR")).setVisible(true);       
        }else{
            ((AuraText) find("isNotR")).setVisible(false);
            for(Reserva r : listaLimpia){

                if(reservasColumn.find(r.getCedula()) == null){

                    AuraColumn reservaCol = createReserva(r, Menu.TipoMenu.ALMUERZO);

                    reservasColumn.insert(
                        reservaCol
                    );
                }
            }
        }
    }

    private AuraColumn createReserva(Reserva res, Menu.TipoMenu tipo){
        return new AuraColumn()
                    .margin(40,40,0,40)
                    .padding(20)
                    .radius(15)
                    .gap(20)
                    .id(res.getCedula() + "-" + tipo.toString())
                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                    .content(r -> {
                        r.insert(
                            new AuraText(res.getCedula() + " - " + res.getEstadoReserva().toString())
                                .font(EstiloGral.LABEL_BOLD_FONT)
                                .textColor(EstiloGral.BG_COLOR)
                        );
 
                        r.insert(
                            new AuraRow()
                                .gap(20)
                                .content(row -> {
                                    row.insert(
                                        new AuraButton("Cancelar")
                                            .textColor(EstiloGral.BG_COLOR)
                                            .background(EstiloGral.ERROR_COLOR)
                                            .font(EstiloGral.LABEL_FONT)
                                            .id("cancelarBtn")
                                    );

                                    row.insert(
                                        new AuraButton("Aprobar")
                                            .textColor(EstiloGral.BG_COLOR)
                                            .background(EstiloGral.GREEN_COLOR)
                                            .font(EstiloGral.LABEL_FONT)
                                            .id("confirmarBtn")
                                    );
                                })
                        );
                    });
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
        return ((AuraInput) find("porcentaje")).getText();
    }

    public String getRole(){
        return ((AuraSelect) find("role")).getText();
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

}

