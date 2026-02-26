package com.comedor.view;

import aura.animations.AnimateBackground;
import aura.animations.AnimateColor;
import aura.animations.AnimateScale;
import aura.animations.AnimateShake;
import aura.components.AuraButton;
import aura.components.AuraImage;
import aura.components.AuraInput;
import aura.components.AuraSpacer;
import aura.components.AuraText;
import aura.components.AuraWindow;
import aura.core.AuraBox;
import aura.layouts.AuraColumn;
import aura.layouts.AuraGrid;
import aura.layouts.AuraRow;

public class LoginView extends AuraWindow {

    public LoginView() {
        super("SGCU - Iniciar sesion");

        fullScreen()
        .noResizable()
        .background(new AuraImage(getResourcePath("/images/comedor.png")))
        .icon(new AuraImage(getResourcePath("/images/logoColor.png")));

        AuraButton loginButton = new AuraButton("Iniciar Sesión")
                                        .margin(20, 40)
                                        .background(EstiloGral.DARK_COLOR)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .font(EstiloGral.MIDDLE_FONT)
                                        .shadow(EstiloGral.WHITE_TRANSP_COLOR, 7)
                                        .id("loginBtn")
                                        .onHover((b, h) -> {
                                            new AnimateBackground(b, h? EstiloGral.LIGHT_COLOR : EstiloGral.DARK_COLOR, 250).start();
                                            new AnimateColor(h ? EstiloGral.BG_COLOR : EstiloGral.DARK_COLOR, h? EstiloGral.DARK_COLOR : EstiloGral.BG_COLOR, 250, color -> {
                                                b.textColor(color);
                                            }).start();
                                            new AnimateScale(b, h? 1.03f : 1, 150).start();
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
                                    .margin(0, 40)
                                    .info(createInfo("Cedula sin puntos Ej: 12345678"), 1, 0, 1, 1)
                                    .id("cedula");

        AuraInput passwordInput = new AuraInput()
                                    .fillWidth()
                                    .padding(15)
                                    .radius(15)
                                    .font(EstiloGral.INPUT_FONT)
                                    .background(EstiloGral.WHITE_TRANSP_COLOR)
                                    .margin(0, 40)
                                    .info(createInfo("Contraseña sin espacios"), 1, 0, 1, 1)
                                    .id("password");

        insert(

            new AuraGrid(8, 10)
                .fillParent()
                .content(grid -> {

                    grid.insert(
                        new AuraSpacer()
                            .colSpan(3)
                            .rowSpan(7)
                        );

                    grid.insert(
                        new AuraColumn()
                            .colSpan(4)
                            .rowSpan(7)
                            .padding(20, 0, 0, 0)
                            .content(col -> {

                                col.insert(
                                    new AuraImage(getResourcePath("/images/logoWhite.png"))
                                        .size(200, 200)
                                    );

                                col.insert(
                                    new AuraText("SGCU - Iniciar Sesión")
                                        .font(EstiloGral.TITLE_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                    );

                                col.insert(
                                    new AuraSpacer()
                                        .weight(1f)
                                    );

                                col.insert(
                                    new AuraText("CEDULA")
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .font(EstiloGral.LABEL_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(0, 90, 10, 0)
                                    );

                                col.insert(cedulaInput);

                                col.insert(
                                    new AuraText("CONTRASEÑA")
                                        .alignSelf(AuraColumn.Alignment.LEFT)
                                        .font(EstiloGral.LABEL_FONT)
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(80, 90, 10, 0)
                                    );

                                col.insert(passwordInput);

                                col.insert(
                                    new AuraRow()
                                        .fillWidth()
                                        .margin(20, 60, 0, 60)
                                        .content(options -> {
                                        
                                            options.add(new AuraSpacer().weight(1f));
                                            options.add(registerButton);

                                        })
                                    );

                                col.insert(
                                    new AuraSpacer()
                                        .weight(0.75f)
                                    );

                            })
                        );

                    grid.insert(
                        new AuraSpacer()
                            .colSpan(3)
                            .rowSpan(7)
                        );

                    grid.insert(
                        new AuraRow()
                            .colSpan(10)
                            .align(AuraRow.Alignment.BOTTOM)
                            .content(footer -> {

                                footer.insert(
                                    new AuraText("© 2026 SGCU. Todos los derechos reservados.")
                                        .textColor(EstiloGral.BG_COLOR)
                                        .margin(20, 40)
                                        .font(EstiloGral.LABEL_FONT)
                                    );

                                footer.insert(
                                    new AuraSpacer()
                                        .weight(1f)
                                    );

                                footer.insert(loginButton);

                            })
                    );

                })

        );
    }

    private static String getResourcePath(String ruta) {
        return LoginView.class.getResource(ruta).getFile();
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

}