package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;

import java.util.Optional;

public class Mapper {
    private Mapper() {

    }

    public static User mapInsertUserDTOToModel(InsertUserDTO userDTO) {
        return new User(null, userDTO.getUsername(), userDTO.getPassword(), RoleType.valueOf(userDTO.getRole()));
    }

    public static Optional<UserReadOnlyDTO> mapUserToUserReadOnlyDTO(User user) {
        if (user == null) return Optional.empty();
        return Optional.of(new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRoleType().name()));
    }

    public static Teacher mapTeacherInsertDTOToModel(TeacherInsertDTO dto) {
        return new Teacher(null, dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getFatherName(), dto.getPhoneNum(),
                dto.getEmail(), dto.getStreet(), dto.getStreetNum(), dto.getCityId(), dto.getZipcode(), null, null, null);
    }

    public static Teacher mapTeacherUpdateDTOToModel(TeacherUpdateDTO dto) {
        return new Teacher(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getFatherName(), dto.getPhoneNum(),
                dto.getEmail(), dto.getStreet(), dto.getStreetNum(), dto.getCityId(), dto.getZipcode(), null, null, null);
    }

    public static Optional<TeacherReadOnlyDTO> mapTeacherToReadOnlyDTO(Teacher teacher) {
        if (teacher == null) return Optional.empty();
        return Optional.of(new TeacherReadOnlyDTO(teacher.getId(), teacher.getUuid(), teacher.getFirstname(), teacher.getLastname(), teacher.getVat(), teacher.getFatherName(), teacher.getPhoneNum(), teacher.getEmail(), teacher.getStreet(), teacher.getStreetNum(), teacher.getCityId(), teacher.getZipcode()));
    }

    public static Student mapStudentInsertDTOToModel(StudentInsertDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getFatherName(), dto.getPhoneNum(),
                dto.getEmail(), dto.getStreet(), dto.getStreetNum(), dto.getCityId(), dto.getZipcode(), null, null, null);
    }

    public static Student mapStudentUpdateDTOToModel(StudentUpdateDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getFatherName(), dto.getPhoneNum(),
                dto.getEmail(), dto.getStreet(), dto.getStreetNum(), dto.getCityId(), dto.getZipcode(), null, null, null);
    }

    public static Optional<StudentReadOnlyDTO> mapStudentToReadOnlyDTO(Student student) {
        if (student == null) return Optional.empty();
        return Optional.of(new StudentReadOnlyDTO(student.getId(), student.getUuid(), student.getFirstname(), student.getLastname(), student.getVat(), student.getFatherName(), student.getPhoneNum(), student.getEmail(), student.getStreet(), student.getStreetNum(), student.getCityId(), student.getZipcode()));
    }

}
