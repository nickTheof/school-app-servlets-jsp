package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/school-app/teachers/view/teacher")
public class TeacherDetailsController extends HttpServlet {
    private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String message = null;

        if (idParam == null || idParam.trim().isEmpty()) {
            message = "Το ID εκπαιδευτή είναι απαραίτητο.";
        } else {
            try {
                Long id = Long.parseLong(idParam.trim());
                TeacherReadOnlyDTO teacher = teacherService.getTeacherById(id);
                req.setAttribute("teacher", teacher);
            } catch (NumberFormatException e) {
                message = "Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός.";
            }  catch (TeacherNotFoundException e) {
                message = "Δεν βρέθηκε εκπαιδευτής με το συγκεκριμένο ID.";
            } catch (TeacherDaoException e) {
                message = "Σφάλμα κατά την ανάκτηση των στοιχείων του εκπαιδευτή.";
            }
        }

        if (message != null) {
            req.setAttribute("message", message);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/teacher-details.jsp").forward(req, resp);
    }

}
