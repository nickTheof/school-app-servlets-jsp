package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentDetailsControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ICityService mockCityService;
    @Mock
    private IStudentService mockStudentService;

    @InjectMocks
    private StudentDetailsController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        StudentDetailsController defaultController = new StudentDetailsController();
        assertNotNull(defaultController);
    }

    @Test
    void doGetIdNullShouldSetMessageAndForward() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Το ID μαθητή είναι απαραίτητο"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-details.jsp");
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    void doGetIdEmptyShouldSetMessageAndForward() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Το ID μαθητή είναι απαραίτητο"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-details.jsp");
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    void doGetInvalidIdShouldSetMessageAndForward() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("error");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-details.jsp");
        verify(requestDispatcher).forward(request, response);

    }



    @Test
    void doGetStudentNotFoundShouldSetErrorMessageAndForward() throws ServletException, IOException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(mockStudentService.getStudentById(1L))
                .thenThrow(new StudentNotFoundException("Δεν βρέθηκε μαθητής με το συγκεκριμένο ID."));
        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Δεν βρέθηκε μαθητής με το συγκεκριμένο ID."));
        verify(request, times(0)).setAttribute(eq("student"), any());
        verify(request, times(0)).setAttribute(eq("cities"), any());
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-details.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetStudentDaoExceptionShouldSetErrorMessageAndForward() throws ServletException, IOException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(mockStudentService.getStudentById(1L))
                .thenThrow(new StudentDaoException("Σφάλμα κατά την ανάκτηση των στοιχείων του μαθητή."));
        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Σφάλμα κατά την ανάκτηση των στοιχείων του μαθητή."));
        verify(request, times(0)).setAttribute(eq("student"), any());
        verify(request, times(0)).setAttribute(eq("cities"), any());
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-details.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetCityDaoExceptionShouldSetErrorMessageAndForward() throws ServletException, IOException, CityDaoException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(mockCityService.getAllCities())
                .thenThrow(new CityDaoException("Σφάλμα κατά την ανάκτηση των στοιχείων των πόλεων."));
        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Σφάλμα κατά την ανάκτηση των στοιχείων των πόλεων."));
        verify(request, times(0)).setAttribute(eq("student"), any());
        verify(request, times(0)).setAttribute(eq("cities"), any());
        verify(request).getRequestDispatcher(eq("/WEB-INF/jsp/student-details.jsp"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetSuccessRetrieveShouldSetAttributesAndForward() throws ServletException, IOException, CityDaoException, StudentNotFoundException, StudentDaoException {
        List<City> mockedCities = List.of(new City(1, "Αθήνα"));
        StudentReadOnlyDTO mockStudentReadOnlyDTO = new StudentReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "testName", "testSurname", "999999999", "testFatherName", "6912345678", "test@example.com", "Main", "11", 1, "12345");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(mockStudentService.getStudentById(1L)).thenReturn(mockStudentReadOnlyDTO);
        when(mockCityService.getAllCities())
                .thenReturn(mockedCities);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("student"), eq(mockStudentReadOnlyDTO));
        verify(request).setAttribute(eq("cities"), eq(mockedCities));
        verify(request, times(0)).setAttribute(eq("message"), any());
        verify(request).getRequestDispatcher(eq("/WEB-INF/jsp/student-details.jsp"));
        verify(requestDispatcher).forward(request, response);
    }

}