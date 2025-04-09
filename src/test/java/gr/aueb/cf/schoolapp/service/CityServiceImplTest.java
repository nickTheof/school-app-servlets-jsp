package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceImplTest {
    private static ICityDAO cityDAO;
    private static ICityService cityService;

    @BeforeAll
    public static void setupClass() {
        cityDAO = new CityDAOImpl();
        cityService = new CityServiceImpl(cityDAO);
    }

    @Test
    void getAllCities() throws CityDaoException {
        List<City> cities = cityService.getAllCities();
        assertEquals(11, cities.size());
    }

}