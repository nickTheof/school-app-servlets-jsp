package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dto.FiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherDaoException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements ITeacherService {
    private final ITeacherDAO teacherDAO;

    public TeacherServiceImpl(ITeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO) throws TeacherDaoException, TeacherAlreadyExistsException {
        Teacher insertedTeacher;
        Teacher teacher;
        try {
            teacher = Mapper.mapTeacherInsertDTOToModel(insertDTO);

            if (teacherDAO.getByVat(teacher.getVat()) != null) {
                throw new TeacherAlreadyExistsException("Teacher with vat " + insertDTO.getVat() + " already exists");
            }
            insertedTeacher = teacherDAO.insert(teacher);
            return Mapper.mapTeacherToReadOnlyDTO(insertedTeacher).orElse(null);
        } catch (TeacherDaoException | TeacherAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public TeacherReadOnlyDTO updateTeacher(Long id, TeacherUpdateDTO teacherUpdateDTO) throws TeacherDaoException, TeacherAlreadyExistsException, TeacherNotFoundException {
        Teacher fetchedTeacher;
        Teacher teacher;
        Teacher updatedTeacher;
        try {
            if (teacherDAO.getById(id) == null) {
                throw new TeacherNotFoundException("Teacher with id " + id + " was not found");
            }
            fetchedTeacher = teacherDAO.getByVat(teacherUpdateDTO.getVat());
            if (fetchedTeacher != null && !fetchedTeacher.getId().equals(teacherUpdateDTO.getId())){
                throw new TeacherAlreadyExistsException("Teacher with vat " + teacherUpdateDTO.getVat() + " already exists");
            }
            teacher = Mapper.mapTeacherUpdateDTOToModel(teacherUpdateDTO);
            updatedTeacher = teacherDAO.update(teacher);
            return Mapper.mapTeacherToReadOnlyDTO(updatedTeacher).orElse(null);
        } catch (TeacherDaoException | TeacherNotFoundException | TeacherAlreadyExistsException e) {
            throw e;

        }
    }

    @Override
    public void deleteTeacher(Long id) throws TeacherNotFoundException, TeacherDaoException {
        try {
            if (teacherDAO.getById(id) == null) {
                throw new TeacherNotFoundException("Teacher with id " + id + " was not found");
            }
            teacherDAO.delete(id);
        } catch (TeacherDaoException | TeacherNotFoundException e) {
            throw e;
        }
    }

    @Override
    public TeacherReadOnlyDTO getTeacherById(Long id) throws TeacherNotFoundException, TeacherDaoException {
        try {
            Teacher teacher = teacherDAO.getById(id);
            return Mapper.mapTeacherToReadOnlyDTO(teacher).orElseThrow(()->new TeacherNotFoundException("Teacher with id " + id + " was not found"));
        } catch (TeacherDaoException | TeacherNotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getAllTeachers() throws TeacherDaoException {
        List<Teacher> teachers = new ArrayList<>();

        try {
            teachers = teacherDAO.getAll();
            return teachers.stream()
                    .map(Mapper::mapTeacherToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (TeacherDaoException e) {
            throw e;
        }
    }

    @Override
    public TeacherReadOnlyDTO getTeacherByUuid(String uuid) throws TeacherDaoException, TeacherNotFoundException {
        try {
            Teacher teacher = teacherDAO.getByUuid(uuid);
            return Mapper.mapTeacherToReadOnlyDTO(teacher).orElseThrow(()-> new TeacherNotFoundException("Teacher with uuid " + uuid + " was not found"));
        } catch (TeacherDaoException | TeacherNotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getFilteredTeachers(FiltersDTO filtersDTO) throws TeacherDaoException {
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = teacherDAO.getFilteredTeachers(filtersDTO.getFirstname(), filtersDTO.getLastname());
            return teachers.stream()
                    .map(Mapper::mapTeacherToReadOnlyDTO)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
