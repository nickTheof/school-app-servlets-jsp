package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.CityServiceImpl;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/school-app/teachers/insert")
public class TeacherInsertController extends HttpServlet {
    private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);
    private final ICityDAO cityDAO = new CityDAOImpl();
    private final ICityService cityService = new CityServiceImpl(cityDAO);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> cities = null;
        try {
            cities = cityService.getAllCities();
        } catch (CityDaoException e) {
            req.getSession().setAttribute("message", e.getMessage());
            req.getRequestDispatcher(req.getContextPath() + "/school-app/teachers/insert").forward(req, resp);
        }
        req.setAttribute("cities", cities);

        if (req.getSession().getAttribute("insertDTO") != null) {
            req.setAttribute("insertDTOInfo", req.getSession().getAttribute("insertDTO"));
            req.setAttribute("firstnameError", req.getSession().getAttribute("firstnameError"));
            req.setAttribute("lastnameError", req.getSession().getAttribute("lastnameError"));
            req.setAttribute("vatError", req.getSession().getAttribute("vatError"));
            req.setAttribute("fatherNameError",req.getSession().getAttribute("fatherNameError"));
            req.setAttribute("phoneNumError",req.getSession().getAttribute("phoneNumError"));
            req.setAttribute("emailError",req.getSession().getAttribute("emailError"));
            req.setAttribute("streetError",req.getSession().getAttribute("streetError"));
            req.setAttribute("streetNumError",req.getSession().getAttribute("streetNumError"));
            req.setAttribute("zipcodeError",req.getSession().getAttribute("zipcodeError") );
            // Clear session data (so it doesn't persist after refresh)
            req.getSession().removeAttribute("insertDTO");
            req.getSession().removeAttribute("firstnameError");
            req.getSession().removeAttribute("lastnameError");
            req.getSession().removeAttribute("vatError");
            req.getSession().removeAttribute("fatherNameError");
            req.getSession().removeAttribute("phoneNumError");
            req.getSession().removeAttribute("emailError");
            req.getSession().removeAttribute("streetError");
            req.getSession().removeAttribute("streetNumError");
            req.getSession().removeAttribute("zipcodeError");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/teacher-insert.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherInsertDTO insertDTO;
        Map<String, String> errors;
        String firstnameError;
        String lastnameError;
        String vatError;
        String fatherNameError;
        String phoneNumError;
        String emailError;
        String streetError;
        String streetNumError;
        String zipcodeError;
        String message;

        // Data binding
        String firstname = (req.getParameter("firstname") != null) ? req.getParameter("firstname").trim() : "";
        String lastname = (req.getParameter("lastname") != null) ? req.getParameter("lastname").trim() : "";
        String vat = (req.getParameter("vat") != null) ? req.getParameter("vat").trim() : "";
        String fatherName = (req.getParameter("fatherName") != null) ? req.getParameter("fatherName").trim() : "";
        String phoneNum = (req.getParameter("phoneNum") != null) ? req.getParameter("phoneNum").trim() : "";
        String email = (req.getParameter("email") != null) ? req.getParameter("email").trim() : "";
        String street = (req.getParameter("street") != null) ? req.getParameter("street").trim() : "";
        String streetNum = (req.getParameter("streetNum") != null) ? req.getParameter("streetNum").trim() : "";
        String zipcode = (req.getParameter("zipcode") != null) ? req.getParameter("zipcode").trim() : "";
        Integer cityId = (req.getParameter("cityId") != null) ? Integer.parseInt(req.getParameter("cityId").trim()) : 0;
        insertDTO = new TeacherInsertDTO(firstname, lastname, vat, fatherName, phoneNum,
                email, street, streetNum, cityId, zipcode);


        try {
            // Validate dto
            errors = TeacherValidator.validate(insertDTO);

            if (!errors.isEmpty()) {
                firstnameError = errors.getOrDefault("firstname", "");
                lastnameError = errors.getOrDefault("lastname", "");
                vatError = errors.getOrDefault("vat", "");
                fatherNameError = errors.getOrDefault("fatherName", "");
                phoneNumError = errors.getOrDefault("phoneNumber", "");
                emailError = errors.getOrDefault("email", "");
                streetError = errors.getOrDefault("street", "");
                streetNumError = errors.getOrDefault("streetNumber", "");
                zipcodeError = errors.getOrDefault("zipCode", "");

                req.getSession().setAttribute("firstnameError", firstnameError);
                req.getSession().setAttribute("lastnameError", lastnameError);
                req.getSession().setAttribute("vatError", vatError);
                req.getSession().setAttribute("fatherNameError", fatherNameError);
                req.getSession().setAttribute("phoneNumError", phoneNumError);
                req.getSession().setAttribute("emailError", emailError);
                req.getSession().setAttribute("streetError", streetError);
                req.getSession().setAttribute("streetNumError", streetNumError);
                req.getSession().setAttribute("zipcodeError", zipcodeError);
                req.getSession().setAttribute("insertDTO", insertDTO);
                resp.sendRedirect(req.getContextPath() + "/school-app/teachers/insert");
                return;
            }

            // Call the service

            TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);
            HttpSession session = req.getSession(false);
            session.setAttribute("teacherInfo", readOnlyDTO);
            // PRG Pattern
            resp.sendRedirect(req.getContextPath() + "/school-app/teachers/teacher-inserted");

        } catch (TeacherDaoException | TeacherAlreadyExistsException e) {
            message = e.getMessage();
            req.getSession().setAttribute("message", message);
            req.getRequestDispatcher("/WEB-INF/jsp/teacher-insert.jsp")
                    .forward(req, resp);
        }
    }
}
