package gr.aueb.cf.schoolapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error-handler")
public class NotFoundPageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null && statusCode == 404) {
            req.getRequestDispatcher("/WEB-INF/jsp/not-found-page.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/school-app/dashboard");
        }
    }
}
