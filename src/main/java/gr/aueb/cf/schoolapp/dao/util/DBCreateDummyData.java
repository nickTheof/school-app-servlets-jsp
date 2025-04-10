package gr.aueb.cf.schoolapp.dao.util;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dao.*;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class DBCreateDummyData {
    private static final ICityDAO cityDAO = new CityDAOImpl();
    private static final IStudentDAO studentDAO = new StudentDAOImpl();
    private static final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private static final IUserDAO userDAO = new UserDAOImpl();

    private DBCreateDummyData() {

    }

    public static void createCityDummyData() throws CityDaoException {
        List<City> cities = List.of(
                new City(1, "Αθήνα"), new City(2, "Πάτρα"), new City(3, "Βόλος"),
                new City(4, "Λάρισσα"), new City(5, "Θεσσαλονίκη"), new City(6, "Κέρκυρα"),
                new City(7, "Δράμα"), new City(8, "Πύργος"), new City(9, "Καλάματα"),
                new City(10, "Ηράκλειο"), new City(11, "Χανιά")
        );

        for (City city: cities) {
            cityDAO.insert(city);
        }

    }

    public static void createTeachersDummyData() throws TeacherDaoException {
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
            teacherDAO.insert(teacher);
        }
    }

    public static void createStudentsDummyData() throws StudentDaoException {
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

    public static void createUsersDummyData() throws UserDaoException {
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
}
