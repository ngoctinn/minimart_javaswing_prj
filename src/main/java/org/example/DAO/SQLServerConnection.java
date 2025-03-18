package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;trustServerCertificate=false;integratedSecurity=true;";
        
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                System.out.println("Kết nối thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
