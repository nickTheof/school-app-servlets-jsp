package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.service.IStudentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentsViewControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private IStudentService studentService;

    private StudentsViewController controller;

    @BeforeEach
    public void setup() {
        controller = new StudentsViewController(studentService);
    }

    @Test
    void defaultConstructorShouldInstantiateService() {
        StudentsViewController defaultController = new StudentsViewController();
        assertNotNull(defaultController);
    }

    @Test
    void doGet_EmptyStudentsWillForward() throws StudentDaoException, ServletException, IOException {
        when(request.getParameter(anyString())).thenReturn(null);
        when(studentService.getFilteredStudents(any(FiltersDTO.class))).thenReturn(List.of());
        when(request.getRequestDispatcher("/WEB-INF/jsp/students.jsp")).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Δεν υπάρχουν μαθητές με τα εν λόγω κριτήρια."));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_WithResults_WillForwardWithStudents() throws StudentDaoException, ServletException, IOException {
        List<StudentReadOnlyDTO> mockStudents = List.of(
                new StudentReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223")
        );

        when(request.getParameter("firstname")).thenReturn("Γ");
        when(request.getParameter("lastname")).thenReturn("Π");
        when(studentService.getFilteredStudents(any(FiltersDTO.class))).thenReturn(mockStudents);
        when(request.getRequestDispatcher("/WEB-INF/jsp/students.jsp")).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("students"), eq(mockStudents));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ServiceThrowsException_WillForwardWithErrorMessage() throws ServletException, IOException, StudentDaoException {
        when(request.getParameter("firstname")).thenReturn("Γ");
        when(request.getParameter("lastname")).thenReturn("Π");
        when(studentService.getFilteredStudents(any(FiltersDTO.class)))
                .thenThrow(new StudentDaoException("DB error"));
        when(request.getRequestDispatcher("/WEB-INF/jsp/students.jsp")).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute("message", "DB error");
        verify(requestDispatcher).forward(request, response);
    }

}