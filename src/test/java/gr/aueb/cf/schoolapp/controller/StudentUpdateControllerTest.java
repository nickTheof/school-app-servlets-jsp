package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.*;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.IStudentService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentUpdateControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private IStudentService mockStudentService;
    @Mock
    private ICityService mockCityService;
    @InjectMocks
    private StudentUpdateController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        StudentUpdateController defaultController = new StudentUpdateController();
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
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetStudentServiceFailsSetErrorWillForward() throws CityDaoException, ServletException, IOException, StudentNotFoundException, StudentDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockStudentService.getStudentById(1L)).thenThrow(new StudentDaoException("DB error"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(session).setAttribute(eq("message"), eq("DB error"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetStudentServiceFailsTeacherNotFoundSetErrorWillForward() throws CityDaoException, ServletException, IOException, StudentNotFoundException, StudentDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockStudentService.getStudentById(1L)).thenThrow(new StudentNotFoundException("Student not found"));
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(session).setAttribute(eq("message"), eq("Student not found"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
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
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("message");
    }

    @Test
    void doGetSuccessRenderingWithNullUpdateDTOWillForward() throws CityDaoException, ServletException, IOException, StudentNotFoundException, StudentDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        StudentReadOnlyDTO updateDTOInfo = new StudentReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockStudentService.getStudentById(1L)).thenReturn(updateDTOInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("updateDTO")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(request).setAttribute(eq("updateDTOInfo"), eq(updateDTOInfo));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetSuccessRenderingWithNotNullUpdateDTOWillForward() throws CityDaoException, ServletException, IOException, StudentNotFoundException, StudentDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        StudentReadOnlyDTO updateDTOInfo = new StudentReadOnlyDTO(1L, "uuid", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");

        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getParameter("id")).thenReturn("1");
        when(mockStudentService.getStudentById(1L)).thenReturn(updateDTOInfo);
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

        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
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

        verify(response).sendRedirect("/school-app/students/update?id=1");
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

        verify(response).sendRedirect("/school-app/students/update?id=1");
    }

    @Test
    void doPostSuccessfulUpdateRedirectsToConfirmation() throws ServletException, IOException, StudentAlreadyExistsException, StudentNotFoundException, StudentDaoException {
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

        StudentReadOnlyDTO updatedStudent = new StudentReadOnlyDTO(1L, "uuid", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223");

        when(mockStudentService.updateStudent(eq(1L), any())).thenReturn(updatedStudent);

        controller.doPost(request, response);

        verify(session).setAttribute("studentInfo", updatedStudent);
        verify(response).sendRedirect("/school-app/students/student-updated");
    }

    @Test
    void doPostThrowsTeacherNotFoundExceptionForwardsToErrorPage() throws ServletException, IOException, StudentAlreadyExistsException, StudentNotFoundException, StudentDaoException {
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

        doThrow(new StudentNotFoundException("Student not found"))
                .when(mockStudentService).updateStudent(eq(1L), any());

        controller.doPost(request, response);

        verify(session).setAttribute("message", "Student not found");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostThrowsNumberFormatExceptionForwardsToErrorPage() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doPost(request, response);

        verify(session).setAttribute("message", "For input string: \"\"");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-update.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}