package com.parque.dinosaurios;

import com.parque.dinosaurios.persistence.ConexionDB;
import com.parque.dinosaurios.simulacion.ControladorSimulacion;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("  INICIANDO SISTEMA SECUENCIAL - PARQUE DE DINOSAURIOS  ");
        System.out.println("========================================================");

        // 1. Inicializar y actualizar la base de datos antes de la simulación
        ConexionDB.inicializarLiquibase();

        // 2. Inicializamos el motor de la simulación
        ControladorSimulacion simulador = new ControladorSimulacion();

        // 3. Corremos la simulación por 5 pasos
        simulador.ejecutarSimulacion(5);

        System.out.println("\n========================================================");
        System.out.println("            SIMULACIÓN CONCLUIDA EXITOSAMENTE           ");
        System.out.println("========================================================");
    }
}