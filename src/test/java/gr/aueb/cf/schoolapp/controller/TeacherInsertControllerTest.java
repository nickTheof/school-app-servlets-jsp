package gr.aueb.cf.schoolapp.controller;


import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;


import java.io.IOException;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;


class TeacherInsertControllerTest {

    private static TeacherInsertController controller;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;

    @BeforeAll
    public static void setupClass() throws SQLException {
        controller = new TeacherInsertController();
        DBHelper.eraseTableData("teachers");
    }

    @BeforeEach
    public void setup() throws TeacherDaoException {
        DBCreateDummyData.createTeachersDummyData();
        // Mocks
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);
        when(req.getSession()).thenReturn(session);
        when(req.getSession(anyBoolean())).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(req.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("teachers");
    }

    @AfterAll
    public static void tearAll() throws TeacherDaoException {
        DBCreateDummyData.createTeachersDummyData();
    }

    @Test
    void insertTeacher_whenValidData_shouldRedirect() throws ServletException, IOException {
        when(req.getParameter("firstname")).thenReturn("testName");
        when(req.getParameter("lastname")).thenReturn("testSurname");
        when(req.getParameter("vat")).thenReturn("999999999");
        when(req.getParameter("fatherName")).thenReturn("testFatherName");
        when(req.getParameter("phoneNum")).thenReturn("6912345678");
        when(req.getParameter("email")).thenReturn("test@example.com");
        when(req.getParameter("street")).thenReturn("Main");
        when(req.getParameter("streetNum")).thenReturn("11");
        when(req.getParameter("zipcode")).thenReturn("12345");
        when(req.getParameter("cityId")).thenReturn("1");

        controller.doPost(req, resp);
        verify(resp).sendRedirect(eq("/school-app/teachers/teacher-inserted"));
    }

    @Test
    void insertTeacher_whenValidData_shouldSetCorrectTeacherInfoInSession() throws ServletException, IOException {
        when(req.getParameter("firstname")).thenReturn("testName");
        when(req.getParameter("lastname")).thenReturn("testSurname");
        when(req.getParameter("vat")).thenReturn("999999999");
        when(req.getParameter("fatherName")).thenReturn("testFatherName");
        when(req.getParameter("phoneNum")).thenReturn("6912345678");
        when(req.getParameter("email")).thenReturn("test@example.com");
        when(req.getParameter("street")).thenReturn("Main");
        when(req.getParameter("streetNum")).thenReturn("11");
        when(req.getParameter("zipcode")).thenReturn("12345");
        when(req.getParameter("cityId")).thenReturn("1");

        controller.doPost(req, resp);

        ArgumentCaptor<TeacherReadOnlyDTO> dtoCaptor = ArgumentCaptor.forClass(TeacherReadOnlyDTO.class);

        verify(session).setAttribute(eq("teacherInfo"), dtoCaptor.capture());

        TeacherReadOnlyDTO capturedDTO = dtoCaptor.getValue();
        assertEquals("testName", capturedDTO.getFirstname());
        assertEquals("testSurname", capturedDTO.getLastname());
        assertEquals("999999999", capturedDTO.getVat());
        assertEquals("testFatherName", capturedDTO.getFatherName());
        assertEquals("6912345678", capturedDTO.getPhoneNum());
        assertEquals("test@example.com", capturedDTO.getEmail());
        assertEquals("Main", capturedDTO.getStreet());
        assertEquals("11", capturedDTO.getStreetNum());
        assertEquals("12345", capturedDTO.getZipcode());
        assertEquals(1, capturedDTO.getCityId());
        assertEquals(13, capturedDTO.getId());
    }

