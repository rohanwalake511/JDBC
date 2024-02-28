package org.example.jdbc;

import org.example.jdbc.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {

    private static final String INSERT=
            "INSERT into customer(first_name,last_name,email,phone,address,city,state,zipcode) VALUES(?,?,?,?,?,?,?,?)";

    private static final String GET_ONE="SELECT customer_id,first_name,last_name,email,phone,address,city,state,zipcode FROM customer" +
            " WHERE customer_id=?";

    private static final String UPDATE = "UPDATE customer SET first_name = ?, last_name=?, " +
            "email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

    private static final String DELETE="DELETE FROM customer WHERE customer_id=?";

    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer create(Customer dto) {

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
    public Customer findById(long id) {
        Customer customer=new Customer();
        try(PreparedStatement preparedStatement=this.connection.prepareStatement(GET_ONE);){

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipcode(resultSet.getString("zipcode"));
            }


        }catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);

        }
        return customer;    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer = null;
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
            customer = this.findById(dto.getId());
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
        return customer;
    }

    @Override
    public void delete(long id) {

        int i;
        Customer customer=new Customer();
        try(PreparedStatement preparedStatement=this.connection.prepareStatement(DELETE);){

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);

        }

    }
}