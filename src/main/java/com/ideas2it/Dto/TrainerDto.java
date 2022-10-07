package com.ideas2it.Dto;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.util.ValidEmail;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Trainer} entity
 */
@Builder
@Getter
@Setter
public class TrainerDto implements Serializable {

    @NotEmpty(message = "UUID is mandatory")
    private String uuid;
    @NotEmpty(message = "Employee name is mandatory")
    @Pattern(regexp = "(([A-z][a-z]{1,20})(\\s)){2,}", message =
            "Name must be in only alphabets and first name, second name required")
    private String employeeName;
    @NotNull(message = "date of birth is mandatory")
    @Past(message = "invalid date")
    private LocalDate employeeDateOfBirth;
    private String employeeDesignation;
    @NotEmpty(message = "mail is mandatory")
    @ValidEmail(message = "invalid email")
    private String employeeMail;
    @NotEmpty(message = "mobile number is mandatory")
    @Pattern(regexp = "[+]91[6-9][0-9]{9}", message = "mobile number must be " +
            "contains country code(+91) and starts with 6-9 and contains 10 digit")
    private String employeeMobileNumber;
    @NotEmpty(message = "address is mandatory")
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
}