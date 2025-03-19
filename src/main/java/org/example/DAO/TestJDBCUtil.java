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

            //Bước 3: Thực thi câu lệnh ( truy vấn các bảng)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
