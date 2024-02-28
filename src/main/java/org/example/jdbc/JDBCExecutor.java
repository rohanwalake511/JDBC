package org.example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost"
                , "postgres", "user", "");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            // customerDAO.delete(10018);

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}