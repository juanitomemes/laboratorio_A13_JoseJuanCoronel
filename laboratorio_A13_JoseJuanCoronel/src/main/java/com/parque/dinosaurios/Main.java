package com.parque.dinosaurios;

import com.parque.dinosaurios.factory.ParqueFactory;
import com.parque.dinosaurios.model.Turista;
import com.parque.dinosaurios.zona.LugarArribo;
import com.parque.dinosaurios.zona.RecintoCentral;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Simulación de Flujo en Zonas del Parque!");
        System.out.println("=========================================");

        // 1. Inicializar Zonas con capacidades y costos fijos de prueba
        LugarArribo entrada = new LugarArribo(30, 15.0); // Capacidad 30, Boleto $15
        RecintoCentral centro = new RecintoCentral(50, 25.0); // Capacidad 50, Souvenir $25

        // 2. Crear 5 turistas de prueba usando la fábrica
        List<Turista> filaEspera = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            filaEspera.add(ParqueFactory.crearTurista(i));
        }

        System.out.println("\n--- FASE 1: PROCESANDO ENTRADA EN TAQUILLA ---");
        for (Turista t : filaEspera) {
            entrada.procesarIngresoParque(t);
        }

        System.out.println("\n--- FASE 2: MOVIENDO TURISTAS ACTIVOS AL RECINTO CENTRAL ---");
        // Copiamos la lista para evitar errores de modificación concurrente al moverlos
        List<Turista> ingresados = new ArrayList<>(entrada.getTuristasPresentes());
        for (Turista t : ingresados) {
            entrada.salirTurista(t); // Sale de la entrada
            centro.ingresarTurista(t); // Entra al centro
            centro.interactuar(t); // Intenta comprar souvenir
        }

        System.out.println("\n--- MONITOREO DE INGRESOS FINANCIEROS ---");
        System.out.println("Ganancias Totales por Boletos: $" + entrada.getIngresosPorBoletos());
        System.out.println("Ganancias Totales por Souvenirs: $" + centro.getIngresosSouvenirs());
    }
}