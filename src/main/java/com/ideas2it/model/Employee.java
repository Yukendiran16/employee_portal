package com.ideas2it.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
@MappedSuperclass
public class Employee {

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "company_name")
    private static final String companyName = "ideas2IT";

    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "((([a-zA-Z_]{3,20})(\\s)){2,})*",message = "Name shall consist of 2-30 English letters " + "and First name Second name is required")
    @Column(name = "name")
    private String employeeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@NotEmpty(message = "Date of birth is required.")
    @Column(name = "date_of_birth")
    private LocalDate employeeDateOfBirth;

    //@Pattern(regexp = "([a-z\\sA-Z_]{3,50})*",message = "Designation shall consist of 2-30 English letters")
    //@NotEmpty(message = "Designation is required.")
    @Column(name = "designation")
    private String employeeDesignation;

    //@Email(message = "Enter valid mail")
    //@NotEmpty(message = "Mail id is required.")
    @Column(name = "mail")
    private String employeeMail;

    //@Pattern(regexp = "([6-9][0-9]{9})*", message = "Enter valid mobile number it may consist of (0-9)")
    //@NotEmpty(message = "Mobile number is required.")
    @Column(name = "mobile_number")
    private String employeeMobileNumber;

    @Column(name = "address")
    //@NotEmpty(message = "Address is required.")
    private String currentAddress;

    //@Pattern(regexp = "([0-9]{12})*", message = "Enter valid Aadhaar card number")
    //@NotEmpty(message = "Aadhaar card number is required.")
    @Column(name = "aadhaar_card_number")
    private String aadhaarCardNumber;

    //@Pattern(regexp = "([A-Z0-9]{10})*", message = "Enter valid pan card number")
    //@NotEmpty(message = "Pan card number is required.")
    @Column(name = "pan_card_number")
    private String panCardNumber;

    @Column(name = "employee_status")
    private boolean isActive = false;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeDateOfBirth(LocalDate employeeDateOfBirth) {
            this.employeeDateOfBirth = employeeDateOfBirth;
    }
    public LocalDate getEmployeeDateOfBirth() {
        return employeeDateOfBirth;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeMail(String employeeMail) {
        this.employeeMail = employeeMail;
    }

    public String getEmployeeMail() {
        return employeeMail;
    }

    public void setEmployeeMobileNumber(String employeeMobileNumber) {
        this.employeeMobileNumber = employeeMobileNumber;
    }

    public String getEmployeeMobileNumber() {
        return employeeMobileNumber;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setAadhaarCardNumber(String aadhaarCardNumber) {
        this.aadhaarCardNumber = aadhaarCardNumber;
    }

    public String getAadhaarCardNumber() {
        return aadhaarCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {
        this.panCardNumber = panCardNumber;
    }

    public String getPanCardNumber() {
        return panCardNumber;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

}
