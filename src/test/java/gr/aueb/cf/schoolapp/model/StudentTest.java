package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void defaultConstructorGettersAndSetters(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Student student = new Student();

        assertNull(student.getId());
        assertNull(student.getFirstname());
        assertNull(student.getLastname());
        assertNull(student.getVat());
        assertNull(student.getFatherName());
        assertNull(student.getPhoneNum());
        assertNull(student.getEmail());
        assertNull(student.getStreet());
        assertNull(student.getStreetNum());
        assertNull(student.getZipcode());
        assertNull(student.getCityId());
        assertNull(student.getUuid());
        assertNull(student.getUpdatedAt());
        assertNull(student.getCreatedAt());

        student.setId(1L);
        student.setFirstname("Nikolas");
        student.setLastname("Theofanis");
        student.setVat("1111111111");
        student.setFatherName("Christos");
        student.setPhoneNum("2102102100");
        student.setEmail("test@gmail.com");
        student.setStreet("Stadiou");
        student.setStreetNum("47");
        student.setZipcode("11111");
        student.setCityId(1);
        student.setUuid("ee421a09-cd3b-499c-b054-4cf96d13e5b7");
        LocalDateTime dateTime = LocalDateTime.parse("26/02/2025 14:48", formatter);
        student.setCreatedAt(dateTime);
        student.setUpdatedAt(dateTime);

        assertEquals(Long.valueOf(1), student.getId());
        assertEquals("Nikolas", student.getFirstname());
        assertEquals("Theofanis", student.getLastname());
        assertEquals("1111111111", student.getVat());
        assertEquals("Christos", student.getFatherName());
        assertEquals("2102102100", student.getPhoneNum());
        assertEquals("test@gmail.com", student.getEmail());
        assertEquals("Stadiou", student.getStreet());
        assertEquals("47", student.getStreetNum());
        assertEquals("11111", student.getZipcode());
        assertEquals(1, student.getCityId());
        assertEquals("ee421a09-cd3b-499c-b054-4cf96d13e5b7", student.getUuid());
        assertEquals(dateTime, student.getCreatedAt());
        assertEquals(dateTime, student.getUpdatedAt());

    }

    @Test
    void overloadedConstructor() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("26/02/2025 14:48", formatter);

        Student student = new Student(1L, "Nikolas", "Theofanis", "1111111111", "Christos", "2102102100", "test@gmail.com",
                "Stadiou", "47", 1, "11111", "ee421a09-cd3b-499c-b054-4cf96d13e5b7", dateTime, dateTime);

        assertEquals(Long.valueOf(1), student.getId());
        assertEquals("Nikolas", student.getFirstname());
        assertEquals("Theofanis", student.getLastname());
        assertEquals("1111111111", student.getVat());
        assertEquals("Christos", student.getFatherName());
        assertEquals("2102102100", student.getPhoneNum());
        assertEquals("test@gmail.com", student.getEmail());
        assertEquals("Stadiou", student.getStreet());
        assertEquals("47", student.getStreetNum());
        assertEquals("11111", student.getZipcode());
        assertEquals(1, student.getCityId());
        assertEquals("ee421a09-cd3b-499c-b054-4cf96d13e5b7", student.getUuid());
        assertEquals(dateTime, student.getCreatedAt());
        assertEquals(dateTime, student.getUpdatedAt());
    }

}