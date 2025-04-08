package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/school-app/students/view")
public class StudentsViewController extends HttpServlet {
    private final IStudentDAO studentDAO = new StudentDAOImpl();
    private final IStudentService studentService = new StudentServiceImpl(studentDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String filterFirstname = (firstname != null) ? firstname.trim() : "";
        String filterLastname = (lastname != null) ? lastname.trim() : "";

        FiltersDTO filtersDTO = new FiltersDTO(filterFirstname, filterLastname);
        try {
            List<StudentReadOnlyDTO> filteredStudents = studentService.getFilteredStudents(filtersDTO);
            if (filteredStudents.isEmpty()) {
                req.setAttribute("message", "Δεν υπάρχουν μαθητές με τα εν λόγω κριτήρια.");
                req.getRequestDispatcher("/WEB-INF/jsp/students").forward(req, resp);
                return;
            }
            req.setAttribute("students", filteredStudents);
            req.getRequestDispatcher("/WEB-INF/jsp/students").forward(req, resp);
        } catch (StudentDaoException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/students").forward(req, resp);
        }
    }
}
