package gr.aueb.cf.schoolapp.exceptions;

import java.io.Serial;

public class StudentDaoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public StudentDaoException(String message) {
        super(message);
    }
}
