package gr.aueb.cf.schoolapp.validator;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.BaseUserDTO;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {
    private static final IUserDAO userDao = new UserDAOImpl();
    private static final IUserService userService = new UserServiceImpl(userDao);

    private UserValidator() {

    }

    public static  <T extends BaseUserDTO> Map<String, String> validate(T dto) throws UserDaoException {
        Map<String, String> errors = new HashMap<>();

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            errors.put("confirmPassword", "Τα πεδία κωδικός και επιβεβαίωση κωδικού διαφέρουν.");
        }

        if (dto.getPassword().length() < 5 || dto.getPassword().length() > 32 ) {
            errors.put("password", "Ο κωδικός πρέπει να είναι μεταξύ 5 και 32 χαρακτήρων.");
        }

        if (dto.getUsername().matches("^.*\\s+.*$")) {
            errors.put("username", "Το email δεν πρέπει να περιλαμβάνει κενά");
        }

        if (dto.getPassword().matches("^.*\\s+.*$")) {
            errors.put("password", "Ο κωδικός δεν πρέπει να περιλαμβάνει κενά");
        }
        try {
            if (userService.isEmailExists(dto.getUsername())) {
                errors.put("username", "Το e-mail/username υπάρχει ήδη");
            }
        } catch (UserDaoException e) {
            throw e;
        }
        return errors;
    }
}
