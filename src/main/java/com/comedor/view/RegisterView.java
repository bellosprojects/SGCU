package com.comedor.view;

import com.comedor.model.User;

import aura.animations.AnimateBackground;
import aura.animations.AnimateShake;
import aura.components.AuraButton;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraGrid;
import aura.layouts.AuraRow;

public class RegisterView extends AuraWindow {

    public RegisterView() {
        super("SGCU - Registro");

        fullScreen()
        .noResizable()
        .background(new AuraImage(getResourcePath("/images/comedor.png")))
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

        insert(
            new AuraColumn()
                .fillParent()
                .content(column -> {

                    column.insert(
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
                                    new AuraText("SGCU - Registro")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                );
                            })
                    );

                    column.insert(
                        new AuraText("Ingresa tu Cedula")
                                .alignSelf(AuraColumn.Alignment.LEFT)
                                .font(EstiloGral.LABEL_FONT)
                                .textColor(EstiloGral.BG_COLOR)
                                .margin(100, 250, 10, 0)
                    );

                    column.insert(
                        new AuraRow()
                            .gap(40)
                            .fillWidth()
                            .margin(0,200,0,0)
                            .content(cedulaRow -> {

                                cedulaRow.insert(
                                    new AuraInput()
                                        .weight(0.5f)
                                        .padding(15)
                                        .radius(15)
                                        .font(EstiloGral.INPUT_FONT)
                                        .background(EstiloGral.WHITE_TRANSP_COLOR)
                                        .info(createInfo("Cedula sin puntos Ej: 12345678"), 1, 0, 1, 1)
                                        .id("cedula")
                                );

                                cedulaRow.insert(
                                    new AuraButton("Buscar")
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .background(EstiloGral.BUTTON_COLOR)
                                        .radius(15)
                                        .fillHeight()
                                        .id("findUser")
                                );

                                cedulaRow.insert(
                                    new AuraSpacer()
                                );

                            })
                    );


                     column.insert(
                        new AuraRow()
                            .fillWidth()
                            .margin(50,200)
                            .content(dataInfo -> {
                                dataInfo.insert(
                                    new AuraGrid(2, 3)
                                        .weight(1f)
                                        .alignSelf(AuraRow.Alignment.BOTTOM)
                                        .gap(40)
                                        .margin(0,0,0,80)
                                        .content(gridInfo -> {
                                            gridInfo.insert(
                                                new AuraColumn()
                                                    .gap(10)
                                                    .colSpan(2)
                                                    .content(usernameCol -> {
                                                        usernameCol.insert(
                                                            new AuraText("Nombre")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .margin(0,50,0,0)
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .id("usernameLabel")
                                                        );

                                                        usernameCol.insert(
                                                            new AuraText(" ")
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .fillWidth()
                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                .padding(15)
                                                                .radius(15)
                                                                .id("username")
                                                        );

                                                        AuraInput emailInput = new AuraInput()
                                                                                .font(EstiloGral.INPUT_FONT)
                                                                                .fillWidth()
                                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                                .padding(15)
                                                                                .radius(15)
                                                                                .id("email");

                                                        emailInput.setVisible(false);

                                                        usernameCol.insert(
                                                            emailInput
                                                        );
                                                    })
                                            );

                                            gridInfo.insert(
                                                new AuraSpacer()
                                            );

                                            gridInfo.insert(
                                                new AuraColumn()
                                                    .gap(10)
                                                    .content(facuCol -> {
                                                        facuCol.insert(
                                                            new AuraText("Facultad")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .margin(0,50,0,0)
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .id("facuLabel")
                                                        );

                                                        AuraInput passwordInput = new AuraInput()
                                                                                .font(EstiloGral.INPUT_FONT)
                                                                                .fillWidth()
                                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                                .padding(15)
                                                                                .radius(15)
                                                                                .id("password");

                                                        passwordInput.setVisible(false);

                                                        facuCol.insert(
                                                            passwordInput
                                                        );

                                                        facuCol.insert(
                                                            new AuraText(" ")
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .fillWidth()
                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                .padding(15)
                                                                .radius(15)
                                                                .id("facultad")
                                                        );
                                                    })
                                            );

                                            gridInfo.insert(
                                                new AuraColumn()
                                                    .gap(10)
                                                    .content(roleCol -> {
                                                        roleCol.insert(
                                                            new AuraText("Rol")
                                                                .font(EstiloGral.LABEL_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .margin(0,50,0,0)
                                                                .alignSelf(AuraColumn.Alignment.LEFT)
                                                                .id("roleLabel")
                                                        );

                                                        AuraInput passwordInput = new AuraInput()
                                                                                .font(EstiloGral.INPUT_FONT)
                                                                                .fillWidth()
                                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                                .padding(15)
                                                                                .radius(15)
                                                                                .id("confirmPassword");

                                                        passwordInput.setVisible(false);

                                                        roleCol.insert(
                                                            passwordInput
                                                        );

                                                        roleCol.insert(
                                                            new AuraText(" ")
                                                                .font(EstiloGral.INPUT_FONT)
                                                                .fillWidth()
                                                                .background(EstiloGral.WHITE_TRANSP_COLOR)
                                                                .padding(15)
                                                                .radius(15)
                                                                .id("role")
                                                        );
                                                    })
                                            );
                                        })
                                );

                                dataInfo.insert(
                                    new AuraColumn()
                                        .gap(20)
                                        .id("imageColumn")
                                        .content(imageCol -> {
                                            imageCol.insert(
                                                new AuraImage(" ")
                                                    .size(300,300)
                                                    .id("image")
                                                    .radius(15)
                                            );
                                        })
                                );
                            })
                    );

                    column.insert(
                        new AuraSpacer()
                    );


                    column.insert(
                        new AuraRow()
                            .fillWidth()
                            .margin(40,80)
                            .gap(40)
                            .content(footer -> {

                                footer.insert(
                                    new AuraSpacer()
                                );

                                footer.insert(
                                    new AuraButton("Volver")
                                        .background(EstiloGral.GREY_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("backBtn")
                                );

                                AuraButton registerButton = new AuraButton("Registrar")
                                                                .background(EstiloGral.BUTTON_COLOR)
                                                                .font(EstiloGral.MIDDLE_FONT)
                                                                .textColor(EstiloGral.BG_COLOR)
                                                                .id("registerBtn");

                                registerButton.setVisible(false);

                                footer.insert(
                                    registerButton
                                );

                                footer.insert(
                                    new AuraButton("Continuar")
                                        .background(EstiloGral.BUTTON_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .id("nextBtn")
                                );
                            })
                    );

                })
        );
    }

