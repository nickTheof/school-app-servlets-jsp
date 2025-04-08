package gr.aueb.cf.schoolapp.model;

import java.time.LocalDateTime;


public class Teacher extends Person{
    public Teacher() {
    }

    public Teacher(Long id, String firstname, String lastname, String vat, String fatherName, String phoneNum, String email, String street, String streetNum, Integer cityId, String zipcode, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, cityId, zipcode, uuid, createdAt, updatedAt);
    }


}
