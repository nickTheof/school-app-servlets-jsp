package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    @Test
    void defaultConstructorGettersAndSetters(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Teacher teacher = new Teacher();

        assertNull(teacher.getId());
        assertNull(teacher.getFirstname());
        assertNull(teacher.getLastname());
        assertNull(teacher.getVat());
        assertNull(teacher.getFatherName());
        assertNull(teacher.getPhoneNum());
        assertNull(teacher.getEmail());
        assertNull(teacher.getStreet());
        assertNull(teacher.getStreetNum());
        assertNull(teacher.getZipcode());
        assertNull(teacher.getCityId());
        assertNull(teacher.getUuid());
        assertNull(teacher.getUpdatedAt());
        assertNull(teacher.getCreatedAt());

        teacher.setId(1L);
        teacher.setFirstname("Nikolas");
        teacher.setLastname("Theofanis");
        teacher.setVat("1111111111");
        teacher.setFatherName("Christos");
        teacher.setPhoneNum("2102102100");
        teacher.setEmail("test@gmail.com");
        teacher.setStreet("Stadiou");
        teacher.setStreetNum("47");
        teacher.setZipcode("11111");
        teacher.setCityId(1);
        teacher.setUuid("ee421a09-cd3b-499c-b054-4cf96d13e5b7");
        LocalDateTime dateTime = LocalDateTime.parse("26/02/2025 14:48", formatter);
        teacher.setCreatedAt(dateTime);
        teacher.setUpdatedAt(dateTime);

        assertEquals(Long.valueOf(1), teacher.getId());
        assertEquals("Nikolas", teacher.getFirstname());
        assertEquals("Theofanis", teacher.getLastname());
        assertEquals("1111111111", teacher.getVat());
        assertEquals("Christos", teacher.getFatherName());
        assertEquals("2102102100", teacher.getPhoneNum());
        assertEquals("test@gmail.com", teacher.getEmail());
        assertEquals("Stadiou", teacher.getStreet());
        assertEquals("47", teacher.getStreetNum());
        assertEquals("11111", teacher.getZipcode());
        assertEquals(1, teacher.getCityId());
        assertEquals("ee421a09-cd3b-499c-b054-4cf96d13e5b7", teacher.getUuid());
        assertEquals(dateTime, teacher.getCreatedAt());
        assertEquals(dateTime, teacher.getUpdatedAt());

    }

    @Test
    void overloadedConstructor() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("26/02/2025 14:48", formatter);

        Teacher teacher = new Teacher(1L, "Nikolas", "Theofanis", "1111111111", "Christos", "2102102100", "test@gmail.com",
                "Stadiou", "47", 1, "11111", "ee421a09-cd3b-499c-b054-4cf96d13e5b7", dateTime, dateTime);

        assertEquals(Long.valueOf(1), teacher.getId());
        assertEquals("Nikolas", teacher.getFirstname());
        assertEquals("Theofanis", teacher.getLastname());
        assertEquals("1111111111", teacher.getVat());
        assertEquals("Christos", teacher.getFatherName());
        assertEquals("2102102100", teacher.getPhoneNum());
        assertEquals("test@gmail.com", teacher.getEmail());
        assertEquals("Stadiou", teacher.getStreet());
        assertEquals("47", teacher.getStreetNum());
        assertEquals("11111", teacher.getZipcode());
        assertEquals(1, teacher.getCityId());
        assertEquals("ee421a09-cd3b-499c-b054-4cf96d13e5b7", teacher.getUuid());
        assertEquals(dateTime, teacher.getCreatedAt());
        assertEquals(dateTime, teacher.getUpdatedAt());
    }
}