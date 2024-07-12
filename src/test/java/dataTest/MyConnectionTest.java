package dataTest;

import data.MyConnectData;
import java.sql.*;

public class MyConnectionTest {
    
    private static final String POSTGRESQL_SELECT_TEST = "SELECT id, license_plate FROM car"; 
    public static void main(String[] args) throws SQLException {
        
        Connection connection = MyConnectData.getConnection();
        PreparedStatement stm = connection.prepareStatement(POSTGRESQL_SELECT_TEST);
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            System.out.println("Linea: " + rst.getInt("id") + " " + rst.getString("license_plate"));
        }
    }
}