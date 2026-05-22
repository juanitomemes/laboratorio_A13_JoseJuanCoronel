package com.parque.dinosaurios.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ParqueDAO {

    /**
     * Registra un ingreso financiero en la base de datos.
     */
    public void registrarIngreso(String tipo, double monto, int pasoSimulacion) {
        String sql = "INSERT INTO ingresos (tipo, monto, paso_simulacion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tipo);
            ps.setDouble(2, monto);
            ps.setInt(3, pasoSimulacion);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(" Error al guardar ingreso en BD: " + e.getMessage());
        }
    }

    /**
     * Registra un gasto operativo en la base de datos.
     */
    public void registrarGasto(String concepto, double monto, int pasoSimulacion) {
        String sql = "INSERT INTO gastos (concepto, monto, paso_simulacion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, concepto);
            ps.setDouble(2, monto);
            ps.setInt(3, pasoSimulacion);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println(" Error al guardar gasto en BD: " + e.getMessage());
        }
    }

    /**
     * Registra un evento o crisis en la base de datos.
     */
    public void registrarEvento(String tipoEvento, String descripcion, int pasoSimulacion) {
        String sql = "INSERT INTO eventos (tipo_evento, descripcion, paso_simulacion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tipoEvento);
            ps.setString(2, descripcion);
            ps.setInt(3, pasoSimulacion);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("❌ Error al guardar evento en BD: " + e.getMessage());
        }
    }
}