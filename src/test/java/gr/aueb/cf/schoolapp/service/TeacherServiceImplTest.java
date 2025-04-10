package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
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