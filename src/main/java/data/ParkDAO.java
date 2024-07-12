package data;

import cars.Car;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkDAO {
    private static final String POSTGRESQL_INSERT_PARK_TIME = "INSERT INTO parked_time_monthly(id_resident_car, park_time) VALUES (?, ?)";
    private static final String POSTGRESQL_SELECT_ID_RESIDENT_CAR = "SELECT rc.id FROM resident_car AS rc INNER JOIN car AS c On c.id = rc.id_resident_car WHERE c.license_plate = ?";
    private static final String POSTGRESQL_SELECT_EXITS_CARS = "SELECT entry_date, exit_date, entry_time, exit_time FROM car c INNER JOIN park p ON c.id = p.id_car WHERE p.exit_date IS NOT NULL AND p.exit_time IS NOT NULL AND c.license_plate = ? ORDER BY p.entry_date DESC, entry_time DESC LIMIT 1";
    private static final String POSTGRESQL_SELECT_CHECK_OFICIAL_CAR_EXISTS = "SELECT c.id FROM car AS c INNER JOIN oficial_car AS oc ON oc.id_oficial_car = c.id WHERE c.license_plate = ?";
    private static final String POSTGRESQL_SELECT_CHECK_RESIDENT_CAR_EXISTS = "SELECT c.id FROM car AS c INNER JOIN resident_car AS rc ON rc.id_resident_car = c.id WHERE c.license_plate = ?";
    private static final String POSTGRESQL_SELECT_CHECK_RECORD_EXISTS = "SELECT pt.id, pt.park_time FROM parked_time_monthly AS pt INNER JOIN resident_car AS rc ON rc.id = pt.id_resident_car INNER JOIN car AS c ON c.id = rc.id_resident_car WHERE c.license_plate = ?";
    private static final String POSTGRESQL_UPDATE_PARKED_TIME_MONTHLY = "UPDATE parked_time_monthly SET park_time = ? WHERE id = ?";
    
    public static int calculateTimeParking(Car car) {
        int timeParking = 0;
        if (isOficialCar(car)) {
            return timeParking;
        }
        timeParking = getParkingDurationInMInutes(car);
        if (isResidentCar(car)) {
            doUpdateOrInsertParkTimeMonthly(car, timeParking);
            timeParking = 0;
        }
        return timeParking;
    }

    private static void doUpdateOrInsertParkTimeMonthly(Car car, int parkTime) {
        Connection connect = null;
        PreparedStatement stmtCheck = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmtCheck = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_RECORD_EXISTS);
            stmtCheck.setString(1, car.getLicense_plate());
            rst = stmtCheck.executeQuery();
            if (rst.next()) {
                updateParkTimeMonthly(rst, parkTime, connect);
            } else {
                createParkTimeMonthly(car, parkTime, connect);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(rst);
                MyConnectData.close(stmtCheck);
                MyConnectData.close(connect);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    private static void createParkTimeMonthly(Car car, int parkTime, Connection connect) {
            PreparedStatement stmtCheckIdResidentCar = null;
            PreparedStatement stmtInsert = null;
            ResultSet rstCheckIdResidentCar = null;
        try {
            stmtCheckIdResidentCar = connect.prepareStatement(POSTGRESQL_SELECT_ID_RESIDENT_CAR);
            stmtCheckIdResidentCar.setString(1, car.getLicense_plate());
            rstCheckIdResidentCar = stmtCheckIdResidentCar.executeQuery();
            rstCheckIdResidentCar.next();
            int idResidentCar = rstCheckIdResidentCar.getInt("id");
            stmtInsert = connect.prepareStatement(POSTGRESQL_INSERT_PARK_TIME);
            stmtInsert.setInt(1, idResidentCar);
            stmtInsert.setInt(2, parkTime);
            stmtInsert.executeUpdate();
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        } finally {
                try {
                    if (rstCheckIdResidentCar != null) MyConnectData.close(rstCheckIdResidentCar);
                    MyConnectData.close(stmtInsert);
                    MyConnectData.close(stmtCheckIdResidentCar);
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                }
        }
    }
    
    private static void updateParkTimeMonthly(ResultSet rst, int parkTime, Connection connect) {
        PreparedStatement stmtUpdate = null;

        try {
            int idParkedTimeMonthly = rst.getInt("id");
            int existingParkTime = rst.getInt("park_time");
            int newParkTime = existingParkTime + parkTime;
            
            stmtUpdate = connect.prepareStatement(POSTGRESQL_UPDATE_PARKED_TIME_MONTHLY);
            stmtUpdate.setInt(idParkedTimeMonthly, newParkTime);
            stmtUpdate.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                MyConnectData.close(stmtUpdate);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    private static boolean isResidentCar(Car car) {
        boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_RESIDENT_CAR_EXISTS);
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
    
    private static boolean isOficialCar(Car car) {
         boolean exists = false;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;
        
        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_CHECK_OFICIAL_CAR_EXISTS);
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
    
    public static int getParkingDurationInMInutes(Car car) {
        int totalMinutes = 0;
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_EXITS_CARS);
            stmt.setString(1, car.getLicense_plate());
            rst = stmt.executeQuery();

            if (rst.next()) {
                Date entryDate = rst.getDate("entry_date");
                Date exitDate = rst.getDate("exit_date");
                Time entryTime = rst.getTime("entry_time");
                Time exitTime = rst.getTime("exit_time");

                
                LocalDateTime entryDateTime = LocalDateTime.of(
                    entryDate.toLocalDate(),
                    entryTime.toLocalTime()
                );
                LocalDateTime exitDateTime = LocalDateTime.of(
                    exitDate.toLocalDate(),
                    exitTime.toLocalTime()
                );

                Duration duration = Duration.between(entryDateTime, exitDateTime);
                totalMinutes = (int) duration.toMinutes();
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
        return totalMinutes;
    }
}