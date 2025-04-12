package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.service.IUserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private IUserService userService;

    @InjectMocks
    private RegisterUserController controller;

    @Test
    void defaultConstructorShouldInstantiateService() {
        RegisterUserController defaultController = new RegisterUserController();
        assertNotNull(defaultController);
    }

    @Test
    void doGetWillForward() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/jsp/register-user.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostSuccessInsertWillSetSessionAttributesAndRedirect() throws UserDaoException, UserAlreadyExistsException, ServletException, IOException {
        UserReadOnlyDTO mockedUserReadOnlyDTO = new UserReadOnlyDTO(1L, "test@mail.com", "hashedPassword", "ADMIN");
        when(request.getParameter("username")).thenReturn("test@mail.com");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("confirmPassword")).thenReturn("12345");
        when(request.getParameter("role")).thenReturn("ADMIN");
        ArgumentCaptor<InsertUserDTO> userDTOArgumentCaptor = ArgumentCaptor.forClass(InsertUserDTO.class);
        when(userService.insertUser(userDTOArgumentCaptor.capture())).thenReturn(mockedUserReadOnlyDTO);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        controller.doPost(request, response);

        verify(session).setAttribute(eq("userInfo"), eq(mockedUserReadOnlyDTO));
        verify(response).sendRedirect("/school-app/user-registered");

    }

    @Test
    void doPostValidDataUserAlreadyExistsWillSetErrorAndForward() throws UserDaoException, UserAlreadyExistsException, ServletException, IOException {
        when(request.getParameter("username")).thenReturn("test@mail.com");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("confirmPassword")).thenReturn("12345");
        when(request.getParameter("role")).thenReturn("ADMIN");
        ArgumentCaptor<InsertUserDTO> userDTOArgumentCaptor = ArgumentCaptor.forClass(InsertUserDTO.class);
        when(userService.insertUser(userDTOArgumentCaptor.capture())).thenThrow(new UserAlreadyExistsException("User with username test@mail.com already exists"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), eq("User with username test@mail.com already exists"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/user-register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostValidDataServiceFailedWillSetErrorAndForward() throws UserDaoException, UserAlreadyExistsException, ServletException, IOException {
        when(request.getParameter("username")).thenReturn("test@mail.com");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("confirmPassword")).thenReturn("12345");
        when(request.getParameter("role")).thenReturn("ADMIN");
        ArgumentCaptor<InsertUserDTO> userDTOArgumentCaptor = ArgumentCaptor.forClass(InsertUserDTO.class);
        when(userService.insertUser(userDTOArgumentCaptor.capture())).thenThrow(new UserDaoException("SQL Error"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), eq("SQL Error"));
        verify(request).getRequestDispatcher("/WEB-INF/jsp/user-register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostInvalidateDataServiceFailedWillSetErrorsAndForward() throws UserDaoException, UserAlreadyExistsException, ServletException, IOException {
        when(request.getParameter("username")).thenReturn("ff ff");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getParameter("confirmPassword")).thenReturn("123456");
        when(request.getParameter("role")).thenReturn("ADMIN");

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute(eq("usernameMessage"), eq("Το email δεν πρέπει να περιλαμβάνει κενά"));
        verify(request).setAttribute(eq("passwordMessage"), eq("Ο κωδικός πρέπει να είναι μεταξύ 5 και 32 χαρακτήρων."));
        verify(request).setAttribute(eq("confirmPasswordMessage"), eq("Τα πεδία κωδικός και επιβεβαίωση κωδικού διαφέρουν."));
        verify(request).setAttribute(eq("userRegisterDTO"), any(InsertUserDTO.class));


        verify(request).getRequestDispatcher("/WEB-INF/jsp/register-user.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}