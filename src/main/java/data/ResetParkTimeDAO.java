/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.*;

/**
 *
 * @author GuillermoGomezGascon
 */
public class ResetParkTimeDAO {
    private static final String POSTGRESQL_RESET_PARK_TIME = "UPDATE parked_time_monthly SET park_time = 0";

    public static void resetAllParkTimeToZero() {
        Connection connect = null;
        PreparedStatement stmt = null;

        try {
            connect = MyConnectData.getConnection();
            stmt = connect.prepareStatement(POSTGRESQL_RESET_PARK_TIME);
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
}
