package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.exceptions.*;
import gr.aueb.cf.schoolapp.model.Student;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
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
        createDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("students");
    }

    @AfterAll
    public static void tearAll() throws StudentDaoException {
        createDummyData();
    }

    private static void createDummyData() throws StudentDaoException {
        List<Student> students = List.of(
                new Student(1L, "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223", "c3b9d8e4-72fd-4d9f-bf32-1bde576f38aa", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 2)),
                new Student(2L, "Μαρία", "Κολοκοτρώνη", "908172635", "Θεόδωρος", "6976543210", "maria@gmail.com", "Αθήνας", "34", 6, "88990", "d5e8f3a1-93c2-41cb-8f54-3a68d6a1b7d8", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 3, 11, 12, 52)),
                new Student(3L, "Παναγιώτης", "Σωτηρόπουλος", "342156781", "Δημήτρης", "6932185476", "panos@gmail.com", "Λεωφόρος Συγγρού", "88", 4, "66778", "e5f7d1c9-2bf6-4803-8f21-bdf987c1a2e9", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 17)),
                new Student(4L, "Ευαγγελία", "Μαυρομιχάλη", "129874651", "Ιωάννης", "6923456789", "eva@gmail.com", "Ομόνοιας", "22", 7, "23456", "a9d3c4e1-73f1-45c6-8eb9-4d9f67a1b3c8", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 23)),
                new Student(5L, "Στέφανος", "Καραγιάννης", "783456121", "Αντώνιος", "6909876543", "stefanos@gmail.com", "Κηφισίας", "101", 3, "77788", "b1c2d3e4-82b3-49f6-a9c2-3e7d8f1a9b6c", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 44)),
                new Student(6L, "Αικατερίνη", "Βασιλείου", "214365871", "Νικόλαος", "6987654098", "katerina@gmail.com", "Πανεπιστημίου", "66", 5, "54321", "f9e8d7c6-5b4a-4321-9c87-6d5e4c3b2a1f", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 30)),
                new Student(7L, "Δημήτριος", "Λεβέντης", "908745321", "Μιχαήλ", "6935678901", "dimitris@gmail.com", "Βασιλίσσης Σοφίας", "45", 6, "33221", "d3b7c8f9-1e2a-47f6-9c8b-5a4d6e7f2c1b", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 44, 52)),
                new Student(8L, "Νικόλαος", "Σταματίου", "782341652", "Πέτρος", "6978123456", "nikos@gmail.com", "Ακαδημίας", "29", 2, "99887", "c4d7e8f9-53b2-47c1-a9d8-6e5f4a3b2c1d", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 45, 1)),
                new Student(9L, "Χριστίνα", "Αναγνωστοπούλου", "324567892", "Εμμανουήλ", "6956781234", "christina@gmail.com", "Λιοσίων", "77", 4, "76543", "a7b9c8d3-2f1e-46a5-8d7c-4e3f6b2a1c9d", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 45, 17)),
                new Student(10L, "Ιωάννης", "Νικολαΐδης", "876543219", "Αλέξανδρος", "6943218765", "johnny@gmail.com", "Μεσογείων", "98", 1, "45678", "a1c2d3b4-12ef-34ab-56cd-7890f1e2d3c4", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 45, 33)),
                new Student(11L, "Σοφία", "Κυριακοπούλου", "112358132", "Χαράλαμπος", "6967894321", "sofia.k@gmail.com", "Ελευθερίου Βενιζέλου", "11", 5, "34567", "b2d4e6f8-65a3-42f7-a321-0f1e2d3c4b5a", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 45, 44)),
                new Student(12L, "Αντώνης", "Μιχαηλίδης", "102938475", "Νίκος", "6931234567", "antonis.m@gmail.com", "Ανδρέα Παπανδρέου", "17", 7, "12345", "c3f5e7d9-45b2-41c3-a5f7-3e4c2a1b6d8f", LocalDateTime.of(2025, 3, 11, 12, 52), LocalDateTime.of(2025, 4, 8, 21, 45, 55))
        );
        for (Student student: students) {
            studentDAO.insert(student);
        }
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