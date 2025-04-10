package gr.aueb.cf.schoolapp.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private static LogoutController controller;

    @BeforeAll
    public static void setupClass() {
        controller = new LogoutController();
    }

    @Test
    void doGetShouldInvalidSessionAndRedirect() throws IOException, ServletException {
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("/");
        controller.doGet(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    void doGetSessionNullShouldRedirect() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/");
        controller.doGet(request, response);
        verify(response).sendRedirect("/");
    }


}