package gr.aueb.cf.schoolapp.model;


import java.time.LocalDateTime;

public class Student extends Person{
    public Student() {
    }

    public Student(Long id, String firstname, String lastname, String vat, String fatherName, String phoneNum, String email, String street, String streetNum, Integer cityId, String zipcode, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, cityId, zipcode, uuid, createdAt, updatedAt);
    }


}
