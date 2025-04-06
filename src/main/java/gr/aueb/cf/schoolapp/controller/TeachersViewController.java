package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/school-app/teachers/view")
public class TeachersViewController extends HttpServlet {

    private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TeacherReadOnlyDTO> teacherReadOnlyDTOS;

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String firstnameFilter = firstname == null ? "" : firstname;
        String lastnameFilter = lastname == null ? "" : lastname;

        FiltersDTO filtersDTO = new FiltersDTO(firstnameFilter, lastnameFilter);

        try {
            teacherReadOnlyDTOS = teacherService.getFilteredTeachers(filtersDTO);
            if (teacherReadOnlyDTOS.isEmpty()) {
                req.setAttribute("message", "Δεν βρέθηκαν καθηγητές με τα εν λόγω κριτήρια");
                req.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("teachers", teacherReadOnlyDTOS);
            req.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(req, resp);
        } catch (TeacherDaoException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(req, resp);
        }
    }
}
