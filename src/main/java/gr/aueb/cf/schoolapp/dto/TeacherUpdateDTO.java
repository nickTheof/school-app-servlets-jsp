package gr.aueb.cf.schoolapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherUpdateDTO extends BaseTeacherDTO {
    private Long id;

    public TeacherUpdateDTO() {

    }

    public TeacherUpdateDTO(Long id, String firstname, String lastname, String vat, String fatherName, String phoneNum, String email, String street, String streetNum, Integer cityId, String zipcode) {
        super(firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, cityId, zipcode);
        this.id = id;
    }


}
