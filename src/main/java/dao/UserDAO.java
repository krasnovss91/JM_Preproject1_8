package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                long idUser = result.getLong(1);
                String nameUser = result.getString(2);
                String loginUser = result.getString(3);
                String passwordUser = result.getString(4);

                allUsers.add(new User(idUser, nameUser, loginUser, passwordUser));
            }
          result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }


    public User getUserById(long id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            ResultSet result = preparedStatement.executeQuery();
            result.next();
            long idUser = result.getLong(1);
            String nameUser = result.getString(2);
            String loginUser = result.getString(3);
            String passwordUser = result.getString(4);


            user = new User(idUser, nameUser, loginUser, passwordUser);
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }


    public long getUserIdByName(String name) /*throws SQLException*/ {
        String sql = "SELECT * FROM users WHERE name = ?";
        long id = 0L;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);

            ResultSet result = preparedStatement.executeQuery();
            result.next();
            id = result.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public User getUserByName(String name) {

        User user = null;
        long id = 0L;
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            long idUser = result.getLong(1);
            String nameUser = result.getString(2);
            String loginUser = result.getString(3);
            String passwordUser = result.getString(4);
            user = new User(idUser, nameUser, loginUser, passwordUser);

            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, login = ?, password = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public void deleteUserById(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }


 /*   public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE if NOT EXISTS users (id bigint auto_increment, name varchar(256), login varchar(256), password varchar(256), primary key (id))");
        stmt.close();
    } */
    public void createTable() throws SQLException{
        String sql = "CREATE TABLE if NOT EXISTS users (id bigint auto_increment, name varchar(256), login varchar(256), password varchar(256), primary key (id))";
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

   /* public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE if EXISTS users");
        stmt.close();
    } */
    public void dropTable() throws SQLException{
        String sql = "DROP TABLE if EXISTS users";
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
