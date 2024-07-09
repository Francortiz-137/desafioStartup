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
import java.time.LocalDateTime;

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
                login(request,response);
                break;
            case "register":
                register(request,response);
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(userService.authUser(email,password)){
            UserDTO userDTO = userService.findUserByEmail(email);
            request.getSession().setAttribute("user", userDTO.getNick());
            request.getSession().setAttribute("email", email);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("correo");
        String nick = request.getParameter("nick");
        String name = request.getParameter("nombre");
        String password = request.getParameter("password");
        String weightStr = request.getParameter("peso");
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        // Convertir peso a decimal si se proporcionó
        Integer peso = null;
        if (weightStr != null && !weightStr.isEmpty()) {
            peso = Integer.parseInt(weightStr);
        }

        // Lógica para almacenar el nuevo usuario en la base de datos
        UserDTO newUser = new UserDTO(email, nick, name, password, peso, createdAt, updatedAt);
        userService.insertUser(newUser);

        // Redirigir a una página de éxito
        request.getSession().setAttribute("user", nick);
        request.getSession().setAttribute("email", email);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }


}
