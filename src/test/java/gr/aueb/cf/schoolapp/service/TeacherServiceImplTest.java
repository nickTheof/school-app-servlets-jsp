package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.model.Teacher;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceImplTest {

    private static ITeacherDAO teacherDAO;
    private static ITeacherService teacherService;

    @BeforeAll
    public static void setupClass() throws SQLException {
        teacherDAO = new TeacherDAOImpl();
        teacherService = new TeacherServiceImpl(teacherDAO);
        DBHelper.eraseTableData("teachers");
    }

    @BeforeEach
    public void setup() throws TeacherDaoException {
        createDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("teachers");
    }

    @AfterAll
    public static void tearAll() throws TeacherDaoException {
        createDummyData();
    }

    private static void createDummyData() throws TeacherDaoException {
        List<Teacher> teachers = List.of(
            new Teacher(1L, "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223", "e27aee5e-f447-4203-9b4a-7c638f422057", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 35, 30)),
            new Teacher(2L, "Αννα", "Γιαννούτσου", "123456788", "Κώ", "1234567890", "anna@gmail.com", "Γεωργούτσου", "12", 5, "67856", "68bc653a-abc7-4c30-8eb9-247b6b380d2e", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 9, 7, 5, 45)),
            new Teacher(3L, "Μάκης", "Καπέτης", "987654321", "Ευάγγελος", "6935465768", "makis@gmail.com", "Πατησίων", "76", 1, "10434", "94110e89-c23b-4c41-8c6b-f3e8b1fe9664", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 3, 17, 18, 1, 18)),
            new Teacher(4L, "Νίκη", "Γιαννούτσου", "918273645", "Αθανάσιος", "6934564890", "niki@gmail.com", "Λαμπρούτσου", "12", 7, "65098", "31cff198-3280-46c3-bd32-6b4b1fb80ba6", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 3, 17, 18, 1, 18)),
            new Teacher(5L, "Ιωάννα", "Χατζηδάκη", "543216789", "Μάριος", "6912345678", "ioanna@gmail.com", "Κοραή", "5", 3, "55432", "73ad2fc2-4573-4031-922d-12a9e9c8d9b5", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 19, 25, 50)),
            new Teacher(6L, "Χρήστος", "Παπαγεωργίου", "654321789", "Θανάσης", "6901234567", "xristos@gmail.com", "Αγίου Κωνσταντίνου", "9", 4, "99887", "8b5df3cc-50f6-4df9-861a-ff76f812e6f7", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 20, 40, 20)),
            new Teacher(7L, "Δέσποινα", "Τζανετάτου", "112233445", "Ιάκωβος", "6981234567", "despoina@gmail.com", "Πλατεία Βάθη", "7", 6, "33445", "c71c7654-689a-4b78-8bb4-e14dd5c9b157", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 20, 55, 11)),
            new Teacher(8L, "Αλέξανδρος", "Σταυρόπουλος", "778899001", "Κωνσταντίνος", "6948765432", "alex@gmail.com", "Αναγνωστοπούλου", "15", 5, "77001", "9d3c84fb-0bc7-4991-b7b2-1cbbf7e2f711", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 5, 10)),
            new Teacher(9L, "Κατερίνα", "Νικολάου", "445566778", "Πέτρος", "6973456123", "katerina.n@gmail.com", "Ιπποκράτους", "44", 2, "11211", "6ec2e0d6-4d9d-47fd-8d4f-8eeeb2b22e67", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 10, 30)),
            new Teacher(10L, "Σωτήρης", "Αντωνίου", "667788990", "Λεωνίδας", "6932145789", "sotiris@gmail.com", "Ερμού", "60", 3, "33444", "64fe3b7a-4e3a-4a2b-91a3-96a5d813ce1e", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 15, 18)),
            new Teacher(11L, "Αγγελική", "Μανωλάκου", "123443211", "Διονύσης", "6945871234", "aggeliki@gmail.com", "Νίκης", "17", 4, "88992", "782b7e23-88b2-46ef-b3c3-92e38e0db9c9", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 20, 41)),
            new Teacher(12L, "Θεόφιλος", "Γεωργακόπουλος", "554433221", "Ηρακλής", "6923456789", "theofilos@gmail.com", "Φειδίου", "21", 1, "77665", "fd0c0194-24df-4cf6-8c10-dc5d46cf7ad1", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 30, 15))
        );
        for (Teacher teacher: teachers) {
            teacherDAO.insert(teacher);
        }
    }

    @Test
    void insertTeacher() throws TeacherDaoException, TeacherAlreadyExistsException, TeacherNotFoundException {
        TeacherInsertDTO insertDTO = new TeacherInsertDTO("Αθανάσιος", "Ιωάννου", "080654320", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76", 7, "10434");

        TeacherReadOnlyDTO teacherReadOnlyDTO = teacherService.insertTeacher(insertDTO);
        TeacherReadOnlyDTO fetchTeacherReadOnlyDTO = teacherService.getTeacherById(teacherReadOnlyDTO.getId());
        TeacherReadOnlyDTO fetchTeacherReadOnlyDTOByUuid = teacherService.getTeacherByUuid(teacherReadOnlyDTO.getUuid());
        assertEquals(13L, fetchTeacherReadOnlyDTO.getId());
        assertEquals(teacherReadOnlyDTO.getId(), fetchTeacherReadOnlyDTO.getId());
        assertEquals(13L, fetchTeacherReadOnlyDTOByUuid.getId());
    }

    @Test
    void insertTeacherNegative() {
        TeacherInsertDTO insertDTO = new TeacherInsertDTO("Αθανάσιος", "Ιωάννου", "567839201", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76", 7, "10434");

        assertThrows(TeacherAlreadyExistsException.class, () -> {
            teacherService.insertTeacher(insertDTO);
        });
    }

    @Test
    public void updateTeacher()
            throws TeacherDaoException, TeacherNotFoundException, TeacherAlreadyExistsException {

        TeacherUpdateDTO updateDTO = new TeacherUpdateDTO(9L, "Αθανάσιος", "Ιωάννου", "080654320", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");

        teacherService.updateTeacher(9L, updateDTO);

        TeacherReadOnlyDTO teacher = teacherService.getTeacherById(9L);
        assertEquals("080654320", teacher.getVat());
        assertEquals("Αθανάσιος", teacher.getFirstname());
        assertEquals("Ιωάννου", teacher.getLastname());
    }

    @Test
    public void updateTeacherVatExistsNegative() {
        TeacherUpdateDTO updateDTO = new TeacherUpdateDTO(9L, "Αθανάσιος", "Ιωάννου", "987654321", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");
        assertThrows(TeacherAlreadyExistsException.class, () -> {
            teacherService.updateTeacher(9L, updateDTO);
        });
    }

    @Test
    public void updateTeacherNotExistsNegative() {
        TeacherUpdateDTO updateDTO = new TeacherUpdateDTO(15L, "Αθανάσιος", "Ιωάννου", "959595667", "Ανδρέας",
                "6935565765", "a8ana@gmail.com", "Πατησίων", "76",7, "10434");
        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.updateTeacher(15L, updateDTO);
        });
    }


    @Test
    public void deleteTeacherPositive()
            throws TeacherDaoException, TeacherNotFoundException {

        teacherService.deleteTeacher(1L);
        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.getTeacherById(1L);
        });
    }

    @Test
    public void deleteTeacherNegative() {
        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.deleteTeacher(15L);
        });
    }


    @Test
    void getTeacherByIdNegative()  {
        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.getTeacherById(15L);
        });
    }


    @Test
    void getTeacherByUuidNegative()  {
        assertThrows(TeacherNotFoundException.class, () -> {
            teacherService.getTeacherByUuid("eee");
        });
    }


    @Test
    void getAllTeachers() throws TeacherDaoException {
        List<TeacherReadOnlyDTO> teachers = teacherService.getAllTeachers();
        assertEquals(12, teachers.size());
    }

    @Test
    void getFilteredTeachers() throws TeacherDaoException {
        FiltersDTO filtersDTO1 = new FiltersDTO("Γ", "Π");
        FiltersDTO filtersDTO2 = new FiltersDTO("", "Π");
        FiltersDTO filtersDTO3 = new FiltersDTO("Α", "");
        List<TeacherReadOnlyDTO> teachers1 = teacherService.getFilteredTeachers(filtersDTO1);
        List<TeacherReadOnlyDTO> teachers2 = teacherService.getFilteredTeachers(filtersDTO2);
        List<TeacherReadOnlyDTO> teachers3 = teacherService.getFilteredTeachers(filtersDTO3);
        assertEquals(1, teachers1.size());
        assertEquals(2, teachers2.size());
        assertEquals(3, teachers3.size());
    }
}