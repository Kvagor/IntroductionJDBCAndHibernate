package org.example.dao;

import org.example.bean.User;
import org.example.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getJDBCConnection();

    @Override
    public void createUserTable() {
        String sql = "create table if not exists user (id bigint primary key not null auto_increment, name varchar(45),last_name varchar(45),age tinyint(3))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUserTable() {
        String sql = "drop table if exists user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into user (name,last_name,age) values (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete from user where id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "select * from user";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                allUsers.add(new User(id,name,lastName,age));
            }
            connection.commit();
        } catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    @Override
    public void clearUserTable() {
        String sql = "truncate table user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
