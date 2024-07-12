package data;

import cars.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExitDAO {
    
    private static final String POSTGRESQL_SELECT_CHECK_CAR_EXISTS = "SELECT id FROM car WHERE license_plate = ?";
    private static final String POSTGRESQL_SELECT_PARK_ENTRY = "SELECT id FROM park WHERE id_car = ? AND exit_date IS NULL AND exit_time IS NULL";
    private static final String POSTGRESQL_UPDATE_EXIT = "UPDATE park SET exit_date = ?, exit_time = ? WHERE id = ?";
    private static final String POSTGRESQL_SELECT_PARKED_CARS = "SELECT c.license_plate FROM car c INNER JOIN park p ON c.id = p.id_car WHERE p.exit_date IS NULL AND p.exit_time IS NULL";



    public static boolean registerExit(Car car, Date exitDate, Time exitTime) {
        boolean registerComplete = false;
        if (carExists(car)) {
            updateExit(car, exitDate, exitTime);
            registerComplete = true;
        } else {
            System.out.println("Car does not exist in the database.");
        }
        return registerComplete;
    }
    
    private static void updateExit(Car car, Date exitDate, Time exitTime) {
        Connection connect = null;
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtUpdate = null;
        ResultSet rstCheck = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_CAR_EXISTS);
            stmtCheck.setString(1, car.getLicense_plate());
            rstCheck = stmtCheck.executeQuery();
            rstCheck.next();
            int carId = rstCheck.getInt(1);
            
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_PARK_ENTRY);
            stmtCheck.setInt(1, carId);
            rstCheck = stmtCheck.executeQuery();
            if (rstCheck.next()) {
                int parkId = rstCheck.getInt(1);
                stmtUpdate = connect.prepareStatement(POSTGRESQL_UPDATE_EXIT);
                stmtUpdate.setDate(1, exitDate);
                stmtUpdate.setTime(2, exitTime);
                stmtUpdate.setInt(3, parkId);
                stmtUpdate.executeUpdate();
            } else {
                System.out.println("No open entry found for this car.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(rstCheck);
                MyConnectData.close(stmtUpdate);
                MyConnectData.close(stmtCheck);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    public static List<Car> getParkedCarsWithoutExit() {
        List<Car> parkedCars = new ArrayList<>();
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_PARKED_CARS);
            rst = stmt.executeQuery();
            
            while (rst.next()) {
                String licensePlate = rst.getString("license_plate");
                parkedCars.add(new Car(licensePlate));
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
        
        return parkedCars;
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
