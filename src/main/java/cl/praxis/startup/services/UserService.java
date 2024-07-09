package cl.praxis.startup.services;

import cl.praxis.startup.models.UserDTO;

import java.sql.SQLException;

public interface UserService {
    UserDTO insertUser(UserDTO newUser);

    boolean authUser(String email, String password);


    UserDTO registerUser(UserDTO newUser) throws SQLException;

    UserDTO findUserByEmail(String email) throws SQLException;

    boolean userExists(UserDTO newUser) throws SQLException;
}
