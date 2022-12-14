package sample.util;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {
    //Se declara el DBC Driver
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    //La conexión
    private static Connection conn = null;

    //Conexión String // Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
    private static final String connStr = "jdbc:oracle:thin:HR/HR@localhost:1521/xe";


    //Conexión con la DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Donde está tu Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }

        System.out.println("Oracle JDBC Driver registrado!");

        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            System.out.println("!ERROR¡La conexión ha fallado" + e);
            e.printStackTrace();
            throw e;
        }
    }

    //Cerrar la conexión
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }

    //DB Ejecutar operación de consulta
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Conexión con la DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Creación statement
            stmt = conn.createStatement();

            //Ejecutar la operación select (query)
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementación
            //Para evitar el error "java.sql.SQLRecoverableException: Closed Connection: next"
            //Estamos usando CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("!ERROR¡ ocurrido en la operación executeQuery : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Cerrar resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Cerrar Statement
                stmt.close();
            }
            //Cerrar la conexión
            dbDisconnect();
        }
        //Devolver CachedRowSet
        return crs;
    }

    //DB Ejecutar actualización (Para actualizar/insertar/eliminar) Operación
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declarar statement como null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Crear el  Statement
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("!ERROR¡ ocurrido en la operación executeUpdate: " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Cerrar statement
                stmt.close();
            }
            //Cerrar la conexión
            dbDisconnect();
        }
    }
}