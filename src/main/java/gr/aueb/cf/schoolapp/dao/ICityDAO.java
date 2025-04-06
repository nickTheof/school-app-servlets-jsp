package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.model.City;

import java.util.List;

public interface ICityDAO {
    City insert(City city) throws CityDaoException;
    City update(City city) throws CityDaoException;
    void delete(Integer id) throws CityDaoException;
    City getById(Integer id) throws CityDaoException;
    List<City> getAll() throws CityDaoException;
}
