package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolapp.service.IUserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @InjectMocks
    private LoginController controller;
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


    @Test
    void defaultConstructorShouldInstantiateService() {
        LoginController defaultController = new LoginController();
        assertNotNull(defaultController);
    }

    @Test
    void doGetShouldRenderPage() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/jsp/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostSuccessfulLoginAdminRoleRedirectsToDashboard() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass123");
        when(request.getSession(false)).thenReturn(null);
        when(request.getSession(true)).thenReturn(session);
        when(mockUserService.getUserByUsername("admin")).thenReturn(new UserReadOnlyDTO(1L, "admin", "pass123", "ADMIN"));  // Assuming User has getRole()

        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any()))
                    .thenReturn(true);

            controller.doPost(request, response);

            verify(session).setAttribute("authenticated", true);
            verify(session).setAttribute("username", "admin");
            verify(session).setAttribute("role", "ADMIN");
            verify(session).setMaxInactiveInterval(30 * 60);
            verify(response).sendRedirect(anyString());
        }
    }

    @Test
    void doPostInvalidCredentialsShouldForwardToLoginWithError() throws Exception {
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
    void doPostServiceThrowsExceptionShouldForwardToLoginWithError() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(false)).thenReturn(null);
        when(request.getSession(true)).thenReturn(session);
        when(mockUserService.getUserByUsername("admin")).thenThrow(new UserNotFoundException("User with username admin was not found"));


        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any())).thenReturn(true);

            controller.doPost(request, response);

            verify(request).setAttribute(eq("error"), eq("User with username admin was not found"));
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void doPostUserNullServiceThrowsExceptionShouldForwardToLoginWithError() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession(false)).thenReturn(null);
        when(request.getSession(true)).thenReturn(session);
        when(mockUserService.getUserByUsername("admin")).thenReturn(null);


        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any())).thenReturn(true);

            controller.doPost(request, response);

            verify(request).setAttribute(eq("error"), eq("User with username admin was not found"));
            verify(request).getRequestDispatcher("WEB-INF/jsp/login.jsp");
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void doPostShouldInvalidateOldSessionIfExists() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("pass123");

        HttpSession oldSession = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(oldSession);
        HttpSession newSession = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(newSession);

        when(mockUserService.getUserByUsername("admin"))
                .thenReturn(new UserReadOnlyDTO(1L, "admin", "pass123", "ADMIN"));

        try (MockedStatic<AuthenticationProvider> mockedAuth = Mockito.mockStatic(AuthenticationProvider.class)) {
            mockedAuth.when(() -> AuthenticationProvider.authenticate(any()))
                    .thenReturn(true);

            controller.doPost(request, response);

            verify(oldSession).invalidate();
            verify(newSession).setAttribute("authenticated", true);
            verify(response).sendRedirect(anyString());
        }
    }

}