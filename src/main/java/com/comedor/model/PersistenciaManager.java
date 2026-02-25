package com.comedor.model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.Reserva.EstadoReserva;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;

public class PersistenciaManager {

    private final Path localDir = Path.of("C:", "SGCU", "data");
    private final Path usersFile = localDir.resolve("UsuariosRegistrados.json");
    private final Path desayunoFile = localDir.resolve("Desayuno.json");
    private final Path almuerzoFile = localDir.resolve("Almuerzo.json");
    private final Path pricesFile = localDir.resolve("Tarifas.json");
    private final Path reservaDesayuno = localDir.resolve("ReservaDesayuno.json");
    private final Path reservaAlmuerzo = localDir.resolve("ReservaAlmuerzo.json");

    private final Path UCVDataBase = Paths.get("src/main/java/com/comedor/database","Users.json");

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
        if(!Files.exists(reservaDesayuno)){
            crearReservaDesayuno();
        }
        if(!Files.exists(reservaAlmuerzo)){
            crearReservaAlmuerzo();
        }

    }

    private void crearReservaDesayuno(){
        try {
            Files.createFile(reservaDesayuno);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al crear el archivo de reserva de usuarios", EstiloGral.INFO_MESSAGE);
        }
    }

    private void crearReservaAlmuerzo(){
        try {
            Files.createFile(reservaAlmuerzo);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al crear el archivo de reserva de usuarios", EstiloGral.INFO_MESSAGE);
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
                    case "Estudiante" -> prices.setEstudiante(tarifaFinal);
                    case "Profesor" -> prices.setProfesor(tarifaFinal);
                    case "Trabajador" -> prices.setTrabajador(tarifaFinal);
                    default -> {
                        EstiloGral.ShowMessage("Rol no reconocido. No se guardó la tarifa.", EstiloGral.ERROR_MESSAGE);
                        return;
                    }
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

        user.encriptPassword();

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

    public void recargarSaldo(String cedula, double nuevoSaldo) {
        try {
            List<String> lineas = Files.readAllLines(usersFile, java.nio.charset.StandardCharsets.UTF_8);
            for (int i = 0; i < lineas.size(); i++) {
                User user = new User();
                user.fromJSON(lineas.get(i));

                if (user.getCedula().equals(cedula)) {
                    user.setSaldo(nuevoSaldo);
                    lineas.set(i, user.toJson());
                    break;
                }
            }
            Files.write(usersFile, lineas, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al recargar el saldo", EstiloGral.INFO_MESSAGE);
        }
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
        Path rutaDestino = (menu.getTipo() == TipoMenu.DESAYUNO) ? desayunoFile : almuerzoFile;
            
        String menuStr = menu.toJson();

        if(menu.getTipo() == TipoMenu.DESAYUNO){
            vaciarListaDesayuno();
        } else {
            vaciarListaAlmuerzo();
        }

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

    private void vaciarListaDesayuno(){
        try {
            Files.writeString(desayunoFile, "", StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al vaciar el archivo del Desayuno", EstiloGral.INFO_MESSAGE);
        }
    }
    private void vaciarListaAlmuerzo(){
        try {
            Files.writeString(almuerzoFile, "", StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            EstiloGral.ShowMessage("Hubo un error al vaciar el archivo del Almuerzo", EstiloGral.INFO_MESSAGE);
        }
    }         
    //hacer otra funcion aparte para privacidad

    private void modificarEstado(Reserva usuario, TipoMenu tipo){
        try{
            Path ruta = (tipo == TipoMenu.DESAYUNO) ? reservaDesayuno : reservaAlmuerzo;
            if(Files.exists(ruta)){                                    
                List<String> lineas = Files.readAllLines(ruta);    
                Reserva reserva= new Reserva();  
                for(int i = 0; i < lineas.size(); i++){
                    reserva.fromJSON(lineas.get(i)); 
                    if(usuario.getCedula().equals(reserva.getCedula())&&reserva.getEstadoReserva()!=EstadoReserva.CANCELADO){
                        if(usuario.getEstadoReserva()==EstadoReserva.CANCELADO){
                            reserva.setEstadoReserva(usuario.getEstadoReserva());
                            aumentarCupo(tipo);
                        }
                        else if(reserva.getEstadoReserva()==EstadoReserva.EN_ESPERA&&usuario.getEstadoReserva()==EstadoReserva.RESERVADO){
                            reserva.setEstadoReserva(usuario.getEstadoReserva());
                        }
                        lineas.set(i, reserva.toJSON());
                    }
                }
                Files.write(ruta, lineas, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al modificar el estado de la reserva", EstiloGral.INFO_MESSAGE);
        }
    }  

    public void cancelarReserva(String cedula, TipoMenu tipo){

        recargarSaldo(cedula, getSaldoFromCedula(cedula) + getPrecioFromCedula(cedula));

        modificarEstado(new Reserva(cedula, EstadoReserva.CANCELADO), tipo);
    }

    public void aceptarReserva(String cedula, TipoMenu tipo){
        modificarEstado(new Reserva(cedula, EstadoReserva.RESERVADO), tipo);
    }

    public Reserva.EstadoIntento intentarReservar(String cedula, TipoMenu tipo){
        Reserva reservaDes = getReservaFromCedula(cedula, tipo);
        if(reservaDes != null){
            if(reservaDes.getEstadoReserva() == EstadoReserva.CANCELADO){
                return Reserva.EstadoIntento.RESERVA_CANCELADA;
            }
            return Reserva.EstadoIntento.YA_TIENE_RESERVA;
        }
        if(getCupos(tipo) <= 0){
            return Reserva.EstadoIntento.NO_HAY_CUPO;
        }
        return (getSaldoFromCedula(cedula) >= getPrecioFromCedula(cedula))? Reserva.EstadoIntento.RESERVA_EXITOSA : Reserva.EstadoIntento.SALDO_INSUFICIENTE;
    }

    public double getPrecioFromCedula(String cedula){
        return getPorcentajeFromRole(getRoleFromCedula(cedula)) * getCCB() / 100.0;
    }

    public void reservarMenu(String cedula, TipoMenu tipo){
        try{
            Path ruta = (tipo == TipoMenu.DESAYUNO) ? reservaDesayuno : reservaAlmuerzo;
            if(Files.exists(ruta)){                                    
                List<String> lineas = Files.readAllLines(ruta);    
                Reserva reserva;  
                reserva = new Reserva(cedula, EstadoReserva.EN_ESPERA);
                lineas.add(reserva.toJSON());
                Files.write(ruta, lineas, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al intentar reservar el menú", EstiloGral.INFO_MESSAGE);
        }
    }

    private void aumentarCupo(TipoMenu tipo){
        Menu menu = getMenu(tipo);

        menu.agregarCupos();
        guardarMenu(menu);
    }

    private int getCupos(TipoMenu tipo){
        try{
            Menu menu = getMenu(tipo);
            if(menu == null){
                EstiloGral.ShowMessage("No se pudo obtener el menú para verificar los cupos", EstiloGral.INFO_MESSAGE);
                return 0;
            }
            return Integer.parseInt(menu.getCupos());
        } catch (NullPointerException e){
            EstiloGral.ShowMessage("Hubo un error al obtener los cupos del menú", EstiloGral.INFO_MESSAGE);
            return 0;
        }
    }

    public Queue<Reserva> DesayunoWaitingQueue(){
        Queue<Reserva> cola = new LinkedList<>();
        try{
            Path ruta = reservaDesayuno;
            if(Files.exists(ruta)){                                    
                List<String> lineas = Files.readAllLines(ruta);    
                for(String line : lineas){
                    Reserva reserva= new Reserva();  
                    reserva.fromJSON(line); 
                    if(reserva.getEstadoReserva()==EstadoReserva.EN_ESPERA){
                        cola.add(reserva);
                    }
                }
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al intentar reservar el menú", EstiloGral.INFO_MESSAGE);
        }
        return cola;
    }

    public Queue<Reserva> AlmuerzoWaitingQueue(){
        Queue<Reserva> cola = new LinkedList<>();
        try{
            Path ruta = reservaAlmuerzo;
            if(Files.exists(ruta)){                                    
                List<String> lineas = Files.readAllLines(ruta);    
                for(String line : lineas){
                    Reserva reserva= new Reserva();  
                    reserva.fromJSON(line); 
                    if(reserva.getEstadoReserva()==EstadoReserva.EN_ESPERA){
                        cola.add(reserva);
                    }
                }
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al intentar reservar el menú", EstiloGral.INFO_MESSAGE);
        }
        return cola;
    }

    public Reserva getReservaFromCedula(String cedula, TipoMenu tipo){
        try{
            Path ruta = (tipo == TipoMenu.DESAYUNO) ? reservaDesayuno : reservaAlmuerzo;
            if(Files.exists(ruta)){                                    
                List<String> lineas = Files.readAllLines(ruta);    
                Reserva reserva= new Reserva();  
                for(String line : lineas){
                    reserva.fromJSON(line); 
                    if(reserva.getCedula().equals(cedula)){
                        return reserva;
                    }
                }
            }
        } catch (IOException e){ 
            EstiloGral.ShowMessage("Hubo un error al intentar obtener la reserva", EstiloGral.INFO_MESSAGE);
        }
        return null;
    }
}
