/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GuillermoGomezGascon
 */
public class ReportDAO {
    private static final String POSTGRESQL_SELECT_ALL_PARKED_TIME = "SELECT c.license_plate, pt.park_time FROM parked_time_monthly pt INNER JOIN resident_car AS rc ON pt.id_resident_car = rc.id INNER JOIN car AS c ON rc.id_resident_car = c.id ";
    
    public static void generateReport(String fileName) {
        List<ParkingData> parkingDataList = getParkingData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
            writer.write("Matricula\t\tTiempo estacionado (min)\t\tCantidad a pagar\n");
            for (ParkingData data : parkingDataList) {
                float amountToPay = data.getParkTime() * 0.002F;
                String formattedAmount = String.format("%.2f", amountToPay);
                writer.write(data.getLicensePlate() + "\t\t\t" + data.getParkTime() + "\t\t\t\t" + formattedAmount + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ParkingData> getParkingData() {
        List<ParkingData> parkingDataList = new ArrayList<>();
        Connection connect = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_SELECT_ALL_PARKED_TIME);
            rst = stmt.executeQuery();

            while (rst.next()) {
                String licensePlate = rst.getString("license_plate");
                int parkTime = rst.getInt("park_time");
                parkingDataList.add(new ParkingData(licensePlate, parkTime));
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

        return parkingDataList;
    }

}
