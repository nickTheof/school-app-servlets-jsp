package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.model.Teacher;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDAOImplTest {
    private static ITeacherDAO teacherDao;

    @BeforeAll
    public static void setupClass() throws SQLException {
        teacherDao = new TeacherDAOImpl();
        DBHelper.eraseTableData("teachers");
    }

    @BeforeEach
    public void setup() throws TeacherDaoException {
        DBCreateDummyData.createTeachersDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("teachers");
    }

    @AfterAll
    public static void tearAll() throws TeacherDaoException {
        DBCreateDummyData.createTeachersDummyData();
    }

    @Test
    void persistTeacherAndGet() throws TeacherDaoException {
        Teacher teacher = new Teacher(null, "Michael", "Jordan", "1111111111", "Chris", "2102102100", "test@gmail.com",
                "Stadiou", "47", 1, "11111", null, null, null);
        Teacher insertedTeacher = teacherDao.insert(teacher);
        Teacher fetchedTeacher = teacherDao.getById(insertedTeacher.getId());
        assertEquals(13, fetchedTeacher.getId());
    }

    @Test
    void updateTeacher() throws TeacherDaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        Teacher teacher = new Teacher(2L, "Άννα", "Γιαννούτσου", "144445678", "Κώστας", "110345678",
                "anna@gmail.com", "Γεωργούτσου", "12", 5, "67856",
                null,
                null,
                LocalDateTime.parse("18/3/2025 22:54", formatter));

        teacherDao.update(teacher);

        Teacher teacherUpdated = teacherDao.getById(2L);
        assertEquals("Κώστας", teacherUpdated.getFatherName());
        assertEquals("144445678", teacherUpdated.getVat());
        assertEquals("110345678", teacherUpdated.getPhoneNum());
    }

    @Test
    void getByVatPositiveScenario() throws TeacherDaoException {
        Teacher teacher = teacherDao.getByVat("567839201");
        assertEquals(Long.valueOf(1), teacher.getId());
    }

    @Test
    void getByVatNegativeScenario() throws TeacherDaoException {
        Teacher teacher = teacherDao.getByVat("999999999");
        assertNull(teacher);
    }

    @Test
    void getByUuidPositiveScenario() throws TeacherDaoException {
        Teacher teacherByID = teacherDao.getById(1L);
        Teacher teacher = teacherDao.getByUuid(teacherByID.getUuid());
        assertEquals(teacherByID.getId(), teacher.getId());
    }

    @Test
    void getByUuidNegativeScenario() throws TeacherDaoException {
        Teacher teacher = teacherDao.getByUuid("eee");
        assertNull(teacher);
    }

    @Test
    void getFilteredTeachers() throws TeacherDaoException {
        List<Teacher> teachers =teacherDao.getFilteredTeachers("Ν", "Γιαν");
        assertEquals(1, teachers.size());
    }

    @Test
    void getAllTeachers() throws TeacherDaoException {
        List<Teacher> teachers = teacherDao.getAll();
        assertEquals(12, teachers.size());
    }

    @Test
    void deleteTeacher() throws TeacherDaoException {
        teacherDao.delete(1L);
        Teacher deletedTeacher = teacherDao.getById(1L);
        assertNull(deletedTeacher);
    }
}