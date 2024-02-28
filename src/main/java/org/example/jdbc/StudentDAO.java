package org.example.jdbc;

import org.example.jdbc.model.Student;

import java.sql.*;
import java.util.List;

public class StudentDAO extends CustomDAO<Student> {


    private static final String INSERT=
            "INSERT into student(first_name,last_name,email,phone,address,city,state,zipcode) VALUES(?,?,?,?,?,?,?,?)";

    private static final String GET_ONE="SELECT student_id,first_name,last_name,email,phone,address,city,state,zipcode FROM student" +
            " WHERE student_id=?";

    private static final String UPDATE = "UPDATE student SET first_name = ?, last_name=?, " +
            "email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE student_id = ?";

    private static final String DELETE="DELETE FROM student WHERE student_id=?";


    public StudentDAO(Connection connection) {
        super(connection);
        String create_table = "CREATE TABLE student(\n" +
                "    student_id SERIAL,\n" +
                "    first_name VARCHAR(100),\n" +
                "    last_name VARCHAR(100),\n" +
                "    email VARCHAR(100),\n" +
                "    phone VARCHAR(100),\n" +
                "    address VARCHAR(100),\n" +
                "    city VARCHAR(100),\n" +
                "    state VARCHAR(100),\n" +
                "    zipcode VARCHAR(100),\n" +
                "    PRIMARY KEY(student_id)\n" +
                ");";
        try {
            Statement schema_creation = connection.createStatement();
//            schema_creation.executeUpdate("DROP TABLE IF EXIST student;");
            schema_creation.executeUpdate(create_table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findById(long id) {
        Student student =new Student();
        try(PreparedStatement preparedStatement=this.connection.prepareStatement(GET_ONE);){

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                student.setId(resultSet.getLong("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone(resultSet.getString("phone"));
                student.setAddress(resultSet.getString("address"));
                student.setCity(resultSet.getString("city"));
                student.setState(resultSet.getString("state"));
                student.setZipcode(resultSet.getString("zipcode"));
            }


        }catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);

        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public Student update(Student dto) {
        Student student = null;
        try{
            this.connection.setAutoCommit(false);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipcode());
            statement.setLong(9, dto.getId());
            statement.execute();
            this.connection.commit();
            student = this.findById(dto.getId());
        }catch(SQLException e){
            try{
                this.connection.rollback();
            }catch (SQLException sqle){
                e.printStackTrace();
                throw new RuntimeException(sqle);
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public Student create(Student dto) {

        try(PreparedStatement statement=this.connection.prepareStatement(INSERT);) {

            statement.setString(1,dto.getFirstName());
            statement.setString(2,dto.getLastName());
            statement.setString(3,dto.getEmail());
            statement.setString(4,dto.getPhone());
            statement.setString(5,dto.getAddress());
            statement.setString(6,dto.getCity());
            statement.setString(7,dto.getState());
            statement.setString(8,dto.getZipcode());
            statement.execute();
            return  null;


        }catch (SQLException exception){
            exception.printStackTrace();
            throw  new RuntimeException(exception);

        }


    }

    @Override
    public void delete(long id) {

        int i;
        Student student=new Student();
        try(PreparedStatement preparedStatement=this.connection.prepareStatement(DELETE);){

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);

        }

    }
}