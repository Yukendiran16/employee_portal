package com.ideas2it.model;

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
@MappedSuperclass
public class Employee {

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "company_name")
    private final String companyName = "ideas2IT";

    @Column(name = "name")
    private String employeeName;

    @Column(name = "date_of_birth")
    private LocalDate employeeDateOfBirth;

    @Column(name = "designation")
    private String employeeDesignation;

    @Column(name = "mail")
    private String employeeMail;

    @Column(name = "mobile_number")
    private String employeeMobileNumber;

    @Column(name = "address")
    private String currentAddress;

    @Column(name = "aadhaar_card_number")
    private String aadhaarCardNumber;

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
