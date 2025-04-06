package gr.aueb.cf.schoolapp.exceptions;

import java.io.Serial;

public class CityDaoException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public CityDaoException(String message) {
        super(message);
    }


}
