package gr.aueb.cf.schoolapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Person {
    private Long id;
    private String firstname;
    private String lastname;
    private String vat;
    private String fatherName;
    private String phoneNum;
    private String email;
    private String street;
    private String streetNum;
    private Integer cityId;
    private String zipcode;
    private String uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
