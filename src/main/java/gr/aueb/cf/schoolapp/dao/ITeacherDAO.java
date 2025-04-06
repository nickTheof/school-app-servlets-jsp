package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.List;

public interface ITeacherDAO {
    Teacher insert(Teacher teacher) throws TeacherDaoException;
    Teacher update(Teacher teacher) throws TeacherDaoException;
    void delete(Long id) throws TeacherDaoException;
    Teacher getById(Long id) throws TeacherDaoException;
    List<Teacher> getAll() throws TeacherDaoException;
    Teacher getByVat(String vat) throws TeacherDaoException;
    Teacher getByUuid(String uuid) throws TeacherDaoException;
    List<Teacher> getFilteredTeachers(String firstname, String lastname) throws TeacherDaoException;
}
