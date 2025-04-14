package gr.aueb.cf.schoolapp.filter;

import jakarta.servlet.FilterChain;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GRFilterTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private GRFilter grFilter;

    @Test
    void doFilterSetsEncodingAndContinuesChain() throws ServletException, IOException {
        grFilter.doFilter(request, response, filterChain);
        verify(request).setCharacterEncoding("UTF-8");
        verify(filterChain).doFilter(request, response);
    }
}