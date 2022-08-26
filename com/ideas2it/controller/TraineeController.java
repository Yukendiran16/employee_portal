package com.ideas2it.controller;

import java.util.Scanner;
import java.util.UUID;
import java.util.InputMismatchException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException; 

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.controller.EmployeeInformation;


public class TraineeController {

    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private static EmployeeInformation employeeInformation = new EmployeeInformation();

    private static Logger logger = LoggerFactory.getLogger(TraineeController.class);
    

    public static void traineeMenu()  {
 
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {

            try {
                logger.info("\nEnter 1 for Create trainee details in database\n"+ 
			    "Enter 2 for Read   trainee details in database\n"+ 
			    "Enter 3 for Update trainee details in database\n"+ 
	                    "Enter 4 for Delete trainee details in database\n"+
                            "Enter 5 for Assign trainer for trainees\n"+
                            "Enter 6 for Read association between trainer to trainee\n"+
                            "Enter 7 for Exit  \n\n\n"+"----------------------------------------------");
                int userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:	   
     	                createTraineeData();
                        isContinue = true;
                        break;
                    case 2:
                        readTraineeData();
                        isContinue = true;
                        break;
                    case 3:
                        updateTraineeData();
                        isContinue = true;
                        break; 
                    case 4:
                        removeTraineeData();
                        isContinue = true;
                        break;
                    case 5:
                        //assignTraineeForTrainers();
                        isContinue = true;
                        break;
                    case 6:
                        //readTrainersOfGivenTrainee();
                        isContinue = true;
                        break;
                    case 7:
                        isContinue = false;
                        break;
                    default:
                        break;
                }
                logger.info("\n----------------------------------------------");
                logger.info("***************** THANK YOU ******************");
                logger.info("----------------------------------------------");    
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data : " + e);
            } catch (HibernateException e) {
                logger.error("\ninvalid data : " + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
        }
    } 

    /**
     * method used to create trainee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void createTraineeData() throws InputMismatchException, SQLException, HibernateException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String position = "";
        while (isContinue) {

            try {
                UUID uuId = UUID.randomUUID();
                String temp = uuId.toString();
                String[] arr = temp.split("-");
                String uuid = arr[0]+arr[3];
       	    	logger.info("\nEnter Trainee Details :");
                Trainee trainee = employeeInformation.getInformationFromTrainee(uuid);
                String message = employeeService.addTrainee(trainee);                     
                logger.info("" + message);
                logger.info("\n----------------------------------------------");
                isContinue = false;
 
            } catch (InputMismatchException e) {
                logger.error("\ninvalid data" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info(" \nIf you want to continue\n" + "1.yes\n" + "2.no");
            String addAnotherTraineeData = scanner.next();
            logger.info("----------------------------------------------");
            isContinue = !(addAnotherTraineeData.equals("2"));                   
        }
    }

    /**
     * method used to read trainee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void readTraineeData() throws InputMismatchException, SQLException, HibernateException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        int employeeId;
        while (isContinue) { 

            try {     
	        logger.info("\n1.whole data\n" + "2.particular data");
                String readTraineeData = scanner.next();
                logger.info("\n----------------------------------------------"); 
                if (readTraineeData.equals(1)) {
                    List<Trainee> trainees = employeeService.getTraineesData();
                    if (trainees == null) {
                        logger.info("\nNo data found");
                    } else {
                        trainees.forEach(trainee -> logger.info("EmployeeId          : "+trainee.getId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                                                 	         "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+
                                          	                 "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+
                                                                 "----------------------------------------------"));
                        isContinue = false;                 
                    } 
                } else {
                    logger.info("\nEnter EmployeeId :");
                    boolean isValidId = true;
                    while (isValidId) {
                        employeeId = scanner.nextInt();
                        Trainee trainee = employeeService.searchTraineeData(employeeId);
                        if (trainee == null) {
                            logger.info("\nNo data found\n" + "Enter valid Id");
                            isValidId = true; 
                        } else {
                            logger.info("Trainee Detail :"+"\n"+
                                        "EmployeeId          : "+trainee.getId()+"\n"+"EmployeeName        : "+trainee.getEmployeeName()+"\n"+
                               	        "EmployeeDesignation : "+trainee.getEmployeeDesignation()+"\n"+"EmployeeMail        : "+trainee.getEmployeeMail()+"\n"+
                                        "EmployeeMobileNumber: "+trainee.getEmployeeMobileNumber()+"\n"+ "CurrentAddress      : "+trainee.getCurrentAddress()+"\n"+
                                        "----------------------------------------------");
                            isValidId = false;
                        }
                    }
                } 
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String choiceForReadAnotherInformation = scanner.next();          
            isContinue = !(choiceForReadAnotherInformation.equals("2")); 
        }
    }

    /**
     * method used to update employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void updateTraineeData() throws InputMismatchException, SQLException, HibernateException {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        int employeeId;
        String updateForTraineeData = "";
        while (isContinue) {
           
            try {
                logger.info("\nEnter TraineeId :");
                boolean isValidId = true;
                while (isValidId) {
                    employeeId = scanner.nextInt();                
                    Trainee trainee = employeeService.searchTraineeData(employeeId);
                    if (trainee == null) {                           
                        logger.info("\nno data found\n" + "Enter valid Id");
                        isValidId = true; 
                    } else {
                        employeeInformation.getInformationForUpdateTrainee(trainee);
                        String message = employeeService.updateTraineeData(trainee);
                        logger.info("" + message);
                        isValidId = false;
                    }
                }
            } catch (InputMismatchException e) {
        	logger.error("\nException occured" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
       	    } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info("\nif you want to continue\n" + "1.yes\n" + "2.no"); 
            String updateForAnotherTraineeData = scanner.next();
            isContinue = !(updateForAnotherTraineeData.equals("2"));
        }
    }

    /**
     * method used to delete employee profile  
     * @param {@link Scanner} scanner
     * @param {@link boolean} isContinue
     * @return {@link void} returns nothing
     */
    public static void removeTraineeData() throws InputMismatchException, SQLException, HibernateException {
        
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        String removeForTraineeData = "";
        while (isContinue) {

            try {
                int employeeId; 
                boolean isValidId = true;      
                while (isValidId) {
           
                    logger.info("\nEnter EmployeeId :");
                    employeeId = scanner.nextInt();
                    String message = employeeService.deleteTraineeData(employeeId);
                    logger.info("" + message);
                    isValidId = false;
                }
            } catch (InputMismatchException e) {
                logger.error("\nException occured" + e);
            } catch (HibernateException e) {
                logger.error("\nException occured" + e);
            } catch (SQLException e) {
                logger.error("",e);
            }
            logger.info("\nIf you want to continue : \n" + "1.yes\n" + "2.no" );
            String removeMoreDataTrainee = scanner.next();
            logger.info("\n----------------------------------------------");
            isContinue = !(removeMoreDataTrainee.equals("2")); 
        }
    }
}
