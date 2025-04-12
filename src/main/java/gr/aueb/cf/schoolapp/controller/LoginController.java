package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
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
    private final IUserService userService;

    public LoginController() {
        IUserDAO userDao = new UserDAOImpl();
        userService = new UserServiceImpl(userDao);
    }

    public LoginController(IUserService userService) {
        this.userService = userService;
    }

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
                req.setAttribute("error", "Λανθασμένο email ή Κωδικός Εισόδου");
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

            UserReadOnlyDTO user = userService.getUserByUsername(username);
            if (user == null) {
                req.setAttribute("error", "User with username " + username + " was not found");
                req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
                return;
            }

            session.setAttribute("role", user.getRole());

            if ("ADMIN".equals(user.getRole())) {
                session.setMaxInactiveInterval(ADMIN_TIME_OUT);
            }

            // PRG Design Pattern
            resp.sendRedirect(req.getContextPath() + "/school-app/dashboard");
        } catch (UserDaoException | UserNotFoundException e) {
//            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
