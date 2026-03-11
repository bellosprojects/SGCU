package com.comedor.view;

import aura.animations.AnimateBackground;
import aura.animations.AnimateScale;
import aura.animations.AnimateShake;
import aura.components.AuraButton;
import aura.components.AuraContainer;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.core.AuraBox;
import aura.core.Transition;
import aura.layouts.AuraColumn;
import aura.layouts.AuraRow;

public class LoginView extends AuraContainer {

    public LoginView() {
        background(new AuraImage(getResourcePath("/images/comedor.png")));
        initializeComponents();
    }

    private void initializeComponents() {
        AuraButton loginButton = new AuraButton("Iniciar Sesión")
                                        .margin(20, 40)
                                        .background(EstiloGral.DARK_COLOR)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .shadow(EstiloGral.WHITE_TRANSP_COLOR, 7)
                                        .id("loginBtn")
                                        .onHover((b, h) -> {
                                            b.cancelAnimations(Transition.AnimationType.BACKGROUND);
                                            new AnimateBackground(b, h? b.getBackgroundColor().brighter() : b.getBackgroundColor(), 150).start();
                                            new AnimateScale(b, h? 1.05f : 1, 150).start();
                                        });
        
        AuraText registerButton = new AuraText("¿No tienes cuenta? Registrate")
                                        .font(EstiloGral.SMALL_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .cursor(EstiloGral.HOVER_CURSOR)
                                        .id("registerBtn");

        AuraInput cedulaInput = new AuraInput()
                                    .fillWidth()
                                    .padding(15)
                                    .radius(15)
                                    .font(EstiloGral.INPUT_FONT)
                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                    .info(createInfo("Cedula sin puntos Ej: 12345678"), 1, 0, 1, 1)
                                    .id("cedula");

        AuraInput passwordInput = new AuraInput()
                                    .fillWidth()
                                    .padding(15)
                                    .radius(15)
                                    .font(EstiloGral.INPUT_FONT)
                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                    .info(createInfo("Contraseña sin espacios"), 1, 0, 1, 1)
                                    .id("password");

        insert(

            new AuraColumn()
                .padding(20)
                .fillParent()
                .content(col -> {

                    col.insert(
                        new AuraImage(getResourcePath("/images/logoWhite.png"))
                            .heightPorc(0.2f)
                            .maximalSize(200, 200)
                            .minimunSize(120, 120)
                            .ratio(1)
                        );

                    col.insert(
                        new AuraText("Iniciar Sesión")
                            .font(EstiloGral.TITLE_FONT)
                            .textColor(EstiloGral.BG_COLOR)
                        );


                    col.insert(
                        new AuraColumn()
                            .widthPorc(0.5f)
                            .minimunSize(450, -1)
                            .maximalSize(650, -1)
                            .weight(1f)
                            .content(innerColumn -> {

                                innerColumn.insert(
                                    new AuraSpacer()
                                );

                                innerColumn.insert(
                                    new AuraText("CEDULA")
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .font(EstiloGral.LABEL_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(0, 50, 10, 0)
                                    );

                                innerColumn.insert(cedulaInput);

                                innerColumn.insert(
                                    new AuraSpacer()
                                        .weight(0.35f)
                                );


                                innerColumn.insert(
                                    new AuraText("CONTRASEÑA")
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .font(EstiloGral.LABEL_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(0, 50, 10, 0)
                                    );

                                innerColumn.insert(passwordInput);

                                innerColumn.insert(
                                    registerButton
                                        .margin(10,0)
                                        .alignSelf(AuraColumn.Alignment.RIGHT)
                                );

                                innerColumn.insert(
                                    new AuraSpacer()
                                );

                            })
                    );


                    col.insert(
                        new AuraRow()
                            .fillWidth()
                            .align(AuraRow.Alignment.BOTTOM)
                            .content(footer -> {

                                footer.insert(
                                    new AuraText("© 2026 SGCU. Todos los derechos reservados.")
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(0, 0, 20, 0)
                                        .font(EstiloGral.LABEL_FONT)
                                        .textAlign(AuraColumn.Alignment.LEFT)
                                        .weight(1f)
                                    );


                                footer.insert(loginButton);

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

    private AuraText createInfo(String text){
        return new AuraText(text)
            .background(EstiloGral.BG_COLOR)
            .radius(8)
            .font(EstiloGral.SMALL_FONT)
            .padding(3, 6)
            .margin(0, 0, 10, 25);
    }

    public String getCedula(){
        return ((AuraInput) find("cedula")).getText();
    }

    public String getPassword(){
        return ((AuraInput) find("password")).getText();
    }

    public void reset(){
        ((AuraInput) find("cedula")).text("");
        ((AuraInput) find("password")).text("");
    }
}