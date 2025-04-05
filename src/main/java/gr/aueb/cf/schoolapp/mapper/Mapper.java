package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
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
}
