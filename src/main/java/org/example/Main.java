package org.example;



import org.example.jdbc.StudentDAO;
import org.example.jdbc.model.Student;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/";
        String url2 = "jdbc:postgresql://localhost:5432/jdbc_db";
        String username = "user";
        String password = "";
        Connection db;

        try {
            db = DriverManager.getConnection(url, username, password);
            Statement databaseCreation = db.createStatement();
            databaseCreation.executeUpdate("DROP DATABASE IF EXISTS JDBC_DB");
            databaseCreation.executeUpdate("CREATE DATABASE JDBC_DB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            db = DriverManager.getConnection(url2, username, password);


            StudentDAO studao = new StudentDAO(db); //Will create table if not exists
            Student fstu = new Student();
            fstu.setFirstName("Navin");
            fstu.setLastName("S");
            fstu.setAddress("xyz");
            fstu.setCity("Bengaluru");
            fstu.setEmail("karupal2002@gmail.com");
            fstu.setPhone("8217703098");
            fstu.setState("KA");
            fstu.setZipcode("5600035");
            studao.create(fstu);

            Student findstu = studao.findById(1);
            System.out.println(findstu);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}