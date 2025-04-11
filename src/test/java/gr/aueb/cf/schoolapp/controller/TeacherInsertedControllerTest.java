package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherInsertedControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private static TeacherInsertedController controller;

    @BeforeAll
    public static void setupClass() {
        controller = new TeacherInsertedController();
    }

    @Test
    void doGetInsertedTeacherExistsWillForward() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("teacherInfo")).thenReturn(new TeacherReadOnlyDTO());
        when(request.getRequestDispatcher("/WEB-INF/jsp/teacher-inserted.jsp")).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        controller.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        verify(request.getSession()).removeAttribute("teacherInfo");
    }

    @Test
    void doGetInsertedTeacherNotExistsWillRedirect() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("teacherInfo")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");
        controller.doGet(request, response);
        verify(response).sendRedirect("/school-app/dashboard");
    }
}