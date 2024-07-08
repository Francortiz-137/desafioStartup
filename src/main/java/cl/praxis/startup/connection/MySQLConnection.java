package cl.praxis.startup.connection;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/startup";
    private String jadbcUsername = "root";
    private String jdbcPassword = "1234";

    private MySQLConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbcURL, jadbcUsername, jdbcPassword);
        }catch (ClassNotFoundException | SQLException e){
            throw new SQLException(e);
        }
    }

    public static MySQLConnection getInstance() throws SQLException {
        if(instance == null){
            instance = new MySQLConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new MySQLConnection();
        }
        return instance;
    }
}
