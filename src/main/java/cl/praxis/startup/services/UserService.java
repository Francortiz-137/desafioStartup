package cl.praxis.startup.services;

import cl.praxis.startup.models.UserDTO;

public interface UserService {
    UserDTO insertUser(UserDTO newUser);

    boolean authUser(String email, String password);

    UserDTO findUserByEmail(String email);
}
