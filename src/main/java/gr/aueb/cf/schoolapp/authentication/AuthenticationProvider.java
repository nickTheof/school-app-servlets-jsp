package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDao;
import gr.aueb.cf.schoolapp.dao.UserDaoImpl;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

public class AuthenticationProvider {
    private static final IUserDao userDao = new UserDaoImpl();
    private static final IUserService userService = new UserServiceImpl(userDao);

    private AuthenticationProvider() {

    }

    public static boolean authenticate(LoginUserDTO loginUserDTO) throws UserDaoException{
        try {
            return userService.isUserValid(loginUserDTO);
        } catch (UserDaoException e) {
            throw e;
        }

    }

}
