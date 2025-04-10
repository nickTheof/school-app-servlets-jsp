package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.IUserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    private static LoginController controller;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private IUserService mockUserService;

    @BeforeAll
    public static void setupClass() {
        controller = new LoginController();
    }

    @Test
    void doGetShouldRenderPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/jsp/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_SuccessfulLogin_AdminRole_RedirectsToDashboard() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass123");
        when(request.getSession(false)).thenReturn(null);
        when(request.getSession(true)).thenReturn(session);
        when(mockUserService.getUserByUsername("admin")).thenReturn(new UserReadOnlyDTO(1L, "admin", "pass123", "ADMIN"));  // Assuming User has getRole()
        when(session.getAttribute("role")).thenReturn("ADMIN");

        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any()))
                    .thenReturn(true);

            controller = new LoginController(mockUserService);
            controller.doPost(request, response);

            verify(session).setAttribute("authenticated", true);
            verify(session).setAttribute("username", "admin");
            verify(session).setAttribute("role", "ADMIN");
            verify(session).setMaxInactiveInterval(30 * 60);
            verify(response).sendRedirect(anyString());
        }
    }

    @Test
    void doPost_InvalidCredentials_ShouldForwardToLoginWithError() throws Exception {
        when(request.getParameter("username")).thenReturn("wrongUser");
        when(request.getParameter("password")).thenReturn("wrongPass");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any())).thenReturn(false);

            controller.doPost(request, response);

            verify(request).setAttribute(eq("error"), contains("Λανθασμένο"));
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void doPost_ServiceThrowsException_ShouldForwardToLoginWithError() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(false)).thenReturn(null);
        when(request.getSession(true)).thenReturn(session);


        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any())).thenReturn(true);

            controller.doPost(request, response);

            verify(request).setAttribute(eq("error"), eq("User with username admin was not found"));
            verify(requestDispatcher).forward(request, response);
        }
    }

}