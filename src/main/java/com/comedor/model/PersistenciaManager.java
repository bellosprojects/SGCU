package com.comedor.model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.comedor.model.Menu.TipoMenu;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;

public class PersistenciaManager {

    private final Path localDir = Path.of("C:", "SGCU", "data");

    private final Path usersFile = localDir.resolve("UsuariosRegistrados.json");
    private final Path desayunoFile = localDir.resolve("Desayuno.json");
    private final Path almuerzoFile = localDir.resolve("Almuerzo.json");
    private final Path pricesFile = localDir.resolve("Tarifas.json");

    private final Path UCVDataBase = Paths.get("src/main/java/com/comedor/database","users.json");

    public static final String SEPARATOR = "<>";
    
    public PersistenciaManager(){
        createLocalData();
    }

    public void crearUsuariosRegistrados(){
        try {
            Files.createFile(usersFile);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al crear el archivo de usuarios registrados", EstiloGral.INFO_MESSAGE);
        }
    }

    private void createLocalData() {

        //Crear directorio local
        try {
            Files.createDirectories(localDir);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al crear directorios" + e.getMessage(), EstiloGral.INFO_MESSAGE);
        }

        //Crear archivos locales
        if(!Files.exists(usersFile)){
            crearUsuariosRegistrados();
        }
        if(!Files.exists(pricesFile)){
            crearTarifa();
        }

    }

