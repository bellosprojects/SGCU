package com.comedor.view;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;

import com.comedor.model.Menu;
import com.comedor.model.Reserva;
import com.comedor.model.User;

import aura.animations.AnimateBackground;
import aura.animations.AnimateFloat;
import aura.animations.AnimateInteger;
import aura.animations.AnimateShake;
import aura.animations.AnimateString;
import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraModal;
import aura.components.AuraMultiText;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;

public class UserMenuView extends AuraWindow {

    private AuraModal modal;

    public UserMenuView(){
        super("SGCU - Menu de usuario");

        fullScreen()
        .noResizable()
        .background(EstiloGral.BG_COLOR)
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

        SwingUtilities.invokeLater(() -> 
            createModal()
        );

        insert(
            new AuraRow()
                .fillParent()
                .content(row -> {

                    row.insert(
                        new AuraColumn()
                            .fillHeight()
                            .background(EstiloGral.DARK_BG__COLOR)
                            .addBg(EstiloGral.DARK_BG__COLOR.darker(), 1f)
                            .backgroundAngle(90)
                            .padding(20, 0)
                            .weight(0.28f)
                            .content(panelColumn -> {
                                panelColumn.insert(
                                    new AuraRow()
                                        .gap(20)
                                        .content(userRow -> {
                                            userRow.insert(
                                                new AuraImage("")
                                                    .stroke(Color.black, 3)
                                                    .shadow(EstiloGral.WHITE_TRANSP_COLOR, 12)
                                                    .radius(60)
                                                    .margin(10)
                                                    .size(120, 120)
                                                    .id("profileImg")
                                            );

                                            userRow.insert(
                                                new AuraColumn()
                                                    .content(userDataCol -> {
                                                        userDataCol.insert(
                                                            new AuraText("Juan Perez")
                                                                .id("fullname")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.LABEL_BOLD_FONT)
                                                        );

                                                        userDataCol.insert(
                                                            new AuraText("Estudiante")
                                                                .id("role")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                        );
                                                    })
                                            );
                                        })
                                );

                                panelColumn.insert(
                                    new AuraText("12345678")
                                        .id("cedula")
                                        .textColor(EstiloGral.LIGHT_COLOR)
                                        .font(EstiloGral.LABEL_FONT)
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .margin(40,30)
                                    
                                );

                                panelColumn.insert(
                                    new AuraContainer()
                                        .fillWidth()
                                        .height(1)
                                        .background(new Color(71, 71, 58))  
                                );

                                panelColumn.insert(
                                    new AuraText("MENUS")
                                        .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                        .padding(20)
                                        .margin(40,20,20,20)
                                        .fillWidth()
                                        .radius(10)
                                        .cursor(EstiloGral.HOVER_CURSOR)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.LABEL_BOLD_FONT)
                                        .id("menusBtn")
                                );

                                panelColumn.insert(
                                    new AuraText("RESERVACIONES")
                                        .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                        .padding(20)
                                        .margin(10,20,20,20)
                                        .fillWidth()
                                        .radius(10)
                                        .cursor(EstiloGral.HOVER_CURSOR)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.LABEL_BOLD_FONT)
                                        .id("reservationsBtn")
                                );

                                panelColumn.insert(
                                    new AuraSpacer()
                                );

                                panelColumn.insert(
                                    new AuraText("© 2026 SGCU")
                                        .textColor(EstiloGral.LIGHT_COLOR)
                                        .font(EstiloGral.LABEL_FONT)
                                );

                            })

                    );

                    AuraColumn reservationsCol = new AuraColumn()
                                                    .fillHeight()
                                                    .background(EstiloGral.DARK_BG__COLOR2)
                                                    .addBg(EstiloGral.DARK_BG__COLOR2.darker(), 1f)
                                                    .backgroundAngle(90)
                                                    .padding(40)
                                                    .weight(1f)
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .id("reservationsPanel")
                                                    .content(reservaCol -> {
                                                        reservaCol.insert(
                                                            new AuraText("Mis reservaciones")
                                                                .font(EstiloGral.TITLE_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                        );

                                                        reservaCol.insert(
                                                            new AuraText("No tienes reservaciones activas.")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .margin(20)
                                                                .id("haveNotR")
                                                        );
                                                    });

                    reservationsCol.setVisible(false);

                    row.insert(
                        reservationsCol
                    );

                    row.insert(
                        new AuraColumn()
                            .fillHeight()
                            .weight(1f)
                            .padding(40)
                            .align(AuraColumn.Alignment.LEFT)
                            .background(EstiloGral.DARK_BG__COLOR2)
                            .addBg(EstiloGral.DARK_BG__COLOR2.darker(), 1f)
                            .backgroundAngle(90)
                            .id("MenuPanel")
                            .content(mainColumn -> {

                                mainColumn.insert(
                                    new AuraText("Bienvenido de vuelta.")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );

                                mainColumn.insert(
                                    new AuraRow()
                                        .fillWidth()
                                        .gap(20)
                                        .margin(0,0,5,0)
                                        .align(AuraRow.Alignment.BOTTOM)
                                        .content(optionsRow -> {
                                            optionsRow.insert(
                                                new AuraText("Menus disponibles en la fecha: 1 de diciembre del 2025")
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .textColor(EstiloGral.LIGHT_COLOR)
                                                    .id("date")
                                            );

                                            optionsRow.insert(
                                                new AuraSpacer()
                                            );

                                            optionsRow.insert(
                                                new AuraButton("Cerrar sesión")
                                                    .id("backBtn")
                                                    .font(EstiloGral.LABEL_FONT)
                                                    .radius(25)
                                            );
                                        })
                                );

                                mainColumn.insert(
                                    new AuraRow()
                                        .margin(60, 0)
                                        .fillWidth()
                                        .gap(40)
                                        .content(pricesRow -> {
                                            pricesRow.insert(
                                                new AuraColumn()
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .padding(30, 40, 30, 100)
                                                    .gap(10)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .content(carterColumn -> {
                                                        carterColumn.insert(
                                                            new AuraText("Fondos disponibles")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                        );

                                                        carterColumn.insert(
                                                            new AuraText("0,00Bs")
                                                                .id("credits")
                                                                .font(EstiloGral.MIDDLE_FONT2)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                        );

                                                        carterColumn.insert(
                                                            new AuraButton("Recargar")
                                                                .radius(25)
                                                                .id("rechargeBtn")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .margin(10, 0, 0, 0)

                                                        );
                                                    })
                                            );

                                            pricesRow.insert(
                                                new AuraColumn()
                                                    .align(AuraColumn.Alignment.LEFT)
                                                    .padding(30, 40, 30, 100)
                                                    .gap(10)
                                                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                    .stroke(EstiloGral.WHITE_TRANSP_COLOR, 1)
                                                    .fillHeight()
                                                    .content(ccbColumn -> {
                                                        ccbColumn.insert(
                                                            new AuraText("Costo de la bandeja")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                        );

                                                        ccbColumn.insert(
                                                            new AuraText("0,00Bs")
                                                                .id("price")
                                                                .font(EstiloGral.MIDDLE_FONT2)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                        );

                                                        ccbColumn.insert(
                                                            new AuraSpacer()
                                                        );

                                                        ccbColumn.insert(
                                                            new AuraText("Precio para: Estudiante")
                                                                .textColor(EstiloGral.LIGHT_COLOR)
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .id("rolePrice")
                                                        );
                                                    })
                                            );

                                            pricesRow.insert(
                                                new AuraSpacer()
                                            );

                                            pricesRow.insert(
                                                new AuraText("")
                                                    .textColor(EstiloGral.BG_COLOR)
                                                    .font(EstiloGral.CLOCK_FONT)
                                                    .alignSelf(AuraRow.Alignment.TOP)
                                                    .margin(10, 20)
                                                    .id("animate")
                                            );

                                        })      
                                );

                                mainColumn.insert(
                                    new AuraText("Menus disponibles")
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .margin(0,20,0,0)
                                );

                                mainColumn.insert(
                                    new AuraRow()
                                        .fillWidth()
                                        .margin(60,0,0,0)
                                        .gap(40)
                                        .weight(1f)
                                        .content(menusRow -> {

                                            menusRow.insert(
                                                new AuraColumn()
                                                    .weight(1f)
                                                    .fillHeight()
                                                    .content(desayunoColumn -> {
                                                        desayunoColumn.insert(
                                                            new AuraText("Desayuno")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .margin(0,60)
                                                        );

                                                        desayunoColumn.insert(
                                                            new AuraColumn()
                                                                .weight(1f)
                                                                .fillWidth()
                                                                .margin(30, 0, 0, 10)
                                                                .radius(20)
                                                                .background(new AuraImage(getResourcePath("/images/desayunoBackground.jpg")))
                                                                .content(desayuno -> {
                                                                    desayuno.insert(
                                                                        new AuraButton("Reservar")
                                                                            .alignSelf(AuraColumn.Alignment.RIGHT)
                                                                            .radius(0, 20, 10, 0)
                                                                            .background(EstiloGral.BUTTON_COLOR)
                                                                            .font(EstiloGral.LABEL_BOLD_FONT)
                                                                            .textColor(EstiloGral.BG_COLOR)
                                                                            .id("bookBreakfastBtn")
                                                                    );

                                                                    desayuno.insert(
                                                                        new AuraMultiText("")
                                                                            .textColor(EstiloGral.LIGHT_COLOR)
                                                                            .weight(1f)
                                                                            .id("desayunoIng")
                                                                            .block()
                                                                            .background(EstiloGral.TRANSPARENT_COLOR)
                                                                            .font(EstiloGral.LABEL_FONT)
                                                                            .fillWidth()
                                                                            .margin(20, 35)
                                                                    );

                                                                    desayuno.insert(
                                                                        new AuraText("Arepa")
                                                                            .textColor(EstiloGral.BG_COLOR)
                                                                            .font(EstiloGral.MIDDLE_FONT)
                                                                            .padding(20, 0)
                                                                            .fillWidth()
                                                                            .radius(0, 20)
                                                                            .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                                            .id("desayuno")

                                                                    );
                                                                })
                                                        );
                                                    })
                    
                                            );

                                            menusRow.insert(
                                                new AuraColumn()
                                                    .weight(1f)
                                                    .fillHeight()
                                                    .content(almuerzoColumn -> {
                                                        almuerzoColumn.insert(
                                                            new AuraText("Almuerzo")
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .margin(0,60)
                                                        );

                                                        almuerzoColumn.insert(
                                                            new AuraColumn()
                                                                .weight(1f)
                                                                .fillWidth()
                                                                .margin(30, 0, 0, 10)
                                                                .radius(20)
                                                                .background(new AuraImage(getResourcePath("/images/almuerzoBackground.jpg")))
                                                                .content(almuerzo -> {
                                                                    almuerzo.insert(
                                                                        new AuraButton("Reservar")
                                                                            .alignSelf(AuraColumn.Alignment.RIGHT)
                                                                            .radius(0, 20, 10, 0)
                                                                            .background(EstiloGral.BUTTON_COLOR)
                                                                            .font(EstiloGral.LABEL_BOLD_FONT)
                                                                            .textColor(EstiloGral.BG_COLOR)
                                                                            .id("bookLunchBtn")
                                                                    );

                                                                    almuerzo.insert(
                                                                        new AuraMultiText("")
                                                                            .textColor(EstiloGral.LIGHT_COLOR)
                                                                            .weight(1f)
                                                                            .id("almuerzoIng")
                                                                            .block()
                                                                            .background(EstiloGral.TRANSPARENT_COLOR)
                                                                            .font(EstiloGral.LABEL_FONT)
                                                                            .fillWidth()
                                                                            .margin(20, 35)
                                                                    );

                                                                    almuerzo.insert(
                                                                        new AuraText("Arroz")
                                                                            .textColor(EstiloGral.BG_COLOR)
                                                                            .font(EstiloGral.MIDDLE_FONT)
                                                                            .padding(20, 0)
                                                                            .fillWidth()
                                                                            .radius(0, 20)
                                                                            .background(EstiloGral.WHITE_TRANSP_COLOR2)
                                                                            .id("almuerzo")

                                                                    );
                                                                })
                                                        );
                                                    })
                    
                                            );

                                        })
                                );

                            })
                    );

                })
        );

        new AnimateFloat(0, 1, 8000, v -> {
            if(v == 0f) startAnimation();
        })
        .loop()
        .start();
    }

    private void startAnimation(){

        AuraText component = (AuraText) find("animate");

        new AnimateString(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")), 3000, value -> {

            component.text(value);

        })
        .type(Transition.TransitionType.BOUNCE_IN)
        .start();

    } 

    public void showReservas(){
        ((AuraColumn) find("reservationsPanel")).setVisible(true);
        ((AuraColumn) find("MenuPanel")).setVisible(false);
    }

    public void showMenus(){
        ((AuraColumn) find("reservationsPanel")).setVisible(false);
        ((AuraColumn) find("MenuPanel")).setVisible(true);
    }

    public void addReserva(AuraRow reserva, Menu.TipoMenu tipo){

        if(reserva == null) return;

        ((AuraText) find("haveNotR")).setVisible(false);

        ((AuraColumn) find("reservationsPanel")).insert(reserva);

    }

    public AuraRow createReservaCard(Reserva reserva, Menu.TipoMenu tipo){

        Color stateColor = switch(reserva.getEstadoReserva()) {
            case RESERVADO -> EstiloGral.GREEN_COLOR;
            case CANCELADO -> EstiloGral.ERROR_COLOR;
            case EN_ESPERA -> EstiloGral.LIGHT_COLOR;
        };

        return new AuraRow()
                    .fillWidth()
                    .gap(20)
                    .padding(20)
                    .margin(20,0)
                    .background(EstiloGral.WHITE_TRANSP_COLOR2)
                    .radius(15)
                    .id(tipo.toString())
                    .content(row -> {
                        row.insert(

                            new AuraText(tipo.toString())
                                .font(EstiloGral.INPUT_FONT)
                                .textColor(EstiloGral.BG_COLOR)
                        );

                        row.insert(
                            new AuraSpacer()
                        );

                        row.insert(
                            new AuraText(reserva.getEstadoReserva().toString())
                                .font(EstiloGral.MIDDLE_FONT)
                                .textColor(stateColor)
                        );

                        if(reserva.getEstadoReserva() != Reserva.EstadoReserva.CANCELADO){
                            row.insert(
                                new AuraButton("Cancelar")
                                    .background(EstiloGral.ERROR_COLOR)
                                    .font(EstiloGral.LABEL_FONT)
                                    .textColor(EstiloGral.BG_COLOR)
                                    .radius(15)
                                    .id("cancelarReserva")
                            );
                        }

                    });
    }

    public void setDesayuno(Menu desayuno){

        if(desayuno == null) return;

        ((AuraText) find("date")).text("Menus disponibles en la fecha: " + desayuno.getFecha());

        ((AuraText) find("desayuno")).text(desayuno.getPlato());

        ((AuraMultiText) find("desayunoIng")).text("Ingredientes: " + desayuno.getIngredientes());

    }

    public void setAlmuerzo(Menu almuerzo){

        if(almuerzo == null) return;

        ((AuraText) find("date")).text("Menus disponibles en la fecha: " + almuerzo.getFecha());

        ((AuraText) find("almuerzo")).text(almuerzo.getPlato());

        ((AuraMultiText) find("almuerzoIng")).text("Ingredientes: " + almuerzo.getIngredientes());

    }

    public void setUser(User user){
        find("profileImg").background(new AuraImage(EstiloGral.getImgPath(user.getCedula())));
        ((AuraText) find("fullname")).text(user.getNombres());
        ((AuraText) find("role")).text(user.getRole());
        ((AuraText) find("rolePrice")).text("Precio para: " + user.getRole());
        ((AuraText) find("cedula")).text("C.I: " + user.getCedula());

        AuraText credits = (AuraText) find("credits");
        
        new AnimateFloat(0.0f, (float) (double) user.getSaldo() , 1800, value -> {

            credits.text(String.format("%.2fBs", value));

        })
        .delay(1500)
        .start();
    }

    public void setPrecio(double precio){
        AuraText price = (AuraText) find("price");

        new AnimateFloat(0.0f, (float) precio, 1800, value -> {

            price.text(String.format("%.2fBs", value));

        })
        .delay(1500)
        .start();
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

    private void createModal(){

        modal = new AuraModal(this);

        AuraColumn columnModal = new AuraColumn()
                        .background(EstiloGral.BG_COLOR)
                        .radius(15)
                        .padding(30)
                        .content(col -> {
                            col.insert(
                                new AuraText("Recargar")
                                    .font(EstiloGral.MIDDLE_FONT)
                                    .textColor(EstiloGral.DARK_COLOR)
                                    .margin(5,120,40,120)
                            );

                            col.insert(
                                new AuraText("Monto")
                                    .font(EstiloGral.LABEL_FONT)
                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                    .margin(0,50,10,0)
                            );

                            col.insert(
                                new AuraInput()
                                    .radius(15)
                                    .padding(15)
                                    .font(EstiloGral.INPUT_FONT)
                                    .textColor(EstiloGral.BG_COLOR)
                                    .carterColor(EstiloGral.BG_COLOR)
                                    .background(EstiloGral.DARK_COLOR)
                                    .fillWidth()
                                    .id("rechargeMonto")
                            );

                            col.insert(
                                new AuraText("Nro. de Referencia")
                                    .font(EstiloGral.LABEL_FONT)
                                    .alignSelf(AuraColumn.Alignment.LEFT)
                                    .margin(40,50,10,0)
                            );

                            col.insert(
                                new AuraInput()
                                    .radius(15)
                                    .padding(15)
                                    .font(EstiloGral.INPUT_FONT)
                                    .textColor(EstiloGral.BG_COLOR)
                                    .carterColor(EstiloGral.BG_COLOR)
                                    .background(EstiloGral.DARK_COLOR)
                                    .fillWidth()
                                    .id("rechargeRef")
                            );

                            col.insert(
                                new AuraRow()
                                    .gap(40)
                                    .margin(40,0,0,0)
                                    .content(optionsRow -> {
                                        optionsRow.insert(
                                            new AuraButton("Cancelar")
                                                .background(EstiloGral.GREY_COLOR)
                                                .font(EstiloGral.INPUT_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .onClick(b -> {
                                                    modal.close();
                                                })
                                        );

                                        optionsRow.insert(
                                            new AuraButton("Confirmar")
                                                .background(EstiloGral.BUTTON_COLOR)
                                                .font(EstiloGral.INPUT_FONT)
                                                .textColor(EstiloGral.BG_COLOR)
                                                .id("confirmRechargeBtn")
                                        );
                                    })
                            );
                        });


        modal.content(columnModal);
    }

    public void showRecharge(){ 
        modal.display();
    }

    public void hideRecharge(){
        ((AuraInput) modal.find("rechargeMonto")).text("");
        ((AuraInput) modal.find("rechargeRef")).text("");
        modal.close();
    }

    public AuraModal getModal(){
        return this.modal;
    }

    public void updateSaldo(Double saldo){
        AuraText credits = (AuraText) find("credits");
        
        new AnimateFloat(0.0f, (float) (double) saldo , 1800, value -> {

            credits.text(String.format("%.2fBs", value));

        })
        .delay(1000)
        .start();
    }

    public String getMonto(){
        return ((AuraInput) modal.find("rechargeMonto")).getText();
    }

    public String getNumeroReferencia(){
        return ((AuraInput) modal.find("rechargeRef")).getText();
    }

    public void verificarFaceId(String path){
        AuraModal faceIdModal = new AuraModal(this);

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
                                                            new AuraImage(EstiloGral.getImgPath(((AuraText) find("cedula")).getText().replace("C.I: ", "")))
                                                                .radius(15)
                                                                .size(300,300)
                                                        );

                                                    })
                                            );
                                        })
                                );
                            });

        faceIdModal.content(content);
        faceIdModal.display();

        new AnimateInteger(-2, 300, 1000, value -> {
            barra.setLocation(0, value);
            barra2.setLocation(0, value);
        })
        .delay(500)
        .type(Transition.TransitionType.EASE_IN_OUT)
        .pingPong()
        .then(() -> {
            faceIdModal.close();
        })
        .start();

    }
}