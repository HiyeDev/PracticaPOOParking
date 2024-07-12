package data;

import cars.OficialCar;
import java.sql.*;

public class OficialCarDAO {
    
    private static final String POSTGRESQL_SELECT_CHECK_CAR_EXISTS = "SELECT id FROM car WHERE license_plate = ?";
    private static final String POSTGRESQL_SELECT_CHECK_OFICIAL_CAR_EXISTS = "SELECT c.id FROM car AS c INNER JOIN oficial_car AS oc ON oc.id_oficial_car = c.id WHERE c.license_plate = ?";
    private static final String POSTGRESQL_INSERT_CAR = "INSERT INTO car(license_plate) VALUES(?)";
    private static final String POSTGRESQL_INSERT_OFICIAL_CAR = "INSERT INTO oficial_car(id_oficial_car) VALUES(?)";
    
    public static boolean registerOficialCar(OficialCar oficialCar) {
        boolean registerComplete = false;
        if (!existsOnOficialCarTable(oficialCar)) {
            if (!existsOnCarTable(oficialCar)) {
                createCar(oficialCar);
            }
            createOficialCar(oficialCar);
            registerComplete = true;
        }
        return registerComplete;
    }
    
    private static void createCar(OficialCar oficialCar) {
        Connection connect = null;
        PreparedStatement stmt = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_INSERT_CAR);
            stmt.setString(1, oficialCar.getLicense_plate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(stmt);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    private static void createOficialCar(OficialCar oficialCar) {
        Connection connect = null;
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtInsert = null;
        ResultSet rstCheck = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmtCheck.setString(1, oficialCar.getLicense_plate());
            rstCheck = stmtCheck.executeQuery();
            rstCheck.next();
            int carId = rstCheck.getInt(1);
            stmtInsert = connect.prepareStatement(POSTGRESQL_INSERT_OFICIAL_CAR);
            stmtInsert.setInt(1, carId);
            stmtInsert.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(rstCheck);
                MyConnectData.close(stmtInsert);
                MyConnectData.close(stmtCheck);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    
    private static boolean existsOnCarTable(OficialCar oficialCar) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmt.setString(1, oficialCar.getLicense_plate());
            rst = stmt.executeQuery();
            if (rst.next()) {
                exists = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(rst);
                MyConnectData.close(stmt);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return exists;
    }
    
    private static boolean existsOnOficialCarTable(OficialCar oficialCar) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_OFICIAL_CAR_EXISTS);
            stmt.setString(1, oficialCar.getLicense_plate());
            rst = stmt.executeQuery();
            if (rst.next()) {
                exists = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(rst);
                MyConnectData.close(stmt);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return exists;
    }

}
