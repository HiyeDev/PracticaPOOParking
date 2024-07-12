package data;

import java.sql.*;

 public class MyConnectData {
    private static final String POSTGRESQL_URL = "jdbc:postgresql://dpg-cq7o2veehbks7392k65g-a.frankfurt-postgres.render.com:5432/parking_app_cozw";
    private static final String POSTGRESQL_USER = "root";
    private static final String POSTGRESQL_PASSWORD = "PwMnJ81nS4kgAbpshnUD2FKMC0EfGYI2";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(POSTGRESQL_URL, POSTGRESQL_USER, POSTGRESQL_PASSWORD);
    }
    
    public static void close(Connection conn) throws SQLException {
        conn.close();
    }
    
    public static void close(ResultSet rs) throws SQLException {
        rs.close();
    }
    
    public static void close(Statement smtm) throws SQLException {
        smtm.close();
    }
    
    public static void close(PreparedStatement smtm) throws SQLException {
        smtm.close();
    }
}
