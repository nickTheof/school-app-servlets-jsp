package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.exceptions.UserNotFoundException;

public interface IUserService {
    UserReadOnlyDTO insertUser(InsertUserDTO userDTO) throws UserDaoException, UserAlreadyExistsException;
    UserReadOnlyDTO getUserByUsername(String username) throws UserNotFoundException, UserDaoException;
    boolean isUserValid(LoginUserDTO userDTO) throws UserDaoException;
    boolean isEmailExists(String username) throws UserDaoException;
}
