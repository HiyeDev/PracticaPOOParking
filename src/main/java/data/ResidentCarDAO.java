/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import cars.OficialCar;
import cars.ResidentCar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author GuillermoGomezGascon
 */
public class ResidentCarDAO {
    private static final String POSTGRESQL_SELECT_CHECK_CAR_EXISTS = "SELECT id FROM car WHERE license_plate = ?";
    private static final String POSTGRESQL_SELECT_CHECK_RESIDENT_CAR_EXISTS = "SELECT c.id FROM car AS c INNER JOIN resident_car AS rc ON rc.id_resident_car = c.id WHERE c.license_plate = ?";
    private static final String POSTGRESQL_INSERT_CAR = "INSERT INTO car(license_plate) VALUES(?)";
    private static final String POSTGRESQL_INSERT_RESIDENT_CAR = "INSERT INTO resident_car(id_resident_car) VALUES(?)";
    
    public static boolean registerResidentCar(ResidentCar residentCar) {
        boolean registerComplete = false;
        if (!existsOnResidentCarTable(residentCar)) {
            if (!existsOnCarTable(residentCar)) {
                createCar(residentCar);
            }
            createResidentCar(residentCar);
            registerComplete = true;
        }
        return registerComplete;
    }
    
    private static void createCar(ResidentCar residentCar) {
        Connection connect = null;
        PreparedStatement stmt = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_INSERT_CAR);
            stmt.setString(1, residentCar.getLicense_plate());
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
    
    private static void createResidentCar(ResidentCar residentCar) {
        Connection connect = null;
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtInsert = null;
        ResultSet rstCheck = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmtCheck.setString(1, residentCar.getLicense_plate());
            rstCheck = stmtCheck.executeQuery();
            rstCheck.next();
            int carId = rstCheck.getInt(1);
            stmtInsert = connect.prepareStatement(POSTGRESQL_INSERT_RESIDENT_CAR);
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
    
    
    private static boolean existsOnCarTable(ResidentCar residentCar) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmt.setString(1, residentCar.getLicense_plate());
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
    
    private static boolean existsOnResidentCarTable(ResidentCar residentCar) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_RESIDENT_CAR_EXISTS);
            stmt.setString(1, residentCar.getLicense_plate());
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
