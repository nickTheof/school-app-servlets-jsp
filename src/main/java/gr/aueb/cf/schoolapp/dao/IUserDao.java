package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.model.User;

public interface IUserDao {
    User insert(User user) throws UserDaoException;
    User getById(Long id) throws UserDaoException;
    User getByUsername(String username) throws UserDaoException;
    boolean isUserValid(String username, String password) throws UserDaoException;
    boolean isEmailExists(String username) throws UserDaoException;
}
