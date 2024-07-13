package cl.praxis.startup.controllers;

import cl.praxis.startup.models.UserDTO;
import cl.praxis.startup.services.UserService;
import cl.praxis.startup.services.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch(request.getParameter("action")) {
            case "registerPage":
                showRegisterPage(request,response);
                break;
            case "loginPage":
                showLoginPage(request,response);
                break;
            case "login":
                try {
                    login(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "register":
                try {
                    register(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default: return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(userService.authUser(email,password)){
            UserDTO userDTO = userService.findUserByEmail(email);
            request.getSession().setAttribute("user", userDTO.getNick());
            request.getSession().setAttribute("email", email);
            if(userService.isAdmin(userDTO))
                request.getSession().setAttribute("userRole", "admin");
            request.getSession().setAttribute("users", getAllUsers(request,response));
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }else{
            request.setAttribute("error", "Incorrect email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String email = request.getParameter("email");
        String nick = request.getParameter("nick");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String weightStr = request.getParameter("weight");
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        // Convertir peso a decimal si se proporcionó
        Integer peso = null;
        if (weightStr != null && !weightStr.isEmpty()) {
            peso = Integer.parseInt(weightStr);
        }

        // Lógica para almacenar el nuevo usuario en la base de datos
        UserDTO newUser = new UserDTO(email, nick, name, password, peso, createdAt, updatedAt);

        if(!userService.userExists(newUser)){
            request.setAttribute("error", "Email already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }else{
            userService.registerUser(newUser);
            // Redirigir a una página de éxito
            request.getSession().setAttribute("user", nick);
            request.getSession().setAttribute("email", email);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    private List<UserDTO> getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return userService.findAllUsers();
    }
}
