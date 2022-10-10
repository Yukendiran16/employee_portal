package com.ideas2it.Dto;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.util.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Trainer} entity
 */
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TrainerDto implements Serializable {

    @NotNull(message = "UUID is mandatory")
    private String uuid;
    @NotEmpty(message = "Employee name is mandatory")
    @Pattern(regexp = "(([A-z][a-z]{1,20})(\\s)){2,}", message =
            "Name must be in only alphabets and first name, second name required")
    private String employeeName;
    @Past(message = "invalid date")
    private LocalDate employeeDateOfBirth;
    @NotEmpty(message = "designation is mandatory")
    private String employeeDesignation;
    @NotEmpty(message = "mail is mandatory")
    @Email(message = "invalid email")
    private String employeeMail;
    @NotEmpty(message = "mobile number is mandatory")
    @Pattern(regexp = "[+]91[6-9][0-9]{9}", message = "mobile number must be " +
            "contains country code(+91) and starts with 6-9 and contains 10 digit")
    private String employeeMobileNumber;
    @NotEmpty(message = "address is mandatory")
    @Pattern(regexp = "(([0-9/A-Za-z-]+)?)(,?[a-z\\sA-Z]+)((,([a-z\\sA-Z]+)+){2,3})(-[0-9]{6})(,([a-z\\sA-Z]+)+)",
            message = "in valid address something missing (house number, street, village or town, city, pinCode, state)")
    private String currentAddress;
    @NotEmpty(message = "aadhaar card number is mandatory")
    @Pattern(regexp = "[0-9]{12}", message = "aadhaar card number must be contains 12 digit")
    private String aadhaarCardNumber;
    @NotEmpty(message = "pan card number is mandatory")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]", message = "pan card number contains " +
            "first 5 uppercase letters after 4 numbers after one uppercase letters total 10")
    private String panCardNumber;
    private boolean isActive = false;
    private int trainerId;
    private Set<Trainee> trainees;

    public void setIsActive(boolean active) {
        isActive = active;
    }
}