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

public class Employee {

    private static uuidIsKey; 
    private static id;  
    public static String companyName = "ideas2IT";
    private String employeeId;
    private String employeeName;
    private String employeeDateOfBirth;
    private String employeeDesignation;
    private String employeeMail;    
    private String employeeMobileNumber;
    private String currentAddress;
    private String aadharCardNumber;
    private String panCardNumber;

    public void setUuidIsKey(String uuidIsKey) {

	this.uuidIsKey = uuidIsKey;
    }

    public String getUuidIsKey() {

	return uuidIsKey;
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
