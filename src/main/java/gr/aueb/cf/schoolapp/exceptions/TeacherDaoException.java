package gr.aueb.cf.schoolapp.exceptions;

import java.io.Serial;

public class TeacherDaoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public TeacherDaoException(String message) {
        super(message);
    }
}
