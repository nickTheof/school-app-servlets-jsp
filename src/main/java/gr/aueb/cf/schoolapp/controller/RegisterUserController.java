package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;
import gr.aueb.cf.schoolapp.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/register")
public class RegisterUserController extends HttpServlet {

    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserService userService = new UserServiceImpl(userDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/register-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InsertUserDTO insertUserDTO;
        // Data Binding
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String confirmPassword = req.getParameter("confirmPassword").trim();
        String role = req.getParameter("role");

        String errorMessage = "";
        Map<String, String> errors;

        String usernameMessage;
        String passwordMessage;
        String confirmPasswordMessage;

        User user;

        try {
            insertUserDTO =  new InsertUserDTO(username, password, confirmPassword, role);
            errors = UserValidator.validate(insertUserDTO);
            if (!errors.isEmpty()) {
                usernameMessage = errors.getOrDefault("username", "");
                passwordMessage = errors.getOrDefault("password", "");
                confirmPasswordMessage = errors.getOrDefault("confirmPassword", "");

                req.setAttribute("usernameMessage", usernameMessage);
                req.setAttribute("passwordMessage", passwordMessage);
                req.setAttribute("confirmPasswordMessage", confirmPasswordMessage);

                req.setAttribute("userRegisterDTO", insertUserDTO);
                req.getRequestDispatcher("/WEB-INF/jsp/register-user.jsp").forward(req, resp);
                return;
            }
            UserReadOnlyDTO userReadOnlyDTO = userService.insertUser(insertUserDTO);
            HttpSession session = req.getSession();
            session.setAttribute("userInfo", userReadOnlyDTO);
            resp.sendRedirect(req.getContextPath() + "/school-app/user-registered");

        } catch (UserDaoException | UserAlreadyExistsException e) {
            errorMessage = e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("/WEB-INF/jsp/user-register.jsp")
                    .forward(req, resp);
        }

    }
}
