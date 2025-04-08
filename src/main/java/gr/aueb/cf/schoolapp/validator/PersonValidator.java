package gr.aueb.cf.schoolapp.validator;

import gr.aueb.cf.schoolapp.dto.BasePersonDTO;

import java.util.HashMap;
import java.util.Map;

public class PersonValidator {
    private PersonValidator() {

    }

    public static <T extends BasePersonDTO> Map<String, String> validate(T dto) {
        Map<String, String> errors = new HashMap<>();

        String nameRegex = "^[A-Za-zΑ-Ωα-ωΆ-Ώά-ώ]+$";

        // Firstname validation
        if (dto.getFirstname().length() < 2 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "Firstname should contain 2 to 32 characters");
        }
        if (!dto.getFirstname().matches(nameRegex)) {
            errors.put("firstname", "Firstname should contain only Greek or English letters");
        }

        // Lastname validation
        if (dto.getLastname().length() < 2 || dto.getLastname().length() > 32) {
            errors.put("lastname", "Lastname should contain 2 to 32 characters");
        }
        if (!dto.getLastname().matches(nameRegex)) {
            errors.put("lastname", "Lastname should contain only Greek or English letters");
        }

        // VAT validation
        if (!dto.getVat().matches("^\\d{9}$")) {
            errors.put("vat", "VAT should contain exactly 9 digits");
        }

        // Father name validation
        if (dto.getFatherName().length() < 2 || dto.getFatherName().length() > 32) {
            errors.put("fatherName", "Father name should contain 2 to 32 characters");
        }
        if (!dto.getFatherName().matches(nameRegex)) {
            errors.put("fatherName", "Father name should contain only Greek or English letters");
        }

        // Phone number validation
        if (!dto.getPhoneNum().matches("^\\d{10,}$")) {
            errors.put("phoneNumber", "Phone number should contain at least 10 digits");
        }

        // Email validation
        if (!dto.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            errors.put("email", "Invalid email format");
        }

        // Street validation
        if (dto.getStreet().length() < 2 || dto.getStreet().length() > 32) {
            errors.put("street", "Street name should contain 2 to 32 characters");
        }

        // Street number validation
        if (!dto.getStreetNum().matches("^\\d{1,4}$")) {
            errors.put("streetNumber", "Street number should contain 1 to 4 digits");
        }

        // Zipcode validation
        if (!dto.getZipcode().matches("^[0-9]{5,}$")) {
            errors.put("zipCode", "Zip code should contain at least five digits");
        }

        return errors;
    }
}
