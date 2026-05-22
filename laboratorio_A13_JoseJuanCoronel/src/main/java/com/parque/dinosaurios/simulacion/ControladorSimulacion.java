package com.parque.dinosaurios.simulacion;

import com.parque.dinosaurios.config.ParqueConfig;
import com.parque.dinosaurios.factory.ParqueFactory;
import com.parque.dinosaurios.model.Dinosaurio;
import com.parque.dinosaurios.model.Turista;
import com.parque.dinosaurios.persistence.ParqueDAO;
import com.parque.dinosaurios.zona.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorSimulacion {
    private Random random = new Random();
    private ParqueConfig config = ParqueConfig.getInstancia();

    private ParqueDAO parqueDAO = new ParqueDAO();

    private com.parque.dinosaurios.eventos.GestorEventos gestorEventos = new com.parque.dinosaurios.eventos.GestorEventos();

    // Zonas del Parque
    private LugarArribo lugarArribo;
    private RecintoCentral recintoCentral;
    private Banos banosYSpa;
    private PlantaEnergia plantaEnergia;
    private RecintoObservacion recintoCarnivoros;
    private RecintoObservacion recintoHerbivoros;

    // Listas globales de control
    private List<Turista> turistasTotales = new ArrayList<>();
    private List<Dinosaurio> dinosauriosTotales = new ArrayList<>();
    private List<Turista> filaEsperaEntrada = new ArrayList<>();

    public ControladorSimulacion() {
        inicializarZonas();
        poblarParque();
    }

    private void inicializarZonas() {
        // Inicializamos las zonas con capacidades y costos (puedes externalizarlos después en el properties si lo deseas)
        this.lugarArribo = new LugarArribo(100, 20.0); // Boleto $20
        this.recintoCentral = new RecintoCentral(60, 15.0); // Souvenir $15
        this.banosYSpa = new Banos(30, 40.0); // SPA $40
        this.plantaEnergia = new PlantaEnergia(10); // Capacidad de técnicos

        // Creamos dos zonas de observación diferenciadas
        this.recintoCarnivoros = new RecintoObservacion("RECINTO_CARNIVOROS", 30, 25.0); // Entrada $25
        this.recintoHerbivoros = new RecintoObservacion("RECINTO_HERBIVOROS", 40, 15.0); // Entrada $15
    }

    private void poblarParque() {
        int turistasACrear = config.getInt("tourists.initial");
        int carnivorosACrear = config.getInt("dinosaurs.carnivores");
        int herbivorosACrear = config.getInt("dinosaurs.herbivores");

        // 1. Crear Turistas y mandarlos a la fila de espera exterior
        for (int i = 1; i <= turistasACrear; i++) {
            Turista t = ParqueFactory.crearTurista(i);
            turistasTotales.add(t);
            filaEsperaEntrada.add(t);
        }

        // 2. Crear Dinosaurios y alojarlos en sus respectivos recintos
        int contadorDino = 1;
        for (int i = 1; i <= carnivorosACrear; i++) {
            Dinosaurio d = ParqueFactory.crearDinosaurio(contadorDino++, "CARNIVORO");
            dinosauriosTotales.add(d);
            recintoCarnivoros.alojarDinosaurio(d);
        }
        for (int i = 1; i <= herbivorosACrear; i++) {
            Dinosaurio d = ParqueFactory.crearDinosaurio(contadorDino++, "HERBIVORO");
            dinosauriosTotales.add(d);
            recintoHerbivoros.alojarDinosaurio(d);
        }
    }

    /**
     * Ejecuta el ciclo completo de la simulación por N pasos.
     */
    public void ejecutarSimulacion(int pasosTotales) {
        for (int paso = 1; paso <= pasosTotales; paso++) {
            System.out.println("\n========================================================");
            System.out.println("            SIMULACIÓN DEL PARQUE - PASO " + paso);
            System.out.println("========================================================");

            // --- Persistir un gasto operativo fijo por paso (Mantenimiento diario) ---
            double costoPaso = 150.00;
            plantaEnergia.registrarGastoMantenimiento(costoPaso);
            parqueDAO.registrarGasto("Mantenimiento General Planta", costoPaso, paso);

            // Fase de Arribo
            int turistasAProcesar = Math.min(10, filaEsperaEntrada.size());
            for (int i = 0; i < turistasAProcesar; i++) {
                Turista t = filaEsperaEntrada.remove(0);
                double ingreso = lugarArribo.procesarIngresoParqueMonto(t);
                if (ingreso > 0) {
                    parqueDAO.registrarIngreso("BOLETO_ENTRADA", ingreso, paso);
                }
            }

            // Fase de Movimiento (Ahora le pasamos el paso actual)
            simularMovimientoTuristas(paso);

            // Ciclo Vital de Dinosaurios
            for (Dinosaurio d : dinosauriosTotales) {
                d.incrementarHambre(random.nextInt(15));
            }

            // ---  Disparar Eventos Aleatorios en este paso ---
            gestorEventos.procesarPosibleEvento(dinosauriosTotales, plantaEnergia, paso);

            // Imprimir reporte en consola
            imprimirReporteMonitoreo();

            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    private void simularMovimientoTuristas(int paso) {
        List<Zona> zonasConTuristas = List.of(lugarArribo, recintoCentral, banosYSpa, recintoCarnivoros, recintoHerbivoros);
        List<Turista> turistasAdentro = new ArrayList<>();

        for (Zona z : zonasConTuristas) {
            turistasAdentro.addAll(z.getTuristasPresentes());
        }

        for (Turista t : turistasAdentro) {
            Zona zonaActual = obtenerZonaPorNombre(t.getZonaActual());
            if (zonaActual == null) continue;

            double decision = random.nextDouble();

            if (decision < 0.15) {
                zonaActual.salirTurista(t);
                t.setActivo(false);
                System.out.println("   [Salida] " + t.getId() + " ha decidido abandonar el parque.");
            } else if (decision < 0.70) {
                zonaActual.salirTurista(t);
                moverA_ZonaAleatoria(t, paso); // Enviamos el paso aquí
            } else {
                ejecutarInteraccionZona(zonaActual, t, paso); // Enviamos el paso aquí
            }
        }
    }

    private void moverA_ZonaAleatoria(Turista t, int paso) {
        int destino = random.nextInt(4);
        double gastado = 0;
        switch (destino) {
            case 0:
                if (recintoCentral.ingresarTurista(t)) {
                    gastado = recintoCentral.interactuarMonto(t);
                    if (gastado > 0) parqueDAO.registrarIngreso("SOUVENIR", gastado, paso);
                } else {
                    lugarArribo.ingresarTurista(t);
                }
                break;
            case 1:
                if (banosYSpa.ingresarTurista(t)) {
                    gastado = banosYSpa.ofrecerServicioSpaMonto(t);
                    if (gastado > 0) parqueDAO.registrarIngreso("SPA_PREMIUM", gastado, paso);
                } else {
                    lugarArribo.ingresarTurista(t);
                }
                break;
            case 2:
                gastado = recintoCarnivoros.visitarRecintoMonto(t);
                if (gastado > 0) {
                    parqueDAO.registrarIngreso("ATRACCION_CARNIVOROS", gastado, paso);
                } else if (t.isActivo() && t.getZonaActual().equals("LUGAR_ARRIBO")) {
                    // Si no pudo entrar por falta de presupuesto o cupo regresa a Arribo de forma segura
                    lugarArribo.ingresarTurista(t);
                }
                break;
            case 3:
                gastado = recintoHerbivoros.visitarRecintoMonto(t);
                if (gastado > 0) {
                    parqueDAO.registrarIngreso("ATRACCION_HERBIVOROS", gastado, paso);
                } else if (t.isActivo() && t.getZonaActual().equals("LUGAR_ARRIBO")) {
                    lugarArribo.ingresarTurista(t);
                }
                break;
        }
    }

    private void ejecutarInteraccionZona(Zona zona, Turista t, int paso) {
        double gastado = 0;
        if (zona instanceof RecintoCentral) {
            gastado = ((RecintoCentral) zona).interactuarMonto(t);
            if (gastado > 0) parqueDAO.registrarIngreso("SOUVENIR", gastado, paso);
        } else if (zona instanceof Banos) {
            gastado = ((Banos) zona).ofrecerServicioSpaMonto(t);
            if (gastado > 0) parqueDAO.registrarIngreso("SPA_PREMIUM", gastado, paso);
        } else if (zona instanceof RecintoObservacion) {
            gastado = ((RecintoObservacion) zona).visitarRecintoMonto(t);
            if (gastado > 0) {
                parqueDAO.registrarIngreso("ATRACCION_" + zona.getNombre(), gastado, paso);
            }
        }

    }
    private Zona obtenerZonaPorNombre(String nombre) {
        if (nombre == null) return null;
        if (nombre.equals("LUGAR_ARRIBO")) return lugarArribo;
        if (nombre.equals("RECINTO_CENTRAL")) return recintoCentral;
        if (nombre.equals("BANOS_Y_SPA")) return banosYSpa;
        if (nombre.equals("RECINTO_CARNIVOROS")) return recintoCarnivoros;
        if (nombre.equals("RECINTO_HERBIVOROS")) return recintoHerbivoros;
        return null;
    }

    private void imprimirReporteMonitoreo() {
        System.out.println("\n --- SISTEMA DE MONITOREO DEL PARQUE ---");
        System.out.println("» Cola Exterior: " + filaEsperaEntrada.size() + " turistas esperando.");
        System.out.println("» Ocupación por Zonas:");
        System.out.println("   [Arribo]: " + lugarArribo.getCantidadActual() + " | [Central]: " + recintoCentral.getCantidadActual() +
                " | [Baños/SPA]: " + banosYSpa.getCantidadActual() + " | [Recinto Carnívoros]: " + recintoCarnivoros.getCantidadActual() +
                " | [Recinto Herbívoros]: " + recintoHerbivoros.getCantidadActual());

        double totalIngresos = lugarArribo.getIngresosPorBoletos() + recintoCentral.getIngresosSouvenirs() +
                banosYSpa.getIngresosSpa() + recintoCarnivoros.getIngresosEntradas() +
                recintoHerbivoros.getIngresosEntradas();

        System.out.println("» Financiero Global: Ingresos acumulados: $" + String.format("%.2f", totalIngresos));

        long hambrientos = dinosauriosTotales.stream().filter(d -> d.getNivelHambre() > 70).count();
        System.out.println("» Alerta Veterinaria: " + hambrientos + " dinosaurios en estado de hambre crítica.");
    }
}