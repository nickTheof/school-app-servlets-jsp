package gr.aueb.cf.schoolapp.dao;

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

    private static void createDummyData() throws TeacherDaoException {
        List<Teacher> teachers = List.of(
                new Teacher(1L, "Γεώργιος", "Παναγιώτου", "567839201", "Στέφανος", "6987654321", "george@gmail.com", "Σταδίου", "55", 2, "11223", "e27aee5e-f447-4203-9b4a-7c638f422057", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 8, 21, 35, 30)),
                new Teacher(2L, "Άννα", "Γιαννούτσου", "123456788", "Κώ", "1234567890", "anna@gmail.com", "Γεωργούτσου", "12", 5, "67856", "68bc653a-abc7-4c30-8eb9-247b6b380d2e", LocalDateTime.of(2025, 3, 17, 18, 1, 18), LocalDateTime.of(2025, 4, 9, 7, 5, 45)),
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
            teacherDao.insert(teacher);
        }
    }

}