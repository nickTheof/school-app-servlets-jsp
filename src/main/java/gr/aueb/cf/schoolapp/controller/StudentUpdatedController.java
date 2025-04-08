package gr.aueb.cf.schoolapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/school-app/students/student-updated")
public class StudentUpdatedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("studentInfo") != null) {
            req.getRequestDispatcher("/WEB-INF/jsp/student-updated.jsp").forward(req, resp);
            req.getSession().removeAttribute("studentInfo");
        } else {
            resp.sendRedirect(req.getContextPath() + "/school-app/dashboard");
        }

    }
}
