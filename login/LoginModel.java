package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {

        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (this.connection == null) {
            System.exit(1);
        }
    }

    public boolean checkIfDatabaseConnected() {
        return this.connection != null;
    }

    public boolean isLogin(String username, String password, String option) throws Exception {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";

        try {
            preparedStatement = this.connection.prepareStatement(sql);
            // parameters that are passed while logging in
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, option);

            resultSet = preparedStatement.executeQuery();

            boolean bool1;
            // function that checks if there is more data in database
            if (resultSet.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }

        // function that closes the connection
        finally {
            {
                preparedStatement.close();
                resultSet.close();
            }
        }
    }
}
