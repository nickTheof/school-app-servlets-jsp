package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.model.Student;

import java.util.List;

public interface IStudentDAO {
    Student insert(Student student) throws StudentDaoException;
    Student update(Student student) throws StudentDaoException;
    void delete(Long id) throws StudentDaoException;
    Student getById(Long id) throws StudentDaoException;
    List<Student> getAll() throws StudentDaoException;
    Student getByVat(String vat) throws StudentDaoException;
    Student getByUuid(String uuid) throws StudentDaoException;
    List<Student> getFilteredStudents(String firstname, String lastname) throws StudentDaoException;
}
