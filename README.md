# SGCU - Sistema de Gestión de Comedor Universitario
### Sprint 2 Grupo #1
  
## Propósito
Aplicación de escritorio para gestionar un comedor universitario con interfaces basadas en roles: menús diarios, precios personalizados y administración de tarifas y menús.
  
## Características
- Registro de usuarios con validación contra base de datos UCV
- Autenticación por cédula y contraseña
- Consulta de menús y precios según rol
- Administración de tarifas y cálculo de CCB
- Publicación de menús diarios
  
## Stack Tecnológico
- Java 17
- Java Swing (UI)
- Maven 3
- Patrón MVC
- Persistencia en archivos (C:\SGCU\data\)
  
## Arquitectura
- MVC con una interfaz de NavigationDelegate para coordinar las distintas Views
- PersistenciaManager centralizado para acceso a datos
- Roles: Estudiante, Profesor, Trabajador, Admin

## Pruebas
- Junit para pruebas de caja negra [Ver archivos de clases de equivalencia](https://docs.google.com/spreadsheets/d/18ol_YHN0MA4lMdHIxkTGT99giSoDIEJALTGZZSajJRg/edit?usp=sharing)