package cl.praxis.startup.services.impl;

import cl.praxis.startup.dao.UserDAO;
import cl.praxis.startup.dao.impl.UserDAOImpl;
import cl.praxis.startup.models.UserDTO;
import cl.praxis.startup.services.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public UserDTO insertUser(UserDTO newUser) {
        return userDAO.insertUser(newUser);
    }

    @Override
    public boolean authUser(String email, String password) {
        return userDAO.authUser(email, password);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }
}
