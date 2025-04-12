package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherDeleteControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ITeacherService mockTeacherService;

    @InjectMocks
    private TeacherDeleteController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        TeacherDeleteController defaultController = new TeacherDeleteController();
        assertNotNull(defaultController);
    }

    @Test
    void doGetIdNullWillForwardError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("error"), eq("Το ID εκπαιδευτή για την διαγραφή είναι απαραίτητο."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetIdEmptyWillForwardError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("error"), eq("Το ID εκπαιδευτή για την διαγραφή είναι απαραίτητο."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetSuccessDeleteWillForward() throws ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(mockTeacherService).deleteTeacher(1L);
        verify(request).setAttribute("id", 1L);
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetIdStringWillForwardNumberFormatError() throws ServletException, IOException, TeacherNotFoundException, TeacherDaoException {
        when(request.getParameter("id")).thenReturn("error");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), eq("Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
        verifyNoInteractions(mockTeacherService);
    }

    @Test
    void doGetWithNotFoundWillSetError() throws TeacherNotFoundException, TeacherDaoException, ServletException, IOException {
        when(request.getParameter("id")).thenReturn("100");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doThrow(new TeacherNotFoundException("Δεν βρέθηκε εκπαιδευτής με το συγκεκριμένο ID.")).when(mockTeacherService).deleteTeacher(100L);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), eq("Δεν βρέθηκε εκπαιδευτής με το συγκεκριμένο ID."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetWithTeacherDaoExceptionWillSetError() throws TeacherNotFoundException, TeacherDaoException, ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doThrow(new TeacherDaoException("DB error")).when(mockTeacherService).deleteTeacher(1L);

        controller.doGet(request, response);

        verify(request).setAttribute("error", "Σφάλμα κατά την ανάκτηση των στοιχείων του εκπαιδευτή.");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/teacher-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}