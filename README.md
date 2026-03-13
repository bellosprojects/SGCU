# SGCU - Sistema de Gestión de Comedor Universitario
### Sprint 3 Grupo #1

## Propósito
Aplicación de escritorio para gestionar un comedor universitario con interfaces basadas en roles: menús diarios, precios personalizados, administración de tarifas, menús y usuarios, y funcionalidades de cajero.

## Características
- Registro de usuarios con validación contra base de datos UCV
- Autenticación por cédula y contraseña
- Consulta de menús y precios según rol del usuario
- Administración de tarifas y cálculo de CCB
- Publicación y gestión de menús diarios (desayuno y almuerzo) con control de cupos
- Gestión de becarios y descuentos
- Funcionalidad de cajero para transacciones
- Gestión de saldo y recargas
- Soporte para resoluciones de pantalla iguales o mayores a 1360x768
- Interfaz de usuario personalizada con animaciones y componentes reutilizables

## Stack Tecnológico
- Java 17
- Java Swing (UI base)
- Biblioteca personalizada "Aura" para componentes UI avanzados
- Maven 3
- Patrón MVC
- Persistencia en archivos (C:\SGCU\data\)

## Arquitectura
- Patrón MVC con una interfaz de NavigationDelegate para coordinar las distintas vistas
- PersistenciaManager centralizado para acceso a datos
- Roles de usuario: Administrador, Estudiante, Profesor, Trabajador, Becario, Exonerado, Cajero
- Componentes UI modulares con soporte para animaciones y estados reactivos

## Pruebas
- JUnit 5 para pruebas unitarias y de integración
- Mockito para mocking en pruebas
- Cobertura de pruebas para controladores, modelos y utilidades
- Pruebas específicas para:
  - CajeroController: Lógica de transacciones
  - CCBCalculoController: Cálculos de tarifas
  - GestionarMenuController: Gestión de menús
  - RegisterController: Registro de usuarios
  - SaldoPanaTest: Gestión de saldo
  - UserMenuController: Funcionalidades de usuario
  - ListaComensalesTest: Manejo de listas
  - PersistenciaManagerTest: Persistencia de datos
  - ModelUtilsTest: Utilidades de modelo

## Funcionalidades Detalladas
### Gestión de Usuarios
- Registro de nuevos usuarios con validación contra la base de datos de la Universidad Central de Venezuela (UCV)
- Autenticación segura mediante cédula y contraseña encriptada
- Roles definidos: Administrador, Estudiante, Profesor, Trabajador, Becario, Exonerado, Cajero
- Gestión de saldo personal y recargas para usuarios

### Gestión de Menús
- Creación y publicación de menús diarios para desayuno y almuerzo
- Especificación de platos, ingredientes y cupos disponibles
- Control de reservas por tipo de menú
- Consulta de menús disponibles según fecha y tipo

### Administración de Tarifas
- Actualización de precios por rol de usuario
- Cálculo automático del CCB (Costo de Comida Básica)
- Gestión de descuentos para becarios

### Funcionalidades de Cajero
- Procesamiento de reservas pendientes
- Gestión de transacciones y pagos
- Visualización de listas de comensales por servicio

### Panel de Administración
- Dashboard para administradores con acceso a todas las funcionalidades
- Gestión de becarios y asignación de descuentos
- Visualización de reservas y comensales por tipo de menú
- Actualización de tarifas en tiempo real

## Implementación
### Persistencia de Datos
- Almacenamiento en archivos JSON locales en el directorio C:\SGCU\data\
- Archivos separados para usuarios registrados, menús (desayuno/almuerzo), tarifas, reservas y listas de comensales
- Validación contra base de datos UCV para registro de usuarios
- Encriptación de contraseñas para seguridad

### Controladores
- LoginController: Manejo de autenticación
- RegisterController: Gestión de registro de usuarios
- UserMenuController: Lógica de menú de usuario
- PanelAdminController: Funcionalidades de administración
- GestionarMenuController: Creación y gestión de menús
- CCBCalculoController: Cálculos de tarifas y CCB
- GestionarBecariosController: Administración de becarios
- CajeroController: Procesamiento de transacciones

### Modelos
- User: Representación de usuarios con roles y atributos
- Menu: Estructura de menús con platos, ingredientes y cupos
- Prices: Gestión de tarifas por rol
- Reserva: Manejo de reservas de menús
- ComensalesPorServicio: Listas de comensales por servicio

## Interfaz de Usuario
### Biblioteca "Aura"
Utiliza una biblioteca personalizada llamada "Aura" construida sobre Java Swing, que proporciona:

#### Componentes
- **AuraWindow**: Ventana principal con soporte para pantalla completa y no redimensionable
- **AuraButton**: Botones personalizables con colores, fuentes y animaciones
- **AuraInput**: Campos de entrada de texto con validación
- **AuraSelect**: Selectores desplegables para opciones
- **AuraText**: Textos con fuentes y colores configurables
- **AuraImage**: Visualización de imágenes
- **AuraModal**: Ventanas modales para interacciones secundarias
- **AuraContainer**: Contenedores para organizar componentes
- **AuraToast**: Notificaciones emergentes
- **AuraSpacer**: Espaciadores para separación

#### Layouts
- **AuraColumn**: Disposición vertical de componentes
- **AuraRow**: Disposición horizontal de componentes

#### Animaciones
- **AnimateBackground**: Transiciones de color de fondo
- **AnimateColor**: Animaciones de cambio de color
- **AnimateFloat**: Animaciones numéricas
- **AnimateOpacity**: Transiciones de opacidad
- **AnimateRipple**: Efectos de onda
- **AnimateScale**: Escalado de componentes
- **AnimateShake**: Vibraciones
- **AnimateString**: Animaciones de texto

#### Estados Reactivos
- **AuraState**: Gestión de estados reactivos para actualizaciones dinámicas
- **AuraWhen**: Renderizado condicional basado en estados

### Diseño Responsivo
- Soporte para resoluciones >= 1360x768
- Adaptación automática del layout según el tamaño de pantalla
- Ocultamiento/mostrado de elementos en pantallas pequeñas
- Diseño centrado en usabilidad con navegación intuitiva

### Estilos
- Tema oscuro con colores personalizados
- Fuentes consistentes (títulos, etiquetas, inputs)
- Bordes redondeados y transparencias para estética moderna
- Iconos e imágenes integradas