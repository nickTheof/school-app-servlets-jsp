package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CityTest {

    @Test
    void defaultConstructorGettersAndSetters() {
        City city = new City();
        assertNull(city.getId());
        assertNull(city.getName());
        city.setId(1);
        city.setName("Athens");
        assertEquals(1, city.getId());
        assertEquals("Athens", city.getName());
    }

    @Test
    void overloadedConstructor() {
        City city = new City(1, "Athens");
        assertEquals(1, city.getId());
        assertEquals("Athens", city.getName());
    }
}