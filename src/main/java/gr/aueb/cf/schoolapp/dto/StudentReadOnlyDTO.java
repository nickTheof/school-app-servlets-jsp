package gr.aueb.cf.schoolapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentReadOnlyDTO extends BasePersonDTO {
    private Long id;
    private String uuid;

    public StudentReadOnlyDTO() {

    }

    public StudentReadOnlyDTO(Long id, String uuid, String firstname, String lastname, String vat, String fatherName, String phoneNum, String email, String street, String streetNum, Integer cityId, String zipcode) {
        super(firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, cityId, zipcode);
        this.id = id;
        this.uuid = uuid;
    }
}
