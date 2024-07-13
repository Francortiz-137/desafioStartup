package cl.praxis.startup.dao.impl;

import cl.praxis.startup.connection.MySQLConnection;
import cl.praxis.startup.dao.UserDAO;
import cl.praxis.startup.models.UserDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String SELECT_ALL_USERS = "SELECT id, correo, created_at, nick, nombre, password, peso, updated_at FROM usuarios";
    private static final String EMAIL_EXISTS_SQL = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
    private static final String SELECT_USER_BY_EMAIL = "SELECT id, correo, created_at, nick, nombre, password, peso, updated_at FROM usuarios WHERE correo = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO usuarios (correo, created_at, nick, nombre, password, peso, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String AUTH_USER_SQL = "SELECT id, correo, created_at, nick, nombre, password, peso, updated_at FROM usuarios WHERE correo = ? AND password = ?";
    private static final String IS_ADMIN_SQL = "SELECT COUNT(*) FROM roles_usuarios ru JOIN roles r ON ru.rol_id = r.id WHERE ru.usuario_id = ? AND r.nombre = 'admin'";
    @Override
    public UserDTO insertUser(UserDTO user) {
        UserDTO newUser = new UserDTO();
        try(PreparedStatement preparedStatement = MySQLConnection.getInstance().getConnection().prepareStatement(INSERT_USER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, String.valueOf(user.getCreatedAt()));
            preparedStatement.setString(3, user.getNick());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getWeight());
            preparedStatement.setString(7, String.valueOf(user.getUpdatedAt()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                newUser = new UserDTO(id, user.getEmail(), user.getNick(), user.getName(), user.getPassword(), user.getWeight(), user.getCreatedAt(),user.getUpdatedAt());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return newUser;
    }

    public boolean authUser(String email, String password) {
        try (PreparedStatement statement = MySQLConnection.getInstance().getConnection().prepareStatement(AUTH_USER_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean emailExists(String email) throws SQLException {
        try (PreparedStatement statement = MySQLConnection.getInstance().getConnection().prepareStatement(EMAIL_EXISTS_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        UserDTO user = null;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
                String nick = rs.getString("nick");
                String name = rs.getString("nombre");
                String password = rs.getString("password");
                Integer weight = rs.getInt("peso");
                Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
                LocalDateTime createdAt = createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null;
                LocalDateTime updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;
                user = new UserDTO(id,email,nick,name,password,weight,createdAt,updatedAt);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean isUserAdmin(int id) {
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_ADMIN_SQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                String email = rs.getString("correo");
                Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
                String nick = rs.getString("nick");
                String name = rs.getString("nombre");
                String password = rs.getString("password");
                Integer weight = rs.getInt("peso");
                Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
                LocalDateTime createdAt = createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null;
                LocalDateTime updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;
                UserDTO user = new UserDTO(id,email,nick,name,password,weight,createdAt,updatedAt);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
