package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.util.DBCreateDummyData;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.exceptions.CityDaoException;
import gr.aueb.cf.schoolapp.model.City;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityDAOImplTest {
    private static ICityDAO cityDAO;

    @BeforeAll
    public static void setupClass() throws SQLException {
        cityDAO = new CityDAOImpl();
        DBHelper.eraseTableData("cities");
    }

    @BeforeEach
    public void setup() throws CityDaoException {
        DBCreateDummyData.createCityDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("cities");
    }

    @AfterAll
    public static void tearAll() throws CityDaoException {
        DBCreateDummyData.createCityDummyData();
    }

    @Test
    void persistCityAndGet() throws CityDaoException {
        City city = new City(null, "Αγρίνιο");
        City insertedCity = cityDAO.insert(city);
        assertEquals(12, insertedCity.getId());
        City getCity = cityDAO.getById(12);
        assertEquals("Αγρίνιο", getCity.getName());
    }

    @Test
    void updateCity() throws CityDaoException {
        cityDAO.update(new City(1, "London"));
        City city = cityDAO.getById(1);
        assertEquals("London", city.getName());
    }

    @Test
    void deleteCity() throws CityDaoException {
        City city = new City(null, "London");
        City insertedCity = cityDAO.insert(city);
        cityDAO.delete(insertedCity.getId());
        City deletedCity = cityDAO.getById(insertedCity.getId());
        assertNull(deletedCity);

    }

    @Test
    void getAllCities() throws CityDaoException {
        List<City> cities = cityDAO.getAll();
        assertEquals(11, cities.size());
    }

}