package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;

import java.util.List;

public interface ITeacherService {
    TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO)
            throws TeacherDaoException, TeacherAlreadyExistsException;
    TeacherReadOnlyDTO updateTeacher(Long id, TeacherUpdateDTO updateDTO)
            throws TeacherDaoException, TeacherAlreadyExistsException, TeacherNotFoundException;
    void deleteTeacher(Long id) throws TeacherNotFoundException, TeacherDaoException;
    TeacherReadOnlyDTO getTeacherById(Long id) throws TeacherNotFoundException, TeacherDaoException;
    List<TeacherReadOnlyDTO> getAllTeachers() throws TeacherDaoException;
    TeacherReadOnlyDTO getTeacherByUuid(String uuid) throws TeacherDaoException, TeacherNotFoundException;
    List<TeacherReadOnlyDTO> getFilteredTeachers(FiltersDTO filtersDTO) throws TeacherDaoException;
}
