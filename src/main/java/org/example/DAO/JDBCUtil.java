package org.example.DAO;

import java.sql.*;

public class JDBCUtil {

    // Thông tin kết nối máy Tín
//    private static final String SERVER_NAME = "PDESKTOP-3J3NO7F\\SQLEXRESS"; // Thay đổi nếu cần
//    private static final String DATABASE_NAME = "QuanLyBanHang"; // Thay đổi nếu cần
//    private static final String USERNAME = "sa"; // Thay đổi nếu cần
//    private static final String PASSWORD = "123"; // Thay đổi nếu cần

    // Thông tin kết nối máy Thư
    private static final String SERVER_NAME = "DESKTOP-QFAM29O\\TIN"; // Thay đổi nếu cần
    private static final String DATABASE_NAME = "QuanLyBanHang"; // Thay đổi nếu cần
    private static final String USERNAME = "sa"; // Thay đổi nếu cần
    private static final String PASSWORD = "123"; // Thay đổi nếu cần

    private static final String URL = "jdbc:sqlserver://" + SERVER_NAME + ";" +
            "databaseName=" + DATABASE_NAME + ";" +
            "encrypt=true;trustServerCertificate=true;";

    // Kết nối đến SQL Server
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối đến SQL Server: " + e.getMessage());
            return null;
        }
    }

    // Thực thi câu lệnh SQL (SELECT)
    public static ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            if (connection != null) {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thực thi câu lệnh SQL: " + e.getMessage());
        }
        return resultSet;
    }

    // Thực thi câu lệnh SQL (SELECT) với PreparedStatement
    public static ResultSet executePreparedQuery(String sql, Object... params) {
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
                resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thực thi câu lệnh SQL: " + e.getMessage());
        }
        return resultSet;
    }

    // Thực thi câu lệnh SQL (INSERT, UPDATE, DELETE)
    public static int executeUpdate(String sql) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("Lỗi thực thi câu lệnh SQL: " + e.getMessage());
            }
        }
        return 0;
    }

    // Thực thi câu lệnh SQL với PreparedStatement
    public static int executePreparedUpdate(String sql, Object... params) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Lỗi thực thi câu lệnh SQL: " + e.getMessage());
            }
        }
        return 0;
    }

    // Thực thi Procedure
    public static int executeProcedure(String procedureName, Object... params) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call " + procedureName + "}");
                for (int i = 0; i < params.length; i++) {
                    callableStatement.setObject(i + 1, params[i]);
                }
                return callableStatement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Lỗi thực thi Procedure: " + e.getMessage());
            }
        }
        return 0;
    }




    // Đóng kết nối
    public static void closeConnection(){
        try {
            Connection connection = getConnection();
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi đóng kết nối: " + e.getMessage());
        }
    }
}
