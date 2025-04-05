package gr.aueb.cf.schoolapp.dto;

public class LoginUserDTO extends BaseUserDTO{
    public LoginUserDTO() {

    }

    public LoginUserDTO(String username, String password) {
        super(username, password);
    }
}