    private void crearTarifa(){

        String initialPrices = new Prices().toJson();

        try {
            Files.writeString(
                pricesFile, initialPrices, 
                java.nio.charset.StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error en el archivo del CCB", EstiloGral.INFO_MESSAGE);
        }

    }

    public void guardarTarifa(Double tarifaFinal, String role){

        try{

            if(Files.exists(pricesFile)){                                                                             //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(pricesFile, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
                Prices prices = new Prices();
                prices.fromJSON(lineas.get(0));

                switch (role) {
                    case "Estudiante":
                        prices.setEstudiante(tarifaFinal);
                        break;
                    case "Profesor":
                        prices.setProfesor(tarifaFinal);
                        break;
                    case "Trabajador":
                        prices.setTrabajador(tarifaFinal);
                        break;
                    default:
                        break;
                }

                lineas.clear();
                lineas.add(prices.toJson());

                Files.write(pricesFile, lineas, java.nio.charset.StandardCharsets.UTF_8);
            }

        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al guardar en el archivo del CCB", EstiloGral.INFO_MESSAGE);
        }
    }

    public Double getPorcentajeFromRole(String role){
        try{
            if(Files.exists(pricesFile)){                                    //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(pricesFile, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
                Prices prices = new Prices();
                prices.fromJSON(lineas.get(0));

                return switch(role){
                    case "Estudiante" -> prices.getEstudiante();
                    case "Profesor" -> prices.getProfesor();
                    case "Trabajador" -> prices.getTrabajador();
                    default -> 0.0;
                };
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al leer el archivo de CCB", EstiloGral.INFO_MESSAGE);
        }
        return 0.0;
    }

    public void guardarCCB(Double CCB){
        try{
            if(Files.exists(pricesFile)){                                                                             //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(pricesFile, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
                Prices prices = new Prices();
                prices.fromJSON(lineas.get(0));

                prices.setCCB(CCB);
                lineas.clear();
                lineas.add(prices.toJson());
                Files.write(pricesFile, lineas, java.nio.charset.StandardCharsets.UTF_8);

            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al guardar en el archivo del CCB", EstiloGral.INFO_MESSAGE);
        }
    }

    public Double getCCB(){
        try{
            List<String> lineas = Files.readAllLines(pricesFile, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
            Prices prices = new Prices();
            prices.fromJSON(lineas.get(0));
            return prices.getCCB();
        }
        catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al leer en el archivo del CCB", EstiloGral.INFO_MESSAGE);
        }
        return 0.0;
    }

    public boolean isUserInDataBase(String cedula, String role){
        try{                             //evalua si el archivo existe
            List<String> lineas = Files.readAllLines(UCVDataBase, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
            for(String line : lineas){
                User user = new User();
                user.fromJSON(line);

                if(user.getCedula().equals(cedula)){
                    return user.getRole().equals(role);
                }
            }

        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al obtener el dato", EstiloGral.INFO_MESSAGE);                   
        }
        return false;                 
    }

    public void guardarUsuario(User user){
        
        if(isCedulaRegistered(user.getCedula()) || !isUserInDataBase(user.getCedula(), user.getRole())){
            return;
        }
        
        String newUser = user.toJson();
        //Guardarlo
        try{

            Files.writeString(usersFile, newUser + System.lineSeparator(), 
            java.nio.charset.StandardCharsets.UTF_8,
            StandardOpenOption.APPEND, StandardOpenOption.CREATE);

        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al guardar el usuario", EstiloGral.INFO_MESSAGE);  
        }
    }
          
    private User getUserFromCedula(String cedula){ 
        
        try {
            
            List<String> lineas = Files.readAllLines(usersFile, java.nio.charset.StandardCharsets.UTF_8);
            for(String line : lineas){
                User user = new User();
                user.fromJSON(line);

                if(user.getCedula().equals(cedula)){
                    return user;
                }
            }

        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al cargar el usuario", EstiloGral.INFO_MESSAGE);  
        }

        return null;
    }

    private String getPasswordFromCedula(String cedula){     
        User user = getUserFromCedula(cedula);
        if(user == null){
            return null; 
        }          
        return getUserFromCedula(cedula).getPassword();
    }

    public String getRoleFromCedula(String cedula) {   
        User user = getUserFromCedula(cedula);
        if(user == null){
            return null; 
        }                 
        return getUserFromCedula(cedula).getRole();
    }

    public String getNameFromCedula(String cedula) {   
        User user = getUserFromCedula(cedula);
        if(user == null){
            return null; 
        }                 
        return getUserFromCedula(cedula).getNombres();
    }

    public Double getSaldoFromCedula(String cedula) {    
        User user = getUserFromCedula(cedula);
        if(user == null){
            return null; 
        }                
        return getUserFromCedula(cedula).getSaldo();
    }

    public boolean isCedulaRegistered(String cedula){       
        User user = getUserFromCedula(cedula);
        return user != null;
    }

    public boolean autenticar(String cedula, String password){

        try {
            boolean cedulaExists = isCedulaRegistered(cedula);
            String storedPassword = getPasswordFromCedula(cedula);
            return cedulaExists && storedPassword.equals(ModelUtils.encriptar(password));
        } catch (Exception e) {
            EstiloGral.ShowMessage("Hubo un error al autenticar el usuario", EstiloGral.INFO_MESSAGE);
        }

        return false;
    }

    public void guardarMenu(Menu menu){

        String menuStr = menu.toJson();
        Path rutaDestino = (menu.getTipo() == TipoMenu.DESAYUNO) ? desayunoFile : almuerzoFile;

        try {
            Files.writeString(
                rutaDestino, 
                menuStr + System.lineSeparator(), 
                StandardOpenOption.TRUNCATE_EXISTING, 
                StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al guardar en el archivo del Menu", EstiloGral.INFO_MESSAGE);
        }
    }

    public Menu getMenu(TipoMenu tipo){
        try{
            Path ruta = (tipo == TipoMenu.DESAYUNO) ? desayunoFile : almuerzoFile;
            if(Files.exists(ruta)){                                    //evalua si el archivo existe
                List<String> lineas = Files.readAllLines(ruta);        //crea una lista con todas las lineas del archivo
                if(!lineas.isEmpty()){
                    String linea = lineas.get(0);                             
                    Menu menu = new Menu();
                    menu.fromJson(linea);
                    return menu;
                }
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al leer del archivo del Menu", EstiloGral.INFO_MESSAGE);
        }
        return null;
    }

    public User getUserFromCedulaInDataBase(String cedula){

        
        try{                             //evalua si el archivo existe
            List<String> lineas = Files.readAllLines(UCVDataBase, java.nio.charset.StandardCharsets.UTF_8);        //crea una lista con todas las lineas del archivo
            for(String line : lineas){
                User user = new User();
                user.fromJSON(line);

                if(user.getCedula().equals(cedula)){
                    return user;
                }
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al leer en el archivo de la Base de datos", EstiloGral.INFO_MESSAGE);
        }
        return null;
    }
}
