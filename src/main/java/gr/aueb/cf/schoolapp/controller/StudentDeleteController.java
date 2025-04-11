package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/school-app/students/delete")
public class StudentDeleteController extends HttpServlet {
    private final IStudentService studentService;

    public StudentDeleteController() {
        IStudentDAO studentDAO = new StudentDAOImpl();
        studentService = new StudentServiceImpl(studentDAO);
    }

    // Dependency Injection Via Constructor injection - useful for mocking services in tests
    public StudentDeleteController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String message = null;

        if (idParam == null || idParam.trim().isEmpty()) {
            message = "Το id μαθητή είναι απαραίτητο";
        } else {
            try {
                Long id = Long.parseLong(idParam);
                studentService.deleteStudent(id);
                req.setAttribute("id", id);
            } catch (NumberFormatException e) {
                message = "Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός.";
            } catch (StudentDaoException e) {
                message = "Σφάλμα στην βάση δεδομένων κατά την διαγραφή του μαθητή.";
            } catch (StudentNotFoundException e) {
                message = "Δεν βρέθηκε μαθητής με το συγκεκριμένο ID.";
            }
        }
        if (message != null) {
            req.setAttribute("error", message);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp").forward(req, resp);
    }
}
