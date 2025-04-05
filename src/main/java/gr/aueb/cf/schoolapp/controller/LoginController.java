package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.dao.IUserDao;
import gr.aueb.cf.schoolapp.dao.UserDaoImpl;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final IUserDao userDao = new UserDaoImpl();
    private final IUserService userService = new UserServiceImpl(userDao);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ADMIN_TIME_OUT = 30 * 60;

        // Data Binding
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        LoginUserDTO userDTO = new LoginUserDTO(username, password);

        boolean principleIsAuthenticated = false;
        try {
            principleIsAuthenticated = AuthenticationProvider.authenticate(userDTO);

            if (!principleIsAuthenticated) {
                req.setAttribute("error", "Invalid Credentials");
                req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
                return;
            }

            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("authenticated", true);
            session.setAttribute("username", username);
            session.setAttribute("role", userService.getUserByUsername(username).getRole());

            if (session.getAttribute("role").equals("ADMIN")) {
                session.setMaxInactiveInterval(ADMIN_TIME_OUT);
            }

            // PRG Design Pattern
            resp.sendRedirect(req.getContextPath() + "/school-app/dashboard");
        } catch (UserDaoException | UserNotFoundException e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
