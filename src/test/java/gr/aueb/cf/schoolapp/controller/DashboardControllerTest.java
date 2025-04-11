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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private static DashboardController controller;

    @BeforeAll
    public static void setupClass() {
        controller = new DashboardController();
    }

    @Test
    void doGetWillForward() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp")).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}