package com.parque.dinosaurios.persistence;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    // Ruta donde se guardará el archivo local de la base de datos H2
    private static final String URL = "jdbc:h2:./db/parque_dino;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /**
     * Devuelve una conexión activa a la base de datos JDBC.
     */
    public static Connection getConexion() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Inicializa Liquibase para revisar y aplicar scripts de base de datos pendientes.
     */
    public static void inicializarLiquibase() {
        System.out.println("🔄 Iniciando migración de base de datos con Liquibase...");
        try (Connection conn = getConexion()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(conn));

            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(), database);

            // Ejecuta las migraciones pendientes
            liquibase.update("");
            System.out.println(" Base de datos actualizada correctamente por Liquibase.");
        } catch (Exception e) {
            System.err.println(" Error al ejecutar las migraciones de Liquibase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}