package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.exceptions.UserDaoException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.SecUtil;
import gr.aueb.cf.schoolapp.util.DBUtil;

import java.sql.*;

public class UserDaoImpl implements IUserDao{
    @Override
    public User insert(User user) throws UserDaoException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        ResultSet rs;
        User insertedUser = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getUsername());
            ps.setString(2, SecUtil.hashPassword(user.getPassword()));
            ps.setString(3, user.getRoleType().name());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                insertedUser = getById(rs.getLong(1));
            }
            return insertedUser;

        } catch (SQLException e) {
            throw new UserDaoException("SQL Error. Error in inserting user with username " + user.getUsername());
        }
    }

    @Override
    public User getById(Long id) throws UserDaoException {
        String sql = "SELECT * FROM users WHERE id = ?";
        ResultSet rs;
        User user = null;
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"), RoleType.valueOf(rs.getString("role")));
            }
            return user;
        } catch (SQLException e) {
            throw new UserDaoException("SQL Error. Error in getting user with id " + id);
        }
    }

    @Override
    public User getByUsername(String username) throws UserDaoException {
        String sql = "SELECT * FROM users WHERE username = ?";
        ResultSet rs;
        User user = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"), RoleType.valueOf(rs.getString("role")));
            }
            return user;
        } catch (SQLException e) {
            throw new UserDaoException("SQL Error. Error in getting user with username " + username);
        }
    }

    @Override
    public boolean isUserValid(String username, String password) throws UserDaoException {
        String sql = "SELECT * FROM users WHERE username = ?";
        ResultSet rs;
        User user = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"), RoleType.valueOf(rs.getString("role")));
            } else {
                return false;
            }
            return SecUtil.isPasswordValid(password, user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDaoException("SQL Error. Error in checking validity of user with username " + username);

        }
    }

    @Override
    public boolean isEmailExists(String username) throws UserDaoException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        ResultSet rs;
        int count = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new UserDaoException("SQL Error. Error in checking user with username " + username);
        }
    }
}
