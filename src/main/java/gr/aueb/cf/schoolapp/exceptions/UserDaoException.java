package gr.aueb.cf.schoolapp.exceptions;

import java.io.Serial;

public class UserDaoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserDaoException(String message) {
        super(message);
    }
}
