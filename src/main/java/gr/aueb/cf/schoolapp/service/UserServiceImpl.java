package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.dto.LoginUserDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.User;

public class UserServiceImpl implements IUserService{
    private final IUserDAO userDao;

    public UserServiceImpl(IUserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserReadOnlyDTO insertUser(InsertUserDTO userDTO) throws UserDaoException, UserAlreadyExistsException {
        User insertedUser;
        User user;
        try {
            if (userDao.getByUsername(userDTO.getUsername()) != null) {
                throw new UserAlreadyExistsException("User with username " + userDTO.getUsername() + " already exists");
            }
            user = Mapper.mapInsertUserDTOToModel(userDTO);
            insertedUser = userDao.insert(user);
            return Mapper.mapUserToUserReadOnlyDTO(insertedUser).orElse(null);
        } catch (UserDaoException | UserAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public UserReadOnlyDTO getUserByUsername(String username) throws UserNotFoundException, UserDaoException {
        User user;
        try {
            user = userDao.getByUsername(username);
            if (user == null) {
                throw new UserNotFoundException("User with username " + username + " was not found");
            }
            return Mapper.mapUserToUserReadOnlyDTO(user).orElse(null);
        } catch (UserNotFoundException | UserDaoException e) {
            throw e;
        }
    }

    @Override
    public boolean isUserValid(LoginUserDTO userDTO) throws UserDaoException {
        try {
            return userDao.isUserValid(userDTO.getUsername(), userDTO.getPassword());
        } catch (UserDaoException e) {
            throw e;
        }
    }

    @Override
    public boolean isEmailExists(String username) throws UserDaoException {
        try {
            return userDao.isEmailExists(username);
        } catch (UserDaoException e) {
            throw e;
        }
    }
}
