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

@MappedSuperclass
public class Employee {

    @Column(uuid = "uuid")
    private String uuid; 

    @id
    @Column(id = "id")
    private int id;  

    @Column(companyName = "companyName")
    public static String companyName = "ideas2IT";

    @Column(employeeId = "employeeId")
    private String employeeId;

    @Column(employeeName = "name")
    private String employeeName;

    @Column(employeeDateOfBirth = "dateOfBirth")
    private String employeeDateOfBirth;

    @Column(employeeDesignation = "designation")
    private String employeeDesignation;

    @Column(employeeMail = "mail")
    private String employeeMail; 

    @Column(employeeMobileNumber = "mobileNumber")   
    private String employeeMobileNumber;

    @Column(currentAddress = "address")
    private String currentAddress;

    @Column(aadharCardNumber = "aadharCardNumber")
    private String aadharCardNumber;

    @Column(panCardNumber = "panCardNumber")
    private String panCardNumber;

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getUuid() {

	return uuid;
    }

    public void setEmployeeId(String employeeId) {

	this.employeeId = employeeId;
    }

    public String getEmployeeId() {

	return employeeId;
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
}
