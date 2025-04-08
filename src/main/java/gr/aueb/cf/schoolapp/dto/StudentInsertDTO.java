package gr.aueb.cf.schoolapp.dto;

public class StudentInsertDTO extends BasePersonDTO {
    public StudentInsertDTO() {

    }

    public StudentInsertDTO(String firstname, String lastname, String vat, String fatherName, String phoneNum, String email, String street, String streetNum, Integer cityId, String zipcode) {
        super(firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, cityId, zipcode);
    }
}
