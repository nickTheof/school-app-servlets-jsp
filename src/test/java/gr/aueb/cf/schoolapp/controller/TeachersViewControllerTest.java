package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.service.ITeacherService;
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
class TeachersViewControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ITeacherService teacherService;

    private TeachersViewController controller;

    @BeforeEach
    public void setup() {
        controller = new TeachersViewController(teacherService);
    }

    @Test
    void doGet_EmptyTeachersWillForward() throws TeacherDaoException, ServletException, IOException {
        when(request.getParameter(anyString())).thenReturn(null);
        when(teacherService.getFilteredTeachers(any(FiltersDTO.class))).thenReturn(List.of());
        when(request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp")).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("message"), eq("Δεν βρέθηκαν καθηγητές με τα εν λόγω κριτήρια"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_WithResults_WillForwardWithTeachers() throws TeacherDaoException, ServletException, IOException {
        List<TeacherReadOnlyDTO> mockTeachers = List.of(
                new TeacherReadOnlyDTO(1L,"c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223")
        );

        when(request.getParameter("firstname")).thenReturn("Γ");
        when(request.getParameter("lastname")).thenReturn("Π");
        when(teacherService.getFilteredTeachers(any(FiltersDTO.class))).thenReturn(mockTeachers);
        when(request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp")).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("teachers"), eq(mockTeachers));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_ServiceThrowsException_WillForwardWithErrorMessage() throws ServletException, IOException, TeacherDaoException {
        when(request.getParameter("firstname")).thenReturn("Γ");
        when(request.getParameter("lastname")).thenReturn("Π");
        when(teacherService.getFilteredTeachers(any(FiltersDTO.class)))
                .thenThrow(new TeacherDaoException("DB error"));
        when(request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp")).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute("message", "DB error");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void defaultConstructorShouldInstantiateService() {
        TeachersViewController defaultController = new TeachersViewController();
        assertNotNull(defaultController);
    }



}