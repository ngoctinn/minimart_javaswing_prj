package org.example.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLSConnection_test {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;trustServerCertificate=true;";
    private static final String USER = "sa"; // Nếu dùng Windows Authentication, để trống
    private static final String PASSWORD = "123"; // Thay bằng mật khẩu thật của SQL Server

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Load Driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối SQL Server thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println(" Không tìm thấy driver SQL Server!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối SQL Server: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection(); // Test kết nối
    }
}

