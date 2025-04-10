package gr.aueb.cf.schoolapp.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotFoundPageControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private static NotFoundPageController controller;

    @BeforeAll
    public static void setupClass() {
        controller = new NotFoundPageController();
    }

    @Test
    void doGetWithStatus404WillForward() throws ServletException, IOException {
        when(request.getAttribute("jakarta.servlet.error.status_code")).thenReturn(404);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGetWithNotStatus404WillRedirect() throws ServletException, IOException {
        when(request.getAttribute("jakarta.servlet.error.status_code")).thenReturn(200);
        when(request.getContextPath()).thenReturn("");

        controller.doGet(request, response);
        verify(response).sendRedirect("/school-app/dashboard");
    }


}