    public void setData(User user){
        ((AuraText) find("username")).text(user.getNombres());
        ((AuraText) find("facultad")).text(user.getFacultadSeleccionada());
        ((AuraText) find("role")).text(user.getRole());
        ((AuraImage) find("image")).background(new AuraImage(EstiloGral.getImgPath(user.getCedula())));
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

    public void nextStep(){

        if(getUsername().trim().isEmpty()) return;

        //Borrar imagen
        ((AuraImage) find("image")).opacity(0f);

        //Cambiar nombre de los Label
        ((AuraText) find("usernameLabel")).text("Correo");
        ((AuraText) find("facuLabel")).text("Contraseña");
        ((AuraText) find("roleLabel")).text("Confirmar Contraseña");

        //Intercambiar text por inputs
        ((AuraText) find("username")).setVisible(false);
        ((AuraInput) find("email")).setVisible(true);

        ((AuraText) find("facultad")).setVisible(false);
        ((AuraInput) find("password")).setVisible(true);

        ((AuraText) find("username")).setVisible(false);
        ((AuraInput) find("email")).setVisible(true);

        ((AuraText) find("role")).setVisible(false);
        ((AuraInput) find("confirmPassword")).setVisible(true);

        //Cambiar boton de next por Registrar
        ((AuraButton) find("nextBtn")).setVisible(false);
        ((AuraButton) find("registerBtn")).setVisible(true);
    }

    public String getCedula(){
        return ((AuraInput) find("cedula")).getText();
    }

    public String getPassword(){
        return ((AuraInput) find("password")).getText();
    }

    public String getEmail(){
        return ((AuraInput) find("email")).getText();
    }

    public String getFacultad(){
        return ((AuraText) find("facultad")).getText();
    }

    public String getConfirmPassword(){
        return ((AuraInput) find("confirmPassword")).getText();
    }

    public String getUsername(){
        return ((AuraText) find("username")).getText();
    }

    private AuraText createInfo(String text){
        return new AuraText(text)
            .background(EstiloGral.BG_COLOR)
            .radius(8)
            .font(EstiloGral.SMALL_FONT)
            .padding(3, 6)
            .margin(0, 0, 10, 25);
    }
}