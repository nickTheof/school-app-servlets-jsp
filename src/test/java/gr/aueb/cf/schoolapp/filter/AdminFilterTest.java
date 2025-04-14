package gr.aueb.cf.schoolapp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminFilterTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpSession session;

    @InjectMocks
    private AdminFilter adminFilter;

    @Test
    void doFilterNoSessionRedirectsToLogin() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("");
        adminFilter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/login");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void doFilterNoAdminInSessionRedirectsToLogin() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(false);
        when(request.getContextPath()).thenReturn("");
        adminFilter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/login");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void doFilterAdminInSessionAllowAccess() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn("ADMIN");
        adminFilter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
}