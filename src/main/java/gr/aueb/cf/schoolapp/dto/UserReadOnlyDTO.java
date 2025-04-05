package gr.aueb.cf.schoolapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReadOnlyDTO extends BaseUserDTO {
    private Long id;
    private String role;

    public UserReadOnlyDTO() {

    }

    public UserReadOnlyDTO(Long id, String username, String password, String role) {
        super(username, password);
        this.id = id;
        this.role = role;
    }
}
