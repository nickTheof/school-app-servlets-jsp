package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.StudentDaoException;
import gr.aueb.cf.schoolapp.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements IStudentService {
    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public StudentReadOnlyDTO insertStudent(StudentInsertDTO studentInsertDTO) throws StudentDaoException, StudentAlreadyExistsException {
        Student student;
        Student insertedStudent;

        try {
            if (studentDAO.getByVat(studentInsertDTO.getVat()) != null) {
                throw new StudentAlreadyExistsException("Student with vat " + studentInsertDTO.getVat() + " already exists");
            }
            student = Mapper.mapStudentInsertDTOToModel(studentInsertDTO);
            insertedStudent = studentDAO.insert(student);
            return Mapper.mapStudentToReadOnlyDTO(insertedStudent).orElse(null);
        } catch (StudentDaoException | StudentAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public StudentReadOnlyDTO updateStudent(Long id, StudentUpdateDTO studentUpdateDTO) throws StudentDaoException, StudentAlreadyExistsException, StudentNotFoundException {
        Student fetchedStudent;
        Student student;
        Student updatedStudent;
        try {
            if (studentDAO.getById(id) == null) {
                throw new StudentNotFoundException("Student with id " + id + " was not found");
            }
            fetchedStudent = studentDAO.getByVat(studentUpdateDTO.getVat());
            if (fetchedStudent != null && !fetchedStudent.getId().equals(studentUpdateDTO.getId())){
                throw new StudentAlreadyExistsException("Student with vat " + studentUpdateDTO.getVat() + " already exists");
            }
            student = Mapper.mapStudentUpdateDTOToModel(studentUpdateDTO);
            updatedStudent = studentDAO.update(student);
            return Mapper.mapStudentToReadOnlyDTO(updatedStudent).orElse(null);
        } catch (StudentDaoException | StudentNotFoundException | StudentAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public void deleteStudent(Long id) throws StudentDaoException, StudentNotFoundException {
        try {
            if (studentDAO.getById(id) == null) {
                throw new StudentNotFoundException("Student with id " + id + " was not found");
            }
            studentDAO.delete(id);
        } catch (StudentDaoException | StudentNotFoundException e) {
            throw e;
        }
    }

    @Override
    public StudentReadOnlyDTO getStudentById(Long id) throws StudentDaoException, StudentNotFoundException {
        Student student;
        try {
            student = studentDAO.getById(id);
            return Mapper.mapStudentToReadOnlyDTO(student).orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " was not found"));
        } catch (StudentDaoException | StudentNotFoundException e) {
            throw e;
        }
    }

    @Override
    public StudentReadOnlyDTO getStudentByUuid(String uuid) throws StudentDaoException, StudentNotFoundException {
        Student student;
        try {
            student = studentDAO.getByUuid(uuid);
            return Mapper.mapStudentToReadOnlyDTO(student).orElseThrow(() -> new StudentNotFoundException("Student with uuid " + uuid + " was not found"));
        } catch (StudentDaoException | StudentNotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getAllStudents() throws StudentDaoException {
        List<Student> students;
        try {
            students = studentDAO.getAll();
            return students.stream()
                    .map(Mapper::mapStudentToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (StudentDaoException e) {
            throw e;
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getFilteredStudents(FiltersDTO filtersDTO) throws StudentDaoException {
        List<Student> students;
        try {
            students = studentDAO.getFilteredStudents(filtersDTO.getFirstname(), filtersDTO.getLastname());
            return students.stream()
                    .map(Mapper::mapStudentToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (StudentDaoException e) {
            throw e;
        }
    }
}
