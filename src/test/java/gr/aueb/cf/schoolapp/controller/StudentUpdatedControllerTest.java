package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentUpdatedControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private static StudentUpdatedController controller;

    @BeforeAll
    public static void setupClass() {
        controller = new StudentUpdatedController();
    }

    @Test
    void doGetUpdatedStudentExistsWillForward() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("studentInfo")).thenReturn(new StudentReadOnlyDTO());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        controller.doGet(request, response);
        verify(request).getRequestDispatcher("/WEB-INF/jsp/student-updated.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(request.getSession()).removeAttribute("studentInfo");
    }

    @Test
    void doGetUpdatedStudentNotExistsWillRedirect() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("studentInfo")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");
        controller.doGet(request, response);
        verify(response).sendRedirect("/school-app/dashboard");
    }

}