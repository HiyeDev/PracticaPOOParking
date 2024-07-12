package data;

import cars.Car;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

public class EntryDAO {
    
    private static final String POSTGRESQL_SELECT_CHECK_CAR_EXISTS = "SELECT id FROM car WHERE license_plate = ?";
    private static final String POSTGRESQL_INSERT_NEW_ENTRY = "INSERT INTO park(id_car, entry_date, entry_time) VALUES (?, ?, ?)";
    private static final String POSTGRESQL_INSERT_CAR = "INSERT INTO car(license_plate) VALUES(?)";

        
    public static boolean registerNewEntry(Car car, Date entryDate, Time entryTime) {
        boolean registerComplete = false;
        if (!carExists(car)) {
            createCar(car);
        }
        createNewEntry(car, entryDate, entryTime);
        return registerComplete;
    }
        
    private static void createNewEntry(Car car, Date entryDate, Time entryTIme) {
        Connection connect = null;
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtInsert = null;
        ResultSet rstCheck = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmtCheck.setString(1, car.getLicense_plate());
            rstCheck = stmtCheck.executeQuery();
            rstCheck.next();
            int carId = rstCheck.getInt(1);
            stmtInsert = connect.prepareStatement(POSTGRESQL_INSERT_NEW_ENTRY);
            stmtInsert.setInt(1, carId);
            stmtInsert.setDate(2, entryDate);
            stmtInsert.setTime(3, entryTIme);
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
        
    private static void createCar(Car car) {
        Connection connect = null;
        PreparedStatement stmt = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_INSERT_CAR);
            stmt.setString(1, car.getLicense_plate());
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

    private static boolean carExists(Car car) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmt.setString(1, car.getLicense_plate());
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
