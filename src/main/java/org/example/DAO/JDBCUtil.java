package org.example.DAO;

import java.sql.*;

public class JDBCUtil {

    // Thông tin kết nối
    private static final String SERVER_NAME = "DESKTOP-3J3NO7F\\SQLEXPRESS"; // Thay đổi nếu cần
    private static final String DATABASE_NAME = "QuanLyBanHang"; // Thay đổi nếu cần
    private static final String USERNAME = "sa"; // Thay đổi nếu cần
    private static final String PASSWORD = "123"; // Thay đổi nếu cần

    private static final String URL = "jdbc:sqlserver://" + SERVER_NAME + ";" +
            "databaseName=" + DATABASE_NAME + ";" +
            "encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối đến SQL Server: " + e.getMessage());
            return null;
        }
    }

    public static void printTableNames() {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (connection != null) {
                System.out.println("Kết nối thành công!");
                System.out.println("Danh sách bảng trong cơ sở dữ liệu:");
                while (resultSet.next()) {
                    System.out.println("- " + resultSet.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn danh sách bảng: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printTableNames();
    }
}
