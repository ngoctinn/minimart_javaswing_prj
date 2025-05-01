package org.example.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCUtil {
    public static void main(String[] args) {
        try {
            //Bước 1: Tạo kết nối
            Connection connection = JDBCUtil.getConnection();

            //Bước 2: Tạo statement
            Statement statement = connection.createStatement();

            // thông bá kết nối thành công
            if (connection != null) {
                System.out.println("Kết nối thành công");
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
