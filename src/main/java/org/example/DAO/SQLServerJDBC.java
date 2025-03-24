package org.example.DAO;

import java.sql.*;

public class SQLServerJDBC {

    public static void main(String[] args) {

        String serverName = "RILEY"; // Kiểm tra lại tên server
        String databaseName = "QuanLyBanHang"; // Thay đổi thành tên database của bạn
        String username = "sa"; // Thay đổi nếu cần
        String password = "123"; // Thay đổi nếu cần

        String url = "jdbc:sqlserver://" + serverName + ";" +
                "databaseName=" + databaseName + ";encrypt=true;trustServerCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Kết nối thành công!");

                // Truy vấn danh sách các bảng
                String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                System.out.println("Danh sách bảng trong cơ sở dữ liệu:");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("TABLE_NAME"));
                }

                // Đóng kết nối
                resultSet.close();
                statement.close();
                connection.close();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
