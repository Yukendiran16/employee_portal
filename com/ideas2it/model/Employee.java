

/**
* The Employee class is an POJO class
* The class implements an application that
* creates an properties of employee and then
* using getter and setters for getting and setting properties
*
* @author  Yukendiran K
* @version 1.0
* @since   2022-08-04 
*/


package com.ideas2it.model;

import javax.persistence.MappedSuperclass
import javax.persistence.Column;


@MappedSuperclass
public class Employee {

    @Column(name = "uuid")
    private String uuid;   

    @Column(name = "companyName")
    public static String companyName = "ideas2IT";

    @Column(name = "name")
    private String employeeName;

    @Column(name = "dateOfBirth")
    private String employeeDateOfBirth;

    @Column(name = "designation")
    private String employeeDesignation;

    @Column(name = "mail")
    private String employeeMail; 

    @Column(name = "mobileNumber")   
    private String employeeMobileNumber;

    @Column(name = "address")
    private String currentAddress;

    @Column(name = "aadharCardNumber")
    private String aadharCardNumber;

    @Column(name = "panCardNumber")
    private String panCardNumber;

    @Column(name = "employeeStatus")
    private boolean is_Active = false;

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getUuid() {

	return uuid;
    }

    public void setCompanyName(String companyName) {

	this.companyName = companyName;
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
    
    public void setEmployeeDateOfBirth(String employeeDateOfBirth) {

	this.employeeDateOfBirth = employeeDateOfBirth;
    }
    
    public String getEmployeeDateOfBirth() {
          
        return employeeDateOfBirth;
    }

    public void setEmployeeDesignation(String employeeDesignation) {

	this.employeeDesignation= employeeDesignation;
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

    public void setAadharCardNumber(String aadharCardNumber) {

	this.aadharCardNumber = aadharCardNumber;
    }

    public String getAadharCardNumber() {

	return aadharCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {

	this.panCardNumber = panCardNumber;
    }

    public String getPanCardNumber() {

	return panCardNumber;
    }
    
    public void setIs_Active(boolean is_Active) {

	this.is_Active = is_Active;
    }

    public boolean getIs_Active() {

	return is_Active;
    }
}
