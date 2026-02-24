package com.comedor.view;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.comedor.model.Menu;
import com.comedor.model.User;

import aura.animations.AnimateBackground;
import aura.animations.AnimateFloat;
import aura.animations.AnimateShake;
import aura.animations.AnimateString;
import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraImage;
import aura.components.AuraMultiText;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;

public class UserMenuView extends AuraWindow {

    public UserMenuView(){
        super("SGCU - Menu de usuario");

        fullScreen()
        .noResizable()
        .background(EstiloGral.BG_COLOR)
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

        insert(
            new AuraRow()
                .fillParent()
                .content(row -> {

                    row.insert(
                        new AuraColumn()
                            .fillHeight()
                            .background(new Color(33, 33, 29))
                            .addBg(new Color(24, 24, 22), 1f)
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

                    row.insert(
                        new AuraColumn()
                            .fillHeight()
                            .weight(1f)
                            .padding(40)
                            .align(AuraColumn.Alignment.LEFT)
                            .background(new Color(71, 71, 58))
                            .addBg(new Color(36, 36, 29), 1f)
                            .backgroundAngle(90)
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
                                                                .id("recharge")
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
                                                                            .id("reservarDesayunoBtn")
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
                                                                            .id("reservarAlmuerzoBtn")
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
        return getClass().getResource(ruta).getFile();
    }

    public void InvalidateInputs(String... ids){

        for(String id : ids){

            AuraBox<?> component = find(id);

            component.background(EstiloGral.WHITE_TRANSP_COLOR);

            AnimateBackground t = new AnimateBackground(component, EstiloGral.ERROR_COLOR, 200)
                                    .pingPong()
                                    .cancelPrev(true);

            AnimateShake t2 = new AnimateShake(component, 5, 500);

            t.parallel(t2).start();
        }

    }

}