    @Test
    void insertTeacher_whenValidDataWithSpaces_shouldBeTrimmedAndSetCorrectTeacherInfoInSession() throws ServletException, IOException {
        when(req.getParameter("firstname")).thenReturn(" testName ");
        when(req.getParameter("lastname")).thenReturn(" testSurname ");
        when(req.getParameter("vat")).thenReturn(" 999999999 ");
        when(req.getParameter("fatherName")).thenReturn(" testFatherName ");
        when(req.getParameter("phoneNum")).thenReturn(" 6912345678 ");
        when(req.getParameter("email")).thenReturn(" test@example.com ");
        when(req.getParameter("street")).thenReturn(" Main ");
        when(req.getParameter("streetNum")).thenReturn(" 11 ");
        when(req.getParameter("zipcode")).thenReturn(" 12345 ");
        when(req.getParameter("cityId")).thenReturn(" 1 ");

        controller.doPost(req, resp);

        ArgumentCaptor<TeacherReadOnlyDTO> dtoCaptor = ArgumentCaptor.forClass(TeacherReadOnlyDTO.class);

        verify(session).setAttribute(eq("teacherInfo"), dtoCaptor.capture());

        TeacherReadOnlyDTO capturedDTO = dtoCaptor.getValue();
        assertEquals("testName", capturedDTO.getFirstname());
        assertEquals("testSurname", capturedDTO.getLastname());
        assertEquals("999999999", capturedDTO.getVat());
        assertEquals("testFatherName", capturedDTO.getFatherName());
        assertEquals("6912345678", capturedDTO.getPhoneNum());
        assertEquals("test@example.com", capturedDTO.getEmail());
        assertEquals("Main", capturedDTO.getStreet());
        assertEquals("11", capturedDTO.getStreetNum());
        assertEquals("12345", capturedDTO.getZipcode());
        assertEquals(1, capturedDTO.getCityId());
        assertEquals(13, capturedDTO.getId());
    }

    @Test
    void insertTeacher_whenInvalidData_shouldDisplayTeacherAlreadyExists() throws ServletException, IOException {
        when(req.getParameter("firstname")).thenReturn("testName");
        when(req.getParameter("lastname")).thenReturn("testSurname");
        // Vat 987654321 already exists in db
        when(req.getParameter("vat")).thenReturn("987654321");

        when(req.getParameter("fatherName")).thenReturn("testFatherName");
        when(req.getParameter("phoneNum")).thenReturn("6912345678");
        when(req.getParameter("email")).thenReturn("test@example.com");
        when(req.getParameter("street")).thenReturn("Main");
        when(req.getParameter("streetNum")).thenReturn("11");
        when(req.getParameter("zipcode")).thenReturn("12345");
        when(req.getParameter("cityId")).thenReturn("1");

        controller.doPost(req, resp);

        verify(req).setAttribute(
                eq("message"),
                eq("Teacher with vat 987654321 already exists")
        );

        verify(req).getRequestDispatcher("/WEB-INF/jsp/teacher-insert.jsp");
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    void insertTeacher_whenInvalidData_setErrorInSessionAndRedirects() throws ServletException, IOException {
        when(req.getParameter("firstname")).thenReturn(null);
        when(req.getParameter("lastname")).thenReturn(null);
        when(req.getParameter("vat")).thenReturn(null);
        when(req.getParameter("fatherName")).thenReturn(null);
        when(req.getParameter("phoneNum")).thenReturn(null);
        when(req.getParameter("email")).thenReturn(null);
        when(req.getParameter("street")).thenReturn(null);
        when(req.getParameter("streetNum")).thenReturn(null);
        when(req.getParameter("zipcode")).thenReturn(null);
        when(req.getParameter("cityId")).thenReturn(null);

        controller.doPost(req, resp);

        verify(resp).sendRedirect("/school-app/teachers/insert");
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
    void doGet_shouldSetCitiesAndForwardToInsertJSP() throws ServletException, IOException {
        controller.doGet(req, resp);
        verify(req).setAttribute(eq("cities"), any());
        verify(req).getRequestDispatcher("/WEB-INF/jsp/teacher-insert.jsp");
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    void doGet_withSessionErrors_shouldSetErrorsAndClearThem() throws ServletException, IOException {
        when(session.getAttribute("insertDTO")).thenReturn("dummyDTO");
        when(session.getAttribute("firstnameError")).thenReturn("Some error");

        controller.doGet(req, resp);

        verify(req).setAttribute("insertDTOInfo", "dummyDTO");
        verify(req).setAttribute("firstnameError", "Some error");

        verify(session).removeAttribute("insertDTO");
        verify(session).removeAttribute("firstnameError");

        verify(req).getRequestDispatcher("/WEB-INF/jsp/teacher-insert.jsp");
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    void doGet_whenCityServiceThrowsException_shouldSetErrorMessageAndForward() throws ServletException, IOException, CityDaoException {
        ICityService mockCityService = mock(ICityService.class);
        ITeacherService realTeacherService = new TeacherServiceImpl(new TeacherDAOImpl());

        TeacherInsertController controllerWithMockCity = new TeacherInsertController(realTeacherService, mockCityService);

        when(mockCityService.getAllCities()).thenThrow(new CityDaoException("DB Error"));

        controllerWithMockCity.doGet(req, resp);

        verify(req).setAttribute(eq("message"), eq("DB Error"));
        verify(req).getRequestDispatcher("/school-app/teachers/insert");
        verify(requestDispatcher).forward(req, resp);
    }
}