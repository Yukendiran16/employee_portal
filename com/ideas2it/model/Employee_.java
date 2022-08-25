package com.ideas2it.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, String> employeeName;
	public static volatile SingularAttribute<Employee, String> employeeDateOfBirth;
	public static volatile SingularAttribute<Employee, String> panCardNumber;
	public static volatile SingularAttribute<Employee, Boolean> is_Active;
	public static volatile SingularAttribute<Employee, String> employeeMobileNumber;
	public static volatile SingularAttribute<Employee, String> aadharCardNumber;
	public static volatile SingularAttribute<Employee, String> employeeId;
	public static volatile SingularAttribute<Employee, Integer> id;
	public static volatile SingularAttribute<Employee, String> uuid;
	public static volatile SingularAttribute<Employee, String> employeeMail;
	public static volatile SingularAttribute<Employee, String> employeeDesignation;
	public static volatile SingularAttribute<Employee, String> currentAddress;

	public static final String EMPLOYEE_NAME = "employeeName";
	public static final String EMPLOYEE_DATE_OF_BIRTH = "employeeDateOfBirth";
	public static final String PAN_CARD_NUMBER = "panCardNumber";
	public static final String IS__ACTIVE = "is_Active";
	public static final String EMPLOYEE_MOBILE_NUMBER = "employeeMobileNumber";
	public static final String AADHAR_CARD_NUMBER = "aadharCardNumber";
	public static final String EMPLOYEE_ID = "employeeId";
	public static final String ID = "id";
	public static final String UUID = "uuid";
	public static final String EMPLOYEE_MAIL = "employeeMail";
	public static final String EMPLOYEE_DESIGNATION = "employeeDesignation";
	public static final String CURRENT_ADDRESS = "currentAddress";

}

