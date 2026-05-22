package com.parque.dinosaurios;

import com.parque.dinosaurios.simulacion.ControladorSimulacion;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("  INICIANDO SISTEMA SECUENCIAL - PARQUE DE DINOSAURIOS  ");
        System.out.println("========================================================");

        // Inicializamos el motor de la simulación
        ControladorSimulacion simulador = new ControladorSimulacion();

        // Corremos la simulación por 5 pasos (puedes aumentar el número si lo deseas)
        simulador.ejecutarSimulacion(5);

        System.out.println("\n========================================================");
        System.out.println("            SIMULACIÓN CONCLUIDA EXITOSAMENTE           ");
        System.out.println("========================================================");
    }
}