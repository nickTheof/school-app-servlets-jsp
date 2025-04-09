package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private static IUserDAO userDAO;

    @BeforeAll
    public static void setupClass() throws SQLException {
        userDAO = new UserDAOImpl();
        DBHelper.eraseTableData("users");
    }

    @BeforeEach
    public void setup() throws UserDaoException {
        createDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("users");
    }

    @AfterAll
    public static void tearAll() throws UserDaoException {
        createDummyData();
    }

    private static void createDummyData() throws UserDaoException {
        List<User> users = List.of(
                new User(1L, "thanos@aueb.gr", "12345", RoleType.ADMIN),
                new User(2L, "anna@gmail.com", "12345", RoleType.LIGHT_ADMIN),
                new User(3L, "niki@aueb.gr", "12345", RoleType.ADMIN),
                new User(4L, "test@aueb.gr", "12345", RoleType.ADMIN),
                new User(5L, "test2@aueb.gr", "12345", RoleType.LIGHT_ADMIN)
        );
        for (User user: users) {
            userDAO.insert(user);
        }
    }


    @Test
    void persistUserAndGet() throws UserDaoException {
        User newUser = new User(null, "test@test.com", "12345", RoleType.ADMIN);
        User insertedUser = userDAO.insert(newUser);
        User fetchedUser = userDAO.getById(insertedUser.getId());
        User fetchUserByUsername = userDAO.getByUsername("test@test.com");
        assertEquals(fetchedUser.getId(), insertedUser.getId());
        assertEquals(6L, insertedUser.getId());
        assertEquals(fetchedUser.getId(), fetchUserByUsername.getId());
    }

    @Test
    void isUserValidPositiveScenario() throws UserDaoException {
        User newUser = new User(null, "test@test.com", "12345", RoleType.ADMIN);
        User insertedUser = userDAO.insert(newUser);
        assertTrue(userDAO.isUserValid("test@test.com", "12345"));
    }

    @Test
    void isUserValidNegativeScenario() throws UserDaoException {
        User newUser = new User(null, "test@test.com", "12345", RoleType.ADMIN);
        User insertedUser = userDAO.insert(newUser);
        assertFalse(userDAO.isUserValid("test@test.com", "falsypassword"));
    }

    @Test
    void isEmailExistsPositiveScenario() throws UserDaoException {
        User newUser = new User(null, "test@test.com", "12345", RoleType.ADMIN);
        User insertedUser = userDAO.insert(newUser);
        assertTrue(userDAO.isEmailExists("test@test.com"));
    }


    @Test
    void isEmailExistsNegativeScenario() throws UserDaoException {
        User newUser = new User(null, "test@test.com", "12345", RoleType.ADMIN);
        User insertedUser = userDAO.insert(newUser);
        assertFalse(userDAO.isEmailExists("falsetest@test.com"));
    }
}