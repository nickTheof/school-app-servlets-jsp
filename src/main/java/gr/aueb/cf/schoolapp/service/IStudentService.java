package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;

import java.util.List;

public interface IStudentService {
    StudentReadOnlyDTO insertStudent(StudentInsertDTO studentInsertDTO)
            throws StudentDaoException, StudentAlreadyExistsException;
    StudentReadOnlyDTO updateStudent(Long id, StudentUpdateDTO studentUpdateDTO)
            throws StudentDaoException,  StudentAlreadyExistsException, StudentNotFoundException;
    void deleteStudent(Long id) throws StudentDaoException, StudentNotFoundException;
    StudentReadOnlyDTO getStudentById(Long id) throws StudentDaoException, StudentNotFoundException;
    StudentReadOnlyDTO getStudentByUuid(String uuid) throws StudentDaoException, StudentNotFoundException;
    List<StudentReadOnlyDTO> getAllStudents() throws StudentDaoException;
    List<StudentReadOnlyDTO> getFilteredStudents(FiltersDTO filtersDTO) throws StudentDaoException;
}
