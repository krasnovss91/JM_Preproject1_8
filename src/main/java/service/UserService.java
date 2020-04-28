package service;


import dao.UserDAO;
import exception.DBException;
import model.User;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public UserService() {
    }

    public User getUserByName(String name) { // Почему нет исключения?????
        return getUserDAO().getUserByName(name);
    }

    public User getUserById(Long id) {
        return getUserDAO().getUserById(id);
    }

    public List<User> getAllUsers() {
        return getUserDAO().getAllUsers();
    }

    public boolean addUser(User user) throws SQLException {
        if (getUserByName(user.getName()) == null) {
            getUserDAO().addUser(user);
            return true;
        }
        return false;
    }

    public void deleteUserById(Long id) throws SQLException {
        getUserDAO().deleteUserById(id);
    }

    public void updateUser(User user) throws SQLException {
        getUserDAO().updateUser(user);
    }


    public void createTable() throws DBException {
        UserDAO dao = getUserDAO();
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").
                    append("localhost:").
                    append("3306/").
                    append("db_example?").
                    append("user=root&").
                    append("password=password");

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserDAO getUserDAO() {
        return new UserDAO(getMysqlConnection());
    }
}
