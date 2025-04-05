package gr.aueb.cf.schoolapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertUserDTO extends BaseUserDTO {
    private String role;

    public InsertUserDTO() {

    }

    public InsertUserDTO(String username, String password, String passwordConfirm, String role) {
        super(username, password, passwordConfirm);
        this.role = role;
    }
}
