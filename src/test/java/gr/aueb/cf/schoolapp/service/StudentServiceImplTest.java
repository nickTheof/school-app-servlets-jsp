package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.exceptions.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceImplTest {

    private static IStudentDAO studentDAO;
    private static IStudentService studentService;

    @BeforeAll
    public static void setupClass() throws SQLException {
        studentDAO = new StudentDAOImpl();
        studentService = new StudentServiceImpl(studentDAO);
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
    public static void tearAll() throws StudentDaoException {
        DBCreateDummyData.createStudentsDummyData();;
    }

    @Test
    void insertStudent() throws StudentDaoException, StudentAlreadyExistsException, StudentNotFoundException {
        StudentInsertDTO insertDTO = new StudentInsertDTO("Αθανάσιος", "Ιωάννου", "080654320", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76", 7, "10434");

        StudentReadOnlyDTO studentReadOnlyDTO = studentService.insertStudent(insertDTO);
        StudentReadOnlyDTO fetchStudentReadOnlyDTO = studentService.getStudentById(studentReadOnlyDTO.getId());
        StudentReadOnlyDTO fetchStudentReadOnlyDTOByUuid = studentService.getStudentByUuid(studentReadOnlyDTO.getUuid());
        assertEquals(13L, fetchStudentReadOnlyDTO.getId());
        assertEquals(studentReadOnlyDTO.getId(), fetchStudentReadOnlyDTO.getId());
        assertEquals(13L, fetchStudentReadOnlyDTOByUuid.getId());
    }

    @Test
    void insertStudentNegative() {
        StudentInsertDTO insertDTO = new StudentInsertDTO("Αθανάσιος", "Ιωάννου", "782341652", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76", 7, "10434");

        assertThrows(StudentAlreadyExistsException.class, () -> {
            studentService.insertStudent(insertDTO);
        });
    }

    @Test
    public void updateStudent()
            throws StudentDaoException, StudentAlreadyExistsException, StudentNotFoundException {

        StudentUpdateDTO updateDTO = new StudentUpdateDTO(9L, "Αθανάσιος", "Ιωάννου", "080654320", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");

        studentService.updateStudent(9L, updateDTO);

        StudentReadOnlyDTO student = studentService.getStudentById(9L);
        assertEquals("080654320", student.getVat());
        assertEquals("Αθανάσιος", student.getFirstname());
        assertEquals("Ιωάννου", student.getLastname());
    }

    @Test
    public void updateStudentVatExistsNegative() {
        StudentUpdateDTO updateDTO = new StudentUpdateDTO(9L, "Αθανάσιος", "Ιωάννου", "782341652", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");
        assertThrows(StudentAlreadyExistsException.class, () -> {
            studentService.updateStudent(9L, updateDTO);
        });
    }

    @Test
    public void updateStudentNotExistsNegative() {
        StudentUpdateDTO updateDTO = new StudentUpdateDTO(15L, "Αθανάσιος", "Ιωάννου", "782341652", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(15L, updateDTO);
        });
    }


    @Test
    public void deleteStudentPositive()
            throws StudentDaoException, StudentNotFoundException {

        studentService.deleteStudent(1L);
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(1L);
        });
    }

    @Test
    public void deleteStudentNegative() {
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(15L);
        });
    }


    @Test
    void getStudentByIdNegative()  {
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(15L);
        });
    }


    @Test
    void getStudentByUuidNegative()  {
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentByUuid("eee");
        });
    }


    @Test
    void getAllStudents() throws StudentDaoException {
        List<StudentReadOnlyDTO> students = studentService.getAllStudents();
        assertEquals(12, students.size());
    }

    @Test
    void getFilteredStudents() throws StudentDaoException {
        FiltersDTO filtersDTO1 = new FiltersDTO("Γ", "Π");
        FiltersDTO filtersDTO2 = new FiltersDTO("", "Π");
        FiltersDTO filtersDTO3 = new FiltersDTO("Α", "");
        List<StudentReadOnlyDTO> students1 = studentService.getFilteredStudents(filtersDTO1);
        List<StudentReadOnlyDTO> students2 = studentService.getFilteredStudents(filtersDTO2);
        List<StudentReadOnlyDTO> students3 = studentService.getFilteredStudents(filtersDTO3);
        assertEquals(1, students1.size());
        assertEquals(1, students2.size());
        assertEquals(2, students3.size());
    }
}