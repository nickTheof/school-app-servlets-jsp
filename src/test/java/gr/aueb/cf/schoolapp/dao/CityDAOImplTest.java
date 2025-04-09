package gr.aueb.cf.schoolapp.dao;

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
        createDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseTableData("cities");
    }

    @AfterAll
    public static void tearAll() throws CityDaoException {
        createDummyData();
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



    private static void createDummyData() throws CityDaoException {

        City city1 = new City(null, "Αθήνα");
        City city2 = new City(null, "Πάτρα");
        City city3 = new City(null, "Βόλος");
        City city4 = new City(null, "Λάρισσα");
        City city5 = new City(null, "Θεσσαλονίκη");
        City city6 = new City(null, "Κέρκυρα");
        City city7 = new City(null, "Δράμα");
        City city8 = new City(null, "Πύργος");
        City city9 = new City(null, "Καλάματα");
        City city10 = new City(null, "Ηράκλειο");
        City city11 = new City(null, "Χανιά");
        cityDAO.insert(city1);
        cityDAO.insert(city2);
        cityDAO.insert(city3);
        cityDAO.insert(city4);
        cityDAO.insert(city5);
        cityDAO.insert(city6);
        cityDAO.insert(city7);
        cityDAO.insert(city8);
        cityDAO.insert(city9);
        cityDAO.insert(city10);
        cityDAO.insert(city11);
    }

}