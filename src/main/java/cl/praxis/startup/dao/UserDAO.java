package cl.praxis.startup.dao;

import cl.praxis.startup.models.UserDTO;

public interface UserDAO {
    UserDTO insertUser(UserDTO newUser);

    boolean authUser(String email, String password);

    UserDTO findUserByEmail(String email);
}
