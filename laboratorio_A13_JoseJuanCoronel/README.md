# Parque Turístico de Dinosaurios - Sistema de Simulación

Proyecto desarrollado como parte del laboratorio de programación avanzada para la simulación de un ecosistema de parque temático.

## Descripción General
El sistema es una simulación secuencial no determinista en Java 17 que modela el comportamiento de turistas, dinosaurios y trabajadores dentro de un parque de dinosaurios. El sistema gestiona flujos de caja, eventos aleatorios, mantenimiento de infraestructura y estados críticos, persistiendo toda la información operativa y financiera en una base de datos local.

## Herramientas Utilizadas
* **Lenguaje:** Java 17
* **Gestor de Dependencias:** Apache Maven
* **Base de Datos:** H2 Database (modo archivo local)
* **Gestión de Migraciones:** Liquibase (para versionamiento de esquema SQL)
* **Entorno:** Compatible con IntelliJ IDEA / Eclipse

## Estructura del Proyecto
* `com.parque.dinosaurios.simulacion`: Motor principal de la simulación.
* `com.parque.dinosaurios.persistence`: Capa DAO y configuración de persistencia.
* `com.parque.dinosaurios.zona`: Modelado de las instalaciones (LugarArribo, PlantaEnergia, Recintos, etc.).
* `com.parque.dinosaurios.factory`: Implementación del patrón Factory.
* `db/changelog`: Archivos de configuración de Liquibase.

## Patrones de Diseño Implementados
1. **Singleton:** Utilizado en `ParqueConfig` para asegurar una única instancia de configuración durante toda la ejecución.
2. **Factory Method:** Utilizado en `ParqueFactory` para la creación desacoplada de entidades como `Turista` y `Dinosaurio`, centralizando la lógica de instanciación.

## Instrucciones de Configuración y Ejecución
1. **Clonar el repositorio:** Asegúrate de tener instalado Maven en tu entorno.
2. **Dependencias:** El proyecto descargará automáticamente las dependencias (H2, Liquibase) mediante Maven al abrir el proyecto.
3. **Ejecución:**
    - Ubica la clase `Main.java` en `src/main/java/com/parque/dinosaurios/Main.java`.
    - Ejecuta el método `main`.
    - La consola mostrará el progreso paso a paso.
    - La base de datos se generará automáticamente en la carpeta `./db/` de la raíz del proyecto.

## Persistencia de Datos
El sistema registra automáticamente:
* **Ingresos:** Ventas de boletos, souvenirs y servicios de SPA.
* **Gastos:** Costos operativos de mantenimiento de la Planta de Energía.
* **Eventos:** Registro de crisis (apagones, escapes, tormentas, etc.) asociados al paso de simulación en el que ocurrieron.