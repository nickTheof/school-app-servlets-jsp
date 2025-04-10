package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.exceptions.UserNotFoundException;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private static IUserDAO userDAO;
    private static IUserService userService;

    @BeforeAll
    public static void setupClass() throws SQLException {
        userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
        DBHelper.eraseTableData("users");
    }

    @BeforeEach
    public void setup() throws UserDaoException {
        DBCreateDummyData.createUsersDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("users");
    }

    @AfterAll
    public static void tearAll() throws UserDaoException {
        DBCreateDummyData.createUsersDummyData();
    }

    @Test
    void insertUser() throws UserDaoException, UserAlreadyExistsException, UserNotFoundException {
        InsertUserDTO userDTO = new InsertUserDTO("test@test.gr", "12345", "12345", "ADMIN");
        UserReadOnlyDTO readOnlyDTO = userService.insertUser(userDTO);
        UserReadOnlyDTO fetchUser = userService.getUserByUsername("test@test.gr");
        assertEquals(6L, fetchUser.getId());
    }

    @Test
    void insertUserNegative() {
        InsertUserDTO userDTO = new InsertUserDTO("test@aueb.gr", "12345", "12345", "ADMIN");
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.insertUser(userDTO);
        });
    }

    @Test
    void getUserByUsername() throws UserNotFoundException, UserDaoException {
        UserReadOnlyDTO existentUser = userService.getUserByUsername("test@aueb.gr");
        assertEquals("test@aueb.gr", existentUser.getUsername());
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername("falseusername@mail.com");
        });
    }

    @Test
    void isUserValid() throws UserDaoException {
        LoginUserDTO validUser = new LoginUserDTO("test@aueb.gr", "12345");
        LoginUserDTO invalidUsername = new LoginUserDTO("invalid@mail.com", "12345");
        LoginUserDTO invalidUserPassword = new LoginUserDTO("test@aueb.gr", "12345678");
        assertTrue(userService.isUserValid(validUser));
        assertFalse(userService.isUserValid(invalidUsername));
        assertFalse(userService.isUserValid(invalidUserPassword));

    }

    @Test
    void isEmailExists() throws UserDaoException {
        String validUsername = "test@aueb.gr";
        String invalidUsername = "invalid@mail.com";
        assertTrue(userService.isEmailExists(validUsername));
        assertFalse(userService.isEmailExists(invalidUsername));
    }
}