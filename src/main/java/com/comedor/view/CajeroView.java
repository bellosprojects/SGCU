package com.comedor.view;

import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;
import aura.layouts.AuraColumn.Alignment;

import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraImage;
import aura.components.AuraModal;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;

import aura.animations.AnimateInteger;

import java.util.LinkedList;
import java.util.Queue;

import com.comedor.model.Menu;
import com.comedor.model.Reserva;

import aura.core.Transition;

public class CajeroView extends AuraContainer {

    private AuraModal modal;

	public CajeroView() {

        background(new AuraImage(getResourcePath("/images/comedor.png")));

        insert(
            new AuraRow()
                .fillParent()
                .content(mainRow -> {
                    mainRow.insert(
                        new AuraColumn()
                            .weight(1f)
                            .fillHeight()
                            .align(Alignment.LEFT)
                            .content(leftCol -> {

                                leftCol.insert(
                                    new AuraRow()
                                        .gap(40)
                                        .padding(10, 40, 0, 0)
                                        .content(row -> {
                                            row.insert(
                                                new AuraImage(getResourcePath("/images/logoWhite.png"))
                                                    .size(200, 200)
                                            );

                                            row.insert(
                                                new AuraText("SGCU - Gestionar Reservas")
                                                    .font(EstiloGral.TITLE_FONT)
                                                    .textColor(EstiloGral.BG_COLOR)
                                            );
                                        })
                                );

                                leftCol.insert(
                                    new AuraSpacer()
                                );

                                leftCol.insert(
                                    new AuraButton("Volver")
                                        .background(EstiloGral.GREY_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(40, 80)
                                        .id("backBtn")
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
                            .weight(0.4f)
                            .padding(40,60)
                            .content(reservasCol -> {
                                reservasCol.insert(
                                    new AuraText("Reservaciones Pendientes")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
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

        if(!listaLimpia.isEmpty()){
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

        if(!listaLimpia.isEmpty()){
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
                            new AuraText(res.getCedula() + " - " + tipo.toString())
                                .font(EstiloGral.LABEL_BOLD_FONT)
                                .textColor(EstiloGral.BG_COLOR)
                                .id(res.getCedula())
                        );

                        r.insert(
                            new AuraButton("Verificar")
                                .textColor(EstiloGral.BG_COLOR)
                                .background(EstiloGral.GREEN_COLOR)
                                .font(EstiloGral.LABEL_FONT)
                                .id("confirmarBtn")
                        );
                        
                    });
    }

    public void removeReserva(AuraColumn reservaCol){

        AuraColumn reservasColumn = (AuraColumn) find("reservas");

        new AnimateInteger(0, reservaCol.getPreferredSize().width, 300, value -> {
            reservaCol.offset(-value, 0);
            reservasColumn.revalidate();
        })
        .then(() -> {
            reservasColumn.remove(reservaCol);
            reservasColumn.revalidate();
        })
        .start();
    }
    
    private String getResourcePath(String ruta) {
        return getClass().getResource(ruta).toString();
    }

    public void createModal(AuraWindow parent){
        modal = new AuraModal(parent);
    }

    public void verificarFaceId(String path, String cedula){

        modal.removeAll();

        AuraContainer barra = new AuraContainer()
                                .background(EstiloGral.GREEN_COLOR)
                                .size(300, 2);

        AuraContainer barra2 = new AuraContainer()
                                .background(EstiloGral.GREEN_COLOR)
                                .size(300, 2);

        AuraColumn content = new AuraColumn()
                            .gap(40)
                            .padding(20)
                            .radius(15)
                            .background(EstiloGral.BG_COLOR)
                            .content(col -> {
                                col.insert(
                                    new AuraText("Verificando identidad...")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.DARK_COLOR)
                                );

                                col.insert(
                                    new AuraRow()
                                        .gap(50)
                                        .content(row -> {
                                            row.insert(
                                                new AuraContainer()
                                                    .radius(15)
                                                    .clipChildrens(true)
                                                    .size(300, 300)
                                                    .content(f1 -> {
                                                        f1.insert(
                                                            barra
                                                            , 0, -2
                                                        );

                                                        f1.insert(
                                                            new AuraImage(path)
                                                                .radius(15)
                                                                .size(300,300)
                                                        );

                                                    })
                                            );

                                            row.insert(
                                                new AuraContainer()
                                                    .radius(15)
                                                    .size(300, 300)
                                                    .content(f2 -> {
                                                        f2.insert(
                                                            barra2
                                                            , 0, -2
                                                        );

                                                        f2.insert(
                                                            new AuraImage(EstiloGral.getImgPath(cedula))
                                                                .radius(15)
                                                                .size(300,300)
                                                        );

                                                    })
                                            );
                                        })
                                );
                            });

        modal.content(content);
        modal.display();

        new AnimateInteger(-2, 300, 1000, value -> {
            barra.setLocation(0, value);
            barra2.setLocation(0, value);
        })
        .delay(500)
        .type(Transition.TransitionType.EASE_IN_OUT)
        .pingPong()
        .then(() -> {
            modal.close();
        })
        .start();

    }


}
