package com.ideas2it.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * <h1> Employee </h1>
 * <p>
 * The Employee class is an POJO class
 * The class implements an application that
 * creates an properties of employee and then
 * using getter and setters for getting and setting properties
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@MappedSuperclass
public class Employee {

    @Column(name = "uuid",nullable = false)
    private String uuid;

    @Column(name = "company_name")
    private final String companyName = "ideas2IT";

    @Column(name = "name",nullable = false)
    private String employeeName;

    @Column(name = "date_of_birth",nullable = false)
    private LocalDate employeeDateOfBirth;

    @Column(name = "designation",nullable = false)
    private String employeeDesignation;

    @Column(name = "mail",unique = true,nullable = false)
    private String employeeMail;

    @Column(name = "mobile_number",unique = true,nullable = false)
    private String employeeMobileNumber;

    @Column(name = "address",nullable = false)
    private String currentAddress;

    @Column(name = "aadhaar_card_number",unique = true,nullable = false)
    private String aadhaarCardNumber;

    @Column(name = "pan_card_number",unique = true,nullable = false)
    private String panCardNumber;

    @Column(name = "employee_status")
    private boolean isActive = Boolean.FALSE;
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
