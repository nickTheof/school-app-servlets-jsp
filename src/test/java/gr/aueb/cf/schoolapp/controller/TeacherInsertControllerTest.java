package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.model.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//class TeacherInsertControllerTest {
////
//    private static TeacherInsertController controller;
//    private HttpServletRequest req;
//    private HttpServletResponse resp;
//    private HttpSession session;
//
//    @BeforeAll
//    public static void setupClass() throws SQLException {
//        controller = new TeacherInsertController();
//        DBHelper.eraseTableData("teachers");
//    }
//
//    @BeforeEach
//    public void setup() throws TeacherDaoException {
//        DBCreateDummyData.createTeachersDummyData();
//    }
//
//}