package com.ideas2it.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.util.Designation;
import com.ideas2it.util.UserDate;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;


/**
 * A DTO for the {@link Trainee} entity
 */
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRequestDto implements Serializable {

    @NotEmpty(message = "UUID is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uuid;
    @NotEmpty(message = "Employee name is mandatory")
    @Pattern(regexp = "(([A-Z][a-z]{2,20})(( )([A-Z][a-z]*)))+", message =
            "Name must be in only alphabets and first name, second name required")
    private String employeeName;
    @NotEmpty(message = "date is must")
    @UserDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String employeeDateOfBirth;
    @NotEmpty(message = "Employee designation is mandatory")
    @Designation
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isActive = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int traineeId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Trainer> trainers;
}