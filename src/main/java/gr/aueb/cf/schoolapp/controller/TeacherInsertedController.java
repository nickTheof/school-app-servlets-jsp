package gr.aueb.cf.schoolapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/school-app/teachers/teacher-inserted")
public class TeacherInsertedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("teacherInfo") != null) {
            req.getRequestDispatcher("/WEB-INF/jsp/teacher-inserted.jsp").forward(req, resp);
            req.getSession().removeAttribute("teacherInfo");
            return;
        } else {
            resp.sendRedirect(req.getContextPath() + "/school-app/dashboard");
        }

    }
}
