package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOImpl implements ICityDAO {
    @Override
    public City insert(City city) throws CityDaoException {
        String sql = "INSERT INTO cities (name) VALUES (?)";
        City insertedCity = null;
        ResultSet rsGenKeys;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, city.getName());
            ps.executeUpdate();
            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()) {
                int cityId = rsGenKeys.getInt(1);
                insertedCity = getById(cityId);
            }
            return insertedCity;
        } catch (SQLException e) {
            throw new CityDaoException("SQL Error. Error in inserting city with name " + city.getName());
        }
    }

    @Override
    public City update(City city) throws CityDaoException {
        String sql = "UPDATE cities SET name = ? WHERE id = ?";
        City updatedCity = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.executeUpdate();
            updatedCity = getById(city.getId());

            return updatedCity;
        } catch (SQLException e) {
            throw new CityDaoException("SQL Error. Error updating city with id: " + city.getId());
        }
    }

    @Override
    public void delete(Integer id) throws CityDaoException {
        String sql = "DELETE FROM cities WHERE id = ?";
        ResultSet rs;
        City city = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CityDaoException("SQL Error. Error in deleting city with id " + id);
        }
    }

    @Override
    public City getById(Integer id) throws CityDaoException {
        String sql = "SELECT * FROM cities WHERE id = ?";
        ResultSet rs;
        City city = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                city = new City(rs.getInt("id"), rs.getString("name"));
            }
            return city;
        } catch (SQLException e) {
            throw new CityDaoException("SQL Error. Error in getting city with id " + id);
        }
    }

    @Override
    public List<City> getAll() throws CityDaoException {
        String sql = "SELECT * FROM cities order by name asc";
        List<City> cities = new ArrayList<>();
        ResultSet rs;
        City city;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                city = new City(rs.getInt("id"), rs.getString("name"));
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            throw new CityDaoException("SQL Error. Error in getting all cities");
        }
    }
}
