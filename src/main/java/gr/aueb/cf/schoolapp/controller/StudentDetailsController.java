package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.*;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.CityServiceImpl;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/school-app/students/view/student")
public class StudentDetailsController extends HttpServlet {

    private final ICityService cityService;
    private final IStudentService studentService;

    public StudentDetailsController() {
        ICityDAO cityDAO = new CityDAOImpl();
        IStudentDAO studentDAO = new StudentDAOImpl();
        cityService = new CityServiceImpl(cityDAO);
        studentService = new StudentServiceImpl(studentDAO);
    }

    // Dependency Injection Via Constructor injection - useful for mocking services in tests
    public StudentDetailsController(IStudentService studentService, ICityService cityService) {
        this.studentService = studentService;
        this.cityService = cityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> cities;
        String message = null;
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            message = "Το ID μαθητή είναι απαραίτητο";
        } else {
            try {
                cities = cityService.getAllCities();
                Long id = Long.parseLong(idParam.trim());
                StudentReadOnlyDTO studentReadOnlyDTO = studentService.getStudentById(id);
                req.setAttribute("cities", cities);
                req.setAttribute("student", studentReadOnlyDTO);
            } catch (NumberFormatException e) {
                message = "Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός.";
            }  catch (StudentNotFoundException e) {
                message = "Δεν βρέθηκε μαθητής με το συγκεκριμένο ID.";
            } catch (StudentDaoException e) {
                message = "Σφάλμα κατά την ανάκτηση των στοιχείων του μαθητή.";
            } catch (CityDaoException e) {
                message = "Σφάλμα κατά την ανάκτηση των στοιχείων των πόλεων.";
            }
        }

        if (message != null) {
            req.setAttribute("message", message);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/student-details.jsp").forward(req, resp);
    }
}
