package cl.praxis.startup.dao;

import cl.praxis.startup.models.UserDTO;

import java.sql.SQLException;

public interface UserDAO {
    UserDTO insertUser(UserDTO newUser);

    boolean authUser(String email, String password);

    boolean emailExists(String email) throws SQLException;

    UserDTO findUserByEmail(String email) throws SQLException;
}
