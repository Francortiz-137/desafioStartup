package cl.praxis.startup.services;

import cl.praxis.startup.models.UserDTO;
import cl.praxis.startup.models.VehicleDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserDTO insertUser(UserDTO newUser);

    boolean authUser(String email, String password);


    UserDTO registerUser(UserDTO newUser) throws SQLException;

    UserDTO findUserByEmail(String email) throws SQLException;

    boolean userExists(UserDTO newUser) throws SQLException;

    boolean isAdmin(UserDTO userDTO);

    List<UserDTO> findAllUsers();

    UserDTO findUserById(int id);

    List<VehicleDTO> getVehicles(UserDTO userDTO);
}
