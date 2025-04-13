package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.service.IStudentService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentDeleteControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private IStudentService mockStudentService;

    @InjectMocks
    private StudentDeleteController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        StudentDeleteController defaultController = new StudentDeleteController();
        assertNotNull(defaultController);
    }

    @Test
    void doGetIdNullWillForwardError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("error"), eq("Το id μαθητή είναι απαραίτητο"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetIdEmptyWillForwardError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).setAttribute(eq("error"), eq("Το id μαθητή είναι απαραίτητο"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetSuccessDeleteWillForward() throws ServletException, IOException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(mockStudentService).deleteStudent(1L);
        verify(request).setAttribute("id", 1L);
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetIdStringWillForwardNumberFormatError() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("error");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), eq("Το ID πρέπει να είναι έγκυρος ακέραιος αριθμός."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
        verifyNoInteractions(mockStudentService);
    }

    @Test
    void doGetWithNotFoundWillSetError() throws ServletException, IOException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("100");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doThrow(new StudentNotFoundException("Δεν βρέθηκε μαθητής με το συγκεκριμένο ID.")).when(mockStudentService).deleteStudent(100L);

        controller.doGet(request, response);

        verify(request).setAttribute(eq("error"), eq("Δεν βρέθηκε μαθητής με το συγκεκριμένο ID."));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetWithStudentDaoExceptionWillSetError() throws ServletException, IOException, StudentNotFoundException, StudentDaoException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doThrow(new StudentDaoException("DB error")).when(mockStudentService).deleteStudent(1L);

        controller.doGet(request, response);

        verify(request).setAttribute("error", "Σφάλμα στην βάση δεδομένων κατά την διαγραφή του μαθητή.");
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-deleted.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}