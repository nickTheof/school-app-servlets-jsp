package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherUpdateControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ITeacherService mockTeacherService;
    @Mock
    private ICityService mockCityService;
    @InjectMocks
    private TeacherUpdateController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        TeacherUpdateController defaultController = new TeacherUpdateController();
        assertNotNull(defaultController);
    }


    @Test
    void doGetCityServiceFailsSetErrorWillForward() throws CityDaoException, ServletException, IOException {
        when(mockCityService.getAllCities()).thenThrow(new CityDaoException("DB error"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request, times(0)).setAttribute(eq("cities"), any());
        verify(session).setAttribute(eq("message"), eq("DB error"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetTeacherServiceFailsSetErrorWillForward() throws CityDaoException, ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockTeacherService.getTeacherById(1L)).thenThrow(new TeacherDaoException("DB error"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(session).setAttribute(eq("message"), eq("DB error"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetTeacherServiceFailsTeacherNotFoundSetErrorWillForward() throws CityDaoException, ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockTeacherService.getTeacherById(1L)).thenThrow(new TeacherNotFoundException("Teacher not found"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(session).setAttribute(eq("message"), eq("Teacher not found"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetNumberFormatExceptionSetErrorWillForward() throws CityDaoException, ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("error");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(session).setAttribute(eq("message"), eq("For input string: \"error\""));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetSuccessRenderingWithNullUpdateDTOWillForward() throws CityDaoException, ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        TeacherReadOnlyDTO updateDTOInfo = new TeacherReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockTeacherService.getTeacherById(1L)).thenReturn(updateDTOInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("updateDTO")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(request).setAttribute(eq("updateDTOInfo"), eq(updateDTOInfo));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetSuccessRenderingWithNotNullUpdateDTOWillForward() throws CityDaoException, ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        TeacherReadOnlyDTO updateDTOInfo = new TeacherReadOnlyDTO(1L, "uuid", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");

        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockTeacherService.getTeacherById(1L)).thenReturn(updateDTOInfo);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("updateDTO")).thenReturn(updateDTOInfo);
        when(session.getAttribute("firstnameError")).thenReturn("Firstname error");
        when(session.getAttribute("lastnameError")).thenReturn("Lastname error");
        when(session.getAttribute("vatError")).thenReturn("VAT error");
        when(session.getAttribute("fatherNameError")).thenReturn("Father name error");
        when(session.getAttribute("phoneNumError")).thenReturn("Phone error");
        when(session.getAttribute("emailError")).thenReturn("Email error");
        when(session.getAttribute("streetError")).thenReturn("Street error");
        when(session.getAttribute("streetNumError")).thenReturn("Street num error");
        when(session.getAttribute("zipcodeError")).thenReturn("Zipcode error");

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute("cities", mockedCities);
        verify(request).setAttribute("updateDTOInfo", updateDTOInfo);

        verify(request).setAttribute("firstnameError", "Firstname error");
        verify(request).setAttribute("lastnameError", "Lastname error");
        verify(request).setAttribute("vatError", "VAT error");
        verify(request).setAttribute("fatherNameError", "Father name error");
        verify(request).setAttribute("phoneNumError", "Phone error");
        verify(request).setAttribute("emailError", "Email error");
        verify(request).setAttribute("streetError", "Street error");
        verify(request).setAttribute("streetNumError", "Street num error");
        verify(request).setAttribute("zipcodeError", "Zipcode error");

        verify(session).removeAttribute("updateDTO");
        verify(session).removeAttribute("firstnameError");
        verify(session).removeAttribute("lastnameError");
        verify(session).removeAttribute("vatError");
        verify(session).removeAttribute("fatherNameError");
        verify(session).removeAttribute("phoneNumError");
        verify(session).removeAttribute("emailError");
        verify(session).removeAttribute("streetError");
        verify(session).removeAttribute("streetNumError");
        verify(session).removeAttribute("zipcodeError");

        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostValidationFailsRedirectsBackToForm() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstname")).thenReturn(""); // Invalid trigger validation
        when(request.getParameter("lastname")).thenReturn("ValidLastName");
        when(request.getParameter("vat")).thenReturn("123456789");
        when(request.getParameter("fatherName")).thenReturn("Father");
        when(request.getParameter("phoneNum")).thenReturn("6900000000");
        when(request.getParameter("email")).thenReturn("teacher@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("10");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");
        when(request.getContextPath()).thenReturn("");

        when(request.getSession()).thenReturn(session);

        controller.doPost(request, response);

        verify(session).setAttribute(eq("firstnameError"), anyString());
        verify(session).setAttribute(eq("updateDTO"), any());

        verify(response).sendRedirect("/school-app/teachers/update?id=1");
    }

    @Test
    void doPostValidationFailsNullParamsRedirectsBackToForm() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstname")).thenReturn(null); // Invalid - triggers validation
        when(request.getParameter("lastname")).thenReturn(null);
        when(request.getParameter("vat")).thenReturn(null);
        when(request.getParameter("fatherName")).thenReturn(null);
        when(request.getParameter("phoneNum")).thenReturn(null);
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("street")).thenReturn(null);
        when(request.getParameter("streetNum")).thenReturn(null);
        when(request.getParameter("zipcode")).thenReturn(null);
        when(request.getParameter("cityId")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");

        when(request.getSession()).thenReturn(session);

        controller.doPost(request, response);

        verify(session).setAttribute(eq("firstnameError"), anyString());
        verify(session).setAttribute(eq("lastnameError"), anyString());
        verify(session).setAttribute(eq("vatError"), anyString());
        verify(session).setAttribute(eq("fatherNameError"), anyString());
        verify(session).setAttribute(eq("phoneNumError"), anyString());
        verify(session).setAttribute(eq("emailError"), anyString());
        verify(session).setAttribute(eq("streetError"), anyString());
        verify(session).setAttribute(eq("streetNumError"), anyString());
        verify(session).setAttribute(eq("zipcodeError"), anyString());
        verify(session).setAttribute(eq("updateDTO"), any());

        verify(response).sendRedirect("/school-app/teachers/update?id=1");
    }

    @Test
    void doPostSuccessfulUpdateRedirectsToConfirmation() throws TeacherNotFoundException, TeacherDaoException, TeacherAlreadyExistsException, ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstname")).thenReturn("firstname");
        when(request.getParameter("lastname")).thenReturn("lastname");
        when(request.getParameter("vat")).thenReturn("123456789");
        when(request.getParameter("fatherName")).thenReturn("Father");
        when(request.getParameter("phoneNum")).thenReturn("6900000000");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("10");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");
        when(request.getContextPath()).thenReturn("");

        when(request.getSession(anyBoolean())).thenReturn(session);

        TeacherReadOnlyDTO updatedTeacher = new TeacherReadOnlyDTO(1L, "uuid", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");

        when(mockTeacherService.updateTeacher(eq(1L), any())).thenReturn(updatedTeacher);

        controller.doPost(request, response);

        verify(session).setAttribute("teacherInfo", updatedTeacher);
        verify(response).sendRedirect("/school-app/teachers/teacher-updated");
    }

    @Test
    void doPostThrowsTeacherNotFoundExceptionForwardsToErrorPage() throws TeacherNotFoundException, TeacherDaoException, TeacherAlreadyExistsException, ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstname")).thenReturn("firstname");
        when(request.getParameter("lastname")).thenReturn("lastname");
        when(request.getParameter("vat")).thenReturn("123456789");
        when(request.getParameter("fatherName")).thenReturn("Father");
        when(request.getParameter("phoneNum")).thenReturn("6900000000");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("10");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        doThrow(new TeacherNotFoundException("Teacher not found"))
                .when(mockTeacherService).updateTeacher(eq(1L), any());

        controller.doPost(request, response);

        verify(session).setAttribute("message", "Teacher not found");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostThrowsNumberFormatExceptionForwardsToErrorPage() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doPost(request, response);

        verify(session).setAttribute("message", "For input string: \"\"");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }



}