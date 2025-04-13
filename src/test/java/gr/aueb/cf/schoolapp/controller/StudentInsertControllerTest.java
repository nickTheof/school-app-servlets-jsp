package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.*;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentInsertControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ICityService mockCityService;
    @Mock
    private IStudentService studentService;

    @InjectMocks
    private StudentInsertController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        StudentInsertController defaultController = new StudentInsertController();
        assertNotNull(defaultController);
    }

    @Test
    void insertStudentWhenValidDataShouldRedirect() throws ServletException, IOException, StudentAlreadyExistsException, StudentDaoException {
        StudentReadOnlyDTO mockStudentReadOnlyDTO = new StudentReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "testName", "testSurname", "999999999", "testFatherName", "6912345678", "test@example.com", "Main", "11", 1, "12345");
        when(request.getParameter("firstname")).thenReturn("testName");
        when(request.getParameter("lastname")).thenReturn("testSurname");
        when(request.getParameter("vat")).thenReturn("999999999");
        when(request.getParameter("fatherName")).thenReturn("testFatherName");
        when(request.getParameter("phoneNum")).thenReturn("6912345678");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("11");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");
        when(studentService.insertStudent(any(StudentInsertDTO.class))).thenReturn(mockStudentReadOnlyDTO);
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        controller.doPost(request, response);
        verify(response).sendRedirect(eq("/school-app/students/student-inserted"));
    }

    @Test
    void insertStudentWhenValidDataShouldSetCorrectStudentInfoInSession() throws ServletException, IOException, StudentAlreadyExistsException, StudentDaoException {
        StudentReadOnlyDTO mockStudentReadOnlyDTO = new StudentReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "testName", "testSurname", "999999999", "testFatherName", "6912345678", "test@example.com", "Main", "11", 1, "12345");

        when(request.getParameter("firstname")).thenReturn("testName");
        when(request.getParameter("lastname")).thenReturn("testSurname");
        when(request.getParameter("vat")).thenReturn("999999999");
        when(request.getParameter("fatherName")).thenReturn("testFatherName");
        when(request.getParameter("phoneNum")).thenReturn("6912345678");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("11");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");
        when(studentService.insertStudent(any(StudentInsertDTO.class))).thenReturn(mockStudentReadOnlyDTO);
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        controller.doPost(request, response);

        verify(session).setAttribute(eq("studentInfo"), eq(mockStudentReadOnlyDTO));

        assertEquals("testName", mockStudentReadOnlyDTO.getFirstname());
        assertEquals("testSurname",mockStudentReadOnlyDTO.getLastname());
        assertEquals("999999999", mockStudentReadOnlyDTO.getVat());
        assertEquals("testFatherName", mockStudentReadOnlyDTO.getFatherName());
        assertEquals("6912345678", mockStudentReadOnlyDTO.getPhoneNum());
        assertEquals("test@example.com", mockStudentReadOnlyDTO.getEmail());
        assertEquals("Main", mockStudentReadOnlyDTO.getStreet());
        assertEquals("11", mockStudentReadOnlyDTO.getStreetNum());
        assertEquals("12345", mockStudentReadOnlyDTO.getZipcode());
        assertEquals(1, mockStudentReadOnlyDTO.getCityId());
        assertEquals(1, mockStudentReadOnlyDTO.getId());
    }

    @Test
    void insertStudentWhenValidDataWithSpacesShouldBeTrimmed() throws ServletException, IOException, StudentAlreadyExistsException, StudentDaoException {
        when(request.getParameter("firstname")).thenReturn(" testName ");
        when(request.getParameter("lastname")).thenReturn(" testSurname ");
        when(request.getParameter("vat")).thenReturn(" 999999999 ");
        when(request.getParameter("fatherName")).thenReturn(" testFatherName ");
        when(request.getParameter("phoneNum")).thenReturn(" 6912345678 ");
        when(request.getParameter("email")).thenReturn(" test@example.com ");
        when(request.getParameter("street")).thenReturn(" Main ");
        when(request.getParameter("streetNum")).thenReturn(" 11 ");
        when(request.getParameter("zipcode")).thenReturn(" 12345 ");
        when(request.getParameter("cityId")).thenReturn(" 1 ");
        when(request.getSession(false)).thenReturn(session);

        when(studentService.insertStudent(any())).thenReturn(new StudentReadOnlyDTO());

        ArgumentCaptor<StudentInsertDTO> dtoCapture = ArgumentCaptor.forClass(StudentInsertDTO.class);

        controller.doPost(request, response);

        verify(studentService).insertStudent(dtoCapture.capture());

        StudentInsertDTO insertDTO = dtoCapture.getValue();

        assertEquals("testName", insertDTO.getFirstname());
        assertEquals("testSurname", insertDTO.getLastname());
        assertEquals("999999999", insertDTO.getVat());
        assertEquals("testFatherName", insertDTO.getFatherName());
        assertEquals("6912345678", insertDTO.getPhoneNum());
        assertEquals("test@example.com", insertDTO.getEmail());
        assertEquals("Main", insertDTO.getStreet());
        assertEquals("11", insertDTO.getStreetNum());
        assertEquals("12345", insertDTO.getZipcode());
        assertEquals(1, insertDTO.getCityId());
    }

    @Test
    void insertStudentWhenInvalidDataShouldDisplayTeacherAlreadyExists() throws ServletException, IOException, StudentAlreadyExistsException, StudentDaoException {
        when(request.getParameter("firstname")).thenReturn("testName");
        when(request.getParameter("lastname")).thenReturn("testSurname");
        // Vat 987654321 already exists in db
        when(request.getParameter("vat")).thenReturn("987654321");

        when(request.getParameter("fatherName")).thenReturn("testFatherName");
        when(request.getParameter("phoneNum")).thenReturn("6912345678");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("street")).thenReturn("Main");
        when(request.getParameter("streetNum")).thenReturn("11");
        when(request.getParameter("zipcode")).thenReturn("12345");
        when(request.getParameter("cityId")).thenReturn("1");

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doThrow(new StudentAlreadyExistsException("Student with vat 987654321 already exists"))
                .when(studentService).insertStudent(any(StudentInsertDTO.class));

        controller.doPost(request, response);

        verify(request).setAttribute(
                eq("message"),
                eq("Student with vat 987654321 already exists")
        );


        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-insert.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void insertTeacherWhenInvalidDataSetErrorInSessionAndRedirects() throws ServletException, IOException {
        when(request.getParameter("firstname")).thenReturn(null);
        when(request.getParameter("lastname")).thenReturn(null);
        when(request.getParameter("vat")).thenReturn(null);
        when(request.getParameter("fatherName")).thenReturn(null);
        when(request.getParameter("phoneNum")).thenReturn(null);
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("street")).thenReturn(null);
        when(request.getParameter("streetNum")).thenReturn(null);
        when(request.getParameter("zipcode")).thenReturn(null);
        when(request.getParameter("cityId")).thenReturn(null);

        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
        controller.doPost(request, response);

        verify(response).sendRedirect("/school-app/students/insert");
        verify(session).setAttribute(eq("firstnameError"), eq("Firstname should contain only Greek or English letters"));
        verify(session).setAttribute(eq("lastnameError"), eq("Lastname should contain only Greek or English letters"));
        verify(session).setAttribute(eq("vatError"), eq("VAT should contain exactly 9 digits"));
        verify(session).setAttribute(eq("fatherNameError"), eq("Father name should contain only Greek or English letters"));
        verify(session).setAttribute(eq("phoneNumError"), eq("Phone number should contain at least 10 digits"));
        verify(session).setAttribute(eq("emailError"), eq("Invalid email format"));
        verify(session).setAttribute(eq("streetError"), eq("Street name should contain 2 to 32 characters"));
        verify(session).setAttribute(eq("streetNumError"), eq("Street number should contain 1 to 4 digits"));
        verify(session).setAttribute(eq("zipcodeError"), eq("Zip code should contain at least five digits"));
    }


    @Test
    void doGetShouldSetCitiesAndForwardToInsertJSP() throws ServletException, IOException, CityDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        when(mockCityService.getAllCities()).thenReturn(mockedCities);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-insert.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetWithSessionErrorsShouldSetErrorsAndClearThem() throws ServletException, IOException {
        when(session.getAttribute("insertDTO")).thenReturn("dummyDTO");
        when(session.getAttribute("firstnameError")).thenReturn("Some error");

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        controller.doGet(request, response);

        verify(request).setAttribute("insertDTOInfo", "dummyDTO");
        verify(request).setAttribute("firstnameError", "Some error");

        verify(session).removeAttribute("insertDTO");
        verify(session).removeAttribute("firstnameError");

        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-insert.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetWhenCityServiceThrowsExceptionShouldSetErrorMessageAndForward() throws ServletException, IOException, CityDaoException {
        when(mockCityService.getAllCities()).thenThrow(new CityDaoException("DB Error"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);

        verify(request).setAttribute(eq("message"), eq("DB Error"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/students.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}