package cl.praxis.startup.controllers;

import cl.praxis.startup.models.UserDTO;
import cl.praxis.startup.models.VehicleDTO;
import cl.praxis.startup.services.UserService;
import cl.praxis.startup.services.VehicleService;
import cl.praxis.startup.services.impl.UserServiceImpl;
import cl.praxis.startup.services.impl.VehicleServiceImpl;
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
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        vehicleService = new VehicleServiceImpl();
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
            case "showVehicles":
                showVehiclesPage(request,response);
                break;
            case "addVehicle":
                addVehicle(request,response);
                break;
            default:
                showIndex(request,response);
                break;
        }
    }

    private void addVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        String userIdStr = request.getParameter("userId");

        if (name == null || name.trim().isEmpty() ||
                url == null || url.trim().isEmpty() ||
                userIdStr == null || userIdStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("user-vehicles.jsp").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdStr);
        VehicleDTO vehicleDTO = new VehicleDTO(name,url,userId);
        vehicleService.insertVehicle(vehicleDTO);
        UserDTO userDTO = userService.findUserById(userId);
        List<VehicleDTO> vehicleList = userService.getVehicles(userDTO);
        request.setAttribute("vehicles", vehicleList);
        request.getRequestDispatcher("user-vehicles.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    private void showIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void showVehiclesPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("userId"));
        UserDTO userDTO = userService.findUserById(id);
        List<VehicleDTO> vehicleList = userService.getVehicles(userDTO);
        request.setAttribute("vehicles", vehicleList);
        request.setAttribute("userId", id);
        request.getRequestDispatcher("user-vehicles.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

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

        if (email == null || email.trim().isEmpty() ||
                nick == null || nick.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        // Convertir peso a decimal si se proporcionó
        float weight = 0.0f;
        if (weightStr != null && !weightStr.isEmpty()) {
            weight = Float.parseFloat(weightStr);
        }else{
            throw new ServletException("Invalid weight");
        }

        // Lógica para almacenar el nuevo usuario en la base de datos
        UserDTO newUser = new UserDTO(email, nick, name, password, weight, createdAt, updatedAt);

        if(userService.userExists(newUser)){
            request.setAttribute("error", "Email already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }else{
            userService.registerUser(newUser);
            // Redirigir a una página de éxito
            request.getSession().setAttribute("user", nick);
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("users", getAllUsers(request,response));
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    private List<UserDTO> getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return userService.findAllUsers();
    }
}
