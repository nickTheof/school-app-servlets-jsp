package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.model.Student;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOImplTest {
    private static IStudentDAO studentDAO;

    @BeforeAll
    public static void setupClass() throws SQLException {
        studentDAO = new StudentDAOImpl();
        DBHelper.eraseTableData("students");
    }

    @BeforeEach
    public void setup() throws StudentDaoException {
        DBCreateDummyData.createStudentsDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("students");
    }

    @AfterAll
    public static void tearAll() throws StudentDaoException{
        DBCreateDummyData.createStudentsDummyData();
    }

    @Test
    void persistStudentAndGet() throws StudentDaoException {
        Student student = new Student(null, "Michael", "Jordan", "1111111111", "Chris", "2102102100", "test@gmail.com",
                "Stadiou", "47", 1, "11111", null, null, null);
        Student insertedStudent = studentDAO.insert(student);
        Student fetchedStudent = studentDAO.getById(insertedStudent.getId());
        assertEquals(13, fetchedStudent.getId());
    }

    @Test
    void updateStudent() throws StudentDaoException {
        Student student = new Student(2L, "Μαρ", "Κολοκοτ", "908172635", "Θόδωρος", "6976543210", "maria@gmail.com", "Αθήνας", "34", 6, "88990", "d5e8f3a1-93c2-41cb-8f54-3a68d6a1b7d8", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 3, 11, 12, 52));
        studentDAO.update(student);
        Student studentUpdated = studentDAO.getById(2L);

        assertEquals("Μαρ", studentUpdated.getFirstname());
        assertEquals("Κολοκοτ", studentUpdated.getLastname());
        assertEquals("Θόδωρος", studentUpdated.getFatherName());
    }

    @Test
    void getByVatPositiveScenario() throws StudentDaoException {
        Student student = studentDAO.getByVat("908172635");
        assertEquals(2L, student.getId());
    }

    @Test
    void getByVatNegativeScenario() throws StudentDaoException {
        Student student = studentDAO.getByVat("999999999");
        assertNull(student);
    }

    @Test
    void getByUuidPositiveScenario() throws StudentDaoException {
        Student studentByID = studentDAO.getById(1L);
        Student student = studentDAO.getByUuid(studentByID.getUuid());
        assertEquals(studentByID.getId(), student.getId());
    }

    @Test
    void getByUuidNegativeScenario() throws StudentDaoException {
        Student student = studentDAO.getByUuid("eee");
        assertNull(student);
    }

    @Test
    void getFilteredStudents() throws StudentDaoException {
        List<Student> students = studentDAO.getFilteredStudents("Ε", "Μ");
        assertEquals(1, students.size());
    }

    @Test
    void getAllStudents() throws StudentDaoException {
        List<Student> students = studentDAO.getAll();
        assertEquals(12, students.size());
    }

    @Test
    void deleteTeacher() throws StudentDaoException {
        studentDAO.delete(1L);
        Student deletedStudent = studentDAO.getById(1L);
        assertNull(deletedStudent);
    }
}